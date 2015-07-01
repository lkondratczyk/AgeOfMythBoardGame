import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 5/4/15
 * 
 * The god card Baldr, which is an explore card that allows the user to
 * to be the only to explore
 */
public class Baldr extends ExploreCard{
	
	
	/**
	 * The constructor for the god card Baldr
	 * 
     * @param front The Card's front image
     * @param back The Card's back image
     * @param name The Card's name
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
	 */
	Baldr(ImageIcon front, ImageIcon back, String name, String firstDescription,
			String secondDescription) {
		super(front, back, false, name, firstDescription,
				secondDescription, 0);
		this.setCost(2);
	}
	
	/**
	 * Overrides execute in Building card to allow special god ability
	 */
	public void execute(Game game){
		int numberOfPlayers = 3;
		ArrayList<String> tollMessage = new ArrayList<String>();
		UserInterface<String> handleToll = new UserInterface<String>();
		
		//interface to handle toll payment
		tollMessage.add("Pay two favor to prove you are worthy.");
		String temp = handleToll.provideTextOptions("Pay tribute to your god?", 
				game, tollMessage, "Run away in fear...");
		if(temp != null){ //null if player opted out
			if(game.getActivePlayer().getWallet()[3] < 2){
				//if here, player can't afford
				super.execute(game);
			}
			else{
				//deduct cost and use god power
				game.getActivePlayer().getWallet()[3] -= 2;
				game.getBank()[3] += 2;
				RandomSelection<ProductionTile> selector = 
						new RandomSelection<ProductionTile>();
				ArrayList<ProductionTile> options = 
						new ArrayList<ProductionTile>();
				//populating options from pool
				for(int i = 0; i < numberOfPlayers + getValue(); i++){
					options.add(selector.getRandomFromList(
							game.getProductionPool(), true, 0));
				}
				
				ArrayList<ProductionTile> playerOptions = 
						new ArrayList<ProductionTile>();
				//adding options to choices if player owns terrain available
				for(int k = 0; k < options.size(); k++){
					if(game.getActivePlayer().getTerrain().contains((
							options.get(k)).getType())){
						playerOptions.add(options.get(k));
					}
				}
				//the interface call for handling player selections of tiles
				if(options.size() > 0){
					UserInterface<ProductionTile> ui = 
							new UserInterface<ProductionTile>();
					ProductionTile selected = ui.provideMenuOptions(
							"Select a resource tile :", game, 
							playerOptions, "Pass");
					if(selected != null){
						game.getActivePlayer().getProduction().add(selected);
						options.remove(selected);
						game.getActivePlayer().getTerrain().remove(
								selected.getType());
					}
				}
				//return unused tiles to pool
				for(int i = 0; i < options.size(); i++){
					game.getProductionPool().add(options.get(i));
				}
			}
		}
		else{
			super.execute(game); //skip power and use as regular explore card
		}

	}
}
		
