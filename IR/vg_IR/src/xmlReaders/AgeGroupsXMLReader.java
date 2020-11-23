package xmlReaders;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import genralClasses.AgeGroups;

public class AgeGroupsXMLReader extends XMLMatchableReader<AgeGroups, Attribute>{

	@Override
	public AgeGroups createModelFromElement(Node node, String provenanceInfo) {
		// TODO Auto-generated method stub
		
		AgeGroups agegroups = new AgeGroups();
		
		String group = getValueFromChildElement(node, "group");
		int id = Integer.parseInt(getValueFromChildElement(node, "id"));
		
		agegroups.setgroup(group);
		agegroups.setid(id);
		
		return agegroups;
	}

}
