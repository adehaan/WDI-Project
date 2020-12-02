package fusers;

import java.util.HashSet;
import java.util.Set;

//import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Movie;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import genralClasses.VideoGames;


public class PlatformsEvaluationRule extends EvaluationRule<VideoGames, Attribute> {

	@Override
	public boolean isEqual(VideoGames record1, VideoGames record2, Attribute schemaElement) {
		
		Set<String> platforms1 = new HashSet<>();
		Set<String> platforms2 = new HashSet<>();
		
		platforms1 = (Set) record1.getGenres();
		platforms2 = (Set) record2.getGenres();
		
		return platforms1.containsAll(platforms2) && platforms2.containsAll(platforms1);
	}

	@Override
	public boolean isEqual(VideoGames record1, VideoGames record2, Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}

