/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * The top level module for running the board game engine
 * and implementing the user interface
 */
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game{
	
	private Player player1;
	private Player player2;
	private Player player3;	
	private Player activePlayer;
	private ArrayList<ProductionTile> productionPool;
	private ArrayList<VictoryPool> victory;
	private int[] bank;
	private Board board;
	private JFrame gui;
	
	/**
	 * The default constructor for a game 
	 */
	Game() {
		player1 = new Player("Player 1", true); //player 1 is the human
		player2 = new Player("Player 2", false);
		player3 = new Player("Player 3", false);
		productionPool  = new ArrayList<ProductionTile>();
		victory = new ArrayList<VictoryPool>();
		bank = new int[5];
		gui = new JFrame("AOM");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * Allows the first 3 players to place a victory point in a victory bin
	 * before drawing cards
	 */
	public void setVictoryPoints(){
		VictoryPool playerSelection;
		int numberOfPlayers = 3;
		for(int i = 0; i < 3; i++){
			if(bank[4] > 0){
				UserInterface<VictoryPool> manageVictoryPoints = 
						new UserInterface<VictoryPool>();
				playerSelection = (VictoryPool) manageVictoryPoints.
						provideMenuOptions("Place a Victory cube: ", 
						this, victory, null);
				bank[4] --;
				playerSelection.setValue(playerSelection.getValue() + 1);
			}
			activePlayer = activePlayer.getNext();
		}
		for(int i = 3; i < numberOfPlayers; i++){
			activePlayer = activePlayer.getNext();
		}
	}
	
	/**
	 * Manages the Draw Cards phase in the turn sequence, as well 
	 * as managing the player interaction during permanent deck selection 
	 */
	public void drawCards(){
		int handSize;
		int numberOfPlayers = 3;
		
		for(int i = 0; i < numberOfPlayers; i ++){
			Card choice;
			switch(activePlayer.getAge()){
			case ARCHAIC:
				handSize = 4; 
				break;
			case CLASSICAL:
				handSize = 5;
				break;
			case HEROIC:
				handSize = 6;
				break;
			case MYTHIC:
				handSize = 7;
				break;
			default:
				handSize = 4;
				break;
			}
			do{
				UserInterface<Card> drawDeck = new UserInterface<Card>(); 
				choice = (Card) drawDeck.provideMenuOptions(
						"Select a permanent card or pass", this, 
						activePlayer.getPermDeck(), "Draw " + (
						handSize - activePlayer.getHand().size()) +
								" cards from the random deck");
				if(choice != null){
					activePlayer.getHand().add(choice);	
					activePlayer.getPermDeck().remove(choice);	
				}
			}
			while((activePlayer.getHand().size() < handSize) && 
					(choice != null) && (activePlayer.getRandDeck().size() > 0));
			while(activePlayer.getHand().size() < handSize){
				if(activePlayer.getRandDeck().size() == 0){
					while(activePlayer.getUsedRandDeck().size() != 0){
						activePlayer.getRandDeck().add(
								activePlayer.getUsedRandDeck().get(0));
						activePlayer.getUsedRandDeck().remove(
								activePlayer.getUsedRandDeck().get(0));
					}
				}
				RandomSelection<Card> selector = new RandomSelection<Card>();
				activePlayer.getHand().add(selector.getRandomFromList(
						activePlayer.getRandDeck(), true, 0));
			}
			activePlayer = activePlayer.getNext();
		}
	}
	
	/**
	 * Manages the Action Card phase in the game's turn sequence
	 */
	public void actionCardPhase(){
		int numberOfPlayers = 3;
		for(int i = 0; i < numberOfPlayers*3; i++){
			playActionCard();
			activePlayer= activePlayer.getNext();
		}
	}
	
	/**
	 * Handles the Player's interaction of Card selection
	 */
	public void playActionCard(){
		if(!checkForVictory()){
			UserInterface<Card> ui = new UserInterface<Card>();
			Card selection = ui.provideMenuOptions(
					"Select an Action Card to play: ", this, 
					activePlayer.getHand(), null);
			if(selection.getPermanent())
				activePlayer.getPermDeck().add(selection);
			else
				activePlayer.getUsedRandDeck().add(selection);
			activePlayer.getHand().remove(selection);
			selection.execute(this);
		}
	}

	/**
	 * At the end of the turn phase, this method reduces each players resources
	 * to no more than 5 of each without a Storehouse and 8 with a Storehouse 
	 */
	public void handleSpoilage(){
		int numberOfPlayers = 3;
		int storeHouse;
		for(int i = 0; i < numberOfPlayers; i++){
			storeHouse = 0;
			for(int j = 0; j < activePlayer.getCity().size(); j++){
				if(activePlayer.getCity().get(j).getType() == Building.Type.STOREHOUSE){
					storeHouse = 3;
				}
			}
			for(int k = 0; k < 4; k++){
				if(activePlayer.getWallet()[k] > 5 + storeHouse){
					bank[k] = bank[k] + activePlayer.getWallet()[k] - (5 + storeHouse);
					activePlayer.getWallet()[k] = (5 + storeHouse);
				}
			}
			activePlayer = activePlayer.getNext();
		}
	}
	
	/**
	 * Manages the discard phase of the game, where all random cards
	 * are discarded to the usedRandomCard decks and the players choose
	 * which permanent cards to discard
	 */
	public void discard(){
		int numberOfPlayers = 3;
		Card discard = activePlayer.getHand().get(0);
		for(int i = 0; i < numberOfPlayers; i++){
			for(int j = 0; j < activePlayer.getHand().size(); j++){
				while((activePlayer.getHand().size() > j) &&
						activePlayer.getHand().get(j).getPermanent() == false){
					activePlayer.getUsedRandDeck().add(
							activePlayer.getHand().get(j));
					activePlayer.getHand().remove(j);
				}
			}
			for(int j = 0; j < activePlayer.getHand().size(); j++){
				UserInterface<Card> cardMenus = new UserInterface<Card>();
				discard = cardMenus.provideMenuOptions(
						"Select a Permanent card to discard: ", 
						this, activePlayer.getHand(), "Keep remaining cards.");
				if(discard == null){
					j = activePlayer.getHand().size();
				}
				else{
					activePlayer.getPermDeck().add(discard);
					activePlayer.getHand().remove(discard);
					j--;
				}
			}
			activePlayer = activePlayer.getNext();
		}
	}
	
	/**
	 * After each build card is played, this checks to see if The Wonder was
	 * built. We wait until after all builds have been used to allow the player
	 * to exhaust their build card before ending the game
	 * 
	 * @return True if The Wonder has been built
	 */
	public boolean checkForVictory(){
		int numberOfPlayers = 3;
		if(bank[4] ==0){
			return true;
		}
		for(int i = 0; i < numberOfPlayers; i++){
			for(int j = 0; j < activePlayer.getCity().size(); j++){
				if(activePlayer.getCity().get(j).getType() == Building.Type.THE_WONDER){
					return true;
				}
			}
			activePlayer = activePlayer.getNext();
		}
		return false;
	}
	
	/**
	 * Manages the distribution of certain victory bins to players at the end
	 * of the game, before the winner is determined 
	 */
	public void assignVictoryPoints(){
		int numberOfPlayers = 3;
		ArrayList<Player> mostBuildings = new ArrayList<Player>();
		int maxBuildings = 0;
		
		for(int i = 0; i < numberOfPlayers; i++){
			for(int j = 0; j < activePlayer.getCity().size(); j++){
				if(activePlayer.getCity().get(j).getType() == Building.Type.THE_WONDER){
					activePlayer.getWallet()[4] += 
							victory.get(1).getValue();
				}
			}
			if(activePlayer.getCity().size() >
			maxBuildings){	
				maxBuildings = activePlayer.getCity().size();
			}
			activePlayer = activePlayer.getNext();
		}	
		
		for(int i = 0; i < numberOfPlayers; i++){
			if(activePlayer.getCity().size() == maxBuildings){
				mostBuildings.add(activePlayer);				
			}
			activePlayer = activePlayer.getNext();
		}
		RandomSelection<Player> chooseBuildingWinner = 
				new RandomSelection<Player>();
		(chooseBuildingWinner.getRandomFromList(mostBuildings, false, 0)).
				getWallet()[4] += victory.get(0).getValue();
	}
	
	/**
	 * At the end of the game, this takes the Players and determines the winner
	 * 
	 * @return A randomly drawn winner of those with the most victory points
	 */
	public Player findWinner(){
		int numberOfPlayers = 3;
		ArrayList<Player> winnerCandidates = new ArrayList<Player>();
		Player winnerBase = activePlayer;
		for(int i = 0; i < numberOfPlayers - 1; i++){
			activePlayer = activePlayer.getNext();
			if(winnerBase.getWallet()[4] < activePlayer.getWallet()[4]){
				winnerBase = activePlayer;
			}
		}
		activePlayer = activePlayer.getNext().getNext();
		winnerCandidates.add(winnerBase);
		for(int i = 0; i < numberOfPlayers - 1; i++){
			if(winnerBase.getWallet()[4] == activePlayer.getWallet()[4]){
				winnerCandidates.add(activePlayer);
			}
			activePlayer = activePlayer.getNext();
		}
		RandomSelection<Player> selector = new RandomSelection<Player>();
		return selector.getRandomFromList(winnerCandidates, false, 0);
	}
	
	/**
	 * Built to detect imbalances in the game's overall funds to error check
	 * card algorithms. For 3 players, total resources are 37 each
	 * 
	 * @param game The game being altered
	 * @throws IllegalArgumentException The previous action created imbalance
	 */
	public void leakDetector(Game game) throws IllegalArgumentException{
		int numberOfPlayers = 3;
		int totalEachResource = 37;
		int balance;
		for(int i = 0; i < 4; i++){
			balance = 0;
			if(game.activePlayer.getWallet()[i] < 0){
				throw new IllegalArgumentException();
			}
			if(game.bank[i] < 0){
				throw new IllegalArgumentException();
			}
			for(int j = 0; j < numberOfPlayers; j++){
				balance += activePlayer.getWallet()[i];
				activePlayer = activePlayer.getNext();
			}
			balance += bank[i];
			if(balance != totalEachResource){
				System.out.println("!!!!!!!!!!Error at " + i);
				throw new IllegalArgumentException();
			}
		}
	}
	
	/**
	 * Executes the turn sequences managed by the game
	 */
	public void run(){
		InitializeGame.initialize(this);

		/*initialize gui*/
	    gui.setLocationByPlatform(true);
	    gui.pack();
	    gui.setMinimumSize(new Dimension(1200, 550));
	    gui.setVisible(true);
	    gui.setResizable(true); 
	    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    board = new Board();
		board.initialize(this);	  
		
		//initialize game
		InitializeGame.choosePlayerResources(this);
		
		while(!checkForVictory()){
			
			setVictoryPoints();
			
			drawCards();
			
			actionCardPhase();
			
			if(!checkForVictory()){
				
				handleSpoilage();
				
				discard();
			}
		}
		
		assignVictoryPoints();
		
		/*Announce end of game using user interface*/
		ArrayList<String> endGame = new ArrayList<String>();
		endGame.add("YAY!!!");
		endGame.add("BOO!!!");
		UserInterface<String> winnerAnnouncer = 
				new UserInterface<String>();
		winnerAnnouncer.provideTextOptions("The Winner is: " + 
		(findWinner()).getName() + "!!!", this, endGame, null);
		System.exit(1);
	}
	
	/**
	 * Main method for running the game
	 * 
	 * @param args
	 */
	public static void main(String [] args){
		Game game = new Game();
		game.run();	
	}
	
	/**
	 * Mutator for Player 1
	 * 
	 * @param player1 Player 1
	 */
	public void setPlayer1(Player player1){
		this.player1 = player1;
	}
	
	/**
	 * Mutator for Player 2
	 * 
	 * @param player2 Player 2
	 */
	public void setPlayer2(Player player2){
		this.player2 = player2;		
	}

	/**
	 * Mutator for Player 3
	 * 
	 * @param player3 Player 3
	 */
	void setPlayer3(Player player3){
		this.player3 = player3;
	}
	
	/**
	 * Mutator for the active Player
	 * 
	 * @param activePlayer The active Player
	 */
	public void setActivePlayer(Player activePlayer){
		this.activePlayer = activePlayer;
	}
	
	/**
	 * Mutator for the production pool
	 * 
	 * @param productionPool The production pool
	 */
	public void setProductionPool(ArrayList<ProductionTile> productionPool){
		this.productionPool = productionPool;
	}
	
	/**
	 * Mutator for the victory pool set
	 * 
	 * @param victory The victory pool set
	 */
	public void setVictory(ArrayList<VictoryPool> victory){
		this.victory = victory;
	}
	
	/**
	 * Mutator for the bank resources
	 * 
	 * @param bank The bank resources
	 */
	public void setBank(int[] bank){
		this.bank = bank;
	}
	
	/**
	 * Mutator for the game board
	 * 
	 * @param board The game board
	 */
	public void setBoard(Board board){
		this.board = board;
	}
	
	/**Mutator for the game GUI
	 * 
	 * @param gui The game GUI
	 */
	public void setGUI(JFrame gui){
		this.gui = gui;
	}

	/**
	 * Accessor for Player 1
	 * 
	 * @return Player 1
	 */
	public Player getPlayer1(){
		return this.player1;
	}
	
	/**
	 * Accessor for Player 2
	 * 
	 * @return Player 2
	 */
	public Player getPlayer2(){
		return this.player2;		
	}
	
	/**
	 * Accessor for Player 3
	 * 
	 * @return Player 3
	 */
	public Player getPlayer3(){
		return this.player3;
	}
	
	/**
	 * Accessor for the active Player
	 * 
	 * @return The active Player
	 */
	public Player getActivePlayer(){
		return this.activePlayer;
	}
	
	/**
	 * Accessor for the production pool
	 * 
	 * @return The production pool
	 */
	public ArrayList<ProductionTile> getProductionPool(){
		return this.productionPool;
	}
	
	/**
	 * Accessor for the victory pool set
	 * 
	 * @return The victory pool set
	 */
	public ArrayList<VictoryPool> getVictory(){
		return this.victory;
	}
	
	/**
	 * Accessor for the game bank
	 * @return The game bank
	 */
	public int[] getBank(){
		return this.bank;
	}
	
	/**
	 * Accessor for the game board
	 * 
	 * @return The game board
	 */
	public Board getBoard(){
		return this.board;
	}
	
	/**
	 * Accessor for the game GUI
	 * 
	 * @return The game GUI
	 */
	public JFrame getGUI(){
		return this.gui;
	}
}
