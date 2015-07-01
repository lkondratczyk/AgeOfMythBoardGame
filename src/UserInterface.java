import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.util.ArrayList;

/**
 * Responsible for creating the "controller" to run the game.
 * 
 * @author Lyndon Kondratczyk
 *
 * @param <T> A generic item used in the game. By implementation, it must be
 *            a String, or extend GameItem and/or EnumToImage
 */
public class UserInterface<T> {

	private ArrayList<JButton> selections = new ArrayList<JButton>();
	private JPanel template = new JPanel(new GridLayout(3, 1));
	private JLabel prompt;
	private ImageIcon icon;
	protected T buttonChoice = null;
	protected String textChoice = null;
	protected boolean flag = false;
	protected ActionListener action;
	protected JPanel promptBox;
	protected JPanel optionPanel;
	protected JPanel othersPanel;
	protected JPanel bankPanel;
	protected JLabel bankLabel;
	protected JLabel bankInfoLabel;
	protected JPanel scorePanel;
	protected JLabel scoreLabel;
	protected JLabel scoreInfoLabel;

	/**
	 * Default initialization for component updates
	 */
	UserInterface() {
		
		/* The dynamic components of the interface*/
		template.setPreferredSize(new Dimension(512, 512));
		template.setBorder(new LineBorder(Color.BLACK));
		
		promptBox = new JPanel();
		promptBox.setPreferredSize(new Dimension(128, 512));
		promptBox.setLayout(new BoxLayout(promptBox, BoxLayout.X_AXIS));
		promptBox.setBackground(Color.WHITE);
		promptBox.setOpaque(true);
		
		optionPanel = new JPanel(new GridLayout(0, 4));
		optionPanel.setPreferredSize(new Dimension(256, 512));
		optionPanel.setBackground(Color.WHITE);
		optionPanel.setOpaque(true);	
		
		othersPanel = new JPanel(new GridLayout(0, 2));
		othersPanel.setPreferredSize(new Dimension(128, 512));
		othersPanel.setBackground(Color.WHITE);
		othersPanel.setOpaque(true);
		
		bankPanel = new JPanel(new GridLayout(2, 0));
		scorePanel = new JPanel(new GridLayout(2, 0));
		othersPanel.add(bankPanel);
		othersPanel.add(scorePanel);
		
		bankLabel = new JLabel();
		bankLabel.setFont(new Font("Papyrus", Font.BOLD,14));
		bankLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bankLabel.setBackground(Color.WHITE);
		bankLabel.setOpaque(true);
		bankLabel.setText("BANK:");
		bankLabel.setForeground(Color.BLACK);
		bankPanel.add(bankLabel);
		
		bankInfoLabel = new JLabel();
		bankInfoLabel.setLayout(new GridLayout(2,6));
		bankInfoLabel.setBorder(new LineBorder(Color.BLACK));
		bankInfoLabel.setBackground(Color.black);	
		bankPanel.add(bankInfoLabel);
		
		scoreLabel = new JLabel();
		scoreLabel.setFont(new Font("Papyrus",Font.BOLD,14));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setBackground(Color.WHITE);
		scoreLabel.setOpaque(true);
		scoreLabel.setText("LEADERBOARD:");
		scorePanel.add(scoreLabel);
		
		scoreInfoLabel = new JLabel();
		scoreInfoLabel.setLayout(new GridLayout(3,2));
		scoreInfoLabel.setBorder(new LineBorder(Color.BLACK));
		scorePanel.add(scoreInfoLabel);
	}

