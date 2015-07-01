import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * The template for an AoM Explore Card attributes
 */
public class ExploreCard extends Card{
	/**
	 * The constructor for creating a Gather Card with image
	 *  
     * @param front The Card's front image
     * @param back The Card's back image
     * @param permanent True if Card is permanent
     * @param name The Card name
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
     * @param value The added number of tiles
	 */
	public ExploreCard(ImageIcon front, ImageIcon back,
			boolean permanent, String name, String firstDescription,
			String secondDescription, int value) {
		super(front, back, permanent, name, firstDescription,
				secondDescription, value, 0);
	}
	
	/**
	 * Executes the Card's effect on the Game
	 * 
	 * @param game The game being affected by the card
	 */
	public void execute(Game game){
			int numberOfPlayers = 3;
			RandomSelection<ProductionTile> selector = 
					new RandomSelection<ProductionTile>();
			ArrayList<ProductionTile> options = new ArrayList<ProductionTile>();
			//add random production tiles from the pool
			for(int i = 0; i < numberOfPlayers + getValue(); i++){
				options.add(selector.getRandomFromList(
						game.getProductionPool(), true, 0));
			}
			//loop interface for tile selection until every player picks
			for(int j = 0; j < numberOfPlayers; j++){
				ArrayList<ProductionTile> playerOptions = 
						new ArrayList<ProductionTile>();
				//add options to player selection if they have terrain avail
				for(int k = 0; k < options.size(); k++){
					if(game.getActivePlayer().getTerrain().contains((
							options.get(k)).getType())){
						playerOptions.add(options.get(k));
					}
				}
				//interface for selection
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
				//move to next player
				game.setActivePlayer(game.getActivePlayer().getNext());
					
			}
			//return unselected tiles
			for(int i = 0; i < options.size(); i++){
				game.getProductionPool().add(options.get(i));
			}
		
	}
}
