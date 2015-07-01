import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/12/15
 * 
 * The god card Nepthys, which is a Build Card with the added feature of allowing
 * an activePlayer to reduce the cost of buildings purchased with the card by
 * two of its resource costs
 */
public class Nephthys extends BuildCard{
	
	/**
	 * The constructor for the god card Nephthys
	 * 
     * @param front The Card's front image
     * @param back The Card's back image
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
     * @param value The max number of builds
     * @param cost The cost to play the Card
	 */
	public Nephthys(ImageIcon front, ImageIcon back, 
			String firstDescription, String secondDescription, 
			int value, int cost){
		super(front, back, false, "Nephthys", firstDescription,	
			secondDescription, value,cost);
	}
	
	/**
	 * Overrides execute in Building card to allow special god ability
	 */
	public void execute(Game game){
		
		if(game.getActivePlayer().getCity().size() < 16){
			ArrayList<String> tollMessage = new ArrayList<String>();
			UserInterface<String> handleToll = new UserInterface<String>();
			//interface to handle toll payment
			tollMessage.add("Pay " + getCost() + " favor to prove you are worthy.");
			String temp = handleToll.provideTextOptions("Pay tribute to your god?", game, 
					tollMessage, "Run away in fear...");
			if(temp != null){//null if player opts out
				if(game.getActivePlayer().getWallet()[3] < 2){
					super.execute(game);
				}
				else{
					game.getActivePlayer().getWallet()[3] -= 2;
					game.getBank()[3] += 2;

					ArrayList<String> resources = new ArrayList<String>();
					resources.add("food");
					resources.add("wood");
					resources.add("gold");
					resources.add("favor");
					int discount = 2; //extra discount
					for(int i = 0; i < game.getActivePlayer().getCity().size(); i++){
						if((game.getActivePlayer().getCity().get(i)).getType() == Building.Type.QUARRY){
							discount = 3; //max discount
						}
					}
					int builds = getValue();
					/*code from Build card  */
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
						/*end code copied from Build card*/
					}
				}
			}
		}
	}
}