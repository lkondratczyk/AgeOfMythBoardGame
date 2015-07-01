import java.util.ArrayList;
import java.util.Random;

/**
 * @author Lyndon Kondratczyk
 * @version 3/9/15
 * 
 * A tool for randomly selecting an element from a generic ArrayList
 */
public class RandomSelection <T>{
	
	/**
	 * Chooses a random element from options, removes it and returns it
	 * 
	 * @param remove True if item selected is to be removed
	 * @return The removed Object from options
	 */
	public T getRandomFromList (ArrayList<T> optionList, boolean remove, 
			int passOption) {
		Random rand = new Random();
		int index = rand.nextInt(optionList.size() + passOption);
		if(index == optionList.size()){
			return null;
		}
		else{
			T temp = optionList.get(index);
			if(remove)
				optionList.remove(index);
			return temp;
		}
	}
}
