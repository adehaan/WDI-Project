package genralClasses;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class AgeGroups extends AbstractRecord<Attribute> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected int id;
	protected String group;
	
	
	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}
	
	public String getgroup() {
		return group;
	}

	public void setgroup(String group) {
		this.group = group;
	}
	
	
	@Override
	public boolean hasValue(Attribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}
}
