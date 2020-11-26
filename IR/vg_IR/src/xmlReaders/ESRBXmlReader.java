package xmlReaders;

import java.util.List;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import genralClasses.ESRB;

public class ESRBXmlReader extends XMLMatchableReader<ESRB, Attribute> {

	@Override
	public ESRB createModelFromElement(Node node, String provenanceInfo) {
		ESRB esrb = new ESRB();
		String lst = getValueFromChildElement(node, "Rating");
		esrb.setRatings(lst);
		return esrb;
	}
}
