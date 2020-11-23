package vG;

import java.io.File;

import org.slf4j.Logger;

import de.uni_mannheim.informatik.dws.winter.model.HashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;
import genralClasses.VideoGames;
import xmlReaders.GamesXMLReader;

public class Main {

	private static final Logger logger = WinterLogManager.activateLogger("default");
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		System.out.println("*\n*\tLoading datasets\n*");
		HashedDataSet<VideoGames, Attribute> dataAcademyAwards = new HashedDataSet<>();
		new GamesXMLReader().loadFromXML(new File("target_games_Wiki.xml"), "/Games/Game", dataAcademyAwards);
	}

}
