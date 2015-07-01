import java.util.*;
import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * Creates/executes the starting conditions/tasks of the AoM board game
 */
public interface InitializeGame{
	
	/**
	 * 
	 * @param game The Game to be initialized
	 */
	public static void initialize(Game game){
		initializePlayerPriority(game);
		initializePlayerRaces(game);
		initializeResources(game);
		initializeVictoryStratgies(game);
		initializeBuildingPool(game);
		initializeProductionPool(game);
		initializePlayerDecks(game);
		initializePlayerTerrain(game);
	}
	
	/**
	 * Randomly assigns Player priority. Order always stays the same,
	 * only starting player is changed.
	 * 
	 * @param game The Game with the Players to be assigned
	 */
	public static void initializePlayerPriority(Game game){
				
		game.getPlayer1().setNext(game.getPlayer2());
		game.getPlayer2().setNext(game.getPlayer3());
		game.getPlayer3().setNext(game.getPlayer1());
		game.setActivePlayer(game.getPlayer1());
		
		int turnOrder = (int)(Math.random()*3);
		for(int i = turnOrder; i > 0; i--){
			game.setActivePlayer(game.getActivePlayer().getNext().getNext());
		}
	}
	
	/**
	 * Randomly assigns 3 Players one of each Race
	 * 
	 * @param game The Game with the Players to assign Races
	 */
	public static void initializePlayerRaces(Game game){
		ArrayList<Player.Race> playerRace = new ArrayList<Player.Race>();
		playerRace.add(Player.Race.NORSE);
		playerRace.add(Player.Race.EGYPTIAN);
		playerRace.add(Player.Race.GREEK);
		int numberOfPlayers = 3;
		for(int i = 0; i < numberOfPlayers; i++){
			RandomSelection<Player.Race> selector = 
					new RandomSelection<Player.Race>();
			game.getActivePlayer().setRace(selector.getRandomFromList(
					playerRace, true, 0));
			game.setActivePlayer(game.getActivePlayer().getNext());
		}
	}
	
	/**
	 * Gives the Players and Bank the appropriate starting resources
	 * 
	 * @param game With the Players and bank to be worked on
	 */
	public static void initializeResources(Game game){
		int numberOfPlayers = 3;
		for(int i = 0; i < 4; i++ ){
			game.getBank()[i] = 25;
			for(int j = 0; j < numberOfPlayers; j++){
				game.getActivePlayer().getWallet()[i] = 4;
				game.setActivePlayer(game.getActivePlayer().getNext());
			}
		}
		game.getBank()[4] = 30;
		int turnOrder = (int)(Math.random()*3);
		for(int i = turnOrder; i > 0; i--){
			game.setActivePlayer(game.getActivePlayer().getNext().getNext());
		}
	}
	
	public static void initializeVictoryStratgies(Game game){
		game.getVictory().add(new VictoryPool(VictoryPool.Strategy.MOST_BUILDINGS));
		game.getVictory().add(new VictoryPool(VictoryPool.Strategy.WONDER));
	}

	/**
	 * Puts in each Players buildingPool all the Buildings they can build
	 * 
	 * @param game The Game with the Players to be worked on
	 */
	public static void initializeBuildingPool(Game game){
		for(int i = 0; i < 3; i++){
			game.getActivePlayer().getBuildPool().add(new Building(
					Building.Type.MARKET, new ImageIcon("Market.png"),
					0,0,3,2));
			game.getActivePlayer().getBuildPool().add(new Building(
					Building.Type.STOREHOUSE, new ImageIcon("Storehouse.png"),
					2,2,2,2));
			game.getActivePlayer().getBuildPool().add(new Building(
					Building.Type.QUARRY, new ImageIcon("Quarry.png"),
					4,0,1,0));
			game.getActivePlayer().getBuildPool().add(new Building(
					Building.Type.MONUMENT, new ImageIcon("Monument.png"),
					3,0,2,0));
			game.getActivePlayer().getBuildPool().add(new Building(
					Building.Type.GRANARY, new ImageIcon("Granary.png"),
					0,2,3,0));
			game.getActivePlayer().getBuildPool().add(new Building(
					Building.Type.WOOD_WORKSHOP, new ImageIcon(
					"WoodWorkshop.png"), 2,0,3,0));
			game.getActivePlayer().getBuildPool().add(new Building(
					Building.Type.GOLD_MINT, new ImageIcon("GoldMint.png"),
					3,2,0,0));
			game.getActivePlayer().getBuildPool().add(new Building(
					Building.Type.GREAT_TEMPLE, new ImageIcon("GreatTemple.png"),
					4,4,4,4));
			
			for(int j = 0; j < 10; j++){
				game.getActivePlayer().getBuildPool().add(new Building(
						Building.Type.HOUSE, new ImageIcon("House.png"),
						2,2,0,0));
			}
			game.setActivePlayer(game.getActivePlayer().getNext());
		}
	}
	
