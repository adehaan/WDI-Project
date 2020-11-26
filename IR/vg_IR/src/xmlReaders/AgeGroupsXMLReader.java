package xmlReaders;

import java.util.List;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import genralClasses.AgeGroups;
import genralClasses.CERO;
import genralClasses.ESRB;
import genralClasses.PEGI;

public class AgeGroupsXMLReader extends XMLMatchableReader<AgeGroups, Attribute>{

	@Override
	public AgeGroups createModelFromElement(Node node, String provenanceInfo) {
		// TODO Auto-generated method stub
		
		AgeGroups agegroups = new AgeGroups();
		
		List<ESRB> lstEsrb = getObjectListFromChildElement(node, "Age_group", "ESRB", new ESRBXmlReader(), provenanceInfo);
		//List<CERO> lstCero = getObjectListFromChildElement(node, "Age_group", "CERO", new ESRBXmlReader(), provenanceInfo);
		//List<PEGI> lstPegi = getObjectListFromChildElement(node, "Age_group", "PEGI", new ESRBXmlReader(), provenanceInfo);
		
		//List<String> group = getListFromChildElement(node, "Age_groups");
		//int id = Integer.parseInt(getValueFromChildElement(node, "id"));
		
		//agegroups.setgroup(group);
		agegroups.setESRB(lstEsrb);
		//agegroups.setid(id);
		
		return agegroups;
	}

}
