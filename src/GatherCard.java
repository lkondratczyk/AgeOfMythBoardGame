import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * The template for an AoM Gather Card attributes
 */
public class GatherCard extends Card{
	protected enum GatherType implements EnumToImage<GatherCard.GatherType>{
		TERRAIN, RESOURCE, ALL_RESOURCES, FOOD;
		public ImageIcon enumToImage(GatherCard.GatherType enumerated){
			switch(enumerated){
			case TERRAIN:
				return new ImageIcon("Terrain.png");
			case RESOURCE:
				return new ImageIcon("Resource.png");
			case ALL_RESOURCES:
				return new ImageIcon("AllResources.png");
			case FOOD:
				return new ImageIcon("Food.png");
			default:
				return new ImageIcon("Terrain.png");
			}
		}
	};
	ArrayList<GatherCard.GatherType> gatherBy;
	
	/**
	 * The constructor for creating a Gather Card with image
	 *  
     * @param front The Card's front image
     * @param back The Card's back image
     * @param permanent True if Card is permanent
     * @param name The Card name
     * @param firstDescription The main Card description
     * @param secondDescription The secondary Card description
     * @param cost The cost to play the Card
	 * @param gatherBy1 The first method of gathering
	 * @param gatherBy2 The second method of gathering
	 */
	public GatherCard(ImageIcon front, ImageIcon back,
			boolean permanent, String name, String firstDescription,
			String secondDescription, int cost, 
			GatherCard.GatherType gatherBy1, GatherCard.GatherType gatherBy2) {
		super(front, back, permanent, name, firstDescription,
				secondDescription, 0, cost);
		this.gatherBy = new ArrayList<GatherCard.GatherType>();
		(this.gatherBy).add(gatherBy1);
		if(gatherBy2 != null)
			this.gatherBy.add(gatherBy2);
	}
	
	/**
	 * Distributes bonuses to the card user for their resource bonus buildings
	 * 
	 * @param game The current game state
	 * 
	 * @return The number of villagers counted
	 */
	public int distributeBuildingBonuses(Game game){
		int numberOfVillagers = 0;
		for(int i = 0; i < game.getActivePlayer().getCity().size(); i++){
			int bonusIndex = 5;
			switch(game.getActivePlayer().getCity().get(i).getType()){
				case HOUSE:
					numberOfVillagers++;
					break;
				case MONUMENT:
					bonusIndex = 3;
					break;
				case GRANARY:
					bonusIndex = 0;
					break;
				case GOLD_MINT:
					bonusIndex = 2;
					break;
				case WOOD_WORKSHOP:
					bonusIndex = 1;
					break;
				default:
					bonusIndex = 5;
					break;
			}
			if(bonusIndex < 5){
				if(game.getBank()[bonusIndex] > 1){
					game.getBank()[bonusIndex] -= 2;
					game.getActivePlayer().getWallet()[bonusIndex]  +=2;
				}
				else if(game.getBank()[bonusIndex] == 1){
					game.getBank()[bonusIndex] -= 1;
					game.getActivePlayer().getWallet()[bonusIndex] +=1;
				}
			}
		}
		return numberOfVillagers;
	}
	
	/**
	 * Clears production tiles of villagers 
	 * @param game
	 */
	public void clearProductionTileVillagers(Game game){
		for(int i = 0; i < game.getActivePlayer().getProduction().size(); i++){
			game.getActivePlayer().getProduction().get(i).setVillager(false);
		}
	}
	
	/**
	 * Handles user placement of villagers. The user must place all villagers
	 * 
	 * @param game The current game state
	 * 
	 * @param numberOfVillagers The number of villager available to place
	 */
	public void setVillagers(Game game, int numberOfVillagers){
		ArrayList<ProductionTile> villagerOptions = 
				new ArrayList<ProductionTile>();
		for(int i = 0; i < game.getActivePlayer().getProduction().size(); i++){
			villagerOptions.add(game.getActivePlayer().getProduction().get(i));
		}
		for(int i = 0; (i < numberOfVillagers) && 
				(i < game.getActivePlayer().getProduction().size()); i++){
			UserInterface<ProductionTile> manageVillagers =
					new UserInterface<ProductionTile>();
			ProductionTile temp = (ProductionTile) manageVillagers.
					provideMenuOptions("Please select a resource tile "
					+ "for villager " + (i + 1) + " out of " + 
					numberOfVillagers + " : ", game, villagerOptions, null);
			villagerOptions.remove(temp);
			if(temp != null)
				game.getActivePlayer().getProduction().get(
					game.getActivePlayer().getProduction().indexOf(temp)).
					setVillager(true);
		}
	}
	
	/**
	 * 	Handle the user selection of a gather type
	 * 
	 * @param game The current game state
	 * 
	 * @return The gather type selected
	 */
	public GatherCard.GatherType selectGatherType(Game game){
		UserInterface<GatherCard.GatherType> handleGatherTypeSelection =
				new UserInterface<GatherCard.GatherType>();
		return (GatherType) handleGatherTypeSelection.provideMenuOptions(
				"Select a Gather method: ",
				game, this.gatherBy, "Burn this card instead...");
	}
	
