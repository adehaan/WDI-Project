package xmlFormatters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import genralClasses.ESRB;

public class EsrbXMLFormatter extends XMLFormatter<ESRB> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("ESRBs");
	}

	@Override
	public Element createElementFromRecord(ESRB record, Document doc) {
		Element esrb = doc.createElement("ESRB");

		if(record.getRatings()!=null) {
			esrb.appendChild(createTextElement("Ratings", record.getRatings(), doc));
		}
		return esrb;
	}

}
