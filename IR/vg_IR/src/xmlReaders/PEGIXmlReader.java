package xmlReaders;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;
import genralClasses.PEGI;

public class PEGIXmlReader extends XMLMatchableReader<PEGI, Attribute>{
	@Override
	public PEGI createModelFromElement(Node node, String provenanceInfo) {
		PEGI pegi = new PEGI();
		String lst = getValueFromChildElement(node, "Rating");
		pegi.setRatings(lst);
		return pegi;
	}

}
