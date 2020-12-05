package xmlFormatters;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;
import genralClasses.CERO;
import genralClasses.ESRB;
import genralClasses.PEGI;
import genralClasses.Rating;
import genralClasses.Recommended;
import genralClasses.Tags;
import genralClasses.VideoGames;

public class GamesXMLFormatter extends XMLFormatter<VideoGames> {

	EsrbXMLFormatter esrbFormatter = new EsrbXMLFormatter();
	CeroXMLFormatter ceroFormatter = new CeroXMLFormatter();
	PegiXMLFormatter pegiFormatter = new PegiXMLFormatter();
	TagXMLFormatter tagFormatter = new TagXMLFormatter();
	RecommendedXMLFormatter recFormatter = new RecommendedXMLFormatter();
	RatingXMLFormatter ratingFormatter = new RatingXMLFormatter();

	static String rootPublishers = "Publishers";
	static String childPublishers = "Publisher";
	static String rootGenres = "Genres";
	static String childGenres = "Genre";
	static String rootPlatforms = "Platforms";
	static String childPlatforms = "Platform";
	static String rootCountries = "Countries_of_Origin";
	static String childCountries = "Country_of_Origin";
	static String rootModes = "Modes";
	static String childModes = "Mode";
	static String rootContributors = "Contributors";
	static String childContributors = "Contributor";

	static String rootStores = "Stores";
	static String childStores = "Store";

	static String rootCERO = "CEROs";
	static String childCERO = "CERO";

	static String rootPEGI = "PEGIs";
	static String childPEGI = "PEGI";

	static String rootESRB = "ESRBs";
	static String childESRB = "ESRB";
	
	static String rootTag = "Tags";
	static String childTag = "Tag";
	
	static String rootRating = "Ratings";
	static String childRating = "Rating";
	
	static String rootRecommended = "Recommendeds";
	static String childRecommended = "Recommended";

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
		if (record.getPublishers() != null && record.getPublishers().size() > 0)
			game.appendChild(createElement(record, doc, rootPublishers, childPublishers, VideoGames.PUBLISHERS,
					record.getPublishers()));
		if (record.getPlatforms() != null && record.getPlatforms().size() > 0)
			game.appendChild(createElement(record, doc, rootPlatforms, childPlatforms, VideoGames.PLATFORMS,
					record.getPlatforms()));

		if (record.getGenres() != null && record.getGenres().size() > 0)
			game.appendChild(
					createElement(record, doc, rootGenres, childGenres, VideoGames.GENRES, record.getGenres()));

		game.appendChild(createTextElement("SaleEU", String.format("%.2f", record.getSalesEU()), doc));
		game.appendChild(createTextElement("SaleJP", String.format("%.2f", record.getSalesJP()), doc));
		game.appendChild(createTextElement("SaleNA", String.format("%.2f", record.getSalesNA()), doc));
		game.appendChild(createTextElement("SaleOthers", String.format("%.2f", record.getSalesOthers()), doc));
		game.appendChild(createTextElement("SaleGlobal", String.format("%.2f", record.getSalesGlobal()), doc));

		
		if (record.getWebsite() != null) {
			if (!record.getWebsite().isBlank()) {
				if (!record.getWebsite().isEmpty()) {
					game.appendChild(createTextElement("Website", record.getWebsite(), doc));
				}
			}
		}
		if (record.getSequel() != null) {
			if (!record.getSequel().isBlank()) {
				if (!record.getSequel().isEmpty()) {
					game.appendChild(createTextElement("Sequel", record.getSequel(), doc));
				}
			}
		}
		if (record.getPrequel() != null) {
			if (!record.getPrequel().isBlank()) {
				if (!record.getPrequel().isEmpty()) {
					game.appendChild(createTextElement("Prequel", record.getPrequel(), doc));
				}
			}
		}

		if (record.getCountries() != null && record.getCountries().size() > 0)
			game.appendChild(createElementWithoutProvenance(doc, rootCountries, childCountries, record.getCountries()));
		if (record.getModes() != null && record.getModes().size() > 0)
			game.appendChild(createElementWithoutProvenance(doc, rootModes, childModes, record.getModes()));
		if (record.getContributors() != null && record.getContributors().size() > 0)
			game.appendChild(
					createElementWithoutProvenance(doc, rootContributors, childContributors, record.getContributors()));

		if (record.getStores() != null && record.getStores().size() > 0)
			game.appendChild(createElementWithoutProvenance(doc, rootStores, childStores, record.getStores()));


		if (record.getESRB() != null && record.getESRB().size() > 0)
			game.appendChild(createESRBElement(record, doc));
		if (record.getCERO() != null && record.getCERO().size() > 0)
			game.appendChild(createCEROElement(record, doc));
		if (record.getPEGI() != null && record.getPEGI().size() > 0)
			game.appendChild(createPEGIElement(record, doc));
		
		if (record.getTags() != null && record.getTags().size() > 0)
			game.appendChild(createTagElement(record, doc));
		
		if (record.getRating() != null && record.getRating().size() > 0)
			game.appendChild(createRatingElement(record, doc));
				
		if(record.getRecommended()!=null  && record.getRecommended().size()>0) {
			game.appendChild(createRecommendedElement(record, doc));	
		}
		

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

	protected Element createESRBElement(VideoGames record, Document doc) {
		Element esrbRoot = esrbFormatter.createRootElement(doc);

		for (ESRB a : record.getESRB()) {
			esrbRoot.appendChild(esrbFormatter.createElementFromRecord(a, doc));
		}

		return esrbRoot;
	}

	protected Element createCEROElement(VideoGames record, Document doc) {
		Element ceroRoot = ceroFormatter.createRootElement(doc);

		for (CERO a : record.getCERO()) {
			ceroRoot.appendChild(ceroFormatter.createElementFromRecord(a, doc));
		}

		return ceroRoot;
	}

	protected Element createPEGIElement(VideoGames record, Document doc) {
		Element pegiRoot = pegiFormatter.createRootElement(doc);

		for (PEGI a : record.getPEGI()) {
			pegiRoot.appendChild(pegiFormatter.createElementFromRecord(a, doc));
		}

		return pegiRoot;
	}
	
	protected Element createTagElement(VideoGames record, Document doc) {
		Element tagRoot = tagFormatter.createRootElement(doc);

		for (Tags t : record.getTags()) {
			tagRoot.appendChild(tagFormatter.createElementFromRecord(t, doc));
		}

		return tagRoot;
	}
	
	protected Element createRatingElement(VideoGames record, Document doc) {
		Element ratRoot = ratingFormatter.createRootElement(doc);

		for (Rating t : record.getRating()) {
			ratRoot.appendChild(ratingFormatter.createElementFromRecord(t, doc));
		}

		return ratRoot;
	}
	
	protected Element createRecommendedElement(VideoGames record, Document doc) {
		Element recRoot = recFormatter.createRootElement(doc);
		
		for (Recommended t : record.getRecommended()) {
			recRoot.appendChild(recFormatter.createElementFromRecord(t, doc));
		}
		
		

		return recRoot;
	}
	
	

}
