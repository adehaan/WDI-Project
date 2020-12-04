package xmlFormatters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import genralClasses.CERO;

public class CeroXMLFormatter extends XMLFormatter<CERO> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("CEROs");
	}

	@Override
	public Element createElementFromRecord(CERO record, Document doc) {
		Element actor = doc.createElement("CERO");

		if(record.getRatings()!=null) {
			actor.appendChild(createTextElement("Rating", record.getRatings(), doc));
		}

		return actor;
	}

}
