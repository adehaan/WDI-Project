package blockingGenerator;

import java.util.ArrayList;
import java.util.Arrays;

import de.uni_mannheim.informatik.dws.winter.matching.blockers.generators.RecordBlockingKeyGenerator;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.Pair;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.DataIterator;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import genralClasses.VideoGames;

public class VideoGameBlockingKeyByTitleGenerator extends
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
            	wordlist.remove(stopwords[j]);//remove it
            }
        }
		
		
		String blockingKeyValue = "";
		
		//for(int i = 0; i <= 2 && i < wordlist.size(); i++) {
			blockingKeyValue += wordlist.get(0).substring(0, Math.min(5,wordlist.get(0).length())).toUpperCase();
		//}

		resultCollector.next(new Pair<>(blockingKeyValue, record));
	}
}
