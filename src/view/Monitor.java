package view;

import matchFactory.Match;
import observer.Observer;

/*
 * Monitor class allows us to have subtypes of Observer that
 * watch the model for changes and represent them in the UI.
 *  
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	13052013
 */
public abstract class Monitor extends Observer {
	
	public int id ;
	
	/* the match that we are monitoring */
	public Match monitoredMatch;

}
