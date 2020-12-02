package xmlReaders;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import genralClasses.CERO;

public class CEROXmlReader extends XMLMatchableReader<CERO, Attribute>{

	@Override
	public CERO createModelFromElement(Node node, String provenanceInfo) {
		CERO cero = new CERO();
		String lst = getValueFromChildElement(node, "Rating");
		cero.setRatings(lst);
		return cero;
	}

}
