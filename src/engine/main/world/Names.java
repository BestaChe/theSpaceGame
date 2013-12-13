package engine.main.world;

import java.util.Random;

import engine.main.entities.*;

public class Names {
	
	public static String[] StarNames = { "Musios", "Rakitia", "Nudios", "Shyiatia", "Toroeos", "Sol",    //
										"Ardytia", "Zidios", "Lleucios", "Ustotia", "Brakios", "Aratia", //  18 names
										"Yerilia", "Enasia", "Orecia", "Cerisia", "Ormonia", "Qualia" }; //
										
	public static String[] PlanetNames = { "Doelis-aw", "Mos-ust", "Dra-taiy", "Ralelaren", "Aduesu", "Craila", //
										"Rtzk", "Gar'wor-teur", "Auoeei", "Terra", "Garvanis", "Martilo",       //  18 names
										"Cesam", "Anandi", "Raddankel", "K'he", "Talunto", "Wartepiah" };       //
	
	public static String[] AfterName = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "'Di" }; // 12 names
	
	
	public static String generatePlanetName( Star motherstar, Random rand ) {
		
		StringBuilder result = new StringBuilder();
		
		result.append( motherstar.name() + " ");
		result.append( Names.PlanetNames[rand.nextInt(Names.PlanetNames.length)] );
		result.append(" ");
		result.append( Names.AfterName[rand.nextInt(Names.AfterName.length)] );
		
		return result.toString();
		
	}
	
	public static String generatePlanetName( Random rand ) {
		
		StringBuilder result = new StringBuilder();
		
		result.append( "Orphan Planet" );
		result.append(" ");
		result.append( Names.PlanetNames[rand.nextInt(Names.PlanetNames.length)] );
		result.append(" ");
		result.append( Names.AfterName[rand.nextInt(Names.AfterName.length)] );
		
		return result.toString();
		
	}
	
	public static String generateStarName( Random rand ) {
		
		StringBuilder result = new StringBuilder();
		
		result.append( Names.StarNames[rand.nextInt(Names.StarNames.length)] );
		result.append(" ");
		result.append( Names.AfterName[rand.nextInt(Names.AfterName.length)] );
		
		return result.toString();
		
	}
	
	
	
}
