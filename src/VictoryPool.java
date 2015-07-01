import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 5/4/15
 * 
 * An are for players to strategically place Victory Points
 */
public class VictoryPool implements GameItem<VictoryPool>{
	
	protected enum Strategy{WONDER, MOST_BUILDINGS, GREATEST_ARMY, LAST_BATTLE};
	private Strategy strategy;
	private ImageIcon image;
	private int value;
	private String description;
	
	/**
	 * Constructor for the VictoryBin
	 * 
	 * @param strategy
	 */
	public VictoryPool(VictoryPool.Strategy strategy){
		this.strategy = strategy;
		switch(strategy){
		case WONDER:
			description = "The first player to build The Wonder.";
			image = new ImageIcon("Wonder.png");
			break;
		case MOST_BUILDINGS:
			description = "The player with the most buildings.";
			image = new ImageIcon("MostBuildings.png");
			break;
		case GREATEST_ARMY:
			description = "The player with the most units.";
			image = new ImageIcon("GreatestArmy.png");
		case LAST_BATTLE:
			description = "The player who won the last battle.";
			image = new ImageIcon("LastBattle.png");
		}
		value = 0;
	}
	
	/**
	 * Override Object toString() to be user friendly
	 */
	public String toString(){
		return "" + strategy + ": " + description + " (" + value + ")";
	}
	
	/**
	 * Implements displayItem() from GameItem, used for displaying
	 * on the graphical interface
	 */
	public ImageIcon displayItem(Player owner){
		return image;
	}
	
	/**
	 * Mutator for victory strategy
	 * 
	 * @param strategy The victory strategy
	 */
	public void setStrategy(Strategy strategy){
		this.strategy = strategy;
	}
	
	/**
	 * Mutator for victory strategy image
	 * 
	 * @param image The victory strategy image
	 */
	public void setImage(ImageIcon image){
		this.image = image;
	}
	
	/**
	 * Mutator for the value of the victory strategy
	 * 
	 * @param value The value of the victory strategy
	 */
	public void setValue(int value){
		this.value = value;
	}
	
	/**
	 * Mutator for the victory strategy description
	 * 
	 * @param description The description of the victory strategy
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Accessor for the victory strategy
	 * 
	 * @return The victory strategy
	 */
	public Strategy getStrategy(){
		return this.strategy;
	}
	
	/**
	 * Accessor for the victory strategy image
	 * 
	 * @return The victory strategy image
	 */
	public ImageIcon getImage(){
		return this.image;
	}
	
	/**
	 * Accessor for the victory strategy value
	 * 
	 * @return The victory strategy value
	 */
	public int getValue(){
		return this.value;
	}
	
	/**
	 * Accessor for the victory strategy description
	 * 
	 * @return The victory strategy description
	 */
	public String getDescription(){
		return this.description;
	}
}
