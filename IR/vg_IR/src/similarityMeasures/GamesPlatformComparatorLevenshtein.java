package similarityMeasures;

import java.util.List;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.EqualsSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinEditDistance;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;
import genralClasses.VideoGames;

public class GamesPlatformComparatorLevenshtein implements Comparator<VideoGames, Attribute>{
	private static final long serialVersionUID = 1L;
	private LevenshteinEditDistance sim = new LevenshteinEditDistance();
	
	private ComparatorLogger comparisonLog;
	@Override
	public double compare(VideoGames record1, VideoGames record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		
		List<String> si1 = record1.getPlatforms();
		List<String> si2 = record2.getPlatforms();
    	
		double similarity = 0.0;
		double savesime = 0.0;
		String l1 = si1.get(1);
		String l2 = null;
		//System.out.print(l1);
		
		// Compare element in sales data (l1) with all elements in wiki list
		if(si2 != null) {
			for (int j=0; j<si2.size();j++) {

				// gets every element j of the wiki list
				l2 = si2.get(j);
				
				// every word in element j
				String[] word = l2.split(" ");
				
				// calculate the similarity of every word in wiki element
				for (int k=0; k<word.length;k++) {
					savesime = sim.calculate(l1, word[k]);	
					savesime = ((double) savesime) / (Math.max(l1.length(), l2.length()));
					savesime = 1-savesime;
					
					// take highest similarity found in all wiki elements and words
					if(savesime > similarity) {
						similarity  = savesime;
					}
				}
				
			}
		}
		
		
		// saves the lists to a String, so that we can compare them in the logger
		String s1 ="";
		String s2 ="";
		
		if(si1 != null) {
			s1 = String.join(" ", si1);
		}
		if(si2!=null) {
			s2 = String.join(" ", si2);
		}
		
	    	
		if(this.comparisonLog != null){
			this.comparisonLog.setComparatorName(getClass().getName());
		
			this.comparisonLog.setRecord1Value(s1);
			this.comparisonLog.setRecord2Value(s2);
    	
			this.comparisonLog.setSimilarity(Double.toString(similarity));
		}
		return similarity;
	}
	
	@Override
	public ComparatorLogger getComparisonLog() {
		return this.comparisonLog;
	}

	@Override
	public void setComparisonLog(ComparatorLogger comparatorLog) {
		this.comparisonLog = comparatorLog;
	}
}