	/**
	 * Controls the collection of resources for all players
	 * 
	 * @param game The current game state
	 * @param numberOfPlayers The number of player in the rotation
	 */
	public void collectByGatherType(Game game, int numberOfPlayers){
		
		GatherCard.GatherType gatherTypeChoice = selectGatherType(game);
		
		if(gatherTypeChoice == GatherCard.GatherType.TERRAIN){
			ArrayList<ProductionTile.Terrain> terrainTypes = 
					new ArrayList<ProductionTile.Terrain>();
			terrainTypes.add(ProductionTile.Terrain.HILLS); 
			terrainTypes.add(ProductionTile.Terrain.MOUNTAINS); 
			terrainTypes.add(ProductionTile.Terrain.FOREST); 
			terrainTypes.add(ProductionTile.Terrain.DESERT); 
			terrainTypes.add(ProductionTile.Terrain.FERTILE); 
			terrainTypes.add(ProductionTile.Terrain.SWAMP);
			
			UserInterface<ProductionTile.Terrain> manageTerrainSelection = 
					new UserInterface<ProductionTile.Terrain>();
			ProductionTile.Terrain resourceChoice = manageTerrainSelection.
					provideMenuOptions("Pick a terrain type: ", 
					game, terrainTypes, null);
			for(int i = 0; i < numberOfPlayers; i++){
				gatherByTerrain(game, resourceChoice);
				game.setActivePlayer(game.getActivePlayer().getNext());
			}
		}
		
		if(gatherTypeChoice == GatherCard.GatherType.RESOURCE){
			ArrayList<String> resources = new ArrayList<String>();
			resources.add("food");
			resources.add("wood");
			resources.add("gold");
			resources.add("favor");
			UserInterface<String> handleResourceSelection = 
					new UserInterface<String>();
			String resource = handleResourceSelection.provideTextOptions(
					"Pick a resource type: ", game, resources, null);
			for(int i = 0; i < numberOfPlayers; i++){
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
				game.setActivePlayer(game.getActivePlayer().getNext());
			}
		}
		
		if(gatherTypeChoice == GatherCard.GatherType.ALL_RESOURCES){
			for(int i = 0; i < numberOfPlayers; i++){
				gatherByResource(game, 0);
				gatherByResource(game, 1);
				gatherByResource(game, 2);
				gatherByResource(game, 3);
				game.setActivePlayer(game.getActivePlayer().getNext());
			}
		}
		
		if(gatherTypeChoice == GatherCard.GatherType.FOOD){
			for(int i = 0; i < numberOfPlayers; i++){
				gatherByFood(game);
				game.setActivePlayer(game.getActivePlayer().getNext());
			}
		}
	}
	
	/**
	 * Controls the sequence of events resulting from a Player choosing to
	 * to gather by resource.
	 * 
	 * @param game The game being altered
	 * @param resourceIndex The index of the resource being gathered by.
	 */
	public void gatherByResource(Game game, int resourceIndex){
		for(int i = 0; i < game.getActivePlayer().getProduction().size(); i++){
			if((game.getActivePlayer().getProduction().get(i)).getResource()[resourceIndex] > 0){
				if(game.getBank()[resourceIndex]  > 0){
					game.getActivePlayer().getWallet()[resourceIndex] += 1;
					game.getBank()[resourceIndex] -= 1;
					if(game.getBank()[resourceIndex]  > 0){
						if((game.getActivePlayer().getProduction().get(i)).getVillager()){
							game.getActivePlayer().getWallet()[resourceIndex] += 1;
							game.getBank()[resourceIndex] -= 1;
						}
					}
				}
			}
		}
	}

	/**
	 * Controls the sequence of events resulting from a Player choosing to
	 * to gather by terrain.
	 *  
	 * @param game The game being altered
	 * @param terrainType The terrain type being gathered by
	 */
	public void gatherByTerrain(Game game, ProductionTile.Terrain terrainType){
		for(int i = 0; i < game.getActivePlayer().getProduction().size(); i++){
			if(game.getActivePlayer().getProduction().get(i).getType() == terrainType){
				for(int j = 0; j < 4; j++){
					if(game.getBank()[j] < game.getActivePlayer().getProduction().get(i).getResource()[j]){
						game.getActivePlayer().getWallet()[j] += game.getBank()[j];
						game.getBank()[j] = 0;
					}
					else{
						game.getActivePlayer().getWallet()[j] +=  
								game.getActivePlayer().getProduction().get(i).getResource()[j];
						game.getBank()[j] -=  
								game.getActivePlayer().getProduction().get(i).getResource()[j];
					}
				}
				if(game.getActivePlayer().getProduction().get(i).getVillager()){
					for(int j = 0; j < 4; j++){
						if(game.getBank()[j] > 0)
						if(game.getActivePlayer().getProduction().get(i).getResource()[j] > 0){
							game.getActivePlayer().getWallet()[j] += 1;
							game.getBank()[j] -= 1;
						}
					}
				}
			}
		}
	}
	
	/**
	 * Controls the sequence of events resulting from a Player cho0sing to
	 * to gather by food.
	 * 
	 * @param game The game being altered
	 */
	public void gatherByFood(Game game){
		gatherByResource(game, 0);
	}
	
	/**
	 * Executes the Card's effect on the Game
	 * 
	 * @param game The game being affected by the card
	 */
	public void execute(Game game){
		int numberOfPlayers = 3;
		int numberOfVillagers = 
				distributeBuildingBonuses(game);
		
		clearProductionTileVillagers(game);
		
		setVillagers(game, numberOfVillagers);
		
		collectByGatherType(game, numberOfPlayers);		
	}
}
