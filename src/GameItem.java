import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * An interface to turn game objects into images if needed. Used along with 
 * EnumToImage to make User Interface generic within the context of the game
 */
public interface GameItem<T> {
	/**
	 * Takes a GameItem and turns it into an Image for the generic user 
	 * interface.
	 * 
	 * @param owner Used for images that are conditional on player being human
	 * 
	 * @return The image of the game item
	 */
	public ImageIcon displayItem(Player owner);
}
