package vG;

import java.io.File;

import org.slf4j.Logger;

import blockingGenerator.VideoGameBlockingKeyByTitleGenerator;
import blockingGenerator.VideoGamesBlockingKeyByTitleYearGenerator;
import blockingGenerator.VideoGamesBlockingKeyByYearGenerator;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEngine;
import de.uni_mannheim.informatik.dws.winter.matching.MatchingEvaluator;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.MaximumBipartiteMatchingAlgorithm;
import de.uni_mannheim.informatik.dws.winter.matching.algorithms.RuleLearner;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.NoBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.SortedNeighbourhoodBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.StandardRecordBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.blockers.ValueBasedBlocker;
import de.uni_mannheim.informatik.dws.winter.matching.rules.LinearCombinationMatchingRule;
import de.uni_mannheim.informatik.dws.winter.matching.rules.WekaMatchingRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.MatchingGoldStandard;
import de.uni_mannheim.informatik.dws.winter.model.Performance;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import genralClasses.VideoGames;
import similarityMeasures.GamesGenresComparatorJaroWinkler;
import similarityMeasures.GamesGenresComparatorLevenshtein;
import similarityMeasures.GamesPlatformComparatorJaroWinkler;
import similarityMeasures.GamesPlatformComparatorLevenshtein;
import similarityMeasures.GamesPublisherComparatorLevenshtein;
import similarityMeasures.GamesTitleComparatorEqual;
import similarityMeasures.GamesTitleComparatorJaccard;
import similarityMeasures.GamesTitleComparatorLevenshtein;
import similarityMeasures.GamesYearComparator2Years;
import similarityMeasures.GamesYearComparator5Years;
import similarityMeasures.GamesYearComparatorEqual;
import xmlReaders.GamesXMLReader;

public class Main {

	private static final Logger logger = WinterLogManager.activateLogger("trace");

