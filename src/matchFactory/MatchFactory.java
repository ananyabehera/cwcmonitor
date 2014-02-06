package matchFactory;

/*
 * Abstract class implementing the Abstract Factory design pattern.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	13052013
 */
public abstract class MatchFactory {
	
	/* create matches */
	public abstract Match createMatch(String ID, String[] details);
	
	/* create match events */
	public abstract MatchEvent createMatchEvent(String[] event);


}
