package observer;

import java.util.HashMap;

/*
 * Abstract class implementing the Observer Pattern.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	13052013
 */
public abstract class Subject {
	
	/* maintain a list of all the observers of the subject. */
	protected HashMap<Integer, Observer> observers;
	
	/* add an observer to the subject. */
	public abstract void attach(Observer newObserver);
	
	/* remove an observer from the subject */
	public abstract void detach(Observer removedObserver);
	
	/* notify all observers of the subject that subject has changed. */
	public abstract void notified(String[] strings);

}
