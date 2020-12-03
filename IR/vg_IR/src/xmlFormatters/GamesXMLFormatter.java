package xmlFormatters;

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
		
		game.appendChild(createElement1(record, doc, rootPublishers, childPublishers, VideoGames.PUBLISHERS));
		game.appendChild(createElement2(record, doc, rootPlatforms, childPlatforms, VideoGames.PLATFORMS));
		game.appendChild(createElement3(record, doc, rootGenres, childGenres, VideoGames.GENRES));
		
		return game;
	}

	protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

	protected Element createElement1(VideoGames record, Document doc, String rootElementName, String childElementName, Attribute provenanceName) {
		Element elementRoot = doc.createElement(rootElementName);
		elementRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(provenanceName));

		for (String publisher : record.getPublishers()) {
			elementRoot.appendChild(createTextElementWithProvenance(childElementName, publisher,
					record.getMergedAttributeProvenance(provenanceName), doc));
		}
		
		return elementRoot;
	}
	
	protected Element createElement2(VideoGames record, Document doc, String rootElementName, String childElementName, Attribute provenanceName) {
		Element elementRoot = doc.createElement(rootElementName);
		elementRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(provenanceName));
		
		for (String platform : record.getPlatforms()) {
			elementRoot.appendChild(createTextElementWithProvenance(childElementName, platform,
					record.getMergedAttributeProvenance(provenanceName), doc));
		}
	

		return elementRoot;
	}
	
	protected Element createElement3(VideoGames record, Document doc, String rootElementName, String childElementName, Attribute provenanceName) {
		Element elementRoot = doc.createElement(rootElementName);
		elementRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(provenanceName));
		
		for (String genre : record.getGenres()) {
			elementRoot.appendChild(createTextElementWithProvenance(childElementName, genre,
					record.getMergedAttributeProvenance(provenanceName), doc));
		}

		return elementRoot;
	}
}
