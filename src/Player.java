import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * The template for an AoM Player
 */
public class Player implements GameItem<Player>{
	private String name;
	protected enum Age implements EnumToImage<Player.Age>{
		ARCHAIC, CLASSICAL, HEROIC, MYTHIC;
		public ImageIcon enumToImage(Player.Age enumerated){
			switch(enumerated){
			case ARCHAIC:
				return new ImageIcon("Archaic.png");
			case CLASSICAL:
				return new ImageIcon("Classical.png");
			case HEROIC:
				return new ImageIcon("Heroic.png");
			case MYTHIC:
				return new ImageIcon("Mythic.png");
			default:
				return new ImageIcon("Archaic.png");
			}
		}
	}
	private Age playerAge;
	protected enum Race implements EnumToImage<Player.Race>{
		EGYPTIAN, GREEK, NORSE;
		public ImageIcon enumToImage(Player.Race enumerated){
			switch(enumerated){
			case EGYPTIAN:
				return new ImageIcon("Egyptian.png");
			case GREEK:
				return new ImageIcon("Greek.png");
			case NORSE:
				return new ImageIcon("Norse.png");
			default:
				return new ImageIcon("Egyptian.png");
			}
		}
	}
	private Race playerRace;
	private ArrayList<ProductionTile.Terrain> terrainAvailable;
	private ArrayList<Card> hand;
	private ArrayList<ProductionTile> production;
	private ArrayList<Building> city;
	private ArrayList<Card> randomDeck;
	private ArrayList<Card> usedRandomDeck;
	private ArrayList<Card> permanentDeck;
	private int[] wallet;
	private ArrayList<Building> buildingPool;
	private Player next;
	private boolean human;
	
	/**
	 * Constructor for a Player
	 * 
	 * @param name The name of a Player
	 * @param human True if the Player is human, false if AI
	 */
	public Player(String name, boolean human) {
		this.name = name;
		playerAge = Age.ARCHAIC;
		terrainAvailable = new ArrayList<ProductionTile.Terrain>();
		hand = new ArrayList<Card>();
		production = new ArrayList<ProductionTile>();
		city = new ArrayList<Building>();
		randomDeck = new ArrayList<Card>();
		usedRandomDeck = new ArrayList<Card>();
		permanentDeck = new ArrayList<Card>();
		wallet = new int [5];
		buildingPool = new ArrayList<Building>();
		this.human = human;
	}
	/**
	 * A String describing the Card
	 * 
	 */
	@Override
	public String toString(){
		return name;
	}
	
	/**
	 * Overridden from DisplayItem to return the Player image
	 * 
	 * @return The image for the Player Game Item
	 */
	public ImageIcon displayItem(Player p){
		return this.playerRace.enumToImage(this.playerRace);
	}
	
	/**
	 * Mutator for the Player name
	 * 
	 * @param name Player name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Mutator for the age of the Player
	 * 
	 * @param playerAge The Age of the Player
	 */
	public void setAge(Age playerAge){
		this.playerAge = playerAge;
	}
	
	/**
	 * Mutator for the race of the Player
	 * 
	 * @param playerRace The race of the Player
	 */
	public void setRace(Race playerRace){
		this.playerRace = playerRace;
	}
	
	/**
	 * Mutator for the Player's available terrain
	 * 
	 * @param terrainAvailable The Player's available terrain
	 */
	public void setTerrain(
			ArrayList<ProductionTile.Terrain> terrainAvailable){
		this.terrainAvailable = terrainAvailable;
	}
	
	/**
	 * Mutator for the Player's hand
	 * 
	 * @param hand The Player's hand
	 */
	public void setHand(ArrayList<Card> hand){
		this.hand = hand;
	}
	
	/**
	 * Mutator for the Player's production area
	 * @param production The Player's production area
	 */
	public void setProduction(ArrayList<ProductionTile> production){
		this.production = production;
	}
	
	/**
	 * Mutator for the Player's city area
	 * 
	 * @param city The Player's city area
	 */
	public void setCity(ArrayList<Building> city){
		this.city = city;
	}
	
