/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * The template for an AoM building
 */

import javax.swing.ImageIcon;

public class Building implements GameItem<Building>{
	
	protected enum Type{HOUSE, MARKET, STOREHOUSE, QUARRY, MONUMENT, GRANARY,
							  WOOD_WORKSHOP, GOLD_MINT, GREAT_TEMPLE, THE_WONDER
	}
	private Type type;
	private int[] cost = new int[4]; //[food, wood, gold, favor]
	private ImageIcon image;
	
	/**
     * The constructor for cloning a Building
     * 
     * @param building The building to be copied
     */
	public Building(Building building){
		type = building.type;
		cost = building.cost;
		image = building.image;
	}
	
	/**
     * The constructor for creating a Building without image
     * 
     * @param type The the Building type
     * @param food The Building food cost
     * @param wood The Building wood cost
     * @param gold The Building gold cost
     * @param favor The Building favor cost
     */
	public Building(Building.Type type, ImageIcon image, int food, int wood, 
			int gold, int favor){
		this.type = type;
		this.image = image;
		cost[0] = food;
		cost[1] = wood;
		cost[2] = gold;
		cost[3] = favor;
	}
	
	/**
	 * Overrides Objec.toString to allow to print building
	 * 
	 * @return A String for printing
	 */
	@Override
	public String toString(){
		return (printBuildingName(type) + "(" + printBuildingCost(cost) + ")");
		
	}
	
	/**
	 * Converts Building Type to String for printing
	 * 
	 * @param buildingType The the Building Type to be converted
     * @return String representing Building Type
	 */
	public String printBuildingName(Building.Type buildingType){
		switch(buildingType){
		
			case HOUSE: return "House";
			case MARKET: return "Market";
			case STOREHOUSE: return "Storehouse";
			case QUARRY: return "Quarry";
			case MONUMENT: return "Monument";
			case GRANARY: return "Granary";
			case WOOD_WORKSHOP: return "Wood Workshop";
			case GOLD_MINT: return "Gold Mint";
			case GREAT_TEMPLE: return "Great Temple";
			case THE_WONDER: return "THE WONDER";
			default: return "None";
		}
	}
	
	/**
	 * Converts Building cost to String for printing
	 * 
	 * @param cost The the Building cost to be printed
     * @return String representing Building cost
	 */
	public String printBuildingCost(int[] cost){
		String displayCost = "";
		String resource;
		
		for(int i = 0; i < 4; i++){
				switch(i){
					case(0): resource = "food";
					case(1): resource = "wood";
					case(2): resource = "gold";
					case(3): resource = "favor";
					default: resource = "";
				};
				displayCost = displayCost + cost[i] + resource;
						if(i<3){
							displayCost = displayCost + ", ";
						}
			//}
		}
		return displayCost;
	}
	
	/**
	 * Implements displayItem() from GameItem, used for displaying
	 * on the graphical interface
	 */
	public ImageIcon displayItem(Player owner){
		return image;
	}

	/**
	 * Mutator for building type
	 * 
	 * @param type The building type (enumerated) to be set
	 */
	public void setType(Type type){
		this.type = type;
	}
	
	/**
	 * Mutator for building cost
	 * 
	 * @param cost The building cost to be set 
	 */
	public void setCost(int[] cost){
		this.cost = cost;
	}

	/**
	 * Mutator for building image
	 * 
	 * @param cost The building image to be set 
	 */
	public void setImage(ImageIcon image){
		this.image = image;
	}
	
	/**
	 * Accessor for the building type
	 * 
	 * @return The current building type
	 */
	public Type getType(){
		return this.type;
	}
	
	/**
	 * Accessor for the building cost
	 * 
	 * @return The current building cost
	 */
	public int[] getCost(){
		return this.cost;
	}

	/**
	 * Accessor for the building image
	 * 
	 * @return The current building image
	 */
	public ImageIcon getImage(){
		return this.image;
	}
}
