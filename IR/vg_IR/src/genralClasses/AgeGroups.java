package genralClasses;

import java.io.Serializable;
import java.util.List;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class AgeGroups extends AbstractRecord<Attribute> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected int id;
	protected List<String> group;
	
	
	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}
	
	public List<String> getgroup() {
		return group;
	}

	public void setgroup(List<String> group) {
		this.group = group;
	}
	
	
	@Override
	public boolean hasValue(Attribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}
}
