package fusers;

import java.util.List;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import genralClasses.VideoGames;


public class PublishersFuserFavourSource extends AttributeValueFuser<List<String>, VideoGames, Attribute> {

	public PublishersFuserFavourSource() {
		super(new FavourSources<List<String>, VideoGames, Attribute>());
	}

	@Override
	public boolean hasValue(VideoGames record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(VideoGames.PUBLISHERS);
	}

	@Override
	public List<String> getValue(VideoGames record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getPublishers();
	}

	@Override
	public void fuse(RecordGroup<VideoGames, Attribute> group, 
			VideoGames fusedRecord, 
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, 
			Attribute schemaElement) {
		
		FusedValue<List<String>, VideoGames, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setPublishers(fused.getValue());
		fusedRecord.setAttributeProvenance(VideoGames.PUBLISHERS, fused.getOriginalIds());
	}
}
