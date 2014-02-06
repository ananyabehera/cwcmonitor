package server;

import java.rmi.RemoteException;
import java.util.HashMap;

import matchFactory.Match;
import matchFactory.MatchFactory;

/*
 * Abstract server that is threaded. allows interleaved queries to
 * different servers using threads.
 * 
 */
public abstract class Server implements Runnable {

	// time to sleep between server queries
	protected int delayTime;
	// factory to create matches and balls once queried
	protected static MatchFactory factory;
	// list of matches that use this server
	protected HashMap<String, Match> matchesFromServer;
	
	public abstract String[] getMatchIDs() throws RemoteException;
	
	public abstract  void insertMatch(String matchId, Match aMatch);
	
	public abstract void removeMatch(String matchId);
	
	public abstract String[] getMatchDetails(String ID) throws RemoteException;
	
	public abstract String[] getLastEvent(String id) throws RemoteException; //Promotes Reuse as all types of games have events

	public abstract String[] getFields() throws RemoteException; 

}
