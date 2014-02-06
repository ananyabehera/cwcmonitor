package matchFactory;

import java.util.List;


import observer.Observer;
import observer.Subject;

/*
 * Abstract class implementing the Abstract Factory design pattern.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	13052013
 */
public abstract class Match extends Subject{

	protected String id;
	protected MatchEvent event;

	public abstract List<String> getMatchDetails();
	
	public abstract String getMatchID();
	
	/* update the lastevent to the latest event. */
	public abstract void setLastEvent(MatchEvent event); 
	
	/* return the latest event */
	public abstract MatchEvent getLastEvent();
	
	/* inform all observers of changes in the object. */
	public abstract void notified(String[] strings);
	
	/* get an observer with a particular ID */
	public abstract Observer getMatchObserver(int id); 
}
