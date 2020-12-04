package xmlFormatters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import genralClasses.Tags;

public class TagXMLFormatter extends XMLFormatter<Tags> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Tags");
	}

	@Override
	public Element createElementFromRecord(Tags record, Document doc) {
		Element tags = doc.createElement("Tag");
		
		if(record.getName()!=null) {
			tags.appendChild(createTextElement("name", record.getName(), doc));
		}
		
		tags.appendChild(createTextElement("counts", String.format("%d", record.getCounts()), doc));
			
		return tags;
	}

}
