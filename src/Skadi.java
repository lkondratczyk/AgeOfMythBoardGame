import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 5/4/15
 * 
 * The god card Skadi, which is a Gather Card with the added feature of allowing
 * an activePlayer to be the only one to chose
 */
public class Skadi extends GatherCard{
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
	Skadi() {
		super(new ImageIcon("Skadi.png"), new ImageIcon("Random.png"), false,
				"Skadi", "Resource type or terrain type.", "Pay 1 favor so" +
				" other players gather nothing", 1, 
				GatherCard.GatherType.RESOURCE, GatherCard.GatherType.TERRAIN); 
		this.setCost(1);
	}
	
	/**
	 * Overrides execute in Building card to allow special god ability
	 */
	public void execute(Game game){
		ArrayList<String> tollMessage = new ArrayList<String>();
		UserInterface<String> handleToll = new UserInterface<String>();
		//interface for toll handling
		tollMessage.add("Pay one favor to prove you are worthy.");
		String temp = handleToll.provideTextOptions("Pay tribute to your god?", game, 
				tollMessage, "Run away in fear...");
		if(temp != null){ //true if player didn't opt out
			if(game.getActivePlayer().getWallet()[3] < getCost()){
				//execute as regular gather if player can't afford
				super.execute(game);
			}
			else{
				//transact cost and execute god power
				game.getActivePlayer().getWallet()[3] -= getCost();
				game.getBank()[3] += getCost();
				
				/* copied from gather card*/
				int numberOfVillagers = 
						distributeBuildingBonuses(game);
				
				clearProductionTileVillagers(game);
				
				setVillagers(game, numberOfVillagers);
				
				GatherCard.GatherType gatherTypeChoice = selectGatherType(game);
				//player selected to gather by terrain
				if(gatherTypeChoice == GatherCard.GatherType.TERRAIN){
					ArrayList<ProductionTile.Terrain> terrainTypes = 
							new ArrayList<ProductionTile.Terrain>();
					terrainTypes.add(ProductionTile.Terrain.HILLS); 
					terrainTypes.add(ProductionTile.Terrain.MOUNTAINS); 
					terrainTypes.add(ProductionTile.Terrain.FOREST); 
					terrainTypes.add(ProductionTile.Terrain.DESERT); 
					terrainTypes.add(ProductionTile.Terrain.FERTILE); 
					terrainTypes.add(ProductionTile.Terrain.SWAMP);
					//create interface for selecting terrain type
					UserInterface<ProductionTile.Terrain> manageTerrainSelection = 
							new UserInterface<ProductionTile.Terrain>();
					ProductionTile.Terrain resourceChoice = manageTerrainSelection.provideMenuOptions("Pick a terrain type: ", 
							game, terrainTypes, null);
					gatherByTerrain(game, resourceChoice);
				}
				//player selected to gather by resource
				if(gatherTypeChoice == GatherCard.GatherType.RESOURCE){
					ArrayList<String> resources = new ArrayList<String>();
					resources.add("food");
					resources.add("wood");
					resources.add("gold");
					resources.add("favor");
					UserInterface<String> handleResourceSelection = 
							new UserInterface<String>();
					String resource = handleResourceSelection.provideTextOptions("Pick a resource type: ", game, resources, null);

					switch(resource){
					case("food"):
						gatherByResource(game, 0);
						break;
					case("wood"):
						gatherByResource(game, 1);
						break;
					case("gold"):
						gatherByResource(game, 2);
						break;
					case("favor"):
						gatherByResource(game, 3);
						break;
					default:
							break;
					}
				}
				/*End copy from gather card*/
			}
		}
		else{
			//execute as regular gather if player opted out
			super.execute(game);
		}

	}
}


