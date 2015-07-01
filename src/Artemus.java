import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 5/4/15
 * 
 * The god card Artemus, which is an explore card that allows the user to
 * select extra production tiles
 */
public class Artemus extends ExploreCard{
	
	/**
	 * The constructor for the god card Artemus
	 * 
     * @param front The Card's front image
     * @param back The Card's back image
     * @param name	The name of the card
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
	 */
	Artemus(ImageIcon front, ImageIcon back, String name, 
			String firstDescription, String secondDescription) {
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
		
		//creating payment interface
		tollMessage.add("Pay one favor to prove you are worthy.");
		String temp = handleToll.provideTextOptions("Pay tribute to your god?", 
				game, 
				tollMessage, "Run away in fear...");
		if(temp != null){ //null if opted out
			if(game.getActivePlayer().getWallet()[3] < 1){
				//player can't afford if here
				super.execute(game); //execute as a regular explore card
			}
			else{ //deduct payment and use power
				game.getActivePlayer().getWallet()[3] -= 1;
				game.getBank()[3] += 1;
				RandomSelection<ProductionTile> selector = 
						new RandomSelection<ProductionTile>();
				ArrayList<ProductionTile> options = 
						new ArrayList<ProductionTile>();
			
				for(int i = 0; i < numberOfPlayers + getValue(); i++){
					//populate options from pool
					options.add(selector.getRandomFromList(
							game.getProductionPool(), true, 0));
				}
				for(int j = 0; j < numberOfPlayers + 1; j++){
					ArrayList<ProductionTile> playerOptions = 
							new ArrayList<ProductionTile>();
					//creating a list the player can actually choose from
					for(int k = 0; k < options.size(); k++){
						//check if player has terrain available before adding
						if(game.getActivePlayer().getTerrain().contains((
								options.get(k)).getType())){
							playerOptions.add(options.get(k));
						}
					}
					if(options.size() > 0){
						UserInterface<ProductionTile> ui = 
								new UserInterface<ProductionTile>();
						//use choice list to create interface
						ProductionTile selected = ui.provideMenuOptions(
								"Select a resource tile :", game, 
								playerOptions, "Pass");
						if(selected != null){//player didn't pass
							game.getActivePlayer().getProduction().add(selected);
							options.remove(selected);
							game.getActivePlayer().getTerrain().remove(
									selected.getType());
						}
					}
					if(j != 0){ //skipping the first gives 2 selections
						game.setActivePlayer(game.getActivePlayer().getNext());
					}						
				}
				//add unused tiles back to pool
				for(int i = 0; i < options.size(); i++){
					game.getProductionPool().add(options.get(i));
				}
				
			}
		}
		else{
			super.execute(game); //execute as a regular explore card
		}

	}
}

