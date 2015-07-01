import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/12/15
 * 
 * The god card Horus, which is a Build Card with the added feature of allowing
 * an activePlayer to destroy one of any Player's building
 */
public class Horus extends  BuildCard{
	/**
	 * The constructor for the god card Horus
	 * 
     * @param front The Card's front image
     * @param back The Card's back image
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
     * @param value The max number of builds
     * @param cost The cost to play the Card
	 */
		public Horus(ImageIcon front, ImageIcon back, 
				String firstDescription, String secondDescription, 
				int value, int cost){
		super(front, back, false, "Horus", firstDescription,	
				secondDescription, value, cost);
	}
	
	/**
	 * Overrides execute in Building card to allow special god ability
	 */
	public void execute(Game game){
		int numberOfPlayers = 3;
		Building cityDestroy;
		ArrayList<String> tollMessage = new ArrayList<String>();
		ArrayList<Player> playerTargets = new ArrayList<Player>();
		//ArrayList<String> targetNames = new ArrayList<String>();
		UserInterface<String> handleToll = new UserInterface<String>();
		
		tollMessage.add("Pay one favor to prove you are worthy.");
		//interface to handle toll payment
		String temp = handleToll.provideTextOptions("Pay tribute to your god?", game, 
				tollMessage, "Run away in fear...");
		if(temp != null){ //null if user opts out
			if(game.getActivePlayer().getWallet()[3] < 1){
				//do nothing if player can't afford
			}
			else{
				for(int i = 0; i < numberOfPlayers; i++){
					//create a list of player "targets"
					playerTargets.add(game.getActivePlayer());
					game.setActivePlayer(game.getActivePlayer().getNext());	
				}
				//transact payment and execute god power
				game.getActivePlayer().getWallet()[3] -= 1;
				game.getBank()[3] += 1;
				//interface for selecting a player "target"
				UserInterface<Player> selectPlayerTargets = new UserInterface<Player>();
				Player playerDestroy = selectPlayerTargets.provideMenuOptions(
						"Select the victim of a cataclysm.", game, playerTargets, 
						"Demand mercy for your tribute");
				if(playerDestroy == null){ //null if player opts out
					//do nothing if player opts out
				}
				else{
					//interface for selecting "target's" buildings
					UserInterface<Building> handleTargetCity = new UserInterface<Building>();
					cityDestroy = handleTargetCity.provideMenuOptions("Which of " + 
							playerDestroy.getName() + "'s cites"	+ 
							" shall suffer my wrath?", game, playerDestroy.getCity(),
							"Demand mercy for your tribute");
					//return building to pool
					playerDestroy.getBuildPool().add(cityDestroy);
					playerDestroy.getCity().remove(cityDestroy);
				}
			}
		}	
		
		super.execute(game);
	}
}
