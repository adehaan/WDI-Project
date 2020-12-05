package xmlFormatters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import genralClasses.Recommended;


public class RecommendedXMLFormatter extends XMLFormatter<Recommended> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Recommendeds");
	}

	@Override
	public Element createElementFromRecord(Recommended record, Document doc) {
		Element recommended = doc.createElement("Recommended");
		
		if(record.getPlatformName()!=null ){
			if(!record.getPlatformName().isBlank()){
				if(!record.getPlatformName().isEmpty()){
					recommended.appendChild(createTextElement("platform_name", record.getPlatformName(), doc));
				}
			}
			
		}
		
		if(record.getRequirements()!=null) {
			recommended.appendChild(createTextElement("platform_requirements", record.getRequirements(), doc));
		}
		
		return recommended;
	}

}