	/**
	 * Puts in the Game's productionPool all the ProductionTiles available
	 * in the Game
	 * 
	 * @param game The Game to have ProductionTiles added to
	 */
	public static void initializeProductionPool(Game game){

		ArrayList<ProductionTile> tempList = new ArrayList<ProductionTile>();

		tempList.add(new ProductionTile(ProductionTile.Terrain.HILLS, 
				new ImageIcon("Hills_1f.png"), 1, 0, 0, 0, 4));
		tempList.add(new ProductionTile(ProductionTile.Terrain.HILLS, 
				new ImageIcon("Hills_1fv.png"), 0, 0, 0, 1, 4));
		tempList.add(new ProductionTile(ProductionTile.Terrain.HILLS, 
				new ImageIcon("Hills_1w.png"), 0, 1, 0, 0, 4));
		tempList.add(new ProductionTile(ProductionTile.Terrain.HILLS, 
				new ImageIcon("Hills_2g.png"), 0, 0, 2, 0, 4));

		tempList.add(new ProductionTile(ProductionTile.Terrain.MOUNTAINS, 
				new ImageIcon("Mountain_1w.png"), 0, 1, 0, 0, 3));
		tempList.add(new ProductionTile(ProductionTile.Terrain.MOUNTAINS, 
				new ImageIcon("Mountain_1fv.png"), 0, 0, 0, 1, 3));
		tempList.add(new ProductionTile(ProductionTile.Terrain.MOUNTAINS, 
				new ImageIcon("Mountain_2g.png"), 0, 0, 2, 0, 6));

		tempList.add(new ProductionTile(ProductionTile.Terrain.FOREST, 
				new ImageIcon("Forest_1f.png"), 1, 0, 0, 0, 2));
		tempList.add(new ProductionTile(ProductionTile.Terrain.FOREST, 
				new ImageIcon("Forest_2w.png"), 0, 2, 0, 0, 9));
		tempList.add(new ProductionTile(ProductionTile.Terrain.FOREST, 
				new ImageIcon("Forest_1g.png"), 0, 0, 1, 0, 2));
		tempList.add(new ProductionTile(ProductionTile.Terrain.FOREST, 
				new ImageIcon("Forest_1fv.png"), 0, 0, 0, 1, 2));

		tempList.add(new ProductionTile(ProductionTile.Terrain.DESERT, 
				new ImageIcon("Desert_1g.png"), 0, 0, 1, 0, 7));
		tempList.add(new ProductionTile(ProductionTile.Terrain.DESERT, 
				new ImageIcon("Desert_2fv.png"), 0, 0, 0, 2, 7));
		
		tempList.add(new ProductionTile(ProductionTile.Terrain.FERTILE, 
				new ImageIcon("Fertile_1fv.png"), 0, 0, 0, 1, 3));
		tempList.add(new ProductionTile(ProductionTile.Terrain.FERTILE, 
				new ImageIcon("Fertile_1g.png"), 0, 0, 1, 0, 3));
		tempList.add(new ProductionTile(ProductionTile.Terrain.FERTILE, 
				new ImageIcon("Fertile_2f.png"), 2, 0, 0, 0, 12));
		tempList.add(new ProductionTile(ProductionTile.Terrain.FERTILE, 
				new ImageIcon("Fertile_1w.png"), 0, 1, 0, 0, 3));

		tempList.add(new ProductionTile(ProductionTile.Terrain.SWAMP, 
				new ImageIcon("Swamp_1f.png"), 1, 0, 0, 0, 4));
		tempList.add(new ProductionTile(ProductionTile.Terrain.SWAMP, 
				new ImageIcon("Swamp_1fv.png"), 0, 0, 0, 1, 3));
		tempList.add(new ProductionTile(ProductionTile.Terrain.SWAMP, 
				new ImageIcon("Swamp_2w.png"), 0, 2, 0, 0, 4));

		for (int i = 0; i < tempList.size(); i++) {
			ProductionTile temp = tempList.get(i);
			for (int j = 0; j < temp.getQuantity(); j++) {
				game.getProductionPool().add(new ProductionTile(temp));
			}
		}
	}
	
