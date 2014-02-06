package cricketModel;

import matchFactory.Match;
import matchFactory.MatchEvent;
import matchFactory.MatchFactory;

/*
 * Cricket factory is the objects responsible for creating 
 * all the cricket matches in the system.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	13052013
 */
public class CricketFactory extends MatchFactory{

	/*
	 * constructs a CricketFactory object that creates can
	 * create objects of type match.
	 */
	public CricketFactory() {
		
		super();
	}

	/*
	 * create cricket matches (of declared type match).
	 */
	public Match createMatch(String ID, String[] details) {
		
		return new CricketMatch(ID, details);
	}
	
	/*
	 * create cricket match events (of declared type match event).
	 */
	public MatchEvent createMatchEvent(String[] event) {
		
		return new LastBall(event);
	}
}
