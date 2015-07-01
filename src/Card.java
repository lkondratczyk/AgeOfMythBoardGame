import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * The template for an AoM Card attributes
 */
public abstract class Card implements GameItem<Card>{
	private ImageIcon front;
	private ImageIcon back;
	private boolean permanent;
	private String name;
	private String firstDescription;
	private String secondDescription;
	private int value;
	private int cost;

	/**
	 * The constructor for an AoM Card
	 * 
	 * @param front The front image of the card
	 * @param back The back image of the card
	 * @param permanent True if the card is permanent
	 * @param name The name of the Card
	 * @param firstDescription the secondary Card description
	 * @param secondDescription The secondary Card description
	 * @param value The value of the Card
	 * @param cost The cost to play the Card
	 */
	public Card(ImageIcon front, ImageIcon back, boolean permanent, 
			String name, String firstDescription, String secondDescription, 
			int value, int cost) {
		this.front = front;
		this.back = back;
		this.permanent = permanent;
		this.name = name;
		this.firstDescription = firstDescription;
		this.secondDescription = secondDescription;
		this.value = value;
		this.cost = cost;
	}
	
	/**
	 * Takes a building selected by the activePlayer and adds it to his/her
	 * city, removes it from the building pool, and tracks the refund. Used
	 * by Hera
	 * 
	 * @param game The game being updated 
	 * @param selection The Building the Player selected
	 */
	public void addBuilding(Game game, Building selection){
		game.getActivePlayer().getBuildPool().remove(selection);
		game.getActivePlayer().getCity().add(selection);
	}
	
	/**
	 * A String describing the Card
	 * 
	 */
	@Override
	public String toString(){
		if(this.secondDescription.equals("")){
			return ("|" + name + " : " + firstDescription + "|");
		}
		else{
			return ("|" + name + " : " + firstDescription + " : " + 
						secondDescription +	" : Cost - " + cost + "Favor|" );
		}
	}
	
	/**
	 * Implements displayItem() from GameItem, used for displaying
	 * on the graphical interface
	 */
	public ImageIcon displayItem(Player owner){
		if(owner.getHuman()){
			return front;
		}
		else{
			return back;
		}
	}

	/**
	 * The Card's effect on the Game
	 * 
	 * @param game The game being affected by the card
	 */
	public abstract void execute(Game game);
	
	/**
	 * Mutator for the front image of the card
	 * 
	 * @param front Front image of the card
	 */
	public void setFront(ImageIcon front){
		this.front = front;
	}

	/**
	 * Mutator for the back image of the card
	 * 
	 * @param back Back image of the card
	 */
	public void setBack(ImageIcon back){
		this.back = back;
	}
	
	/**
	 * Mutator for the boolean indicating whether the card is 
	 * random or permanent
	 * 
	 * @param permanent True when the card is permanent
	 */
	public void setPermanent(boolean permanent){
		this.permanent = permanent;
	}
	
	/**
	 * Mutator for the name of the card
	 * 
	 * @param name The name of the card
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Mutator for the first description of the card
	 * 
	 * @param firstDescription The first description of the card
	 */
	public void setFDescription(String firstDescription){
		this.firstDescription = firstDescription;
	}
	
	/**
	 * Mutator for the second description of the card
	 * 
	 * @param secondDescription The second description of the card
	 */
	public void setSDescription(String secondDescription){
		this.secondDescription = secondDescription;
	}
	
	/**
	 *  Mutator for the value of the card
	 *  
	 * @param value The value of the card
	 */
	public void setValue(int value){
		this.value = value;
	}
	
	/**
	 * Mutator for the cost of the card
	 * 
	 * @param cost The cost of the card
	 */
	public void setCost(int cost){
		this.cost = cost;
	}
	
	/**
	 * Accessor for the front image of the card
	 * 
	 * @return The front image of the card
	 */
	public ImageIcon getFront(){
		return front;
	}

	/**
	 * Accessor for the back image of the card
	 * 
	 * @return The back image of the card
	 */
	public ImageIcon getBack(){
		return back;
	}
	
	/**
	 * Accessor for the permanent boolean of the card
	 * 
	 * @return The permanent boolean of the card
	 */
	public boolean getPermanent(){
		return permanent;
	}
	
	/**
	 * Accessor for the name of the card
	 * @return The name of the card
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Accessor for the first description of the card
	 * 
	 * @return The first description of the card
	 */
	public String getFDescription(){
		return firstDescription;
	}
	
	/**
	 * Accessor for the second description of the card
	 * 
	 * @return The second description of the card
	 */
	public String getSDescription(){
		return secondDescription;
	}
	
	/**
	 * Accessor for the value of the card
	 * 
	 * @return The value of the card
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * Accessor for the cost of the card
	 * 
	 * @return The cost of the card
	 */
	public int getCost(){
		return cost;
	}
}