	/**
	 * This method provides a list of options to the active player depending
	 * on text base choices. It updates the board along with it. The list of
	 * options is turned into buttons with action listeners to return the 
	 * strings associated with them. Null is returned if an optional pass 
	 * option is selected. The buttons also have a relevant toString() message
	 * attached to their toolkits.
	 * 
	 * @param prompt The message prompting action from a human user
	 * @param game The state of the game being played
	 * @param options The ArrayList of String options
	 * @param passOption The message for the pass option. Set to null to not
	 * 		             allow a pass option.
	 * @return String the user selected from the list. Null if pass selected.
	 */
	public String provideTextOptions(String prompt, Game game,
			ArrayList<String> options, String passOption) {
		ArrayList<String> selected = new ArrayList<String>();
		updateBoard(game);
		//clear for updates
		promptBox.removeAll();
		optionPanel.removeAll();

		int passAvailable = 0;
		if (passOption != null) {
			passAvailable++; //to widen bounds
		}

		if (game.getActivePlayer().getHuman()) {
			
			/*update prompt box for humans*/
			this.prompt = new JLabel("<html><p>" + game.getActivePlayer().getName() + 
					":\n" + prompt + "</p></html>");
			this.prompt.setFont(new Font("Papyrus",Font.BOLD,20));
			this.prompt.setHorizontalAlignment(SwingConstants.CENTER);
			this.prompt.setBackground(Color.WHITE);
			this.prompt.setOpaque(true);
			promptBox.add(new JLabel(
					game.getActivePlayer().displayItem(game.getActivePlayer())));
			promptBox.add(this.prompt);
			
			/*Create buttons for user interface*/
			for (int i = 0; i < options.size() + passAvailable; i++) {
				if (i == options.size()) { // pass option if true
					JButton temp = new JButton(passOption);
					temp.setBackground(Color.BLACK);
					temp.setForeground(Color.WHITE);
					temp.setContentAreaFilled(false);
					temp.setBorderPainted(false);
					temp.setOpaque(true);
					selections.add(temp);
				} 
				else { //String option if false
					JButton temp = new JButton(options.get(i));
					temp.setBackground(Color.BLACK);
					temp.setForeground(Color.WHITE);
					temp.setContentAreaFilled(false);
					temp.setOpaque(true);
					temp.setBorderPainted(false);
					selections.add(temp);
				}
				selections.get(i).setMaximumSize(new Dimension(64, 64));
				final int j = i;
				//add functionality to buttons to return generic object
				selections.get(i).addActionListener(new ActionListener() { 
						public void actionPerformed(ActionEvent e) {
							if (j == options.size()) { //return null to pass
								selected.add(null);
							} 
							else { //return String to handle
								selected.add(options.get(j));
							}
						}
					});
				
				/*Update the rest of the controller*/
				optionPanel.add(selections.get(i));
				game.getGUI().add(template);
				template.add(promptBox);
				template.add(optionPanel);
				template.add(othersPanel);
				game.getGUI().validate();
				game.getGUI().repaint();
				game.getGUI().setVisible(true);
			}
		} 
		else if (options.size() > 0) {
			/*update prompt box for AI*/
			this.prompt = new JLabel("<html><p>" + game.getActivePlayer().getName() + 
					" selected: </p></html>");
			this.prompt.setFont(new Font("Papyrus",Font.PLAIN,20));
			this.prompt.setHorizontalAlignment(SwingConstants.CENTER);
			this.prompt.setBackground(Color.WHITE);
			this.prompt.setOpaque(true);
			promptBox.add(new JLabel(
					game.getActivePlayer().displayItem(game.getActivePlayer())));
			promptBox.add(this.prompt);
			//create a random selector for the AI selection
			RandomSelection<String> optionsList = new RandomSelection<String>();
			String selection = optionsList.getRandomFromList(options, false,
					passAvailable);
			JButton temp;
			if(selection == null){ //pass option selected
				temp = new JButton(passOption);
				selected.add(passOption);
			}
			else{ //String option selected
				temp = new JButton(selection);
				selected.add(selection);
			}
			/*create one button to display to the human only*/
			temp.setBackground(Color.BLACK);
			temp.setForeground(Color.WHITE);
			temp.setContentAreaFilled(false);
			temp.setOpaque(true);
			temp.setBorderPainted(false);
			selections.add(temp);
			/*update graphics*/
			optionPanel.add(selections.get(0));
			game.getGUI().add(template);
			template.add(promptBox);
			template.add(optionPanel);
			template.add(othersPanel);
			game.getGUI().validate();
			game.getGUI().repaint();
			game.getGUI().setVisible(true);
			/*halt for one second to display move to user*/
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}
		/*delay program progression until the user selects an option*/
		while (selected.size() == 0) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/*update remaining graphics*/
		updateBoard(game);
		template.removeAll();
		game.getGUI().remove(template);
		//return selection
		return (selected.size() > 0) ? selected.get(0) : null;
	}
	
