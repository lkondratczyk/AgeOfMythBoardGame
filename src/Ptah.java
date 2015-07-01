import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 5/4/15
 * 
 * The god card Ptah, which is a Gather Card with the added feature of allowing
 * an activePlayer to pick twice before anyone
 */
public class Ptah extends ExploreCard{
	
	/**
	 * The constructor for the god card Ptah
	 * 
     * @param front The Card's front image
     * @param back The Card's back image
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
     * @param value The max number of builds
     * @param cost The cost to play the Card
	 */
	Ptah(ImageIcon front, ImageIcon back, String name, String firstDescription,
			String secondDescription) {
		super(front, back, false, name, firstDescription,
				secondDescription, 2);
		this.setCost(1);
	}
	
	/**
	 * Overrides execute in Building card to allow special god ability
	 */
	public void execute(Game game){
		int numberOfPlayers = 3;
		ArrayList<String> tollMessage = new ArrayList<String>();
		UserInterface<String> handleToll = new UserInterface<String>();
		//interface to handle toll message
		tollMessage.add("Pay one favor to prove you are worthy.");
		String temp = handleToll.provideTextOptions("Pay tribute to your god?", 
				game, tollMessage, "Run away in fear...");
		if(temp != null){ //true if player didn't opt out
			if(game.getActivePlayer().getWallet()[3] < 1){
				//execute as regular card if player can't afford
				super.execute(game);
			}
			else{
				//transact cost and used god power
				game.getActivePlayer().getWallet()[3] -= 1;
				game.getBank()[3] += 1;
				RandomSelection<ProductionTile> selector = 
						new RandomSelection<ProductionTile>();
				ArrayList<ProductionTile> options = 
						new ArrayList<ProductionTile>();
				//populate options with random production tiles from the pool
				for(int i = 0; i < numberOfPlayers + getValue(); i++){
					options.add(selector.getRandomFromList(game.getProductionPool(), 
							true, 0));
				}
				for(int j = 0; j < numberOfPlayers + 1; j++){
					ArrayList<ProductionTile> playerOptions = 
							new ArrayList<ProductionTile>();
					//present players only with tiles having terrain available
					for(int k = 0; k < options.size(); k++){
						if(game.getActivePlayer().getTerrain().contains((
								options.get(k)).getType())){
							playerOptions.add(options.get(k));
						}
					}
					if(options.size() > 0){
						//making an interface for selecting production tiles
						UserInterface<ProductionTile> ui = 
								new UserInterface<ProductionTile>();
						ProductionTile selected = ui.provideMenuOptions(
								"Select a resource tile :", 
								game, playerOptions, "Pass");
						if(selected != null){
							game.getActivePlayer().getProduction().add(selected);
							options.remove(selected);
							game.getActivePlayer().getTerrain().remove(
									selected.getType());
						}
					}
					//switched to next player after second pick
					if(j != 0){
						game.setActivePlayer(game.getActivePlayer().getNext());
					}						
				}
				//return tiles
				for(int i = 0; i < options.size(); i++){
					game.getProductionPool().add(options.get(i));
				}
			}
		}
		else{
			//execute as regular if unaffordable
			super.execute(game);
		}

	}
}

