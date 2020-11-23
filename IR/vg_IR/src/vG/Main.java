package vG;

import java.io.File;

import org.slf4j.Logger;

import blockingGenerator.VideoGameBlockingKeyByTitleGenerator;

import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import genralClasses.VideoGames;
import similarityMeasures.GamesTitleComparatorJaccard;
import similarityMeasures.GamesYearComparator5Years;
import xmlReaders.GamesXMLReader;

public class Main {

	private static final Logger logger = WinterLogManager.activateLogger("default");

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("*\n*\tLoading datasets\n*");
		HashedDataSet<VideoGames, Attribute> dataAcademyAwards = new HashedDataSet<>();
		new GamesXMLReader().loadFromXML(new File("target_games_Wiki.xml"), "/Games/Game", dataAcademyAwards);
		System.out.println(dataAcademyAwards);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File("../Goldstandard_sales_wiki.csv"));

		// create a matching rule
		LinearCombinationMatchingRule<VideoGames, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.5);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000, gsTest);

		// add comparators
		matchingRule.addComparator(new GamesYearComparator5Years(), 0.5);
		matchingRule.addComparator(new GamesTitleComparatorJaccard(), 0.5);

		// create a blocker (blocking strategy)
		StandardRecordBlocker<VideoGames, Attribute> blocker = new StandardRecordBlocker<VideoGames, Attribute>(
				new VideoGameBlockingKeyByTitleGenerator());
//				NoBlocker<Movie, Attribute> blocker = new NoBlocker<>();
//				SortedNeighbourhoodBlocker<Movie, Attribute, Attribute> blocker = new SortedNeighbourhoodBlocker<>(new MovieBlockingKeyByTitleGenerator(), 1);
		blocker.setMeasureBlockSizes(true);
		// Write debug results to file:
		blocker.collectBlockSizeData("data/output/debugResultsBlocking.csv", 100);

		// Initialize Matching Engine
		MatchingEngine<VideoGames, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");
		
		//Processable<Correspondence<VideoGames, Attribute>> correspondences = engine
		//		.runIdentityResolution(dataAcademyAwards, dataActors, null, matchingRule, blocker);

		// Create a top-1 global matching
//				  correspondences = engine.getTopKInstanceCorrespondences(correspondences, 1, 0.0);

//				 Alternative: Create a maximum-weight, bipartite matching
//				 MaximumBipartiteMatchingAlgorithm<Movie,Attribute> maxWeight = new MaximumBipartiteMatchingAlgorithm<>(correspondences);
//				 maxWeight.run();
//				 correspondences = maxWeight.getResult();

		// write the correspondences to the output file
		
		//new CSVCorrespondenceFormatter().writeCSV(new File("data/output/academy_awards_2_actors_correspondences.csv"),
		//		correspondences);

		System.out.println("*\n*\tEvaluating result\n*");
		// evaluate your result
		MatchingEvaluator<VideoGames, Attribute> evaluator = new MatchingEvaluator<VideoGames, Attribute>();
		//Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

		// print the evaluation result
		System.out.println("Academy Awards <-> Actors");
		//System.out.println(String.format("Precision: %.4f", perfTest.getPrecision()));
		//System.out.println(String.format("Recall: %.4f", perfTest.getRecall()));
		//System.out.println(String.format("F1: %.4f", perfTest.getF1()));
	}

}
