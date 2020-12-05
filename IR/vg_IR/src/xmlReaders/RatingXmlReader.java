package xmlReaders;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import genralClasses.Rating;

public class RatingXmlReader extends XMLMatchableReader<Rating, Attribute> {

	@Override
	public Rating createModelFromElement(Node node, String provenanceInfo) {
		// TODO Auto-generated method stub

		Rating rating = new Rating();

		double metacritic = getValueFromChildElement(node, "Metacrtic") != null
				? Double.parseDouble(getValueFromChildElement(node, "Metacrtic"))
				: 0.0;
		double rawg_rating = getValueFromChildElement(node, "RAWG_Rating") != null
				? Double.parseDouble(getValueFromChildElement(node, "RAWG_Rating"))
				: 0.0;

		rating.setMetacritic(metacritic);
		rating.setRawgRating(rawg_rating);

		return rating;
	}

}
