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
		matchingRule.activateDebugReport("data/output/wiki_debugResultsMatchingRule.csv", -1, gsTest);

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

		StandardRecordBlocker<VideoGames, Attribute> blocker = new StandardRecordBlocker<VideoGames, Attribute>(
				new VideoGamesBlockingKeyByTitleYearGenerator());

		// NoBlocker blocker = new NoBlocker();

		blocker.setMeasureBlockSizes(true);

		// Write debug results to file:
		blocker.collectBlockSizeData("data/output/wiki_debugResultsBlocking.csv", -1);

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
		System.out.println(String.format("Number of Predicted: %.4f", perfTest.getNumberOfPredicted()));
		System.out.println(
				String.format("Number of correctly Predicted: %.4f", perfTest.getNumberOfCorrectlyPredicted()));
		System.out.println(String.format("Number of correct Total: %.4f", perfTest.getNumberOfCorrectTotal()));
	}

	public static void rawgtosales() throws Exception {

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
		gsTest.loadFromCSVFile(new File("../Goldstandard_sales_rawg.csv"));

		// create a matching rule
		LinearCombinationMatchingRule<VideoGames, Attribute> matchingRule = new LinearCombinationMatchingRule<>(0.3);
		matchingRule.activateDebugReport("data/output/rawg_debugResultsMatchingRule.csv", -1, gsTest);

		// add comparators
		matchingRule.addComparator(new GamesYearComparator2Years(), 0.4);
		// matchingRule.addComparator(new GamesTitleComparatorEqual(), 0.7);
		// matchingRule.addComparator(new GamesTitleComparatorJaccard(), 0.4);
		matchingRule.addComparator(new GamesTitleComparatorLevenshtein(), 0.6);
		// matchingRule.addComparator(new GamesPlatformComparatorLevenshtein(), 0.1);
		// matchingRule.addComparator(new GamesGenresComparatorLevenshtein(), 0.1);
		// matchingRule.addComparator(new GamesGenresComparatorJaroWinkler(), 0.3);
		// matchingRule.addComparator(new GamesPlatformComparatorJaroWinkler(), 0.3);
		// matchingRule.addComparator(new GamesYearComparatorEqual(), 0.3);

		StandardRecordBlocker<VideoGames, Attribute> blocker = new StandardRecordBlocker<VideoGames, Attribute>(
				new VideoGamesBlockingKeyByTitleYearGenerator());

		// NoBlocker blocker = new NoBlocker();

		blocker.setMeasureBlockSizes(true);

		// Write debug results to file:
		blocker.collectBlockSizeData("data/output/rawg_debugResultsBlocking.csv", -1);

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
		System.out.println(String.format("Number of Predicted: %.4f", perfTest.getNumberOfPredicted()));
		System.out.println(
				String.format("Number of correctly Predicted: %.4f", perfTest.getNumberOfCorrectlyPredicted()));
		System.out.println(String.format("Number of correct Total: %.4f", perfTest.getNumberOfCorrectTotal()));
	}

	public static void wikitosales_rulelearner(String matchingType) throws Exception {

		// TODO Auto-generated method stub
		System.out.println("*\n*\tLoading datasets WIKI \n*");
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
		String options[] = null;
		String modelType = null;

//		if(matchingType=="dt") {
//			options = new String[] {"-C", "0.25"};
//			modelType = "J48";
//		}
//		if(matchingType=="sl") {
//			options = new String[] {"-S"};
//			modelType = "SimpleLogistic";
//		}

		// options = new String[] {"-Q","-W", "weka.classifiers.trees.RandomForest"};
		// modelType = "AdaBoostM1";

		options = new String[] { "-print", "-I", "100", "-S", "2", "-depth", "5" };
		modelType = "RandomForest";

//		options = new String[] { 
//				"-P", "weka.classifiers.trees.RandomForest", 
//				"-P", "weka.classifiers.meta.AdaBoostM1",
//				"-P", "weka.classifiers.functions.SimpleLogistic",
//				"-X", "10", 
//				"-B", "weka.classifiers.trees.RandomForest", "-depth", "5",
//				"-B", "weka.classifiers.meta.AdaBoostM1", "-Q","-W", "weka.classifiers.trees.J48",
//				"-B", "weka.classifiers.functions.SimpleLogistic"};
//		modelType = "Vote";
		// options = new String[] {"-S"};
		// modelType = "SimpleLogistic";

		WekaMatchingRule<VideoGames, Attribute> matchingRule = new WekaMatchingRule<>(0.9, modelType, options);

		if (matchingType == "dt") {
			matchingRule.activateDebugReport("data/output/wiki_dt_debugResultsMatchingRule.csv", -1, gsTest);
		}
		if (matchingType == "sl") {
			matchingRule.activateDebugReport("data/output/wiki_sl_debugResultsMatchingRule.csv", -1, gsTest);
		}

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

		if (matchingType == "dt") {
			blocker.collectBlockSizeData("data/output/wiki_dt_debugResultsBlocking.csv", -1);
		}
		if (matchingType == "sl") {
			blocker.collectBlockSizeData("data/output/wiki_sl_debugResultsBlocking.csv", -1);
		}

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
		if (matchingType == "dt") {
			new CSVCorrespondenceFormatter().writeCSV(new File("data/output/Wiki_2_Sales_dt_correspondences.csv"),
					correspondences);
		}
		if (matchingType == "sl") {
			new CSVCorrespondenceFormatter().writeCSV(new File("data/output/Wiki_2_Sales_sl_correspondences.csv"),
					correspondences);
		}

		System.out.println("*\n*\tEvaluating result\n*");
		// evaluate your result
		MatchingEvaluator<VideoGames, Attribute> evaluator = new MatchingEvaluator<VideoGames, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

		// print the evaluation result
		System.out.println("Videogames Sales <-> Wiki");
		System.out.println(String.format("Precision: %.4f", perfTest.getPrecision()));
		System.out.println(String.format("Recall: %.4f", perfTest.getRecall()));
		System.out.println(String.format("F1: %.4f", perfTest.getF1()));
		System.out.println(String.format("Number of Predicted: %.4f", perfTest.getNumberOfPredicted()));
		System.out.println(
				String.format("Number of correctly Predicted: %.4f", perfTest.getNumberOfCorrectlyPredicted()));
		System.out.println(String.format("Number of correct Total: %.4f", perfTest.getNumberOfCorrectTotal()));
	}

	// RAWG to SALES
	public static void rawgtosales_rulelearner(String matchingType) throws Exception {

		System.out.println("*\n*\tLoading datasets rawg\n*");
		HashedDataSet<VideoGames, Attribute> dataRawg = new HashedDataSet<>();
		HashedDataSet<VideoGames, Attribute> dataSales = new HashedDataSet<>();

		// TODO: Import right rawg file here
		new GamesXMLReader().loadFromXML(new File("../../Datasets/RAWG_xml_1.xml"), "/Games/Game", dataRawg);
		new GamesXMLReader().loadFromXML(new File("data/input/sales_target.xml"), "/Games/Game", dataSales);

		// load the gold standard (test set)
		System.out.println("*\n*\tLoading gold standard\n*");
		MatchingGoldStandard gsTest = new MatchingGoldStandard();
		gsTest.loadFromCSVFile(new File("../Test_sales_rawg3.csv"));

		// load training set
		MatchingGoldStandard gsTrain = new MatchingGoldStandard();
		gsTrain.loadFromCSVFile(new File("../Train_sales_rawg3.csv"));

		// create a matching rule
		String options[] = null;
		String modelType = null;

//		if (matchingType == "dt") {
//			options = new String[] { "-U" };
//			modelType = "J48";
//		}
//		if (matchingType == "sl") {
//			options = new String[] { "-S" };
//			modelType = "SimpleLogistic";
//		}

		options = new String[] { "" };
		modelType = "SimpleLogistic";
		//options = new String[] {"-Q","-W", "weka.classifiers.functions.SimpleLogistic"};
		//modelType = "AdaBoostM1";
		
		//options = new String[] { "-print", "-I", "100", "-S", "2", "-depth", "5" };
		//modelType = "RandomForest";

		WekaMatchingRule<VideoGames, Attribute> matchingRule = new WekaMatchingRule<>(0.70, modelType, options);

		if (matchingType == "dt") {
			matchingRule.activateDebugReport("data/output/rawg_dt_debugResultsMatchingRule.csv", -1, gsTest);
		}
		if (matchingType == "sl") {
			matchingRule.activateDebugReport("data/output/rawg_sl_debugResultsMatchingRule.csv", -1, gsTest);
		}

		// add comparators
		matchingRule.addComparator(new GamesYearComparatorEqual());
		matchingRule.addComparator(new GamesYearComparator2Years());
		matchingRule.addComparator(new GamesYearComparator5Years());
		// matchingRule.addComparator(new GamesTitleComparatorEqual());
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

		if (matchingType == "dt") {
			blocker.collectBlockSizeData("data/output/rawg_dt_debugResultsBlocking.csv", -1);
		}
		if (matchingType == "sl") {
			blocker.collectBlockSizeData("data/output/rawg_sl_debugResultsBlocking.csv", -1);
		}

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
		if (matchingType == "dt") {
			new CSVCorrespondenceFormatter().writeCSV(new File("data/output/Rawg_2_Sales_dt_correspondences.csv"),
					correspondences);
		}
		if (matchingType == "sl") {
			new CSVCorrespondenceFormatter().writeCSV(new File("data/output/Rawg_2_Sales_sl_correspondences.csv"),
					correspondences);
		}

		System.out.println("*\n*\tEvaluating result\n*");
		// evaluate your result
		MatchingEvaluator<VideoGames, Attribute> evaluator = new MatchingEvaluator<VideoGames, Attribute>();
		Performance perfTest = evaluator.evaluateMatching(correspondences, gsTest);

		// print the evaluation result
		System.out.println("Videogames Sales <-> Rawg");
		System.out.println(String.format("Precision: %.4f", perfTest.getPrecision()));
		System.out.println(String.format("Recall: %.4f", perfTest.getRecall()));
		System.out.println(String.format("F1: %.4f", perfTest.getF1()));
		System.out.println(String.format("Number of Predicted: %.4f", perfTest.getNumberOfPredicted()));
		System.out.println(
				String.format("Number of correctly Predicted: %.4f", perfTest.getNumberOfCorrectlyPredicted()));
		System.out.println(String.format("Number of correct Total: %.4f", perfTest.getNumberOfCorrectTotal()));
	}

	// Run Both Matchings
	public static void main(String[] args) throws Exception {
		String matchingType;
		// wikitosales();
		// rawgtosales();
		// wikitosales_rulelearner(matchingType = "dt"); // dt = Decision Tree/ sl =
		// simpleLogression/ has to be filled
		rawgtosales_rulelearner(matchingType = "dt"); // dt = Decision Tree/ sl =
		// simpleLogression/ has to be filled
	}

}
