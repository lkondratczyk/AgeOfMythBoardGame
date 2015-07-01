import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Board{
	
	private JPanel playerContainer= new JPanel();
	
	protected JPanel Controller = new JPanel();
	
	protected JPanel Panplayer1 = new JPanel();
	protected JPanel Panplayer2 = new JPanel();
	protected JPanel PanplayerHM = new JPanel();
	
	protected JPanel player1Info = new JPanel();
	protected JPanel player2Info = new JPanel();
	protected JPanel playerHMInfo = new JPanel();
	
	protected JPanel Info1 = new JPanel();
	protected JPanel Info2 = new JPanel();
	protected JPanel InfoHM = new JPanel();
	
	protected JPanel building = new JPanel();
	protected JPanel resource = new JPanel();
	protected JPanel building1 = new JPanel();
	protected JPanel resource1 = new JPanel();
	protected JPanel buildingHM = new JPanel();
	protected JPanel resourceHM = new JPanel();
	
	protected JButton []reso = new JButton [16];
	protected JButton []built = new JButton [16];
	protected JButton []reso1 = new JButton [16];
	protected JButton []built1 = new JButton [16];
	protected JButton []resoHM = new JButton [16];
	protected JButton []builtHM = new JButton [16];
	
	protected JLabel player1ID;
	protected JLabel player2ID;
	protected JLabel player3ID;
	
	protected JLabel race1;
	protected JLabel race2;
	protected JLabel raceHM;
	
	protected JButton age1;
	protected JButton age2;
	protected JButton ageHM;

	protected JLabel wallet1;
	protected JLabel wallet2;
	protected JLabel walletHM;
	protected JLabel walletInfo1;
	protected JLabel walletInfo2;
	protected JLabel walletInfoHM;
	
	protected JLabel card1;
	protected JLabel CardInfo1;
	protected JLabel card2;
	protected JLabel CardInfo2;
	protected JLabel cardHM;
	protected JLabel CardInfoHM;

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(Game game) {

		playerContainer.setLayout(new GridLayout(3,0));
		
		player1ID = new JLabel();
		player2ID = new JLabel();
		player3ID = new JLabel();
		race1 = new JLabel();
		race2 = new JLabel();
		raceHM = new JLabel();
		age1 = new JButton();
		age2 = new JButton();
		ageHM = new JButton();
		wallet1 = new JLabel();
		walletInfo1 = new JLabel();
		card1 = new JLabel();
		CardInfo1 = new JLabel();
		wallet2 = new JLabel();
		walletInfo2 = new JLabel();
		card2 = new JLabel();
		CardInfo2 = new JLabel();
		walletHM = new JLabel();
		walletInfoHM = new JLabel();
		cardHM = new JLabel();
		CardInfoHM = new JLabel();
		
		PlayerPanel(game);
		PlayerPanel1(game);
		PlayerPanelHM(game);
		
		player1ID.setBackground(Color.WHITE);
		player1ID.setOpaque(true);
		player2ID.setBackground(Color.WHITE);
		player2ID.setOpaque(true);
		player3ID.setBackground(Color.WHITE);
		player3ID.setOpaque(true);
		
		race1.setBackground(Color.WHITE);
		race1.setOpaque(true);
		race2.setBackground(Color.WHITE);
		race2.setOpaque(true);
		raceHM.setBackground(Color.WHITE);
		raceHM.setOpaque(true);
		
		wallet1.setFont(new Font("Papyrus", Font.BOLD,10));
		wallet1.setHorizontalAlignment(SwingConstants.CENTER);
		wallet1.setBackground(Color.WHITE);
		wallet1.setOpaque(true);
		wallet1.setText("WALLET:");
		wallet1.setForeground(Color.BLACK);
		Info1.setLayout(new GridLayout(3,1));
		Info1.add(wallet1);
		walletInfo1.setLayout(new GridLayout(2,6));
		walletInfo1.setBorder(new LineBorder(Color.BLACK));
		Info1.add(walletInfo1);

		card1.setFont(new Font("Papyrus",Font.BOLD,10));
		card1.setHorizontalAlignment(SwingConstants.CENTER);
		card1.setBackground(Color.WHITE);
		card1.setOpaque(true);
		card1.setText("HAND:");
		Info1.add(card1);
		CardInfo1.setLayout(new GridLayout(2,2));
		Info1.add(CardInfo1);
		CardInfo1.setBorder(new LineBorder(Color.BLUE));

		wallet2.setFont(new Font("Papyrus", Font.BOLD,10));
		wallet2.setHorizontalAlignment(SwingConstants.CENTER);
		wallet2.setBackground(Color.WHITE);
		wallet2.setOpaque(true);
		wallet2.setText("WALLET:");
		wallet2.setForeground(Color.BLACK);
		Info2.add(wallet2);
		walletInfo2.setLayout(new GridLayout(2,6));
		walletInfo2.setBorder(new LineBorder(Color.BLACK));
		Info2.add(walletInfo2);

		card2.setFont(new Font("Papyrus",Font.BOLD,10));
		card2.setHorizontalAlignment(SwingConstants.CENTER);
		card2.setBackground(Color.WHITE);
		card2.setOpaque(true);
		card2.setText("HAND:");
		Info2.add(card2);
		CardInfo2.setLayout(new GridLayout(2,2));
		CardInfo2.setBorder(new LineBorder(Color.BLUE));
		Info2.add(CardInfo2);

		walletHM.setFont(new Font("Papyrus", Font.BOLD,10));
		walletHM.setHorizontalAlignment(SwingConstants.CENTER);
		walletHM.setBackground(Color.WHITE);
		walletHM.setOpaque(true);
		walletHM.setText("WALLET:");
		InfoHM.add(walletHM);
		walletInfoHM.setLayout(new GridLayout(2,6));
		walletInfoHM.setBorder(new LineBorder(Color.BLACK));
		InfoHM.add(walletInfoHM);

		cardHM.setFont(new Font("Papyrus",Font.BOLD,10));
		cardHM.setHorizontalAlignment(SwingConstants.CENTER);
		cardHM.setBackground(Color.WHITE);
		cardHM.setOpaque(true);
		cardHM.setText("HAND:");
		InfoHM.add(cardHM);
		CardInfoHM.setLayout(new GridLayout(2,2));
		CardInfoHM.setBorder(new LineBorder(Color.BLUE));
		InfoHM.add(CardInfoHM);
		
		playerContainer.add(player1Info);
		playerContainer.add(player2Info);
		playerContainer.add(playerHMInfo);
		game.getGUI().add(playerContainer, BorderLayout.EAST);
		
	}
	
	/*
	 * The PlayerPanel() function sets up the player panels
	 * with their building, resource buttons and information section
	 */
	private void PlayerPanel(Game game){
	
		Info1.setLayout(new GridLayout(1,1));
		
		player1Info.setLayout(new BorderLayout());        //The right side should display the information of the player
		player1Info.setBorder(new LineBorder(Color.RED));
		Panplayer1.setLayout(new GridLayout(2,1));       //This panels holds the building and resource panel
		//Panplayer1.setBorder(new LineBorder(Color.black));
		Player player = game.getActivePlayer();
		while(player.getHuman() == false){
			player = player.getNext();
		}
		player = player.getNext();
		
		setButtons(player);   //Sets the buttons 
		race1 = new JLabel(player.getRace().enumToImage(player.getRace()));
		JLabel name = new JLabel(player.getName());
		name.setFont(new Font("Papyrus", Font.BOLD,10));
		name.setBackground(Color.WHITE);
		name.setOpaque(true);
		race1.setBackground(Color.BLACK);
		Info1.add(race1);
		age1 = new JButton(player.getAge().enumToImage(player.getAge()));
		age1.setBackground(Color.LIGHT_GRAY);
		age1.setOpaque(true);
		age1.setBorderPainted(false);
		age1.setToolTipText(player.getAge().toString());
		Info1.add(age1);

		player1Info.add(name);
		
		Player1wallet(player);
		Player1cards(player);
		
		building.setBorder(new LineBorder(Color.black));
		building.setLayout(new GridLayout(2,8));
		for(int i = 0; i<16;i++){
			building.add(built[i]);
		
		}
		Panplayer1.add(building);
		resource.setLayout(new GridLayout(2,8));
		resource.setBorder(new LineBorder(Color.blue));
		for(int i = 0; i<16;i++){
			resource.add(reso[i]);
			
			}
		Panplayer1.add(resource);
		player1Info.add(Info1, BorderLayout.CENTER);
		player1Info.add(Panplayer1, BorderLayout.EAST);
	}
	
	
public void Player1wallet(Player player){
	walletInfo1.removeAll();
	
	//walletInfo.setFont(new Font("Courier New", Font.ITALIC, 12));
	JLabel food = new JLabel();
	food.setForeground(Color.GREEN);
	food.setText("Fd:" + player.getWallet()[0]);
	//food.setToolTipText(""+game.player1.wallet[0].toString());
	walletInfo1.add(food);
	
	JLabel wood = new JLabel();
	wood.setText("Wd:" + player.getWallet()[1]);
	wood.setToolTipText("Wood "+player.getWallet()[1]);
	walletInfo1.add(wood);
	
	JLabel gold = new JLabel();
	gold.setForeground(Color.ORANGE);
	gold.setText("Gd:" + player.getWallet()[2]);
	gold.setToolTipText("Gold "+player.getWallet()[2]);
	walletInfo1.add(gold);
	
	JLabel favor = new JLabel();
	favor.setForeground(Color.BLUE);
	favor.setText("Fv:" + player.getWallet()[3]);
	favor.setToolTipText("Favor "+player.getWallet()[3]);
	walletInfo1.add(favor);
}
public void Player1cards(Player player){
	CardInfo1.removeAll();
	
	int rand = 0;
	int perm = 0;
	
	for(int i = 0; i < player.getHand().size(); i ++){
		if(player.getHand().get(i).getPermanent())
			perm ++;
		else
			rand ++;
	}
	
	JLabel permCard = new JLabel("Perm:" + perm);
	JLabel ranCard = new JLabel("Rand:" + rand);
	
	permCard.setToolTipText("Permanent Card "+perm);
	ranCard.setToolTipText("Random Card "+rand);

	CardInfo1.add(permCard);
	CardInfo1.add(ranCard);
	//Info1.add(cardsNum);
}
	
private void PlayerPanel1(Game game){
		
		Info2.setLayout(new GridLayout(3,0));		
		
		player2Info.setLayout(new BorderLayout());
		player2Info.setBorder(new LineBorder(Color.RED));
		Panplayer2.setLayout(new GridLayout(2,1));
		Panplayer2.setBorder(new LineBorder(Color.black));
		
		Player player = game.getActivePlayer();
		while(player.getHuman() == false){
			player = player.getNext();
		}
		player = player.getNext();
		player = player.getNext();
		setButtons1(player);
		race2 = new JLabel(player.getRace().enumToImage(player.getRace()));
		Info2.add(race2);
		age2 = new JButton(player.getAge().enumToImage(player.getAge()));
		age2.setBackground(Color.LIGHT_GRAY);
		age2.setOpaque(true);
		age2.setBorderPainted(false);
		age2.setToolTipText(player.getAge().toString());
		Info2.add(age2);
		
		Player2wallet(player);
		Player2cards(player);
		
		building1.setBorder(new LineBorder(Color.black));
		building1.setLayout(new GridLayout(2,8));
		for(int i = 0; i<16;i++){
		building1.add(built1[i]);
		}
		Panplayer2.add(building1);
		resource1.setLayout(new GridLayout(2,8));
		resource1.setBorder(new LineBorder(Color.blue));
		for(int i = 0; i<16;i++){
			resource1.add(reso1[i]);
			}
		Panplayer2.add(resource1);
		player2Info.add(Info2, BorderLayout.CENTER);
		player2Info.add(Panplayer2, BorderLayout.EAST);
	}

public void Player2wallet(Player player){
	walletInfo2.removeAll();
	
	//walletInfo.setFont(new Font("Courier New", Font.ITALIC, 12));
	JLabel food = new JLabel();
	food.setForeground(Color.GREEN);
	food.setText("Fd:" + player.getWallet()[0]);
	food.setToolTipText("Food "+player.getWallet()[0]);
	walletInfo2.add(food);
	
	JLabel wood = new JLabel();
	wood.setText("Wd:" + player.getWallet()[1]);
	wood.setToolTipText("Wood "+player.getWallet()[1]);
	walletInfo2.add(wood);
	
	JLabel gold = new JLabel();
	gold.setForeground(Color.ORANGE);
	gold.setText("Gd:" + player.getWallet()[2]);
	gold.setToolTipText("Gold "+player.getWallet()[2]);
	walletInfo2.add(gold);
	
	JLabel favor = new JLabel();
	favor.setForeground(Color.BLUE);
	favor.setText("Fv:" + player.getWallet()[3]);
	favor.setToolTipText("Favor "+player.getWallet()[3]);
	walletInfo2.add(favor);
}
public void Player2cards(Player player){
	CardInfo2.removeAll();

	int rand = 0;
	int perm = 0;
	
	for(int i = 0; i < player.getHand().size(); i ++){
		if(player.getHand().get(i).getPermanent())
			perm ++;
		else
			rand ++;
	}
	
	JLabel permCard = new JLabel("Perm: " + perm);
	JLabel ranCard = new JLabel("Rand: " + rand);
	
	permCard.setToolTipText("Permanent Card "+perm);
	ranCard.setToolTipText("Random Card "+rand);
	
	CardInfo2.add(permCard);
	CardInfo2.add(ranCard);
	//Info1.add(cardsNum);
}

private void PlayerPanelHM(Game game){

	InfoHM.setLayout(new GridLayout(3,0));
	
	playerHMInfo.setLayout(new BorderLayout());
	playerHMInfo.setBorder(new LineBorder(Color.RED));
	PanplayerHM.setLayout(new GridLayout(2,1));
	PanplayerHM.setBorder(new LineBorder(Color.black));
	
	Player player = game.getActivePlayer();
	while(player.getHuman() == false){
		player = player.getNext();
	}
	setButtonsHM(player);
	raceHM = new JLabel(player.getRace().enumToImage(player.getRace()));
	InfoHM.add(raceHM);
	ageHM = new JButton(player.getAge().enumToImage(player.getAge()));
	ageHM.setBackground(Color.LIGHT_GRAY);
	ageHM.setOpaque(true);
	ageHM.setBorderPainted(false);
	ageHM.setToolTipText(player.getAge().toString());
	InfoHM.add(ageHM);
	
	PlayerHMwallet(player);
	PlayerHMcards(player);
	
	buildingHM.setBorder(new LineBorder(Color.black));
	buildingHM.setLayout(new GridLayout(2,8));
	for(int i = 0; i<16;i++){
	buildingHM.add(builtHM[i]);
	}
	PanplayerHM.add(buildingHM);
	resourceHM.setLayout(new GridLayout(2,8));
	resourceHM.setBorder(new LineBorder(Color.blue));
	for(int i = 0; i<16;i++){
		resourceHM.add(resoHM[i]);
		}
	
	PanplayerHM.add(resourceHM);
	playerHMInfo.add(InfoHM, BorderLayout.CENTER);
	playerHMInfo.add(PanplayerHM, BorderLayout.EAST);
	
}

public void PlayerHMwallet(Player player){
	walletInfoHM.removeAll();
	//walletInfo.setFont(new Font("Courier New", Font.ITALIC, 12));
	JLabel food = new JLabel();
	food.setForeground(Color.GREEN);
	food.setText("Fd: "+ player.getWallet()[0]);
	food.setToolTipText("Food "+player.getWallet()[0]);
	walletInfoHM.add(food);
	
	JLabel wood = new JLabel();
	wood.setText("Wd:"+ player.getWallet()[1]);
	wood.setToolTipText("Wood "+player.getWallet()[1]);
	walletInfoHM.add(wood);
	
	JLabel gold = new JLabel();
	gold.setForeground(Color.ORANGE);
	gold.setText("Gd:"+ player.getWallet()[2]);
	gold.setToolTipText("Gold "+player.getWallet()[2]);
	walletInfoHM.add(gold);
	
	JLabel favor = new JLabel();
	favor.setForeground(Color.BLUE);
	favor.setText("Fv:"+ player.getWallet()[3]);
	favor.setToolTipText("Favor "+player.getWallet()[3]);
	walletInfoHM.add(favor);
}
public void PlayerHMcards(Player player){
	CardInfoHM.removeAll();
	int rand = 0;
	int perm = 0;
	
	for(int i = 0; i < player.getHand().size(); i ++){
		if(player.getHand().get(i).getPermanent())
			perm ++;
		else
			rand ++;
	}
	
	JLabel permCard = new JLabel("Perm: " + perm);
	JLabel ranCard = new JLabel("Rand: " + rand);
	
	CardInfoHM.add(permCard);
	CardInfoHM.add(ranCard);
	//Info1.add(cardsNum);
}
	/*
	 * The setButton() functions are used to initialize the buttons
	 * and to add action listeners to all the buttons
	 */
	protected void setButtons(Player player){
		for(int i = 0; i<16; i++){
			if(i < player.getProduction().size()){
				reso[i] = new JButton(
						player.getProduction().get(i).displayItem(player));
				reso[i].setToolTipText(player.getProduction().get(i).toString());

			}
			else{
				reso[i] = new JButton(player.getTerrain().get(i - 
						player.getProduction().size()).enumToImage(
						player.getTerrain().get(i - 
								player.getProduction().size())));
				reso[i].setToolTipText(player.getTerrain().get(i - 
						player.getProduction().size()).toString());
			}
			if(i < player.getCity().size()){
				built[i] = new JButton(
						player.getCity().get(i).displayItem(player));
				built[i].setToolTipText(player.getCity().get(i).toString());

			}
			else{
				built[i] = new JButton(new ImageIcon("EmptyLot.png"));
				built[i].setToolTipText("An empty lot for buildings.");
			}
		//	reso.add(new JButton((Icon) this.player1.production.get(i)));
			//built.add(new JButton());
			//this.gui.repaint();
			//reso[i].addActionListener(click);
			//reso[i].setToolTipText("This is a Resource from player 1");
			reso[i].setBackground(Color.BLACK);
			reso[i].setContentAreaFilled(false);
			reso[i].setOpaque(true);
			reso[i].setBorderPainted(false);
			//built[i].setToolTipText("This is a Builing from player 1");
			built[i].setBackground(Color.BLACK);
			built[i].setContentAreaFilled(false);
			built[i].setOpaque(true);
			built[i].setBorderPainted(false);
		}	
	}
	protected void setButtons1(Player player){
		//AEClick click = new AEClick();
		for(int i = 0; i<16; i++){
			if(i < player.getProduction().size()){
				reso1[i] = new JButton(
						player.getProduction().get(i).displayItem(player));
				reso1[i].setToolTipText(player.getProduction().get(i).toString());

			}
			else{
				reso1[i] = new JButton(player.getTerrain().get(i - 
						player.getProduction().size()).enumToImage(
						player.getTerrain().get(i - 
								player.getProduction().size())));
				reso1[i].setToolTipText(player.getTerrain().get(i - 
						player.getProduction().size()).toString());
			}
			if(i < player.getCity().size()){
				built1[i] = new JButton(
						player.getCity().get(i).displayItem(player));
				built1[i].setToolTipText(player.getCity().get(i).toString());

			}
			else{
				built1[i] = new JButton(new ImageIcon("EmptyLot.png"));
				built1[i].setToolTipText("An empty lot for buildings.");
			}
		//	reso.add(new JButton((Icon) this.player1.production.get(i)));
			//built.add(new JButton());
			//this.gui.repaint();
			//reso1[i].addActionListener(click);
			reso1[i].setBackground(Color.BLACK);
			reso1[i].setContentAreaFilled(false);
			reso1[i].setOpaque(true);
			reso1[i].setBorderPainted(false);
			built1[i].setBackground(Color.BLACK);
			built1[i].setContentAreaFilled(false);
			built1[i].setOpaque(true);
			built1[i].setBorderPainted(false);
		}	
	}
	
	protected void setButtonsHM(Player player){
		//AEClick click = new AEClick();
		for(int i = 0; i<16; i++){
			if(i < player.getProduction().size()){
				resoHM[i] = new JButton(
						player.getProduction().get(i).displayItem(player));
				resoHM[i].setToolTipText(player.getProduction().get(i).toString());
			}
			else{
				resoHM[i] = new JButton(player.getTerrain().get(i - 
								player.getProduction().size()).enumToImage(
								player.getTerrain().get(i - 
										player.getProduction().size())));
				resoHM[i].setToolTipText(player.getTerrain().get(i - 
						player.getProduction().size()).toString());
			}
			if(i < player.getCity().size()){
				builtHM[i] = new JButton(
						player.getCity().get(i).displayItem(player));
				builtHM[i].setToolTipText(player.getCity().get(i).toString());

			}
			else{
				builtHM[i] = new JButton(new ImageIcon("EmptyLot.png"));
				builtHM[i].setToolTipText("An empty lot for buildings.");
			}
		//	reso.add(new JButton((Icon) this.player1.production.get(i)));
			//built.add(new JButton());
			//this.gui.repaint();
			//resoHM[i].addActionListener(click);
			//resoHM[i].setToolTipText("This is a Resource from player 1");
			resoHM[i].setBackground(Color.BLACK);
			resoHM[i].setContentAreaFilled(false);
			resoHM[i].setOpaque(true);
			resoHM[i].setBorderPainted(false);
			//builtHM[i].setToolTipText("This is a Builing from player 1");
			builtHM[i].setBackground(Color.BLACK);
			builtHM[i].setContentAreaFilled(false);
			builtHM[i].setOpaque(true);
			builtHM[i].setBorderPainted(false);
		}
	}
}