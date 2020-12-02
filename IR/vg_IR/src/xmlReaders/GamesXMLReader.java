package xmlReaders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

public class GamesXMLReader extends XMLMatchableReader<VideoGames, Attribute> implements FusibleFactory<VideoGames, Attribute>{
	
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
		String strSalesGlobal = getValueFromChildElement(node, "Sale_Others");
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
		List<String> lstCountryOfOrigin = getListFromChildElement(node, "Countries_of_Origins");
		if (lstCountryOfOrigin != null && lstCountryOfOrigin.size() > 0) {
			vg.setCountries(lstCountryOfOrigin);
		}

		// Stores
		List<String> lstStores = getListFromChildElement(node, "Stores");
		if (lstStores != null && lstStores.size() > 0) {
			vg.setStores(lstStores);
		}

		// Publishers
		List<String> lstPublishers = getListFromChildElement(node, "Publishers");
		if (lstPublishers != null && lstPublishers.size() > 0) {
			vg.setPublishers(lstPublishers);
		}

		// DeveloperStudios
		List<String> lstDeveloperStudios = getListFromChildElement(node, "DeveloperStudios");
		if (lstDeveloperStudios != null && lstDeveloperStudios.size() > 0) {
			vg.setDeveloper(lstDeveloperStudios);
		}

		// Genres
		List<String> lstGenres = getListFromChildElement(node, "Genres");
		if (lstGenres != null && lstGenres.size() > 0) {
			vg.setGenres(lstGenres);
		}

		// platforms
		List<String> lstPlatforms = getListFromChildElement(node, "platforms");
		if (lstPlatforms != null && lstPlatforms.size() > 0) {
			vg.setPlatforms(lstPlatforms);
		}

		// Modes
		List<String> lstModes = getListFromChildElement(node, "Modes");
		if (lstModes != null && lstModes.size() > 0) {
			vg.setModes(lstModes);
		}

		// Contributors
		List<String> lstContibutors = getListFromChildElement(node, "Contributors");
		if (lstContibutors != null && lstContibutors.size() > 0) {
			vg.setContributors(lstContibutors);
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
		
		// Age_groups
		//List<PEGI> lstPEGI = getObjectListFromChildElement(node, "PEGIs", "PEGI",
		//		new AgeGroupsXMLReader(), provenanceInfo);
		//if (lstAgeGroups != null && lstAgeGroups.size() > 0) {
		//	vg.setAgeGroups(lstAgeGroups);
		//}
		
		// Age_groups
		//List<CERO> lstCERO = getObjectListFromChildElement(node, "CEROs", "CERO",
		//		new AgeGroupsXMLReader(), provenanceInfo);
		//if (lstAgeGroups != null && lstAgeGroups.size() > 0) {
		//	vg.setAgeGroups(lstAgeGroups);
		//}

		return vg;
	}

}
