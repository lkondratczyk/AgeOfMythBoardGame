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
	 * Checks if the player has a Market
	 * 
	 * @param Player 
	 */
	public boolean CheckForMarket(Player player){
		for(int i = 0; i < player.getCity().size(); i++){
			
			if((player.getCity().get(i)).getType() == Building.Type.MARKET){
				 return true;	
			}
			
		}
		
		return false;
	}
	
	public boolean CheckForGreatTemple(Player player){
		for(int i = 0; i < player.getCity().size(); i++){
			
			if((player.getCity().get(i)).getType() == Building.Type.GREAT_TEMPLE){
				 return true;	
			}
			
		}
		
		return false;
	}
	
	public boolean TradeCost(Game game ){

		ArrayList<String> TradeOptions = new ArrayList<String>();
		TradeOptions.add("Pay");
		UserInterface<String> PaymentOptions = new UserInterface<String>();

		String pay = PaymentOptions.provideTextOptions("Payment: ", game, TradeOptions, "Burn card");
		
		if(pay!=null){
			ArrayList<String> resources = new ArrayList<String>();
		
			if(game.getActivePlayer().getWallet()[0]>0)
				resources.add("food");
				if(game.getActivePlayer().getWallet()[1]>0)
				resources.add("wood");
				if(game.getActivePlayer().getWallet()[2]>0)
				resources.add("gold");
				if(game.getActivePlayer().getWallet()[3]>0)
				resources.add("favor");
				
				UserInterface<String>CostManagement = new UserInterface<String>();
				String cost =CostManagement.provideTextOptions("Payment: ", game, resources, null);

				for(int i = 0; i < this.getCost(); i++){
			switch(cost){
			case("food"):
				game.getActivePlayer().getWallet()[0]-=1;
				game.getBank()[0]+=1;
				break;

			case("wood"):
				
				game.getActivePlayer().getWallet()[1]-=1;
				game.getBank()[1]+=1;
					break;
					
			case("gold"):
				game.getActivePlayer().getWallet()[2]-=1;
				game.getBank()[2]+=1;
				break;
			case("favor"):
				
				game.getActivePlayer().getWallet()[3]-=1;
				game.getBank()[3]+=1;
				
				break;
			default:
					break;
				}
			 }
				resources.clear();
				return true;
		}	
		return false;
	}

	


	public void Trade(Game game){
		
		
		
		
		int numOfReso = 0;
		ArrayList<String> resources = new ArrayList<String>();
				
		UserInterface<String> resourceSelection = new UserInterface<String>();
		
		
		String reso="";
		while(reso!=null ){
			if(game.getActivePlayer().getWallet()[0]>0)
				resources.add("food");
				if(game.getActivePlayer().getWallet()[1]>0)
				resources.add("wood");
				if(game.getActivePlayer().getWallet()[2]>0)
				resources.add("gold");
				if(game.getActivePlayer().getWallet()[3]>0)
				resources.add("favor");
				
			 resourceSelection = new UserInterface<String>();
				
			 reso = resourceSelection.provideTextOptions("Pick resource you would like to trade: ", game, resources, "Done");
			 resources.clear();
			 if(reso!=null){
				 numOfReso++;
			 
			switch(reso){
			case("food"):
				game.getActivePlayer().getWallet()[0]-=1;
				game.getBank()[0]+=1;
				break;

			case("wood"):
				
				game.getActivePlayer().getWallet()[1]-=1;
				game.getBank()[1]+=1;
					break;
					
			case("gold"):
				game.getActivePlayer().getWallet()[2]-=1;
				game.getBank()[2]+=1;
				break;
			case("favor"):
				
				game.getActivePlayer().getWallet()[3]-=1;
				game.getBank()[3]+=1;
				
				break;
			default:
					break;
					
			}
				}
			
		}
		
		
	
		ArrayList<String>bank = new ArrayList<String>();
		
		
			UserInterface<String> SelectionFromBank = new UserInterface<String>();
		
		
			String bankReso= "";
			while(numOfReso!=0){
				
				if(game.getBank()[0]>0)
					bank.add("food");
					if(game.getBank()[1]>0)
						bank.add("wood");
					if(game.getBank()[2]>0)
						bank.add("gold");
					if(game.getBank()[3]>0)
						bank.add("favor");	
			
				SelectionFromBank = new UserInterface<String>();
				
				 bankReso = SelectionFromBank.provideTextOptions("Select resource you would like from bank", game, bank, null);
					bank.clear();
				if(numOfReso!=0){	
					numOfReso--;
				switch(bankReso){
				case("food"):
				game.getBank()[0]-=1;
				game.getActivePlayer().getWallet()[0]+=1;
					break;
	
				case("wood"):
				game.getBank()[1]-=1;
				game.getActivePlayer().getWallet()[1]+=1;
						break;
						
				case("gold"):
				game.getBank()[2]-=1;
				game.getActivePlayer().getWallet()[2]+=1;
						break;
						
				case("favor"):
				game.getBank()[3]-=1;
				game.getActivePlayer().getWallet()[3]+=1;
						break;
						
				default:
						break;
					}
				
				}
				

				 
			}
			if(CheckForGreatTemple(game.getActivePlayer())){
			String option = "";
			ArrayList<String> options = new ArrayList<String>();
			UserInterface<String> handleVictoryTrade =
					new UserInterface<String>();
			options.add("Trade 8 Favor for 1 Victory Point.");
			while(game.getActivePlayer().getWallet()[3] >= 8 && option != null){
				option = handleVictoryTrade.provideTextOptions("Use you Great Temple?", game, options, "Pass");
				if(option != null){
					game.getActivePlayer().getWallet()[3] -= 8;
					game.getBank()[3] += 8;
					game.getActivePlayer().getWallet()[4] +=1;
					game.getBank()[4] -= 1;
				}
			}
					
			}
	}	
//	@Override
	public void execute(Game game){
		if(CheckForMarket(game.getActivePlayer())){
			Trade(game);
		}else if(TradeCost(game)){
			
			Trade(game);
		}
	}

	
}
