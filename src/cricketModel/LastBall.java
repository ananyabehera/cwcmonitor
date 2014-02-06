package cricketModel;

import matchFactory.MatchEvent;

/*
 * LastBall class represents ball event data in the system.
 * each LastBall object is of suntype MatchEvent.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	13052013
 */
public class LastBall extends MatchEvent {
			
	public LastBall(String[] event) {
		
		setEventDetails(event);
	}

	@Override
	public String[] getEventDetails() {
		
		return eventDetails;
	}

	/*
	 * for all parts of event details, update the values
	 * to the latest values passed from the server.
	 */
	@Override
	public void setEventDetails(String[] event) {
		
		eventDetails = new String[event.length];
		for(int i =0; i < event.length; i++)
			eventDetails[i] = event[i];		
	}

}
