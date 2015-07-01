import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * The template for an AoM Next Age Card attributes
 */
public class NextAge extends Card{
	private boolean discount;
	
	/**
	 * The constructor for a Next Age Card
	 * 
     * @param front The Card's front image
     * @param back The Card's back image
     * @param permanent True if Card is permanent
     * @param name The Card name
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
     * @param cost The cost to play the Card
	 * @param discount The discount for the Card
	 */
	public NextAge(ImageIcon front, ImageIcon back,
			boolean permanent, String name, String firstDescription,
			String secondDescription, int cost, boolean discount) {
		super(front, back, permanent, name, firstDescription,
				secondDescription, 0, cost);
		this.discount = discount;
	}
	
	
	/**
	 * Uses the Player's current age to determine the cost to pass to canAfford
	 *  
	 * @param game The game being altered
	 * @return 0 if the Player can't afford it/ is at the max age. Returns the 
	 * cost of each resource otherwise
	 */
	public int canAffordNextAge(Game game){
		int cost;
		switch(game.getActivePlayer().getAge()){
		case ARCHAIC:
			cost = 4;
			break;
		case CLASSICAL: 
			cost = 5;
			break;
		case HEROIC:
			cost = 6;
			break;
		default:
			cost = 0;
			break;
		}
		
		cost -= (discount)?1:0;
		
		if(cost > 0){
			for(int i = 0; i < 4; i++){
				if(game.getActivePlayer().getWallet()[i] < cost){
					return 0;				
				}
			}
			return cost;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * Executes the Card's effect on the Game
	 * 
	 * @param game The game being affected by the card
	 */
	public void execute(Game game){
		int cost = canAffordNextAge(game);
		if(cost > 0){
			ArrayList<String> useCard = new ArrayList<String>();
			//interface for paying for the next age or opting out
			UserInterface<String> manageCardUse = 
					new UserInterface<String>();
			String choice;
			useCard.add("Pay " + cost + 
					" of each resource to advace to the next age.");
			choice = manageCardUse.provideTextOptions("Advance to the next age? ", 
					game, useCard, "Burn this card instead.");
			if(choice != null){ //true if player didn't opt out
				for(int i = 0; i < 4; i++){
					//transact fee
					game.getActivePlayer().getWallet()[i] -= cost;
					game.getBank()[i] += cost;
				}
				//move to next age
				switch(game.getActivePlayer().getAge()){
				case ARCHAIC:
					game.getActivePlayer().setAge(Player.Age.CLASSICAL);
					break;
				case CLASSICAL:
					game.getActivePlayer().setAge(Player.Age.HEROIC);
					break;
				case HEROIC:
					game.getActivePlayer().setAge(Player.Age.MYTHIC);
					//give player Wonder if they advance here
					game.getActivePlayer().getBuildPool().add(new Building(
							Building.Type.THE_WONDER, new ImageIcon(
							"TheWonder.png"), 10,10,10,10));
					break;
				default:
					break;
				}
			}
		}
	}
	
	public void setDiscount(boolean discount){
		this.discount = discount;
	}
	
	public boolean setDiscount(){
		return this.discount;
	}
}