	public static void wikitosales() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("*\n*\tLoading datasets wiki\n*");
		HashedDataSet<VideoGames, Attribute> dataWiki = new HashedDataSet<>();
		HashedDataSet<VideoGames, Attribute> dataSales = new HashedDataSet<>();
		new GamesXMLReader().loadFromXML(new File("data/input/target_games_Wiki.xml"), "/Games/Game", dataWiki);
		new GamesXMLReader().loadFromXML(new File("data/input/sales_target.xml"), "/Games/Game", dataSales);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File("../Goldstandard_sales_wiki.csv"));

		// create a matching rule
		LinearCombinationMatchingRule<VideoGames, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.3);
		matchingRule.activateDebugReport("data/output/debugResultsMatchingRule.csv", 1000000, gsTest);

		// add comparators
		matchingRule.addComparator(new GamesYearComparator2Years(), 0.4);
		// matchingRule.addComparator(new GamesTitleComparatorEqual(), 0.7);
		matchingRule.addComparator(new GamesTitleComparatorJaccard(), 0.4);
		matchingRule.addComparator(new GamesPublisherComparatorLevenshtein(), 0.1);
		// matchingRule.addComparator(new GamesTitleComparatorLevenshtein(), 0.7);
		matchingRule.addComparator(new GamesPlatformComparatorLevenshtein(), 0.05);
		matchingRule.addComparator(new GamesGenresComparatorLevenshtein(), 0.05);
		// matchingRule.addComparator(new GamesGenresComparatorJaroWinkler(), 0.3);
		// matchingRule.addComparator(new GamesPlatformComparatorJaroWinkler(), 0.3);
		// matchingRule.addComparator(new GamesYearComparatorEqual(), 0.3);

		// SortedNeighbourhoodBlocker<VideoGames, Attribute, Correspondence<Attribute,
		// Matchable >> blocker = new SortedNeighbourhoodBlocker<VideoGames, Attribute,
		// Correspondence<Attribute,
		// Matchable>>(new VideoGamesBlockingKeyByYearGenerator(),2);
		// create a blocker (blocking strategy)
		// SortedNeighbourhoodBlocker <VideoGames, Attribute, VideoGames> blocker = new
		// SortedNeighbourhoodBlocker<VideoGames, Attribute, VideoGames>(new
		// VideoGamesBlockingKeyByYearGenerator(),2);

		StandardRecordBlocker<VideoGames, Attribute> blocker = new StandardRecordBlocker<VideoGames, Attribute>(
				new VideoGamesBlockingKeyByTitleYearGenerator());

		// NoBlocker blocker = new NoBlocker();

		blocker.setMeasureBlockSizes(true);

		// Write debug results to file:
		blocker.collectBlockSizeData("data/output/wiki_debugResultsBlocking.csv", 3300);

		// Initialize Matching Engine
		MatchingEngine<VideoGames, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");

		Processable<Correspondence<VideoGames, Attribute>> correspondences = engine.runIdentityResolution(dataSales,
				dataWiki, null, matchingRule, blocker);

		// write the correspondences to the output file

		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/Wiki_2_Sales_correspondences.csv"),
				correspondences);

		System.out.println("*\n*\tEvaluating result\n*");
		// evaluate your result
		MatchingEvaluator<VideoGames, Attribute> evaluator = new MatchingEvaluator<VideoGames, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

		// print the evaluation result
		System.out.println("Videogames Sales <-> Wiki");
		System.out.println(String.format("Precision: %.4f", perfTest.getPrecision()));
		System.out.println(String.format("Recall: %.4f", perfTest.getRecall()));
		System.out.println(String.format("F1: %.4f", perfTest.getF1()));
	}

	public static void rawgtosales() throws Exception {

		// TODO Auto-generated method stub
		System.out.println("*\n*\tLoading datasets rawg\n*");
		HashedDataSet<VideoGames, Attribute> dataRawg = new HashedDataSet<>();
		HashedDataSet<VideoGames, Attribute> dataSales = new HashedDataSet<>();

		// TODO: Import all 11 files
		new GamesXMLReader().loadFromXML(new File("data/input/rawg_target1.xml"), "/Games/Game", dataRawg);
		new GamesXMLReader().loadFromXML(new File("data/input/sales_target.xml"), "/Games/Game", dataSales);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File("../Goldstandard_sales_rawg.csv"));

		// create a matching rule
		LinearCombinationMatchingRule<VideoGames, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.3);
		matchingRule.activateDebugReport("data/output/rawg_debugResultsMatchingRule.csv", 5000, gsTest);

		// add comparators
		matchingRule.addComparator(new GamesYearComparator2Years(), 0.4);
		// matchingRule.addComparator(new GamesTitleComparatorEqual(), 0.7);
		matchingRule.addComparator(new GamesTitleComparatorJaccard(), 0.4);
		// matchingRule.addComparator(new GamesTitleComparatorLevenshtein(), 0.7);
		matchingRule.addComparator(new GamesPlatformComparatorLevenshtein(), 0.1);
		matchingRule.addComparator(new GamesGenresComparatorLevenshtein(), 0.1);
		// matchingRule.addComparator(new GamesGenresComparatorJaroWinkler(), 0.3);
		// matchingRule.addComparator(new GamesPlatformComparatorJaroWinkler(), 0.3);
		// matchingRule.addComparator(new GamesYearComparatorEqual(), 0.3);

		StandardRecordBlocker<VideoGames, Attribute> blocker = new StandardRecordBlocker<VideoGames, Attribute>(
				new VideoGamesBlockingKeyByTitleYearGenerator());

		// NoBlocker blocker = new NoBlocker();

		blocker.setMeasureBlockSizes(true);

		// Write debug results to file:
		blocker.collectBlockSizeData("data/output/rawg_debugResultsBlocking.csv", 3300);

		// Initialize Matching Engine
		MatchingEngine<VideoGames, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");

		Processable<Correspondence<VideoGames, Attribute>> correspondences = engine.runIdentityResolution(dataSales,
				dataRawg, null, matchingRule, blocker);

		// write the correspondences to the output file

		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/Rawg_2_Sales_correspondences.csv"),
				correspondences);

		System.out.println("*\n*\tEvaluating result\n*");
		// evaluate your result
		MatchingEvaluator<VideoGames, Attribute> evaluator = new MatchingEvaluator<VideoGames, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

		// print the evaluation result
		System.out.println("Videogames Sales <-> Rawg");
		System.out.println(String.format("Precision: %.4f", perfTest.getPrecision()));
		System.out.println(String.format("Recall: %.4f", perfTest.getRecall()));
		System.out.println(String.format("F1: %.4f", perfTest.getF1()));
	}

	public static void Wikitosales_rulelearner() throws Exception {

		// TODO Auto-generated method stub
		System.out.println("*\n*\tLoading datasets rawg\n*");
		HashedDataSet<VideoGames, Attribute> dataWiki = new HashedDataSet<>();
		HashedDataSet<VideoGames, Attribute> dataSales = new HashedDataSet<>();

		// TODO: Import all 11 files
		new GamesXMLReader().loadFromXML(new File("data/input/target_games_Wiki.xml"), "/Games/Game", dataWiki);
		new GamesXMLReader().loadFromXML(new File("data/input/sales_target.xml"), "/Games/Game", dataSales);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File("../Test_sales_wiki.csv"));

		// load training set
		MatchingGoldStandard gsTrain = new MatchingGoldStandard();
		gsTrain.loadFromCSVFile(new File("../Train_sales_wiki.csv"));

		// create a matching rule
		String options[] = new String[] { "-S" };
		String modelType = "SimpleLogistic";
		WekaMatchingRule<VideoGames, Attribute> matchingRule = new WekaMatchingRule<>(0.95, modelType, options);
		// WekaMatchingRule<VideoGames, Attribute> matchingRule = new
		// WekaMatchingRule<>(0.6, modelType, options);
		matchingRule.activateDebugReport("data/output/rawg_debugResultsMatchingRule.csv", 5000, gsTest);

		// add comparators
		matchingRule.addComparator(new GamesYearComparatorEqual());
		matchingRule.addComparator(new GamesYearComparator2Years());
		matchingRule.addComparator(new GamesYearComparator5Years());
		matchingRule.addComparator(new GamesTitleComparatorEqual());
		matchingRule.addComparator(new GamesTitleComparatorJaccard());
		matchingRule.addComparator(new GamesTitleComparatorLevenshtein());
		matchingRule.addComparator(new GamesPlatformComparatorLevenshtein());
		matchingRule.addComparator(new GamesPlatformComparatorJaroWinkler());
		matchingRule.addComparator(new GamesGenresComparatorLevenshtein());
		matchingRule.addComparator(new GamesGenresComparatorJaroWinkler());

		// train model
		System.out.println("*\n*\tTraining the Model\n*");
		RuleLearner<VideoGames, Attribute> rulelearner = new RuleLearner<>();
		rulelearner.learnMatchingRule(dataSales, dataWiki, null, matchingRule, gsTrain);
		System.out.println(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));

		// Blocking Strategy
		StandardRecordBlocker<VideoGames, Attribute> blocker = new StandardRecordBlocker<VideoGames, Attribute>(
				new VideoGamesBlockingKeyByTitleYearGenerator());
		// NoBlocker blocker = new NoBlocker();
		blocker.setMeasureBlockSizes(true);
		blocker.collectBlockSizeData("data/output/wiki_debugResultsBlocking.csv", 3300);

		// Initialize Matching Engine
		MatchingEngine<VideoGames, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");

		Processable<Correspondence<VideoGames, Attribute>> correspondences = engine.runIdentityResolution(dataSales,
				dataWiki, null, matchingRule, blocker);

		MaximumBipartiteMatchingAlgorithm<VideoGames, Attribute> Weights = new MaximumBipartiteMatchingAlgorithm<>(
				correspondences);
		Weights.run();
		correspondences = Weights.getResult();

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/Wiki_2_Sales_correspondences.csv"),
				correspondences);

		System.out.println("*\n*\tEvaluating result\n*");
		// evaluate your result
		MatchingEvaluator<VideoGames, Attribute> evaluator = new MatchingEvaluator<VideoGames, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

		// print the evaluation result
		System.out.println("Videogames Sales <-> Wiki");
		System.out.println(String.format("Precision: %.4f", perfTest.getPrecision()));
		System.out.println(String.format("Recall: %.4f", perfTest.getRecall()));
		System.out.println(String.format("F1: %.4f", perfTest.getF1()));
	}

	//RAWG to SALES
	public static void rawgtosales_rulelearner() throws Exception {

		// TODO Auto-generated method stub
		System.out.println("*\n*\tLoading datasets rawg\n*");
		HashedDataSet<VideoGames, Attribute> dataRawg = new HashedDataSet<>();
		HashedDataSet<VideoGames, Attribute> dataSales = new HashedDataSet<>();

		// TODO: Import all 11 files
		new GamesXMLReader().loadFromXML(new File("../../Datasets/RAWG_xml_1.xml"), "/Games/Game", dataRawg);
		new GamesXMLReader().loadFromXML(new File("data/input/sales_target.xml"), "/Games/Game", dataSales);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File("../Test_sales_rawg.csv"));

		// load training set
		MatchingGoldStandard gsTrain = new MatchingGoldStandard();
		gsTrain.loadFromCSVFile(new File("../Train_sales_rawg.csv"));

		// create a matching rule
		String options[] = new String[] { "-S" };
		String modelType = "SimpleLogistic";
		WekaMatchingRule<VideoGames, Attribute> matchingRule = new WekaMatchingRule<>(0.95, modelType, options);
		// WekaMatchingRule<VideoGames, Attribute> matchingRule = new
		// WekaMatchingRule<>(0.6, modelType, options);
		matchingRule.activateDebugReport("data/output/rawg_debugResultsMatchingRule.csv", 5000, gsTest);

		// add comparators
		matchingRule.addComparator(new GamesYearComparatorEqual());
		matchingRule.addComparator(new GamesYearComparator2Years());
		matchingRule.addComparator(new GamesYearComparator5Years());
		matchingRule.addComparator(new GamesTitleComparatorEqual());
		matchingRule.addComparator(new GamesTitleComparatorJaccard());
		matchingRule.addComparator(new GamesTitleComparatorLevenshtein());
		matchingRule.addComparator(new GamesPlatformComparatorLevenshtein());
		matchingRule.addComparator(new GamesPlatformComparatorJaroWinkler());
		matchingRule.addComparator(new GamesGenresComparatorLevenshtein());
		matchingRule.addComparator(new GamesGenresComparatorJaroWinkler());

		// train model
		System.out.println("*\n*\tTraining the Model\n*");
		RuleLearner<VideoGames, Attribute> rulelearner = new RuleLearner<>();
		rulelearner.learnMatchingRule(dataSales, dataRawg, null, matchingRule, gsTrain);
		System.out.println(String.format("Matching rule is:\n%s", matchingRule.getModelDescription()));

		// Blocking Strategy
		StandardRecordBlocker<VideoGames, Attribute> blocker = new StandardRecordBlocker<VideoGames, Attribute>(
				new VideoGamesBlockingKeyByTitleYearGenerator());
		// NoBlocker blocker = new NoBlocker();
		blocker.setMeasureBlockSizes(true);
		blocker.collectBlockSizeData("data/output/rawg_debugResultsBlocking.csv", 3300);

		// Initialize Matching Engine
		MatchingEngine<VideoGames, Attribute> engine = new MatchingEngine<>();

		// Execute the matching
		System.out.println("*\n*\tRunning identity resolution\n*");

		Processable<Correspondence<VideoGames, Attribute>> correspondences = engine.runIdentityResolution(dataSales,
				dataRawg, null, matchingRule, blocker);

		MaximumBipartiteMatchingAlgorithm<VideoGames, Attribute> Weights = new MaximumBipartiteMatchingAlgorithm<>(
				correspondences);
		Weights.run();
		correspondences = Weights.getResult();

		// write the correspondences to the output file
		new CSVCorrespondenceFormatter().writeCSV(new File("data/output/Rawg_2_Sales_correspondences.csv"),
				correspondences);

		System.out.println("*\n*\tEvaluating result\n*");
		// evaluate your result
		MatchingEvaluator<VideoGames, Attribute> evaluator = new MatchingEvaluator<VideoGames, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

		// print the evaluation result
		System.out.println("Videogames Sales <-> Rawg");
		System.out.println(String.format("Precision: %.4f", perfTest.getPrecision()));
		System.out.println(String.format("Recall: %.4f", perfTest.getRecall()));
		System.out.println(String.format("F1: %.4f", perfTest.getF1()));
	}

	// Run Both Matchings
	public static void main(String[] args) throws Exception {
		// wikitosales(method = linear);
		// rawgtosales();
		 rawgtosales_rulelearner();
		// Wikitosales_rulelearner();
	}

}
