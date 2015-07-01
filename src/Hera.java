import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/12/15
 * 
 * The god card Hera, which is a build card that grants a player a house
 */
public class Hera extends BuildCard{
	
	/**
	 * The constructor for the god card Hera
	 * 
     * @param front The Card's front image
     * @param back The Card's back image
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
     * @param value The max number of builds
     * @param cost The cost to play the Card
	 */
	public Hera(ImageIcon front, ImageIcon back, 
			String firstDescription, String secondDescription, 
			int value, int cost){
		super(front, back, false, "Hera", firstDescription,	
			secondDescription, value,cost);
	}
	
	/**
	 * Overrides execute in Building card to allow special god ability
	 */
	public void execute(Game game){
		ArrayList<String> tollMessage = new ArrayList<String>();
		Building houseToAdd = null;
		//check that city is not too large
		if(game.getActivePlayer().getCity().size() == 16){
			super.execute(game);
		}
		else{
			for(int i = 0; i < game.getActivePlayer().getBuildPool().size(); i++){
				if(game.getActivePlayer().getBuildPool().get(i).getType() ==
						Building.Type.HOUSE){
					houseToAdd = game.getActivePlayer().getBuildPool().get(i);
					i = game.getActivePlayer().getBuildPool().size();
				}
			}
			if(houseToAdd != null){
				//player has a spot for a house and can pay favor for it
				tollMessage.add("Pay one favor to prove you are worthy.");
				//interface to handle toll
				UserInterface<String> handleToll = new UserInterface<String>();
				String temp = handleToll.provideTextOptions(
						"Pay tribute to your god?", game, 
						tollMessage, "Run away in fear...");
				if(temp != null){// null if player opts out
					if(game.getActivePlayer().getWallet()[3] < 1){
						//player can't afford if here
					}
					else{
						//player can afford, deduct favor and add building
						game.getActivePlayer().getWallet()[3] -= 1;
						game.getBank()[3] +=1;
						addBuilding(game, houseToAdd);
						super.execute(game);
					}
				}
				else{
					super.execute(game);
				}
			}

		}			
	}
}
