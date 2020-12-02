
package fusers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import genralClasses.VideoGames;


public class PublishersEvaluationRule extends EvaluationRule<VideoGames, Attribute> {

	@Override
	public boolean isEqual(VideoGames record1, VideoGames record2, Attribute schemaElement) {
				
		List<String> help1 = record1.getPublishers();
		Set<String> publishers1 = new HashSet<>(help1);
		
		List<String> help2 = record2.getPublishers();
		Set<String> publishers2 = new HashSet<>(help2);
		
	    
		return publishers1.containsAll(publishers2) && publishers2.containsAll(publishers1);
	}

	@Override
	public boolean isEqual(VideoGames record1, VideoGames record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}

