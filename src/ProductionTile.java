import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * The template for an AoM Production Tiles
 */
public class ProductionTile implements GameItem<ProductionTile>{
	protected enum Terrain implements EnumToImage<ProductionTile.Terrain>{
		DESERT, SWAMP, FOREST, FERTILE, HILLS, MOUNTAINS;
		public ImageIcon enumToImage(ProductionTile.Terrain terrain){
			switch(terrain){
				case DESERT:
					return new ImageIcon("Desert.png");
				case SWAMP:
					return new ImageIcon("Swamp.png");
				case FOREST:
					return new ImageIcon("Forest.png");
				case FERTILE:
					return new ImageIcon("Fertile.png");
				case HILLS:
					return new ImageIcon("Hills.png");
				case MOUNTAINS:
					return new ImageIcon("Mountain.png");
				default:
					return new ImageIcon("Desert.png");
			}
		}
	};

	private Terrain type;
	private ImageIcon image;
	private int[] resource;
	private int quantity;
	private boolean hasVillager;

	/**
	 * The constructor for a ProductionTile
	 * 
	 * @param terrain The Terrain type
	 * @param food The food benefit
	 * @param wood The wood benefit
	 * @param gold The gold benefit
	 * @param favor The favor benefit
	 * @param quantity The number of like template in the starting pool
	 */
	public ProductionTile(ProductionTile.Terrain terrain, ImageIcon image, 
			int food, int wood,	int gold, int favor, int quantity) {
		resource = new int[4];
		type = terrain;
		this.image = image;
		resource[0] = food;
		resource[1] = wood;
		resource[2] = gold;
		resource[3] = favor;
		this.quantity = quantity;
		hasVillager = false;
	}
	
	public ProductionTile(ProductionTile pt) {
		type = pt.type;
		image = new ImageIcon();
		image = pt.image;
		resource = pt.resource;
		quantity = pt.quantity;
		hasVillager = false;
	}
	
	/**
	 * Converts the ProductionTile into a String for printing on a menu
	 */
	@Override
	public String toString(){
		String resources = "";
		String villager = "";
		for(int i = 0; i < resource.length; i ++){
			if(resource[i] > 0){
				resources = resources + resource[i];
				switch(i){
					case 0: 
						resources = resources + " Food";
						break;
					case 1: 
						resources = resources + " Wood";
						break;
					case 2: 
						resources = resources + " Gold";
						break;
					case 3: 
						resources = resources + " Favor";
						break;
					default: break;
				}
			}
		}
		if(this.hasVillager == true)
			villager = "(villager)";
		return ("" + type + " " + villager + " : " + resources);
	}
	
	/**
	 * Implements displayItem() from GameItem, used for displaying
	 * on the graphical interface
	 */
	public ImageIcon displayItem(Player owner){
		BufferedImage tile = new BufferedImage(64, 
				64, BufferedImage.TYPE_INT_RGB);
		Graphics g = tile.createGraphics();
		image.paintIcon(null, g, 0, 0);
		BufferedImage overlay;
		if(this.hasVillager == true){
			//paint a villager on the icon
			try {
				overlay = ImageIO.read(new File("villager.png"));
				g.drawImage(overlay, 0, 0, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ImageIcon(tile);
	}
	
	/**
	 * Mutator for Terrain type
	 * 
	 * @param type Terrain type (enumerated)
	 */
	public void setType(Terrain type){
		this.type = type;
	}
	
	/**
	 * Mutator for Terrain image
	 * 
	 * @param image The Terrain image
	 */
	public void setImage(ImageIcon image){
		this.image = image;
	}
	
	/**
	 * Mutator for Terrain resource
	 * 
	 * @param resource The Terrain resource
	 */
	public void setResource(int[] resource){
		this.resource = resource;
	}
	
	/**
	 * Mutator for the quantity of the Player's Terrain
	 * @param quantity The quantity of the Player's Terrain
	 */
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	/**
	 * Mutator for the Terrain's Villager
	 * 
	 * @param hasVillager True if the Terrain has a villager
	 */
	public void setVillager(boolean hasVillager){
		this.hasVillager = hasVillager;
	}
	
	/**
	 * Accessor for the Terrain type
	 * 
	 * @return The Terrain type
	 */
	public Terrain getType(){
		return this.type;
	}
	
	/**
	 * Accessor for the Terrain image
	 * 
	 * @return The image for the terrain
	 */
	public ImageIcon getImage(){
		return this.image;
	}
	
	/**
	 * Accessor for the Terrain's resources
	 * 
	 * @return The Terrain's resources
	 */
	public int[] getResource(){
		return this.resource;
	}
	
	/**
	 * Acessor for the Player's quantity of the Terrain
	 * 
	 * @return The quantity of the Player's Terrain
	 */
	public int getQuantity(){
		return this.quantity;
	}
	
	/**
	 * Accessor for the Terrain's Villager
	 * 
	 * @return True if the Terrain has a Villager
	 */
	public boolean getVillager(){
		return this.hasVillager;
	}
}
