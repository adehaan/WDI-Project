package blockingGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import genralClasses.VideoGames;

public class VideoGamesBlockingKeyByTitleYearGenerator extends
RecordBlockingKeyGenerator<VideoGames, Attribute>{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void generateBlockingKeys(VideoGames record,
			Processable<Correspondence<Attribute, Matchable>> correspondences,
			DataIterator<Pair<String, VideoGames>> resultCollector) {
		String[] tokens  = record.getTitle().toUpperCase().split(" ");

		
		// list tokins remove stopwords from 	
		String[] stopwords = {"AND", "THE",";",":",".","-",","};		
		ArrayList<String> wordlist = new ArrayList<>(Arrays.asList(tokens));
		
		
		for (int j = 0; j < stopwords.length; j++) {
            if (wordlist.contains(stopwords[j])) {
            	if (wordlist.size()>1) {
            		wordlist.remove(stopwords[j]);//remove it
            	}
            	
            }
        }
		
		String blockingKeyValue = "";

		// take first 3 characters of first word of titel
		// Year in Groups of 5 years
		int year_help = record.getDate()/5;
		blockingKeyValue += Integer.toString(year_help);
		blockingKeyValue += wordlist.get(0).substring(0, Math.min(3,wordlist.get(0).length())).toUpperCase();
					
		resultCollector.next(new Pair<>(blockingKeyValue, record));
	}
}
