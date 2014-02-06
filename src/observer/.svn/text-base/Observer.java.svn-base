package observer;

/*
 * Abstract class implementing the Observer pattern.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	13052013
 */

public abstract class Observer {
	
	/* an index of the observer used when there are multiple 
	 * observers on subject and we need to remove one of them.
	 */
	protected static int count = 1;

	/* update based on a change in the subject */
	public abstract void update(String[] fields);

	/* get this observers id - used when detaching observers*/
	public abstract int getID();
}
