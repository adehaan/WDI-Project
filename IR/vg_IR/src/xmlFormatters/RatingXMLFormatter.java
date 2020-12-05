package xmlFormatters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import genralClasses.Rating;


public class RatingXMLFormatter extends XMLFormatter<Rating> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Ratings");
	}

	@Override
	public Element createElementFromRecord(Rating record, Document doc) {
		Element rating = doc.createElement("Rating");
		rating.appendChild(createTextElement("metacritic", String.format("%.2f", record.getMetacritic()), doc));		
		rating.appendChild(createTextElement("rawg_rating", String.format("%.2f", record.getRawgRating()), doc));
			
		return rating;
	}

}
