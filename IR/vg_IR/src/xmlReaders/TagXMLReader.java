package xmlReaders;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import genralClasses.Tags;

public class TagXMLReader extends XMLMatchableReader<Tags, Attribute>{

	@Override
	public Tags createModelFromElement(Node node, String provenanceInfo) {
		// TODO Auto-generated method stub
		
		Tags tags = new Tags();
		
		String name = getValueFromChildElement(node, "Name");
		int count = Integer.parseInt(getValueFromChildElement(node, "Games_count"));
		
		tags.setName(name);
		tags.setCounts(count);
		
		return tags;
	}

}
