/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * The template for an AoM Build Card attributes
 */
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class BuildCard extends Card {
	
	/**
     * The constructor for creating a Build Card with image
     * 
     * @param front The Card's front image
     * @param back The Card's back image
     * @param permanent True if Card is permanent
     * @param name The Card name
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
     * @param value The max number of builds
     * @param cost The cost to play the Card
     */
	public BuildCard(ImageIcon front, ImageIcon back,
			boolean permanent, String name, String firstDescription,
			String secondDescription, int value, int cost) {
		super(front, back, permanent, name, firstDescription,
				secondDescription, value, cost);
	}
	
	/**
	 * Checks to see if a wallet can cover a cost
	 * 
	 * @param wallet The Player's funds
	 * @param cost An action's resource requirement
	 * @return True if the player can afford the item
	 */
	public boolean canAffordByResource(int[] wallet, int[] cost){
		for(int i = 0; i < 4; i++){
			if(wallet[i] < cost[i]){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * An algorithm that factors in discounts when checking to see if a Player
	 * can afford an item
	 * 
	 * @param wallet The Player's funds
	 * @param cost The cost of an item
	 * @param discount The resource count of a discount
	 * @param index The resource being discounted (starts at zero always)
	 * @return
	 */
	public boolean canAffordWithDiscount(int[] wallet, int[] cost, 
			int discount, int index){
		if(canAffordByResource(wallet, cost)){
			return true;
		}
		else if((discount) > 0 && index == 4){
			return canAffordWithDiscount(wallet, cost, discount - 1, 0);
		}
		else if(discount > 0){
			return canAffordWithDiscount(wallet, cost, discount, index + 1);
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * Create an ArrayList of Buildings that a Player has the option to build
	 * 
	 * @param player The player playing the Build card
	 * @param discount The current discount applicable
	 * @return The ArrayList of Buildings the Player can afford
	 */
	public ArrayList<Building> populateAffordable(Player player, int discount){
		ArrayList<Building> affordable = new ArrayList<Building>();
		boolean houseChecked = false;
		
		if(discount == 0){ //checking if Nephthys is used
			for(int i = 0; i < player.getCity().size(); i++){
				if((player.getCity().get(i)).getType() == Building.Type.QUARRY){
					discount = 1;
				}
			}
		}
		//check every building for affordability
		for(int i = 0; i < player.getBuildPool().size(); i++){
			Building temp = player.getBuildPool().get(i);
			int[] copyCost = new int[4];
			int copyDiscount = discount;
			if(temp != null){
				for(int j = 0; j < 4; j++){
					copyCost[j] = temp.getCost()[j];
				}
				
				for(int j = 0; j < 4; j++){
					while(j < 4 && player.getWallet()[j] < copyCost[j]){
						if(copyDiscount > 0){
							copyCost[j] --;
							copyDiscount --;
						}
						else{
							j = 4;
						}
					}
					if(j == 3){
						if(temp.getType() == Building.Type.HOUSE){
							if(!(houseChecked)){
								houseChecked = true;
								affordable.add(temp);
							}
						}
						else{
							affordable.add(temp);
						}
					}
				}
			}
		}
		return affordable;
	}
	
	/**
	 * Handles the purchase of a building, including moving the building,
	 * calculating the funds changed and returning the discount applied
	 * 
	 * @param game The game being altered by the purchase
	 * @param selection The Building being purchased
	 * @param refundOptions A pool of resources available for refund
	 * @return The amount of the discount that was necessary to apply
	 */
	public int buildBuilding(Game game, 
			Building selection, int[] refundOptions){
		int discountUsed = 0;
		for(int i = 0; i < 4; i ++){
			if(game.getActivePlayer().getWallet()[i] < selection.getCost()[i]){
				int temp = selection.getCost()[i] - game.getActivePlayer().getWallet()[i];
				discountUsed = discountUsed + temp;
				game.getActivePlayer().getWallet()[i] = 0;
				game.getBank()[i] = game.getBank()[i] + selection.getCost()[i] - temp;
				refundOptions[i] = refundOptions[i] + selection.getCost()[i] - temp;
			}
			else{
				game.getActivePlayer().getWallet()[i] = 
						game.getActivePlayer().getWallet()[i] - selection.getCost()[i];
				game.getBank()[i] = game.getBank()[i] + selection.getCost()[i];
				refundOptions[i] = refundOptions[i] + selection.getCost()[i];
			}
		}
		addBuilding(game, selection);
		return discountUsed;
	}
	
	
	
	/**
	 * This method executes a sequence of events in the Game that represent
	 * playing a BuildCard
	 * 
	 * @param game The Game being worked on by the Card
	 */
	
	@Override
	public void execute(Game game) {
		if(game.getActivePlayer().getCity().size() < 16){
			//creating parallel string array for resources
			ArrayList<String> resources = new ArrayList<String>();
			resources.add("food");
			resources.add("wood");
			resources.add("gold");
			resources.add("favor");
			int discount = 0;
			//discount on buildings if quarry owned
			for(int i = 0; i < game.getActivePlayer().getCity().size(); i++){
				if((game.getActivePlayer().getCity().get(i)).getType() == 
						Building.Type.QUARRY){
					discount = 1;
				}
			}
			int builds = getValue();//number of builds available
			
			for(int i = 0; i < builds; i++){ //repeat until builds exhausted
				if(game.getActivePlayer().getCity().size() < 16){
					int[] refundAvailable = {0, 0, 0, 0}; //refundable currency
					int discountUsed = 0;
					UserInterface<Building> buildingSelect = 
							new UserInterface<Building>();
					//create a list of affordable buildings given wallet/discount
					ArrayList<Building> options = populateAffordable(
							game.getActivePlayer(), discount);
					//a user interface for selecting buildings
					Building selection = buildingSelect.provideMenuOptions(
							"Please select a building to build from the " + 
							"following:", game, options, 
							"Pass remainder of turn");
					if(selection == null){
						i = builds; //break loop if pass option selected
					}
					else{
						//track the discount used for unaffordable buildings
						discountUsed = discountUsed + buildBuilding(
								game, selection, refundAvailable);
					}
					//loop until discount is entirely used
					for(int j = 0; j < discount - discountUsed; j++){
						ArrayList<String> refundOptions = 
								new ArrayList<String>();
						//populate list of refundable currency
						for(int k = 0; k < 4; k++){
							if(refundAvailable[k] > 0){
								refundOptions.add(resources.get(k));
							}
						}
						//looped interface for selecting refund
						if(refundOptions.size() > 0){
							UserInterface<String> refundSelect = 
									new UserInterface<String>(); 
							String refund = refundSelect.provideTextOptions(
									"Select a refund: ", game,
									refundOptions, null);
							//translate selected string into currency
							//and transact refund
							switch(refund){
							case("food"):
								game.getActivePlayer().getWallet()[0] += 1;
								game.getBank()[0] -= 1;
								refundAvailable[0] -= 1;
								break;
							case("wood"):
								game.getActivePlayer().getWallet()[1] += 1;
								game.getBank()[1] -= 1;
								refundAvailable[1] -= 1;
								break;
							case("gold"):
								game.getActivePlayer().getWallet()[2] += 1;
								game.getBank()[2] -= 1;
								refundAvailable[2] -= 1;
								break;
							case("favor"):
								game.getActivePlayer().getWallet()[3] += 1;
								game.getBank()[3] -= 1;
								refundAvailable[3] -= 1;
								break;
							default:
									break;
							}
						}
					}
				}
				else{
					i = builds; //finish building
				}
			}
		}
	}
}