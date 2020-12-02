package vG;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.apache.logging.log4j.Logger;

//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.ActorsEvaluationRule;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.DateEvaluationRule;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.DirectorEvaluationRule;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.TitleEvaluationRule;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.ActorsFuserUnion;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DateFuserFavourSource;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DateFuserVoting;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DirectorFuserLongestString;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.TitleFuserShortestString;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.FusibleMovieFactory;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.MovieXMLFormatter;
//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.MovieXMLReader;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import fusers.DateEvaluationRule;
import fusers.DateFuserVoting;
import fusers.GameTitelFuserFavourSource;
import fusers.GenresEvaluationRule;
import fusers.GenresFuserUnion;
import fusers.PlatformsEvaluationRule;
import fusers.PlatformsFuserUnion;
import fusers.PublishersEvaluationRule;
import fusers.PublishersFuserIntersection;
import fusers.TitleEvaluationRule;
import genralClasses.VideoGames;
import xmlFormatters.GamesXMLFormatter;
import xmlReaders.GamesXMLReader;

public class DataFusion_Main 
{
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final org.slf4j.Logger logger = WinterLogManager.activateLogger("traceFile");
	
	public static void main( String[] args ) throws Exception
    {
		// Load the Data into FusibleDataSet
		System.out.println("*\n*\tLoading datasets for fusion\n*");
				
		// Sales DataSet
		FusibleDataSet<VideoGames, Attribute> ds1 = new FusibleHashedDataSet<>();
		new GamesXMLReader().loadFromXML(new File("data/input/sales_target.xml"), "/Games/Game", ds1);
		ds1.printDataSetDensityReport();
		
		// Wiki DataSet
		FusibleDataSet<VideoGames, Attribute> ds2 = new FusibleHashedDataSet<>();
		new GamesXMLReader().loadFromXML(new File("data/input/target_games_Wiki.xml"), "/Games/Game", ds2);
		ds2.printDataSetDensityReport();
		
		// RAWG DataSet
		//TODO: Change to total rawg input
		FusibleDataSet<VideoGames, Attribute> ds3 = new FusibleHashedDataSet<>();
		new GamesXMLReader().loadFromXML(new File("data/input/rawg_target1.xml"), "/Games/Game", ds3);
		ds3.printDataSetDensityReport();

		// Maintain Provenance- Scores for Favour Source = highest Score is favoured
		// sales highest score, rawg second and wiki last, since it is the most unclean score
		ds1.setScore(3.0);
		ds2.setScore(1.0);
		ds3.setScore(2.0);
		

		// load correspondences: have to be in the same order as in file -> sales, wiki & sales, rawg -> ds1, ds2 & ds1, ds3
		System.out.println("*\n*\tLoading correspondences for fusion\n*");
		CorrespondenceSet<VideoGames, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("data/output/Wiki_2_Sales_correspondences.csv"),ds1, ds2);
		correspondences.loadCorrespondences(new File("data/output/RAWG_2_Sales_correspondences.csv"),ds1, ds3);

		// write group size distribution
		correspondences.printGroupSizeDistribution();

		
		// load the gold standard
		// TODO: Create file Goldstandard_Fusion.csv
		System.out.println("*\n*\tEvaluating results\n*");
		DataSet<VideoGames, Attribute> gs = new FusibleHashedDataSet<>();
		new GamesXMLReader().loadFromXML(new File("../goldstandard_fusion.xml"), "/Games/Game", gs);

		for(VideoGames v : gs.get()) {
			System.out.println(String.format("gs: %s", v.getIdentifier()));
		}

		// define the fusion strategy
		DataFusionStrategy<VideoGames, Attribute> strategy = new DataFusionStrategy<>(new GamesXMLReader());
		// write debug results to file
		strategy.activateDebugReport("data/output/fusion_debugResultsDatafusion.csv", -1, gs);
		
		// add attribute fusers
		// TODO: Create Fusers & Evaluationsrules - for each attribute add a Fuser & EvaluationRule
		
		// Title = favor sales source; longest/shortest string	
		strategy.addAttributeFuser(VideoGames.TITLE, new GameTitelFuserFavourSource(), new TitleEvaluationRule());
		// Date = OldestValue (lowest value)
		strategy.addAttributeFuser(VideoGames.DATE, new DateFuserVoting(), new DateEvaluationRule());
		// Publisher = intersection
		strategy.addAttributeFuser(VideoGames.PUBLISHERS, new PublishersFuserIntersection(), new PublishersEvaluationRule());
		// Genre & Platforms = Union
		strategy.addAttributeFuser(VideoGames.GENRES, new GenresFuserUnion(),new GenresEvaluationRule());
		strategy.addAttributeFuser(VideoGames.PLATFORMS, new PlatformsFuserUnion(),new PlatformsEvaluationRule());
		
		// create the fusion engine
		DataFusionEngine<VideoGames, Attribute> engine = new DataFusionEngine<>(strategy);

		// print consistency report
		engine.printClusterConsistencyReport(correspondences, null);
		
		// print record groups sorted by consistency
		engine.writeRecordGroupsByConsistency(new File("data/output/fusion_recordGroupConsistencies.csv"), correspondences, null);

		// run the fusion
		System.out.println("*\n*\tRunning data fusion\n*");
		FusibleDataSet<VideoGames, Attribute> fusedDataSet = engine.run(correspondences, null);

		// write the result
		// TODO: Create File GamesXMLFormatter
		new GamesXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);

		// evaluate
		DataFusionEvaluator<VideoGames, Attribute> evaluator = new DataFusionEvaluator<>(strategy, new RecordGroupFactory<VideoGames, Attribute>());
		
		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		System.out.println(String.format("Accuracy: %.2f", accuracy));
    }
}
