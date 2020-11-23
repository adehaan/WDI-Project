package similarityMeasures;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import genralClasses.VideoGames;

public class GamesTitleComparatorJaccard implements Comparator<VideoGames, Attribute> {
	private static final long serialVersionUID = 1L;
	TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	
	private ComparatorLogger comparisonLog;
	@Override
	public double compare(VideoGames record1, VideoGames record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		String s1 = record1.getTitle().toLowerCase();
		String s2 = record2.getTitle().toLowerCase();

		// calculate similarity
		double similarity = sim.calculate(s1, s2);

		// postprocessing
		int postSimilarity = 0;
		if (similarity <= 0.3) {
			postSimilarity = 0;
		}

		postSimilarity *= similarity;
		
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
		
			this.comparisonLog.setRecord1Value(s1);
			this.comparisonLog.setRecord2Value(s2);
    	
			this.comparisonLog.setSimilarity(Double.toString(similarity));
			this.comparisonLog.setPostprocessedSimilarity(Double.toString(postSimilarity));
		}
		return postSimilarity;
	}

}
