package xmlFormatters;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import genralClasses.VideoGames;

public class GamesXMLFormatter extends XMLFormatter<VideoGames> {

	static String rootPublishers = "Publishers";
	static String childPublishers = "Publisher";
	static String rootGenres = "Genres";
	static String childGenres = "Genre";
	static String rootPlatforms = "Platforms";
	static String childPlatforms = "Platform";
	static String rootCountries = "Countries_of_Origin";
	static String childCountries = "Country_of_Origin";

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("Games");
	}

	@Override
	public Element createElementFromRecord(VideoGames record, Document doc) {
		Element game = doc.createElement("Game");

		game.appendChild(createTextElement("id", record.getIdentifier(), doc));

		game.appendChild(createTextElementWithProvenance("Title", record.getTitle(),
				record.getMergedAttributeProvenance(VideoGames.TITLE), doc));

		String date = String.format("%d", record.getDate());
		game.appendChild(createTextElementWithProvenance("Year", date,
				record.getMergedAttributeProvenance(VideoGames.DATE), doc));
		game.appendChild(createElement(record, doc, rootPublishers, childPublishers, VideoGames.PUBLISHERS,
				record.getPublishers()));
		game.appendChild(
				createElement(record, doc, rootPlatforms, childPlatforms, VideoGames.PLATFORMS, record.getPlatforms()));
		game.appendChild(createElement(record, doc, rootGenres, childGenres, VideoGames.GENRES, record.getGenres()));

		game.appendChild(createTextElement("SaleEU", String.format("%.1f", record.getSalesEU()), doc));
		if (record.getCountries() != null && record.getCountries().size() > 0)
			game.appendChild(createElementWithoutProvenance(doc, rootCountries, childCountries, record.getCountries()));

		return game;
	}

	protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

	protected Element createElement(VideoGames record, Document doc, String rootElementName, String childElementName,
			Attribute provenanceName, List<String> methodcall) {
		Element elementRoot = doc.createElement(rootElementName);
		elementRoot.setAttribute("provenance", record.getMergedAttributeProvenance(provenanceName));

		if (methodcall != null) {
			for (String attribute : methodcall) {
				elementRoot.appendChild(createTextElementWithProvenance(childElementName, attribute,
						record.getMergedAttributeProvenance(provenanceName), doc));
			}
		}

		return elementRoot;
	}

	protected Element createElementWithoutProvenance(Document doc, String rootElementName, String childElementName,
			List<String> methodcall) {
		Element elementRoot = doc.createElement(rootElementName);
		if (methodcall != null) {
			for (String attribute : methodcall) {
				elementRoot.appendChild(createTextElement(childElementName, attribute, doc));
			}
		}

		return elementRoot;
	}

	protected Element createElement2(VideoGames record, Document doc, String rootElementName, String childElementName,
			Attribute provenanceName) {
		Element elementRoot = doc.createElement(rootElementName);
		elementRoot.setAttribute("provenance", record.getMergedAttributeProvenance(provenanceName));

		for (String platform : record.getPlatforms()) {
			elementRoot.appendChild(createTextElementWithProvenance(childElementName, platform,
					record.getMergedAttributeProvenance(provenanceName), doc));
		}

		return elementRoot;
	}

	protected Element createElement3(VideoGames record, Document doc, String rootElementName, String childElementName,
			Attribute provenanceName) {
		Element elementRoot = doc.createElement(rootElementName);
		elementRoot.setAttribute("provenance", record.getMergedAttributeProvenance(provenanceName));

		for (String genre : record.getGenres()) {
			elementRoot.appendChild(createTextElementWithProvenance(childElementName, genre,
					record.getMergedAttributeProvenance(provenanceName), doc));
		}

		return elementRoot;
	}
}
