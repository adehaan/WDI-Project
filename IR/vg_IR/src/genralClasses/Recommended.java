package genralClasses;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Recommended extends AbstractRecord<Attribute> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String platform_name;
	protected String platform_requirements;
	
	
	public String getPlatformName() {
		return platform_name;
	}

	public void setPlatformName(String platform_name) {
		this.platform_name = platform_name;
	}
	
	public String getRequirements() {
		return platform_requirements;
	}

	public void setRequirements(String platform_requirements) {
		this.platform_requirements = platform_requirements;
	}
	
	
	@Override
	public boolean hasValue(Attribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}
}
