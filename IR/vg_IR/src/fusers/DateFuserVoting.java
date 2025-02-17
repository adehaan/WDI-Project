/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package fusers;

import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.Voting;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import genralClasses.VideoGames;


public class DateFuserVoting extends AttributeValueFuser<Integer, VideoGames, Attribute> {

	public DateFuserVoting() {
		super(new Voting<Integer, VideoGames, Attribute>());
	}
	
	@Override
	public boolean hasValue(VideoGames record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(VideoGames.DATE);
	}
	
	@Override
	public Integer getValue(VideoGames record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getDate();
	}

	@Override
	public void fuse(RecordGroup<VideoGames, Attribute> group, VideoGames fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<Integer, VideoGames, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		
		Integer result_year = fused.getValue();
		if(fused.getValue()==null) {
			result_year=0;
		}
		
		fusedRecord.setDate(result_year);
		fusedRecord.setAttributeProvenance(VideoGames.DATE, fused.getOriginalIds());
	}

}
