package xmlReaders;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import genralClasses.Recommended;

public class RecommendedXMLReader extends XMLMatchableReader<Recommended, Attribute>{

	@Override
	public Recommended createModelFromElement(Node node, String provenanceInfo) {
		// TODO Auto-generated method stub
		
		Recommended Recommended = new Recommended();
		
		String platform_name = getValueFromChildElement(node, "Platforn_Name");
		String recommend_requirement = getValueFromChildElement(node, "Recommended_Req_PC");
		
		Recommended.setPlatformName(platform_name);
		Recommended.setRequirements(recommend_requirement);
		
		return Recommended;
	}

}
