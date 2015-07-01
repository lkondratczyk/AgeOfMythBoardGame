import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 5/4/15
 * 
 * The god card Dionysus, which is an Gather card that allows the user to
 * to gather extra food, and be the only to gather
 */
public class Dionysus extends GatherCard{
	
	/**
	 * The default constructor for the god card Dionysus
	 */
	Dionysus() {
		super(new ImageIcon("Dionysus.png"), new ImageIcon("Random.png"), false,
				"Dionysus", "Gather food only.", "Pay 1 favor" +
				"for +1 food per tile, while other players gather nothing", 1, 
				GatherCard.GatherType.FOOD, null); 
		this.setCost(1);
	}
	
	/**
	 * Overrides execute in Building card to allow special god ability
	 */
	public void execute(Game game){
		ArrayList<String> tollMessage = new ArrayList<String>();
		UserInterface<String> handleToll = new UserInterface<String>();
		//interface to handle toll
		tollMessage.add("Pay one favor to prove you are worthy.");
		String temp = handleToll.provideTextOptions("Pay tribute to your god?", 
				game, tollMessage, "Run away in fear...");
		if(temp != null){ //null if player opts out
			if(game.getActivePlayer().getWallet()[3] < getCost()){
				//execute like normal gather if player can't afford
				super.execute(game);
			}
			else{
				//deduct tribute and execute god power
				game.getActivePlayer().getWallet()[3] -= getCost();
				game.getBank()[3] += getCost();
				
				/*copied from build card*/
				int numberOfVillagers = 
						distributeBuildingBonuses(game);
				
				clearProductionTileVillagers(game);
				
				setVillagers(game, numberOfVillagers);
				/*end copy from build card*/
				
				gatherByFood(game);				
			}
		}
		else{
			//execute regular gather card
			super.execute(game);
		}
		if(game.getBank()[0] - game.getActivePlayer().getProduction().size() < 0){
			game.getActivePlayer().getWallet()[0] += game.getBank()[0];
			game.getBank()[0] = 0;
		}
		else{
			game.getActivePlayer().getWallet()[0] += game.getActivePlayer().getProduction().size();
			game.getBank()[0] -= game.getActivePlayer().getProduction().size();
		}
	}
}