	/**
	 * Puts in each Players permanentDeck and randomDeck all the Cards that
	 * are available to draw
	 * 
	 * @param game The game with the Players to be worked on
	 */
	public static void initializePlayerDecks(Game game){
		int numberOfPlayers = 3;
	
		for(int i = 0; i < numberOfPlayers; i ++){
			
			/*----BASE PACK----*/
		switch(game.getActivePlayer().getRace()){
			case NORSE:
				if(game.getActivePlayer().getHuman()){
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade", "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade",  "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade", "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade",  "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getPermDeck().add(new TradeCard(new ImageIcon(
						"Trade_2.png"), new ImageIcon("Permanent.png"),
						true, "Trade", "Payment of 2 resurce to trade ", "", 
						0,2));
				game.getActivePlayer().getPermDeck().add(new TradeCard(new ImageIcon(
						"Trade_2.png"), new ImageIcon("Permanent.png"),
						true, "Trade",  "Payment of 2 resurce to trade ", "", 
						0,2));
				}
				
				game.getActivePlayer().getPermDeck().add(new GatherCard(new ImageIcon(
						"Gather_r_t.png"), new ImageIcon("Permanent.png"),
						true, "Gather", "Resource type or terrain tpye. ", "", 
						0, GatherCard.GatherType.RESOURCE, 
						GatherCard.GatherType.TERRAIN));
				game.getActivePlayer().getPermDeck().add(new GatherCard(new ImageIcon(
						"Gather_r_t.png"), new ImageIcon("Permanent.png"),
						true, "Gather", "Resource type or terrain tpye. ", "", 
						0, GatherCard.GatherType.RESOURCE, 
						GatherCard.GatherType.TERRAIN));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				
				game.getActivePlayer().getPermDeck().add(new BuildCard
						(new ImageIcon("Build1.png"), new ImageIcon(
						"Permanent.png"), true, "Build", "Build up to " + 1 + 
						" building", "", 1, 0));
				game.getActivePlayer().getPermDeck().add(new BuildCard
						(new ImageIcon("Build1.png"), new ImageIcon(
						"Permanent.png"), true, "Build", "Build up to " + 1 + 
						" building", "", 1, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build4.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 4 + 
						" buildings", "", 4, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build3.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 3 + 
						" buildings", "", 3, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build3.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 3 + 
						" buildings", "", 3, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build3.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 3 + 
						" buildings", "", 3, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build2.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 2 + 
						" buildings", "", 2, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build2.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 2 + 
						" buildings", "", 2, 0));
		
				game.getActivePlayer().getPermDeck().add(new NextAge(new ImageIcon(
						"NextAge.png"), new ImageIcon("Permanent.png"),	true, 
						"Next Age", "Advance to the next age-  4 of each " +
						"resource for Classical, 5 for Heroic, 6 for Mythical",
						"", 0, false));	
				game.getActivePlayer().getPermDeck().add(new NextAge(new ImageIcon(
						"NextAge.png"), new ImageIcon("Permanent.png"),	true, 
						"Next Age", "Advance to the next age-  4 of each " +
						"resource for Classical, 5 for Heroic, 6 for Mythical",
						"", 0, false));	
				game.getActivePlayer().getRandDeck().add(new NextAge(new ImageIcon(
						"NextAge1.png"), new ImageIcon("Random.png"), false, 
						"Next Age", "Advance to the next age- 3 of each " +
						"resource for Classical, 4 for Heroic, 5 for Mythical",
						"", 0, true));	
				game.getActivePlayer().getRandDeck().add(new NextAge(new ImageIcon(
						"NextAge1.png"), new ImageIcon("Random.png"), false, 
						"Next Age", "Advance to the next age- 3 of each " +
						"resource for Classical, 4 for Heroic, 5 for Mythical",
						"", 0, true));				
				game.getActivePlayer().getRandDeck().add(new NextAge(new ImageIcon(
						"NextAge1.png"), new ImageIcon("Random.png"), false, 
						"Next Age", "Advance to the next age- 3 of each " +
						"resource for Classical, 4 for Heroic, 5 for Mythical",
						"", 0, true));		
				
				game.getActivePlayer().getPermDeck().add(new ExploreCard(new ImageIcon(
						"Explore1.png"), new ImageIcon("Permanent.png"),true, 
						"Explore", "Draw one more production tile than the " +
						"number of players.", "", 1));
				game.getActivePlayer().getPermDeck().add(new ExploreCard(new ImageIcon(
						"Explore1.png"), new ImageIcon("Permanent.png"),true, 
						"Explore", "Draw one more production tile than the " +
						"number of players.", "", 1));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore0.png"), new ImageIcon("Random.png"),false,	
						"Explore", "Draw the same number of production tiles " +
						"as players.", "", 0));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore0.png"), new ImageIcon("Random.png"),false, 
						"Explore", "Draw the same number of production tiles " +
						"as players.", "", 0));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore2.png"), new ImageIcon("Random.png"),false,	
						"Explore", "Draw two more production tile than the " +
						"number of players.", "", 2));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore2.png"), new ImageIcon("Random.png"),false, 
						"Explore", "Draw two more production tile than the " +
						"number of players.", "", 2));
				
				game.getActivePlayer().getRandDeck().add(new Baldr(new ImageIcon(
						"Baldr.png"), new ImageIcon("Random.png"), "Baldr", 
						"Draw the same number of production tile as players.", 
						"Pay 2 favor to be the only one to gather."));	
				game.getActivePlayer().getRandDeck().add(new Skadi());
				
				game.getActivePlayer().getRandDeck().add(new Hera(new ImageIcon(
						"Hera.png"), new ImageIcon("Random.png"), 
						"Build up to " + 3 + " buildings", "Pay 1 favor to "
						+ "gain 1 house.", 3, 1));
				game.getActivePlayer().getRandDeck().add(new Horus(new ImageIcon(
						"Horus.png"), new ImageIcon("Random.png"), 
						"Build up to 3 buildings", "Pay 1 favor to "
						+ "destroy 1 building", 3, 1));
				game.getActivePlayer().getRandDeck().add(new Nephthys(new ImageIcon(
						"Nephthys.png"), new ImageIcon("Random.png"), 
						"Build up to 3 buildings", "Pay 2 favor to "
						+ "reduce building costs by 2 resources.", 3, 2));
				game.getActivePlayer().getRandDeck().add(new Artemus(new ImageIcon(
						"Artemus.png"), new ImageIcon("Random.png"), "Artemus", 
						"Draw two more production tiles than the number of " + 
						"players.",	"Pay 1 favor to choose twice before " +
						"anyone."));
				game.getActivePlayer().getRandDeck().add(new Dionysus());			
				game.getActivePlayer().getRandDeck().add(new Ptah(new ImageIcon(
						"Ptah.png"), new ImageIcon("Random.png"), "Ptah", 
						"Draw two more production tiles than the number of players.", 
						"Pay 1 favor to select twice before anyone."));
				
				game.setActivePlayer(game.getActivePlayer().getNext());
				break;
				
			case GREEK:
				
				if(game.getActivePlayer().getHuman()){
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade", "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade",  "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade", "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade",  "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade", "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_2.png"), new ImageIcon("Random.png"),
						true, "Trade", "Payment of 2 resurce to trade ", "", 
						0,2));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_2.png"), new ImageIcon("Random.png"),
						true, "Trade",  "Payment of 2 resurce to trade ", "", 
						0,2));
				}
				
				game.getActivePlayer().getPermDeck().add(new GatherCard(new ImageIcon(
						"Gather_r_t.png"), new ImageIcon("Permanent.png"),
						true, "Gather", "Resource type or terrain tpye. ", "", 
						0, GatherCard.GatherType.RESOURCE, 
						GatherCard.GatherType.TERRAIN));
				game.getActivePlayer().getPermDeck().add(new GatherCard(new ImageIcon(
						"Gather_r_t.png"), new ImageIcon("Permanent.png"),
						true, "Gather", "Resource type or terrain tpye. ", "", 
						0, GatherCard.GatherType.RESOURCE, 
						GatherCard.GatherType.TERRAIN));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getPermDeck().add(new BuildCard
						(new ImageIcon("Build1.png"), new ImageIcon(
						"Permanent.png"), true, "Build", "Build up to " + 1 + 
						" building", "", 1, 0));
				
				game.getActivePlayer().getPermDeck().add(new BuildCard
						(new ImageIcon("Build1.png"), new ImageIcon(
						"Permanent.png"), true, "Build", "Build up to "	+ 1 + 
						" building", "", 1, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build4.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 4 + 
						" buildings", "", 4, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build3.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 3 + 
						" buildings", "", 3, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build3.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 3 + 
						" buildings", "", 3, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build3.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 3 + 
						" buildings", "", 3, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build2.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 2 + 
						" buildings", "", 2, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build2.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 2 + 
						" buildings", "", 2, 0));
				
				game.getActivePlayer().getPermDeck().add(new NextAge(new ImageIcon(
						"NextAge.png"), new ImageIcon("Permanent.png"),	true, 
						"Next Age", "Advance to the next age-  4 of each " +
						"resource for Classical, 5 for Heroic, 6 for Mythical",
						"", 0, false));	
				game.getActivePlayer().getPermDeck().add(new NextAge(new ImageIcon(
						"NextAge.png"), new ImageIcon("Permanent.png"),	true, 
						"Next Age", "Advance to the next age-  4 of each " +
						"resource for Classical, 5 for Heroic, 6 for Mythical",
						"", 0, false));	
				game.getActivePlayer().getRandDeck().add(new NextAge(new ImageIcon(
						"NextAge1.png"), new ImageIcon("Random.png"), false, 
						"Next Age", "Advance to the next age- 3 of each " +
						"resource for Classical, 4 for Heroic, 5 for Mythical",
						"", 0, true));
				game.getActivePlayer().getRandDeck().add(new NextAge(new ImageIcon(
						"NextAge1.png"), new ImageIcon("Random.png"), false, 
						"Next Age", "Advance to the next age- 3 of each " +
						"resource for Classical, 4 for Heroic, 5 for Mythical",
						"", 0, true));

				
				game.getActivePlayer().getPermDeck().add(new ExploreCard(new ImageIcon(
						"Explore1.png"), new ImageIcon("Permanent.png"),true, 
						"Explore", "Draw one more production tile than the " +
						"number of players.", "", 1));
				game.getActivePlayer().getPermDeck().add(new ExploreCard(new ImageIcon(
						"Explore1.png"), new ImageIcon("Permanent.png"),true, 
						"Explore", "Draw one more production tile than the " +
						"number of players.", "", 1));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore0.png"), new ImageIcon("Random.png"),false,	
						"Explore", "Draw the same number of production tiles " +
						"as players.", "", 0));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore0.png"), new ImageIcon("Random.png"),false, 
						"Explore", "Draw the same number of production tiles " +
						"as players.", "", 0));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore2.png"), new ImageIcon("Random.png"),false,	
						"Explore", "Draw two more production tile than the " +
						"number of players.", "", 2));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore2.png"), new ImageIcon("Random.png"),false, 
						"Explore", "Draw two more production tile than the " +
						"number of players.", "", 2));
				
				game.getActivePlayer().getRandDeck().add(new Hera(new ImageIcon(
						"Hera.png"), new ImageIcon("Random.png"), 
						"Build up to " + 3 + " buildings", "Pay 1 favor to "
						+ "gain 1 house.", 3, 1));
				game.getActivePlayer().getRandDeck().add(new Horus(new ImageIcon(
						"Horus.png"), new ImageIcon("Random.png"), 
						"Build up to 3 buildings", "Pay 1 favor to "
						+ "destroy 1 building", 3, 1));
				game.getActivePlayer().getRandDeck().add(new Nephthys(new ImageIcon(
						"Nephthys.png"), new ImageIcon("Random.png"), 
						"Build up to 3 buildings", "Pay 2 favor to "
						+ "reduce building costs by 2 resources.", 3, 2));
				
				game.getActivePlayer().getRandDeck().add(new Artemus(new ImageIcon(
						"Artemus.png"), new ImageIcon("Random.png"), "Artemus", 
						"Draw two more production tiles than the number of " + 
						"players.",	"Pay 1 favor to choose twice before " +
						"anyone."));
				game.getActivePlayer().getRandDeck().add(new Dionysus());			
				game.getActivePlayer().getRandDeck().add(new Ptah(new ImageIcon(
						"Ptah.png"), new ImageIcon("Random.png"), "Ptah", 
						"Draw two more production tiles than the number of players.", 
						"Pay 1 favor to select twice before anyone."));
				game.getActivePlayer().getRandDeck().add(new Baldr(new ImageIcon(
						"Baldr.png"), new ImageIcon("Random.png"), "Baldr", 
						"Draw one more production tile than the number of players.", 
						"Pay 2 favor to be the only one to choose."));
				game.getActivePlayer().getRandDeck().add(new Skadi());	

				game.setActivePlayer(game.getActivePlayer().getNext());
				break;
				
			case EGYPTIAN:
				if(game.getActivePlayer().getHuman()){
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade", "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade",  "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Gather_r_t.png"), new ImageIcon("Random.png"),
						false, "Trade", "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade",  "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade", "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getRandDeck().add(new TradeCard(new ImageIcon(
						"Trade_1.png"), new ImageIcon("Random.png"),
						false, "Trade",  "Payment of 1 resurce to trade ", "", 
						0,1));
				game.getActivePlayer().getPermDeck().add(new TradeCard(new ImageIcon(
						"Trade_2.png"), new ImageIcon("Random.png"),
						true, "Trade", "Payment of 2 resurce to trade ", "", 
						0,2));
				game.getActivePlayer().getPermDeck().add(new TradeCard(new ImageIcon(
						"Trade_2.png"), new ImageIcon("Random.png"),
						true, "Trade",  "Payment of 2 resurce to trade ", "", 
						0,2));
				}
				
				game.getActivePlayer().getPermDeck().add(new GatherCard(new ImageIcon(
						"Gather_r_t.png"), new ImageIcon("Permanent.png"),
						true, "Gather", "Resource type or terrain tpye. ", "", 
						0, GatherCard.GatherType.RESOURCE, 
						GatherCard.GatherType.TERRAIN));
				game.getActivePlayer().getPermDeck().add(new GatherCard(new ImageIcon(
						"Gather_r_t.png"), new ImageIcon("Permanent.png"),
						true, "Gather", "Resource type or terrain tpye. ", "", 
						0, GatherCard.GatherType.RESOURCE, 
						GatherCard.GatherType.TERRAIN));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				game.getActivePlayer().getRandDeck().add(new GatherCard(new ImageIcon(
						"Gather_ar.png"), new ImageIcon("Random.png"),
						false, "Gather", "All resources. ", "", 
						0, GatherCard.GatherType.ALL_RESOURCES, null));
				
				game.getActivePlayer().getPermDeck().add(new BuildCard
						(new ImageIcon("Build1.png"), new ImageIcon(
						"Permanent.png"), true, "Build", "Build up to " + 1 + 
						" building", "", 1, 0));
				game.getActivePlayer().getPermDeck().add(new BuildCard
						(new ImageIcon("Build1.png"), new ImageIcon(
						"Permanent.png"), true, "Build", "Build up to " + 1 + 
						" building", "", 1, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build4.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 4 + 
						" buildings", "", 4, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build3.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 3 + 
						" buildings", "", 3, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build3.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 3 + 
						" buildings", "", 3, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build2.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 2 + 
						" buildings", "", 2, 0));
				game.getActivePlayer().getRandDeck().add(new BuildCard
						(new ImageIcon("Build2.png"), new ImageIcon(
						"Random.png"), false, "Build", "Build up to " + 2 + 
						" buildings", "", 2, 0));
				
				game.getActivePlayer().getPermDeck().add(new NextAge(new ImageIcon(
						"NextAge.png"), new ImageIcon("Permanent.png"),	true, 
						"Next Age", "Advance to the next age-  4 of each " +
						"resource for Classical, 5 for Heroic, 6 for Mythical",
						"", 0, false));	
				game.getActivePlayer().getPermDeck().add(new NextAge(new ImageIcon(
						"NextAge.png"), new ImageIcon("Permanent.png"),	true, 
						"Next Age", "Advance to the next age-  4 of each " +
						"resource for Classical, 5 for Heroic, 6 for Mythical",
						"", 0, false));	
				game.getActivePlayer().getRandDeck().add(new NextAge(new ImageIcon(
						"NextAge1.png"), new ImageIcon("Random.png"), false, 
						"Next Age", "Advance to the next age- 3 of each " +
						"resource for Classical, 4 for Heroic, 5 for Mythical",
						"", 0, true));
				game.getActivePlayer().getRandDeck().add(new NextAge(new ImageIcon(
						"NextAge1.png"), new ImageIcon("Random.png"), false, 
						"Next Age", "Advance to the next age- 3 of each " +
						"resource for Classical, 4 for Heroic, 5 for Mythical",
						"", 0, true));
				
				game.getActivePlayer().getPermDeck().add(new ExploreCard(new ImageIcon(
						"Explore1.png"), new ImageIcon("Permanent.png"),true, 
						"Explore", "Draw one more production tile than the " +
						"number of players.", "", 1));
				game.getActivePlayer().getPermDeck().add(new ExploreCard(new ImageIcon(
						"Explore1.png"), new ImageIcon("Permanent.png"),true, 
						"Explore", "Draw one more production tile than the " +
						"number of players.", "", 1));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore0.png"), new ImageIcon("Random.png"),false,	
						"Explore", "Draw the same number of production tiles " +
						"as players.", "", 0));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore0.png"), new ImageIcon("Random.png"),false, 
						"Explore", "Draw the same number of production tiles " +
						"as players.", "", 0));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore2.png"), new ImageIcon("Random.png"),false,	
						"Explore", "Draw two more production tile than the " +
						"number of players.", "", 2));
				game.getActivePlayer().getRandDeck().add(new ExploreCard(new ImageIcon(
						"Explore2.png"), new ImageIcon("Random.png"),false, 
						"Explore", "Draw two more production tile than the " +
						"number of players.", "", 2));
				
			
				game.getActivePlayer().getRandDeck().add(new Ptah(new ImageIcon(
						"Ptah.png"), new ImageIcon("Random.png"), "Ptah", 
						"Draw two more production tiles than the number of players.", 
						"Pay 1 favor to gather twice before anyone."));
				game.getActivePlayer().getRandDeck().add(new Horus(new ImageIcon(
						"Horus.png"), new ImageIcon("Random.png"), 
						"Build up to 3 buildings", "Pay 1 favor to "
						+ "destroy 1 building", 3, 1));
				game.getActivePlayer().getRandDeck().add(new Nephthys(new ImageIcon(
						"Nephthys.png"), new ImageIcon("Random.png"), 
						"Build up to 3 buildings", "Pay 2 favor to "
						+ "reduce building costs by 2 resources.", 3, 2));
				
				game.getActivePlayer().getRandDeck().add(new Artemus(new ImageIcon(
						"Artemus.png"), new ImageIcon("Random.png"), "Artemus", 
						"Draw two more production tiles than the number of " + 
						"players.",	"Pay 1 favor to select twice before others."));
				game.getActivePlayer().getRandDeck().add(new Baldr(new ImageIcon(
						"Baldr.png"), new ImageIcon("Random.png"), "Baldr", 
						"Draw one more production tile than the number of players.", 
						"Pay 2 favor to be the only one to choose."));
				game.getActivePlayer().getRandDeck().add(new Skadi());
				game.getActivePlayer().getRandDeck().add(new Dionysus());
				
				game.setActivePlayer(game.getActivePlayer().getNext());
				break;
			default:
				break;
			}
		}
	}
		
	/**
	 * Puts in Player's terrainAvailable all of the Terrain that is available
	 * to the for the duration of the game
	 * 
	 * @param game The Game with the Players to be worked on
	 */
	public static void initializePlayerTerrain(Game game){
		int numberOfPlayers = 3;
		for(int i = 0; i < numberOfPlayers; i ++){
			switch(game.getActivePlayer().getRace()){
			case EGYPTIAN:
				/*add egyptian terrain*/
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FOREST);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.DESERT);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.DESERT);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.DESERT);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.DESERT);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.DESERT);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.DESERT);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.SWAMP);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.SWAMP);
				game.setActivePlayer(game.getActivePlayer().getNext());
				
				break;
			case GREEK:
				/*add greek terrain*/
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.MOUNTAINS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FOREST);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FOREST);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.DESERT);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.SWAMP);
				game.setActivePlayer(game.getActivePlayer().getNext());
				break;
			case NORSE:
				/*add norse terrain*/
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.MOUNTAINS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.MOUNTAINS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.MOUNTAINS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.MOUNTAINS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FERTILE);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FOREST);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FOREST);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FOREST);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.FOREST);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.HILLS);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.DESERT);
				game.getActivePlayer().getTerrain().add(
						ProductionTile.Terrain.SWAMP);
				game.setActivePlayer(game.getActivePlayer().getNext());
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Facilitates the Players' initial ProductionTile selection
	 * 
	 * @param game The game with the Players selecting ProductionTile
	 */
	public static void choosePlayerResources(Game game){
		int numberOfPlayers = 3;
		RandomSelection<ProductionTile> selector = 
				new RandomSelection<ProductionTile>();
		ArrayList<ProductionTile> options = new ArrayList<ProductionTile>();
		//flag for reversing selection order
		boolean reverseFlag = false;
	
		for(int i = 0; i < numberOfPlayers*6; i++){
			//randomly get appropriate number of production tiles from pool
			options.add(selector.getRandomFromList(
					game.getProductionPool(), true, 0));
		}
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < numberOfPlayers; j++){
				ArrayList<ProductionTile> playerOptions = 
						new ArrayList<ProductionTile>();
				for(int k = 0; k < options.size(); k++){
					//make list of tile that player has opening for
					if(game.getActivePlayer().getTerrain().contains((
							options.get(k)).getType())){
						playerOptions.add(options.get(k));
					}
				}
				if(options.size() > 0){
					//interface for selecting production tiles
					UserInterface<ProductionTile> ui = 
							new UserInterface<ProductionTile>();
					ProductionTile selected = ui.provideMenuOptions(
							"Select a resource tile :", game, 
							playerOptions, "Pass");
					if(selected != null){ //null (false) if player opts out
						//transfer production tile
						game.getActivePlayer().getProduction().add(selected);
						options.remove(selected);
						//remove appropriate terrain available
						game.getActivePlayer().getTerrain().remove(
								selected.getType());
					}
				}
				//keep direction until all players have had a choice
				if(j < numberOfPlayers - 1){
					if(reverseFlag){
						//for back step, traverse # players + 1 with .next
						for(int k = 0; k < numberOfPlayers - 1; k++)
							game.setActivePlayer(game.getActivePlayer().getNext());
					}
					else{
						//step forward if reverseFlag is false
						game.setActivePlayer(game.getActivePlayer().getNext());
					}
				}
			}
			//reverse order every time all players get a choice
			reverseFlag = (reverseFlag)?false:true;
		}
		//return unclaimed resources to production pool
		for(int i = 0; i < options.size(); i++){
			game.getProductionPool().add(options.get(i));
		}
	}
}
