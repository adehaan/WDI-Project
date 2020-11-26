package genralClasses;

import java.io.Serializable;
import java.util.List;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class AgeGroups extends AbstractRecord<Attribute> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected int id;
	protected List<ESRB> ESRB;
	protected List<CERO> CERO;
	protected List<PEGI> PEGI;
	
	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}
	
	public List<ESRB> getESRB() {
		return ESRB;
	}
	public void setESRB(List<ESRB> ESRB) {
		this.ESRB = ESRB;
	}
	
	public List<CERO> getCERO() {
		return CERO;
	}
	public void setCERO(List<CERO> CERO) {
		this.CERO = CERO;
	}
	
	public List<PEGI> getPEGI() {
		return PEGI;
	}
	public void setPEGI(List<PEGI> PEGI) {
		this.PEGI = PEGI;
	}
	
	@Override
	public boolean hasValue(Attribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}
}
