package xmlFormatters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import genralClasses.PEGI;

public class PegiXMLFormatter extends XMLFormatter<PEGI> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("PEGIs");
	}

	@Override
	public Element createElementFromRecord(PEGI record, Document doc) {
		Element pegi = doc.createElement("PEGI");

		if(record.getRatings()!=null) {
			pegi.appendChild(createTextElement("Ratings", record.getRatings(), doc));
		}
		return pegi;
	}

}
