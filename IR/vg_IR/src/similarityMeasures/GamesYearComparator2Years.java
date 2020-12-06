package similarityMeasures;

import java.time.LocalDateTime;

import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.date.YearSimilarity;
import genralClasses.VideoGames;

public class GamesYearComparator2Years implements Comparator<VideoGames, Attribute> {
	private static final long serialVersionUID = 1L;
	private YearSimilarity sim = new YearSimilarity(2);

	private ComparatorLogger comparisonLog;

	@Override
	public double compare(VideoGames record1, VideoGames record2,
			Correspondence<Attribute, Matchable> schemaCorrespondences) {
		
		LocalDateTime rec1Date = LocalDateTime.of(record1.getDate(), 1,1,1,1,1,1);
		LocalDateTime rec2Date = LocalDateTime.of(record2.getDate(), 1,1,1,1,1,1);
		
		double similarity = sim.calculate(rec1Date, rec2Date);

		if (this.comparisonLog != null) {
			this.comparisonLog.setComparatorName(getClass().getName());

			this.comparisonLog.setRecord1Value(rec1Date.toString());
			this.comparisonLog.setRecord2Value(rec2Date.toString());

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
