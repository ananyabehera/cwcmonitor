package matchFactory;

/*
 * Abstract class implementing the Abstract Factory design pattern.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	13052013
 */
public abstract class MatchEvent {
	
	protected String[] eventDetails;
	
	/* return the current events string of details. */
	public abstract String[] getEventDetails();
	
	/* update eventDetails to a new set of details in fields. */
	public abstract void setEventDetails(String[] fields);

}
