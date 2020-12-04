package xmlReaders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import edu.stanford.nlp.util.StringUtils;
import genralClasses.AgeGroups;
import genralClasses.CERO;
import genralClasses.ESRB;
import genralClasses.PEGI;
import genralClasses.Tags;
import genralClasses.VideoGames;

public class GamesXMLReader extends XMLMatchableReader<VideoGames, Attribute>
		implements FusibleFactory<VideoGames, Attribute> {

	@Override
	protected void initialiseDataset(DataSet<VideoGames, Attribute> dataset) {
		super.initialiseDataset(dataset);

// the schema is defined in the Movie class and not interpreted from the file, so we have to set the attributes manually
		dataset.addAttribute(VideoGames.TITLE);
		dataset.addAttribute(VideoGames.GENRES);
		dataset.addAttribute(VideoGames.DATE);
		dataset.addAttribute(VideoGames.PLATFORMS);
		dataset.addAttribute(VideoGames.PUBLISHERS);
	}

	@Override
	public VideoGames createInstanceForFusion(RecordGroup<VideoGames, Attribute> cluster) {
		List<String> ids = new LinkedList<>();

		// collecting all ids of records fused in this group
		for (VideoGames v : cluster.getRecords()) {
			ids.add(v.getIdentifier());
		}

		// sort and merge ids to create an id for the fused record
		Collections.sort(ids);
		String mergedID = StringUtils.join(ids, "+");

		// create fused record
		return new VideoGames(mergedID, "fused");
	}

	@Override
	public VideoGames createModelFromElement(Node node, String provenanceInfo) {
		// TODO Auto-generated method stub

		// id
		String id = getValueFromChildElement(node, "id");
		VideoGames vg = new VideoGames(id, provenanceInfo);

		// Name
		vg.setTitle(getValueFromChildElement(node, "Title"));

		// Year
		String strYear = getValueFromChildElement(node, "Year");
		if (strYear != null && !strYear.isEmpty()) {
			int year = Integer.parseInt(strYear);
			vg.setDate(year);
		}

		// Sale EU
		String strSalesEu = getValueFromChildElement(node, "Sale_EU");
		if (strSalesEu != null && !strSalesEu.isEmpty()) {
			double salesEU = Double.parseDouble(strSalesEu);
			vg.setSalesEU(salesEU);
		}

		// Sale JP
		String strSalesJP = getValueFromChildElement(node, "Sale_JP");
		if (strSalesJP != null && !strSalesJP.isEmpty()) {
			double salesJP = Double.parseDouble(strSalesJP);
			vg.setSalesJP(salesJP);
		}

		// Sale NA
		String strSalesNA = getValueFromChildElement(node, "Sale_NA");
		if (strSalesNA != null && !strSalesNA.isEmpty()) {
			double salesNA = Double.parseDouble(strSalesNA);
			vg.setSalesNA(salesNA);
		}

		// Sale Others
		String strSalesOthers = getValueFromChildElement(node, "Sale_Others");
		if (strSalesOthers != null && !strSalesOthers.isEmpty()) {
			double salesOthers = Double.parseDouble(strSalesOthers);
			vg.setSalesOthers(salesOthers);
		}

		// Sale Global
		String strSalesGlobal = getValueFromChildElement(node, "Sale_Global");
		if (strSalesGlobal != null && !strSalesGlobal.isEmpty()) {
			double salesGlobal = Double.parseDouble(strSalesGlobal);
			vg.setSalesGlobal(salesGlobal);
		}

		// Total length
		String strTotalLength = getValueFromChildElement(node, "Total_Length_in_hrs");
		if (strTotalLength != null && !strTotalLength.isEmpty()) {
			int totalLength = Integer.parseInt(strTotalLength);
			vg.setTotalLength(totalLength);
		}

		// Website
		String strWebsite = getValueFromChildElement(node, "Website");
		if (strWebsite != null && !strWebsite.isEmpty()) {
			vg.setWebsite(strWebsite);
		}

		// Sequel
		String strSequel = getValueFromChildElement(node, "Sequel");
		if (strSequel != null && !strSequel.isEmpty()) {
			vg.setSequel(strSequel);
		}

		// Prequel
		String strPrequel = getValueFromChildElement(node, "Prequel");
		if (strPrequel != null && !strPrequel.isEmpty()) {
			vg.setPrequel(strPrequel);
		}

		// Countries_of_Origins
		List<String> lstCountryOfOrigin = getListFromChildElement(node, "Countries_of_Origin");
		List<String> helper = new ArrayList<>();
		if (lstCountryOfOrigin != null && lstCountryOfOrigin.size() > 0) {
			for (String country : lstCountryOfOrigin) {
				if (!country.equals("") || !country.isBlank() || !country.isEmpty())
					helper.add(country.toUpperCase());
			}
			// {
			vg.setCountries(helper);
		}

		// Stores
		List<String> lstStores = getListFromChildElement(node, "Stores");
		List<String> helperStores = new ArrayList<>();
		if (lstStores != null && lstStores.size() > 0) {
			for (String store : lstStores) {
				if (!store.equals("") || !store.isBlank() || !store.isEmpty())
					helperStores.add(store);
			}
			// {
			vg.setStores(helperStores);
		}
		
		// Publishers
		List<String> lstPublishers = getListFromChildElement(node, "Publishers");
		List<String> helperPublisher = new ArrayList<>();
		if (lstPublishers != null && lstPublishers.size() > 0) {
			for (String publisher : lstPublishers) {
				if (!publisher.equals("") || !publisher.isBlank() || !publisher.isEmpty())
					helperPublisher.add(publisher.toUpperCase());
			}
			// {
			vg.setPublishers(helperPublisher);
		}

		// DeveloperStudios
		List<String> lstDeveloperStudios = getListFromChildElement(node, "DeveloperStudios");
		if (lstDeveloperStudios != null && lstDeveloperStudios.size() > 0) {
			vg.setDeveloper(lstDeveloperStudios);
		}

		// Genres
		List<String> lstGenres = new ArrayList<>();
		for (String genre : getListFromChildElement(node, "Genres")) {
			if (!genre.equals("") || !genre.isBlank() || !genre.isEmpty())
				lstGenres.add(genre.toUpperCase());
		}
		// if (lstGenres != null && lstGenres.size() > 0) {
		vg.setGenres(lstGenres);
		// }

		// platforms
		List<String> lstPlatforms = getListFromChildElement(node, "platforms");
		if (lstPlatforms != null && lstPlatforms.size() > 0) {
			vg.setPlatforms(lstPlatforms);
		}

		// Modes
		List<String> lstModes = getListFromChildElement(node, "Modes");
		List<String> helperMode = new ArrayList<>();
		if (lstModes != null && lstModes.size() > 0) {
			for (String mode : lstModes) {
				if (!mode.equals("") || !mode.isBlank() || !mode.isEmpty())
					helperMode.add(mode.toUpperCase());
			}

			vg.setModes(helperMode);
		}

		// Contributors
		List<String> lstContibutors = getListFromChildElement(node, "Contributors");
		List<String> helperContributor = new ArrayList<>();
		if (lstContibutors != null && lstContibutors.size() > 0) {
			for (String contributor : lstContibutors) {
				if (!contributor.equals("") || !contributor.isBlank() || !contributor.isEmpty())
					helperContributor.add(contributor.toUpperCase());
			}

			vg.setContributors(helperContributor);
		}

		// Tags
		List<Tags> lstTags = getObjectListFromChildElement(node, "Tags", "Tag", new TagXMLReader(), provenanceInfo);
		if (lstTags != null && lstTags.size() > 0) {
			vg.setTags(lstTags);
		}

		// Age_groups
		List<ESRB> lstESRB = getObjectListFromChildElement(node, "ESRBs", "ESRB", new ESRBXmlReader(), provenanceInfo);
		if (lstESRB != null && lstESRB.size() > 0) {
			vg.setESRB(lstESRB);
		}

		List<PEGI> lstPEGI = getObjectListFromChildElement(node, "PEGIs", "PEGI", new PEGIXmlReader(), provenanceInfo);
		if (lstPEGI != null && lstPEGI.size() > 0) {
			vg.setPEGI(lstPEGI);
		}

		List<CERO> lstCERO = getObjectListFromChildElement(node, "CEROs", "CERO", new CEROXmlReader(), provenanceInfo);
		if (lstCERO != null && lstCERO.size() > 0) {
			vg.setCERO(lstCERO);
		}

		return vg;
	}

}
