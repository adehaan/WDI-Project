package videoGames;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Tags extends AbstractRecord<Attribute> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String name;
	protected int counts;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}
	
	
	@Override
	public boolean hasValue(Attribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}
}
