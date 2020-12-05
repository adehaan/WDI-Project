package similarityMeasures;

import java.util.List;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinSimilarity;
import genralClasses.VideoGames;

public class GamesPublisherComparatorLevenshtein implements Comparator<VideoGames, Attribute> {
	private static final long serialVersionUID = 1L;
	private LevenshteinSimilarity sim = new LevenshteinSimilarity();

	private ComparatorLogger comparisonLog;

	@Override
	public double compare(VideoGames record1, VideoGames record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		List<String> s1 = record1.getPublishers();
		List<String> s2 = record2.getPublishers();
		double similarity = 0.0;
		if (s1 != null && !s1.isEmpty()) {
			String sales_Publisher = s1.get(0);

			if (s2 != null && !s2.isEmpty()) {
				if (!s2.get(0).equals("") || !s2.get(0).isEmpty()) {
					for (String publisher : s2) {
						similarity += sim.calculate(sales_Publisher, publisher);

						if (this.comparisonLog != null) {
							this.comparisonLog.setComparatorName(getClass().getName());

							this.comparisonLog.setRecord1Value(sales_Publisher);
							this.comparisonLog.setRecord2Value(publisher);

							this.comparisonLog.setSimilarity(Double.toString(similarity));
						}
					}
				}
			}
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
