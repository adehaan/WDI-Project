package genralClasses;

import java.io.Serializable;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class Rating extends AbstractRecord<Attribute> implements Serializable{
	private static final long serialVersionUID = 1L;
	protected double metacritic;
	protected double rawg_rating;
	
	
	public double getMetacritic() {
		return metacritic;
	}

	public void setMetacritic(double metacritic) {
		this.metacritic = metacritic;
	}
	
	public double getRawgRating() {
		return rawg_rating;
	}

	public void setRawgRating(double rawg_rating) {
		this.rawg_rating = rawg_rating;
	}
	
	
	@Override
	public boolean hasValue(Attribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}
}
