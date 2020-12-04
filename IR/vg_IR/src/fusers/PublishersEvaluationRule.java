
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
		
		boolean r1 = false;
		boolean r2 = false;	
		
		List<String> help1 = record1.getPublishers();
		Set<String> publishers1 = new HashSet<>(help1);
		
		List<String> help2 = record2.getPublishers();
		Set<String> publishers2 = new HashSet<>(help2);
		
		for (String str : help2) {
			if (publishers2.contains(str)) {
				r2 = true;
				break;
			}
		}
		for (String str : help1) {
			if (publishers1.contains(str)) {
				r1 = true;
				break;
			}
		}
	    
		return r1 && r2;
	}

	@Override
	public boolean isEqual(VideoGames record1, VideoGames record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}

