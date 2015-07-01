import java.util.ArrayList;

import javax.swing.ImageIcon;


public class TradeCard extends Card{
	
	public TradeCard(ImageIcon front, ImageIcon back,
			boolean permanent, String name, String firstDescription,
			String secondDescription, int value, int cost) {
		super(front, back, permanent, name, firstDescription,
				secondDescription, value, cost);
	}
	
	/**
	 * Checks if the player as enough to cover the payment to trade
	 * 
	 * @param wallet the Player's wallet
	 * @param cost the price that must be paid before trading
	 */
	public boolean AffordTradeCost(int[] wallet, int[] cost){
		for(int i = 0; i < 4; i++){
			
			if(wallet[i] < cost[i]){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the player has a Market
	 * 
	 * @param Player 
	 */
	public boolean CheckForMarket(Player player){
		for(int i = 0; i < player.city.size(); i++){
			
			if((player.city.get(i)).type == Building.Type.MARKET){
				 return true;	
			}
		}
		
		return false;
	}

	
	/**
	 * 
	 * 
	 * @param game
	 * @param PlayerResource
	 * @param BankResources
	 * @param quantityForPlayer
	 * @param quantityForBank
	 */
	
	
	
	
	public void Trading(Game game, int PlayerResource,int BankResources, int quantityForPlayer, int quantityForBank){
			for(int i = 0; i < game.activePlayer.production.size(); i++){
				if((game.activePlayer.production.get(i)).resource[PlayerResource] >= quantityForBank){
					if(game.bank[BankResources]  >= quantityForPlayer){
				
						game.activePlayer.wallet[PlayerResource] -=quantityForBank;
						
						game.bank[PlayerResource] += quantityForBank;
						
						game.bank[BankResources] -= quantityForPlayer;
						
						game.activePlayer.wallet[BankResources] +=quantityForPlayer;
					}
				}
			}	
		}
	
	public ArrayList<Integer> bankFunds(Game game){
		ArrayList<Integer> gameFunds = new ArrayList<Integer>();
		for(int i = 0; i<game.bank.length;i++){
			
			gameFunds.add(game.bank[i]);
		}
		return gameFunds;
	}
	
	public ArrayList<Integer> PlayerFunds(Player player){
		ArrayList<Integer> PlayerFunds = new ArrayList<Integer>();
		for(int i = 0; i<player.wallet.length;i++){
			
			PlayerFunds.add(player.wallet[i]);
		}
		return PlayerFunds;
	}
	@Override
	public void execute(Game game){
		if(CheckForMarket(game.activePlayer)){
			
			ArrayList<Integer>bank = bankFunds(game);
			
			ArrayList<Integer>playerBank = PlayerFunds(game.activePlayer);
			
			UserInterface<Integer> ProductSelected = new UserInterface<Integer>();
			
			int selectPlayer = ProductSelected.provideMenuOptions("Select the reseources you want to give up", 
					game, playerBank, "Do not want to trade");
			
			int selectBank=ProductSelected.provideMenuOptions("Select the reseources you want to gain", 
					game, bank, "Do not want to trade");
			Trading(game,game.bank[selectBank],game.activePlayer.wallet[selectPlayer],selectPlayer, selectBank);
			
		}
		
	}

}