	/**
	 * This method provides a list of options to the active player depending
	 * on generic object choices. The object must implement GameItem or 
	 * EnumToImage. This function also updates the board. The list of options
	 * is then turned into buttons with action listeners to return the 
	 * objects associated with them. Null is returned if an optional pass 
	 * option is selected. The buttons also have a relevant toString() message
	 * attached to their toolkits.
	 * 
	 * @param prompt The message prompting action from a human user
	 * @param game The state of the game being played
	 * @param options The generic ArrayList of options
	 * @param passOption The message for the pass option. Set to null to not
	 * 		             allow a pass option.
	 * @return Object the user selected from the list. Null if pass selected.
	 */
	public T provideMenuOptions(String prompt, Game game, ArrayList<T> options,
			String passOption) {
		ArrayList<T> selected = new ArrayList<T>();
		/*update graphics*/
		updateBoard(game);
		promptBox.removeAll();
		optionPanel.removeAll();
		
		int passAvailable = 0;
		if (passOption != null) {
			passAvailable++; //increase bounds if pass option true
		}
		if (game.getActivePlayer().getHuman()) { //manual selection for humans
			/*set up the prompt box for humans*/
			this.prompt = new JLabel("<html><p>" + game.getActivePlayer().getName() + 
					": " + prompt + "</p></html>");
			this.prompt.setFont(new Font("Papyrus",Font.BOLD,20));
			this.prompt.setHorizontalAlignment(SwingConstants.CENTER);
			this.prompt.setBackground(Color.WHITE);
			this.prompt.setOpaque(true);
			promptBox.add(new JLabel(game.getActivePlayer().displayItem(
					game.getActivePlayer())));
			promptBox.add(this.prompt);
			/*create buttons out of the generic list*/
			for (int i = 0; i < options.size() + passAvailable; i++) {
					if (i == options.size()) { //button for pass option
						/*create button for pass option*/
						JButton temp = new JButton(passOption);
						temp.setToolTipText(passOption);
						temp.setBackground(Color.BLACK);
						temp.setForeground(Color.WHITE);
						temp.setContentAreaFilled(false);
						temp.setOpaque(true);
						temp.setBorderPainted(false);
						selections.add(temp);
					} 
					else {
						//check if it is a game item
						if (options.get(0) instanceof GameItem) {
							icon = ((GameItem) (options.get(i)))
									.displayItem(game.getActivePlayer());
						} 
						//if not, it is an enumerated type (or game will crash)
						else {
							icon = ((EnumToImage<T>) (options.get(i)))
									.enumToImage((T) (options.get(i)));
						}
						/*create button for object option*/
						JButton temp = new JButton(icon);
						//implement toString() for informative message on hover
						temp.setToolTipText(options.get(i).toString());
						temp.setBackground(Color.BLACK);
						temp.setContentAreaFilled(false);
						temp.setOpaque(true);
						temp.setBorderPainted(false);
						selections.add(temp);
					}
				selections.get(i).setMaximumSize(new Dimension(64, 64));
				final int j = i;
				selections.get(i).addActionListener(new ActionListener() { 
							//adds functionality for buttons to return objects
							public void actionPerformed(ActionEvent e) {
								if (j == options.size()) { //return null = pass
									selected.add(null);
								} 
								else { //return object of selection
									selected.add(options.get(j));
								}
							}
						});
				/*update graphics*/
				optionPanel.add(selections.get(i));
				game.getGUI().add(template);
				template.add(promptBox);
				template.add(optionPanel);
				template.add(othersPanel);
				game.getGUI().validate();
				game.getGUI().repaint();
				game.getGUI().setVisible(true);
			}
		} 
		else if (options.size() > 0) {
			/*message for human to eavesdrop on AI*/
			this.prompt = new JLabel("<html><p>" + game.getActivePlayer().getName() + 
					" selected: </p></html>");
			this.prompt.setFont(new Font("Papyrus",Font.PLAIN,20));
			this.prompt.setHorizontalAlignment(SwingConstants.CENTER);
			this.prompt.setBackground(Color.WHITE);
			this.prompt.setOpaque(true);
			this.prompt.setOpaque(true);
			promptBox.add(new JLabel(
					game.getActivePlayer().displayItem(game.getActivePlayer())));
			promptBox.add(this.prompt);
			//random selector for AI selection
			RandomSelection<T> optionsList = new RandomSelection<T>();
			T selection = optionsList.getRandomFromList(options, false,
					passAvailable);
			selected.add(selection);
			
			JButton temp;
			//check if it is a game item
			if (selected.get(0) instanceof GameItem) {
				icon = ((GameItem) (selected.get(0)))
						.displayItem(game.getActivePlayer());
				temp = new JButton(icon);
			} 
			//if not, it is an enumerated type or pass option
			else if (selected.get(0) != null) { // false if pass option
				icon = ((EnumToImage) (selected.get(0)))
						.enumToImage((T) (selected.get(0)));
				temp = new JButton(icon);
			}
			else{ //pass option
				temp = new JButton(passOption);
				temp.setToolTipText(passOption);
				temp.setBackground(Color.BLACK);
				temp.setForeground(Color.WHITE);
				temp.setContentAreaFilled(false);
				temp.setOpaque(true);
				temp.setBorderPainted(false);
				//add AI selection to controller
				selections.add(temp);
			}
			/*update graphics*/
			temp.setToolTipText(selected.toString());
			temp.setBackground(Color.BLACK);
			temp.setForeground(Color.WHITE);
			temp.setContentAreaFilled(false);
			temp.setOpaque(true);
			temp.setBorderPainted(false);
			selections.add(temp);
			optionPanel.add(selections.get(0));
			game.getGUI().add(template);
			template.add(promptBox);
			template.add(optionPanel);
			template.add(othersPanel);
			game.getGUI().validate();
			game.getGUI().repaint();
			game.getGUI().setVisible(true);
			/*wait for one second so human can see AI decision*/
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
		else { //return pass option
			return null;
		}
		/*delay process while human makes a decision*/
		while (selected.size() == 0) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/*update remaining graphics*/
		updateBoard(game);
		template.removeAll();
		game.getGUI().remove(template);
		return (selected.size() > 0) ? selected.get(0) : null;
	}
	
	/**
	 * Responsible for clearing and updating the game board
	 * 
	 * @param game The current game state
	 */
	public void updateBoard(Game game){

		/*remove potentially invalid data*/
		game.getBoard().building.removeAll();
		game.getBoard().resource.removeAll();
		game.getBoard().building1.removeAll();
		game.getBoard().resource1.removeAll();
		game.getBoard().buildingHM.removeAll();
		game.getBoard().resourceHM.removeAll();
		game.getBoard().Info1.removeAll();
		game.getBoard().Info2.removeAll();
		game.getBoard().InfoHM.removeAll();
		
		//create a player to start as human
		Player player = game.getActivePlayer();
		//rotate until human
		while(player.getHuman() == false){
			player = player.getNext();
		}
		/*set human board data*/
		game.getBoard().setButtonsHM(player);
		game.getBoard().PlayerHMwallet(player);
		game.getBoard().PlayerHMcards(player);
		game.getBoard().ageHM = new JButton(player.getAge().enumToImage(
				player.getAge()));
		game.getBoard().ageHM.setBackground(Color.LIGHT_GRAY);
		game.getBoard().ageHM.setOpaque(true);
		game.getBoard().ageHM.setToolTipText(player.getAge().toString());
		game.getBoard().InfoHM.add(game.getBoard().ageHM);
		game.getBoard().InfoHM.add(game.getBoard().raceHM);
		game.getBoard().InfoHM.add(game.getBoard().ageHM);
		game.getBoard().InfoHM.add(game.getBoard().walletHM);
		game.getBoard().InfoHM.add(game.getBoard().walletInfoHM);
		game.getBoard().InfoHM.add(game.getBoard().cardHM);
		game.getBoard().InfoHM.add(game.getBoard().CardInfoHM);
		
		//move to first AI after human
		player = player.getNext();
		/*set first AI board data*/
		game.getBoard().setButtons(player);
		game.getBoard().Player1wallet(player);
		game.getBoard().Player1cards(player);
		game.getBoard().age1 = new JButton(player.getAge().enumToImage(
				player.getAge()));
		game.getBoard().age1.setBackground(Color.LIGHT_GRAY);
		game.getBoard().age1.setOpaque(true);
		game.getBoard().age1.setToolTipText(player.getAge().toString());
		game.getBoard().Info1.add(game.getBoard().race1);
		game.getBoard().Info1.add(game.getBoard().age1);
		game.getBoard().Info1.add(game.getBoard().wallet1);
		game.getBoard().Info1.add(game.getBoard().walletInfo1);
		game.getBoard().Info1.add(game.getBoard().card1);
		game.getBoard().Info1.add(game.getBoard().CardInfo1);

		//move to second AI after human
		player = player.getNext();
		/*set second AI board data*/
		game.getBoard().setButtons1(player);
		game.getBoard().Player2wallet(player);
		game.getBoard().Player2cards(player);
		game.getBoard().age2 = new JButton(player.getAge().enumToImage(
				player.getAge()));
		game.getBoard().age2.setBackground(Color.LIGHT_GRAY);
		game.getBoard().age2.setOpaque(true);
		game.getBoard().age2.setToolTipText(player.getAge().toString());
		game.getBoard().Info2.add(game.getBoard().age2);
		game.getBoard().Info2.add(game.getBoard().race2);
		game.getBoard().Info2.add(game.getBoard().age2);
		game.getBoard().Info2.add(game.getBoard().wallet2);
		game.getBoard().Info2.add(game.getBoard().walletInfo2);
		game.getBoard().Info2.add(game.getBoard().card2);
		game.getBoard().Info2.add(game.getBoard().CardInfo2);
		
		setOthers(game);
		/*add components back to board*/
		for(int i = 0; i < 16; i++){
			game.getBoard().building.add(game.getBoard().built[i]);
			game.getBoard().resource.add(game.getBoard().reso[i]);
			game.getBoard().building1.add(game.getBoard().built1[i]);
			game.getBoard().resource1.add(game.getBoard().reso1[i]);
			game.getBoard().buildingHM.add(game.getBoard().builtHM[i]);
			game.getBoard().resourceHM.add(game.getBoard().resoHM[i]);
		}
	}

	/**
	 * This method sets the bank and the leaderboard
	 * 
	 * @param game The current games state
	 */
	public void setOthers(Game game){

		bankInfoLabel.removeAll();
		scoreInfoLabel.removeAll();
			
			JLabel food = new JLabel();
			food.setForeground(Color.GREEN);
			food.setText("Food:" + game.getBank()[0]);
			bankInfoLabel.add(food);
			
			JLabel wood = new JLabel();
			wood.setText("Wood:" + game.getBank()[1]);
			bankInfoLabel.add(wood);
			
			JLabel gold = new JLabel();
			gold.setForeground(Color.ORANGE);
			gold.setText("Gold:" + game.getBank()[2]);
			bankInfoLabel.add(gold);
			
			JLabel favor = new JLabel();
			favor.setForeground(Color.BLUE);
			favor.setText("Favor:" + game.getBank()[3]);
			bankInfoLabel.add(favor);
		
			ArrayList<Player> leaderBoard = new ArrayList<Player>();
			ArrayList<Player> options = new ArrayList<Player>();
			
			for(int i = 0; i < 3; i++){
				options.add(game.getActivePlayer());
				game.setActivePlayer(game.getActivePlayer().getNext());
			}
			
			while(leaderBoard.size() < 3){
				Player max = options.get(0);
				for(int i = 0; i < options.size(); i ++){
					if(max.getWallet()[4] == options.get(i).getWallet()[4]){
						//humans take precedence 
						//NONCRITICAL BUG FOR MULTIPLE HUMANS
						if(options.get(i).getHuman()){
							max = options.get(i);
						}
					}
					else if(max.getWallet()[4] < options.get(i).getWallet()[4]){
						max = options.get(i);
					}
				}
				leaderBoard.add(max);
				options.remove(max);
			}
			
			/*create leaderboard entries*/
			JLabel first = new JLabel("1st: " + leaderBoard.get(0) + 
					" (" + leaderBoard.get(0).getWallet()[4] + ")");
			first.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel second = new JLabel("2nd: " + leaderBoard.get(1) + 
					" (" + leaderBoard.get(1).getWallet()[4] + ")");
			second.setHorizontalAlignment(SwingConstants.CENTER);
			JLabel third = new JLabel("3rd: " + leaderBoard.get(2) + 
					" (" + leaderBoard.get(2).getWallet()[4] + ")");
			third.setHorizontalAlignment(SwingConstants.CENTER);
			
			/*update board for graphics*/
			this.scoreInfoLabel.add(first);
			this.scoreInfoLabel.add(second);
			this.scoreInfoLabel.add(third);
	}
}



