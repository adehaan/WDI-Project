package blockingGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.CSVCorrespondenceFormatter;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import genralClasses.VideoGames;

public class VideoGamesBlockingKeyByYearRangeGenerator extends
RecordBlockingKeyGenerator<VideoGames, Attribute>{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void generateBlockingKeys(VideoGames record,
			Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, VideoGames>> resultCollector) {

		String blockingKeyValue = "";

		// Year Range
		blockingKeyValue += Integer.toString((int) record.getDate()/5);
		resultCollector.next(new Pair<>(blockingKeyValue, record));
	}
}
