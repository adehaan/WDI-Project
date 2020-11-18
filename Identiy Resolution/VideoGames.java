
package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
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
	private List<Country> Countries_of_Origin;
	private String Sequel;
	private String Prequel;
	private List<Store> Stores;
	private List<Publisher> Publishers;
	private List<DeveloperStudio> DeveloperStudios;
	private List<Genre> Genres;
	private List<platform> platforms;
	private List<Mode> Modes;
	private List<Contributor> Contributors;

	// Rating: metacritic & RAWG_rating -> umgeschrieben in seperate Variablen
	private double Metacritic;
	private double RAWG_Rating;

	// Recommend: only take the PC req. as String
	private String PC_Requirements;


    // Eigene Klassen/ Dateien für jedes erstellen
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

	public void setDate(LocalDate date) {
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

	public List<Country> getCountries() {
		return Countries_of_Origin;
	}

	public void setCountries(List<Country> Countries_of_Origin) {
		this.Countries_of_Origin = Countries_of_Origin;
	}

	public List<Store> getStores() {
		return Stores;
	}

	public void setStores(List<Store> Stores) {
		this.Stores = Stores;
	}

	public List<Publisher> getPublishers() {
		return Publishers;
	}

	public void setPublishers(List<Publisher> Publishers) {
		this.Publishers = Publishers;
	}

	public List<DeveloperStudio> getDeveloper() {
		return DeveloperStudios;
	}

	public void setDeveloper(List<DeveloperStudio> DeveloperStudios) {
		this.DeveloperStudios = DeveloperStudios;
	}

	public List<Genre> getGenres() {
		return Genres;
	}

	public void setGenres(List<Genre> Genres) {
		this.Genres = Genres;
	}

	public List<platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<platform> platforms) {
		this.platforms = platforms;
	}

	public List<Mode> getModes() {
		return Modes;
	}

	public void setModes(List<Mode> Modes) {
		this.Modes = Modes;
	}

	public List<Contributor> getContributors() {
		return Contributors;
	}

	public void setContributors(List<Contributor> Contributors) {
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









// WHAT IS THIS?

	@Override
	public String toString() {
		return String.format("[Movie %s: %s / %s / %s]", getIdentifier(), getTitle(),
				getDirector(), getDate().toString());
	}

	@Override
	public int hashCode() {
		return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof VideoGames){
			return this.getIdentifier().equals(((VideoGames) obj).getIdentifier());
		}else
			return false;
	}
	
	
	
}
