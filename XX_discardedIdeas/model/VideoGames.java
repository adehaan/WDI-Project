
package model;

import java.time.LocalDate;
import java.util.List;
//import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

/**
 * 
 * @author Katie & Aniek
 * 
 */
public class VideoGames implements Matchable {

	protected String id;

	private String title;
	private LocalDate year;
	private double Sale_EU;
	private double Sale_JP;
	private double Sale_NA;
	private double Sale_Others;
	private double Sale_Global;
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
	private double Metacritic;
	private double RAWG_Rating;

	// Recommend: only take the PC req. as String
	private String PC_Requirements;

	// Eigene Klassen/ Dateien für jedes erstellen
	

	public class Tag {
		public String name;
		public int gameCounts;
	}

	private List<Tag> Tags;
	
	public class AgeGroups {
		public int id;
		public String group;
	}
    private List<AgeGroups> Age_Groups;
	// Tags: Name: GameCount
	// Age_Groups: ID: Liste



	public VideoGames(String identifier) {
		id = identifier;
	    //actors = new LinkedList<>();
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

	public LocalDate getDate() {
		return year;
	}

	public void setDate(LocalDate year) {
		this.year = year;
	}

	public double getSalesEU() {
		return Sale_EU;
	}

	public void setSalesEU(double Sale_EU) {
		this.Sale_EU = Sale_EU;
	}

	public double getSalesJP() {
		return Sale_JP;
	}

	public void setSalesJP(double Sale_JP) {
		this.Sale_JP = Sale_JP;
	}

	public double getSalesNA() {
		return Sale_NA;
	}

	public void setSalesNA(double Sale_NA) {
		this.Sale_NA = Sale_NA;
	}

	public double getSalesOthers() {
		return Sale_Others;
	}

	public void setSalesOthers(double Sale_Others) {
		this.Sale_Others = Sale_Others;
	}

	public double getSalesGlobal() {
		return Sale_Global;
	}

	public void setSalesGlobal(double Sale_Global) {
		this.Sale_Global = Sale_Global;
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

	public double getMetacritic() {
		return Metacritic;
	}

	public void setMetacritic(double Metacritic) {
		this.Metacritic = Metacritic;
	}

	public double getRAWGRating() {
		return RAWG_Rating;
	}

	public void setRAWGRating(double RAWG_Rating) {
		this.RAWG_Rating = RAWG_Rating;
	}

	public String getPCReq() {
		return PC_Requirements;
	}

	public void setPCReq(String PC_Requirements) {
		this.PC_Requirements = PC_Requirements;
	}

	public List<Tag> getTags() {
		return Tags;
	}

	public void setTags(List<Tag> tag) {
		this.Tags = tag;
	}

	public List<AgeGroups> getAgeGroup() {
		return Age_Groups;
	}

	public void setPCReq(List<AgeGroups> Agegroups) {
		this.Age_Groups = Agegroups;
	}








// WHAT IS THIS?

	//@Override
	//public String toString() {
	//	return String.format("[Movie %s: %s / %s / %s]", getIdentifier(), getTitle(),
	//			getDirector(), getDate().toString());
	//}
//
	//@Override
	//public int hashCode() {
	//	return getIdentifier().hashCode();
	//}
//
	//@Override
	//public boolean equals(Object obj) {
	//	if(obj instanceof VideoGames){
	//		return this.getIdentifier().equals(((VideoGames) obj).getIdentifier());
	//	}else
	//		return false;
	//}
	
	
	
}
