package videoGames;

import java.util.List;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class GamesXMLReader extends XMLMatchableReader<VideoGames, Attribute> {

	@Override
	public VideoGames createModelFromElement(Node node, String provenanceInfo) {
		// TODO Auto-generated method stub

		String id = getValueFromChildElement(node, "id");
		VideoGames vg = new VideoGames(id, provenanceInfo);

		// Name
		vg.setTitle(getValueFromChildElement(node, "Name"));

		// Year
		int year = Integer.parseInt(getValueFromChildElement(node, "Year"));
		vg.setDate(year);

		// Sale EU
		double salesEU = Double.parseDouble(getValueFromChildElement(node, "Sale_EU"));
		vg.setSalesEU(salesEU);

		// Sale JP
		double salesJP = Double.parseDouble(getValueFromChildElement(node, "Sale_EU"));
		vg.setSalesJP(salesJP);

		// Sale NA
		double salesNA = Double.parseDouble(getValueFromChildElement(node, "Sale_EU"));
		vg.setSalesNA(salesNA);

		// Sale Others
		double salesOthers = Double.parseDouble(getValueFromChildElement(node, "Sale_EU"));
		vg.setSalesOthers(salesOthers);

		// Sale Global
		double salesGlobal = Double.parseDouble(getValueFromChildElement(node, "Sale_EU"));
		vg.setSalesGlobal(salesGlobal);

		// Total length
		int totalLength = Integer.parseInt(getValueFromChildElement(node, "Total_Length_in_hrs"));
		vg.setTotalLength(totalLength);

		// Website
		vg.setWebsite(getValueFromChildElement(node, "Website"));

		// Sequel
		vg.setSequel(getValueFromChildElement(node, "Sequel"));

		// Prequel
		vg.setPrequel(getValueFromChildElement(node, "Sale_EU"));

		// Countries_of_Origins
		vg.setCountries(getListFromChildElement(node, "Countries_of_Origins"));

		// Stores
		vg.setStores(getListFromChildElement(node, "Stores"));

		// Publishers
		vg.setPublishers(getListFromChildElement(node, "Publishers"));

		// DeveloperStudios
		vg.setDeveloper(getListFromChildElement(node, "DeveloperStudios"));

		// Genres
		vg.setGenres(getListFromChildElement(node, "Genres"));

		// platforms
		vg.setPlatforms(getListFromChildElement(node, "platforms"));

		// Modes
		vg.setModes(getListFromChildElement(node, "Modes"));

		// Contributors
		vg.setContributors(getListFromChildElement(node, "Contributors"));

		// Tags
		vg.setTags(getObjectListFromChildElement(node, "Tags","Tag",new TagXMLReader(), provenanceInfo));

		// Age_groups
		vg.setContributors(getListFromChildElement(node, "Contributors"));
		return vg;
	}

}
