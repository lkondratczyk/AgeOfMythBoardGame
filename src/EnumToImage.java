import javax.swing.ImageIcon;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * An interface to turn enumerated types into images. Used along with 
 * GameItem to make the User Interface generic within the context of the game
 */
public interface EnumToImage<T> {
	
	/**
	 * Takes an enumerated member and turns it into an Image for the generic 
	 * user interface.
	 * 
	 * @param enumerated Strategy is to take enumerated value and compare it
	 * 					 against a list of possible values
	 * 
	 * @return The image corresponding to the enumerated type
	 */
	public ImageIcon enumToImage(T enumerated);
}