	/**
	 * Mutator for the Player's random deck
	 * 
	 * @param randomDeck The Player's random deck
	 */
	public void setRandDeck(ArrayList<Card> randomDeck){
		this.randomDeck = randomDeck;
	}
	
	/**
	 * Mutator for the Player's random deck
	 * 
	 * @param usedRandomDeck The Player's random deck
	 */
	public void setUsedRandDeck(ArrayList<Card> usedRandomDeck){
		this.usedRandomDeck = usedRandomDeck;
	}
	
	/**
	 * Mutator for the Player's permanent deck
	 * 
	 * @param permanentDeck The Player's permanent deck
	 */
	public void setPermDeck(ArrayList<Card> permanentDeck){
		this.permanentDeck = permanentDeck;
	}
	
	/**
	 * Mutator for the Player's wallet
	 * 
	 * @param wallet The Player's wallet
	 */
	public void setWallet(int[] wallet){
		this.wallet = wallet;
	}
	
	/**
	 * Mutator for the Player's building pool
	 * 
	 * @param buildingPool The Player's building pool
	 */
	public void setBuildPool(ArrayList<Building> buildingPool){
		this.buildingPool = buildingPool;
	}
	
	/**
	 * Mutator for the Player's next Player
	 * 
	 * @param next The Player's next Player
	 */
	public void setNext(Player next){
		this.next = next;
	}
	
	/**
	 * Mutator for the boolean indicating whether the player is human
	 * 
	 * @param human True if the player is human
	 */
	public void setHuman(boolean human){
		this.human = human;
	}
	
	/**
	 * Accessor for the Player's name
	 * 
	 * @return The player's name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Accessor for the Player's age
	 * 
	 * @return The Player's age
	 */
	public Age getAge(){
		return this.playerAge;
	}
	
	/**
	 * Accessor for the Player's race
	 * 
	 * @return The Player's race
	 */
	public Race getRace(){
		return this.playerRace;
	}
	
	/**
	 * Accessor for the Player's available terrain
	 * 
	 * @return The Player's available terrain
	 */
	public ArrayList<ProductionTile.Terrain> getTerrain(){
		return this.terrainAvailable;
	}
	
	/**
	 * Accessor for the Player's current hand
	 * 
	 * @return The Player's current hand
	 */
	public ArrayList<Card> getHand(){
		return this.hand;
	}
	
	/**
	 * Accessor for the Player's production area
	 *  
	 * @return The Player's production area
	 */
	public ArrayList<ProductionTile> getProduction(){
		return this.production;
	}
	
	/**
	 * Accessor for the Player's city area
	 * 
	 * @return The Player's city area
	 */
	public ArrayList<Building> getCity(){
		return this.city;
	}
	
	/**
	 * Accessor for the Player's random deck
	 * 
	 * @return the Player's random deck
	 */
	public ArrayList<Card> getRandDeck(){
		return this.randomDeck;
	}
	
	/**
	 * Accessor for the Player's used random deck
	 * 
	 * @return The Player's used random deck
	 */
	public ArrayList<Card> getUsedRandDeck(){
		return this.usedRandomDeck;
	}
	
	/**
	 * Accessor for the Player's permanent deck
	 * 
	 * @return The Player's permanent deck
	 */
	public ArrayList<Card> getPermDeck(){
		return this.permanentDeck;
	}
	
	/**
	 * Accessor for the Player's wallet
	 *  
	 * @return The Player's wallet
	 */
	public int[] getWallet(){
		return this.wallet;
	}
	
	/**
	 * Accessor for the Player's building pool
	 * 
	 * @return The Player's building pool
	 */
	public ArrayList<Building> getBuildPool(){
		return this.buildingPool;
	}
	
	/**
	 * Accessor for the Player's next Player
	 *  
	 * @return The Player's next Player
	 */
	public Player getNext(){
		return this.next;
	}
	
	/**
	 * Acessor for the boolean indicating whether a Player is human
	 * 
	 * @return True if the player is human
	 */
	public boolean getHuman(){
		return this.human;
	}
}
