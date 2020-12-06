package vG;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.Logger;

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
import fusers.DateFuserFavourSource;
import fusers.DateFuserVoting;
import fusers.GameTitelFuserFavourSource;
import fusers.GameTitelFuserVoting;
import fusers.GenresEvaluationRule;
import fusers.GenresFuserIntersection;
import fusers.GenresFuserIntersectionK;
import fusers.GenresFuserUnion;
import fusers.PlatformsEvaluationRule;
import fusers.PlatformsFuserIntersection;
import fusers.PlatformsFuserIntersectionK;
import fusers.PlatformsFuserUnion;
import fusers.PublishersEvaluationRule;
import fusers.PublishersFuserFavourSource;
import fusers.PublishersFuserIntersection;
import fusers.PublishersFuserUnion;
import fusers.GameTitleEvaluationRule;
import genralClasses.VideoGames;
import xmlFormatters.GamesXMLFormatter;
import xmlReaders.GamesXMLReader;

public class DataFusion_Main {
	/*
	 * Logging Options: default: level INFO - console trace: level TRACE - console
	 * infoFile: level INFO - console/file traceFile: level TRACE - console/file
	 * 
	 * To set the log level to trace and write the log to winter.log and console,
	 * activate the "traceFile" logger as follows: private static final Logger
	 * logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final org.slf4j.Logger logger = WinterLogManager.activateLogger("traceFile");

	public static void main(String[] args) throws Exception {
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
		// TODO: Change to total rawg input
		FusibleDataSet<VideoGames, Attribute> ds3 = new FusibleHashedDataSet<>();
		// new GamesXMLReader().loadFromXML(new
		// File("../../Datasets/RAWG_xml_1/RAWG_xml_1.xml"), "/Games/Game", ds3);
		new GamesXMLReader().loadFromXML(new File("../../Datasets/RAWG_target_xml4.xml"), "/Games/Game", ds3);
		ds3.printDataSetDensityReport();

		// Maintain Provenance- Scores for Favour Source = highest Score is favoured
		// sales highest score, rawg second and wiki last, since it is the most unclean
		ds1.setScore(3.0);
		ds2.setScore(1.0);
		ds3.setScore(2.0);

		// load correspondences: have to be in the same order as in file -> sales, wiki
		// & sales, rawg -> ds1, ds2 & ds1, ds3
		System.out.println("*\n*\tLoading correspondences for fusion\n*");
		CorrespondenceSet<VideoGames, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("data/output/Wiki_2_Sales_correspondences.csv"), ds1, ds2);
		// System.out.println("*\n*\tCorrespondences for Sales-Wiki done\n*");
		correspondences.loadCorrespondences(new File("data/output/Rawg_2_Sales_correspondences.csv"), ds1, ds3);
		// System.out.println("*\n*\tCorrespondences for Sales-RAWG done\n*");

		// write group size distribution
		correspondences.printGroupSizeDistribution();

		// load the gold standard
		// TODO: Create file Goldstandard_Fusion.csv
		System.out.println("*\n*\tEvaluating results\n*");
		DataSet<VideoGames, Attribute> gs = new FusibleHashedDataSet<>();
		new GamesXMLReader().loadFromXML(new File("../goldstandard_fusion2.xml"), "/Games/Game", gs);

		for (VideoGames v : gs.get()) {
			System.out.println(String.format("gs: %s", v.getIdentifier()));
		}

		// define the fusion strategy
		DataFusionStrategy<VideoGames, Attribute> strategy = new DataFusionStrategy<>(new GamesXMLReader());
		// write debug results to file
		strategy.activateDebugReport("data/output/fusion_debugResultsDatafusion.csv", -1, gs);

		// Fusion Methods
		// For Titel = Voting & FavourSource -> FavourSource
		// strategy.addAttributeFuser(VideoGames.TITLE, new
		// GameTitelFuserFavourSource(), new GameTitleEvaluationRule());
		strategy.addAttributeFuser(VideoGames.TITLE, new GameTitelFuserVoting(), new GameTitleEvaluationRule());

		// For Date = Voting & FavourSource -> Voting
		strategy.addAttributeFuser(VideoGames.DATE, new DateFuserVoting(), new DateEvaluationRule());
		// strategy.addAttributeFuser(VideoGames.DATE, new DateFuserVoting(), new
		// DateEvaluationRule());

		// For Publisher = Union & Intersection -> Union
		strategy.addAttributeFuser(VideoGames.PUBLISHERS, new PublishersFuserUnion(), new PublishersEvaluationRule());
		// strategy.addAttributeFuser(VideoGames.PUBLISHERS, new
		// PublishersFuserIntersection(), new PublishersEvaluationRule());

		// For Platforms = Union & Intersection & IntersectionK (k=2) -> Union
		strategy.addAttributeFuser(VideoGames.PLATFORMS, new PlatformsFuserUnion(), new PlatformsEvaluationRule());
		// strategy.addAttributeFuser(VideoGames.PLATFORMS, new
		// PlatformsFuserIntersection(), new PlatformsEvaluationRule());
		// strategy.addAttributeFuser(VideoGames.PLATFORMS, new
		// PlatformsFuserIntersectionK(), new PlatformsEvaluationRule());

		// For Genres = Union & Intersection & IntersectionK (k=2) -> Union
		strategy.addAttributeFuser(VideoGames.GENRES, new GenresFuserUnion(), new GenresEvaluationRule());
		// strategy.addAttributeFuser(VideoGames.GENRES, new GenresFuserIntersection(),
		// new GenresEvaluationRule());
		// strategy.addAttributeFuser(VideoGames.GENRES, new GenresFuserIntersectionK(),
		// new GenresEvaluationRule());

		// create the fusion engine
		DataFusionEngine<VideoGames, Attribute> engine = new DataFusionEngine<>(strategy);

		// print consistency report
		engine.printClusterConsistencyReport(correspondences, null);

		// print record groups sorted by consistency
		engine.writeRecordGroupsByConsistency(new File("data/output/fusion_recordGroupConsistencies.csv"),
				correspondences, null);

		// run the fusion
		System.out.println("*\n*\tRunning data fusion\n*");
		FusibleDataSet<VideoGames, Attribute> fusedDataSet = engine.run(correspondences, null);

		// Preprocessing the fused part
		preProcessFusedData(fusedDataSet, ds1, ds2, ds3);

		// write the result
		new GamesXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);

		// evaluate
		DataFusionEvaluator<VideoGames, Attribute> evaluator = new DataFusionEvaluator<>(strategy,
				new RecordGroupFactory<VideoGames, Attribute>());

		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		System.out.println(String.format("Accuracy: %.2f", accuracy));
	}

	public static void preProcessFusedData(FusibleDataSet<VideoGames, Attribute> fusedDataSet,
			FusibleDataSet<VideoGames, Attribute> ds1, FusibleDataSet<VideoGames, Attribute> ds2,
			FusibleDataSet<VideoGames, Attribute> ds3) {
		List<String> lst = new ArrayList<>();
		Collection<VideoGames> col = fusedDataSet.get();
		Collection<VideoGames> col2 = fusedDataSet.get();
		System.out.println("Total number of records in our fused dataset = " + col.size());
		if (col != null) {
			for (VideoGames vg : col) {
				String ii = vg.getIdentifier();
				String tit = vg.getTitle();
				String[] arr = ii.split("\\+");
				String titleSalesds = "";
				String ident = "";
				for (VideoGames vgSales : col2) {
					titleSalesds = vgSales.getTitle();
					ident = vgSales.getIdentifier();
					if (tit.equals(titleSalesds)) {
						if (!ii.equals(ident)) {
							vg.setIdentifier(ident);
							vg.setTitle(vg.getTitle());
							lst.add(ident);
							continue;
						}
					}
				}
			}
			for (String id : lst) {
				fusedDataSet.removeRecord(id);
			}
			System.out.println("Total number of records in our fused dataset = " + fusedDataSet.size());
			for (VideoGames vg : col) {
				String ii = vg.getIdentifier();
				String[] arr = ii.split("\\+");
				String[] sArr = Arrays.stream(arr).filter(x -> x.contains("sales_")).toArray(String[]::new);
				String[] wArr = Arrays.stream(arr).filter(x -> x.contains("wiki_")).toArray(String[]::new);
				String[] rArr = Arrays.stream(arr).filter(x -> x.contains("rawg_")).toArray(String[]::new);
				String title = vg.getTitle();
				// for (String ar : arr) {
				if (sArr.length > 0) {
					for (VideoGames vgSales : ds1.get()) {
						if (title.toUpperCase().equals(vgSales.getTitle().toUpperCase())) {
							vg.setSalesEU(vgSales.getSalesEU());
							vg.setSalesJP(vgSales.getSalesJP());
							vg.setSalesNA(vgSales.getSalesNA());
							vg.setSalesOthers(vgSales.getSalesOthers());
							vg.setSalesGlobal(vgSales.getSalesGlobal());
						}
					}

				}
				if (wArr.length > 0) {
					for (String wVal : wArr) {
						VideoGames attWiki = ds2.getRecord(wVal);
						vg.setCountries(attWiki.getCountries());
						vg.setWebsite(attWiki.getWebsite());
						vg.setModes(attWiki.getModes());
						vg.setContributors(attWiki.getContributors());
						vg.setCERO(attWiki.getCERO());
						vg.setPEGI(attWiki.getPEGI());
						vg.setESRB(attWiki.getESRB());
						vg.setSequel(attWiki.getSequel());
						vg.setPrequel(attWiki.getPrequel());
					}
				}
				if (rArr.length > 0) {
					for (String rVal : rArr) {
						VideoGames attRawg = ds3.getRecord(rVal);
						vg.setTotalLength(attRawg.getTotalLength());
						vg.setStores(attRawg.getStores());
						vg.setRating(attRawg.getRating());
						vg.setRecommended(attRawg.getRecommended());
						vg.setTags(attRawg.getTags());
					}
				}
				// }
			}
//			
		}
	}
}
