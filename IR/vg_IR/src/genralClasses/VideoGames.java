package genralClasses;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

public class VideoGames extends AbstractRecord<Attribute> implements Serializable {

	protected String id;

	private String title;
	private int year;
	private double Sales_EU;
	private double Sales_JP;
	private double Sales_NA;
	private double Sales_Others;
	private double Sales_Global;
	private int Total_Length_in_hrs;
	private String Website;
	private List<String> Countries_of_Origin;
	private String Sequel;
	private String Prequel;
	private List<String> Stores;
	private List<String> Publishers;
	private List<String> DeveloperStudios;
	private List<String> Genres;
	private List<String> platforms;
	private List<String> Modes;
	private List<String> Contributors;

	// Rating: metacritic & RAWG_rating -> umgeschrieben in seperate Variablen
	private List<Rating> Rating;

	// Recommend: only take the PC req. as String
	private List<Recommended> Recommended;

	// Eigene Klassen/ Dateien f�r jedes erstellen

	private List<Tags> Tags;
	private List<ESRB> ESRB;
	private List<PEGI> PEGI;
	private List<CERO> CERO;

	// Tags: Name: GameCount
	// Age_Groups: ID: Liste

	public VideoGames(String identifier, String provenance) {
		super(identifier, provenance);
		id = identifier;
		//this.provenance = provenance;
	}

	@Override
	public String getIdentifier() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDate() {
		return year;
	}

	public void setDate(int year) {
		this.year = year;
	}

	public double getSalesEU() {
		return Sales_EU;
	}

	public void setSalesEU(double Sales_EU) {
		this.Sales_EU = Sales_EU;
	}

	public double getSalesJP() {
		return Sales_JP;
	}

	public void setSalesJP(double Sales_JP) {
		this.Sales_JP = Sales_JP;
	}

	public double getSalesNA() {
		return Sales_NA;
	}

	public void setSalesNA(double Sales_NA) {
		this.Sales_NA = Sales_NA;
	}

	public double getSalesOthers() {
		return Sales_Others;
	}

	public void setSalesOthers(double Sales_Others) {
		this.Sales_Others = Sales_Others;
	}

	public double getSalesGlobal() {
		return Sales_Global;
	}

	public void setSalesGlobal(double Sales_Global) {
		this.Sales_Global = Sales_Global;
	}

	public int getTotalLength() {
		return Total_Length_in_hrs;
	}

	public void setTotalLength(int Total_Length_in_hrs) {
		this.Total_Length_in_hrs = Total_Length_in_hrs;
	}

	public String getWebsite() {
		return Website;
	}

	public void setWebsite(String Website) {
		this.Website = Website;
	}

	public String getSequel() {
		return Sequel;
	}

	public void setSequel(String Sequel) {
		this.Sequel = Sequel;
	}

	public String getPrequel() {
		return Prequel;
	}

	public void setPrequel(String Prequel) {
		this.Prequel = Prequel;
	}

	public List<String> getCountries() {
		return Countries_of_Origin;
	}

	public void setCountries(List<String> Countries_of_Origin) {
		this.Countries_of_Origin = Countries_of_Origin;
	}

	public List<String> getStores() {
		return Stores;
	}

	public void setStores(List<String> Stores) {
		this.Stores = Stores;
	}

	public List<String> getPublishers() {
		return Publishers;
	}

	public void setPublishers(List<String> Publishers) {
		this.Publishers = Publishers;
	}

	public List<String> getDeveloper() {
		return DeveloperStudios;
	}

	public void setDeveloper(List<String> DeveloperStudios) {
		this.DeveloperStudios = DeveloperStudios;
	}

	public List<String> getGenres() {
		return Genres;
	}

	public void setGenres(List<String> Genres) {
		this.Genres = Genres;
	}

	public List<String> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<String> platforms) {
		this.platforms = platforms;
	}

	public List<String> getModes() {
		return Modes;
	}

	public void setModes(List<String> Modes) {
		this.Modes = Modes;
	}

	public List<String> getContributors() {
		return Contributors;
	}

	public void setContributors(List<String> Contributors) {
		this.Contributors = Contributors;
	}

	public List<Rating> getRating() {
		return Rating;
	}

	public void setRating(List<Rating> Rating) {
		this.Rating = Rating;
	}

	public List<Recommended> getRecommended() {
		return Recommended;
	}

	public void setRecommended(List<Recommended> Recommended) {
		this.Recommended = Recommended;
	}

	public List<Tags> getTags() {
		return Tags;
	}

	public void setTags(List<Tags> Tags) {
		this.Tags = Tags;
	}

	public void setESRB(List<ESRB> ESRB) {
		this.ESRB = ESRB;
	}

	public List<ESRB> getESRB() {
		return ESRB;
	}

	public void setPEGI(List<PEGI> PEGI) {
		this.PEGI = PEGI;
	}

	public List<PEGI> getPEGI() {
		return PEGI;
	}

	public void setCERO(List<CERO> CERO) {
		this.CERO = CERO;
	}

	public List<CERO> getCERO() {
		return CERO;
	}

	// NEEEEEEEEEEEEWWWWWWWWWWWWWWWW
	// --------------------------------------------------------------

	private Map<Attribute, Collection<String>> provenance = new HashMap<>();
	private Collection<String> recordProvenance;

	public void setRecordProvenance(Collection<String> provenance) {
		recordProvenance = provenance;
	}

	public Collection<String> getRecordProvenance() {
		return recordProvenance;
	}

	public void setAttributeProvenance(Attribute attribute, Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}

	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}

	public String getMergedAttributeProvenance(Attribute attribute) {
		Collection<String> prov = provenance.get(attribute);

		if (prov != null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}

	public void setProvenance(Map<Attribute, Collection<String>> provenance) {
		this.provenance = provenance;
	}

	// All Attributes that are in more than 1 dataset
	public static final Attribute TITLE = new Attribute("Title");
	public static final Attribute DATE = new Attribute("Year");
	public static final Attribute PUBLISHERS = new Attribute("Publishers");
	public static final Attribute GENRES = new Attribute("Genres");
	public static final Attribute PLATFORMS = new Attribute("Platforms");
	
	@Override
	public boolean hasValue(Attribute attribute) {
		if (attribute == TITLE)
			return this.getTitle() != null && !this.getTitle().isEmpty();
		else if (attribute == DATE)
			return this.getDate() != -1;
		else if (attribute == PUBLISHERS)
			return this.getPublishers() != null && this.getPublishers().size() > 0;
		else if (attribute == GENRES)
			return this.getGenres() != null && this.getGenres().size() > 0;
		else if (attribute == PLATFORMS)
			return this.getPlatforms() != null && this.getPlatforms().size() > 0;
		else
			return false;
	}

	@Override
	public String toString() {
		return String.format("[Game %s: %s / %s / %s / %s]", getIdentifier(), getTitle(), getDate(), getGenres(), getPublishers());
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VideoGames) {
			return this.getIdentifier().equals(((VideoGames) obj).getIdentifier());
		} else
			return false;
	}

}
