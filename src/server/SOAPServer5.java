package server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;

import matchFactory.Match;
import matchFactory.MatchEvent;

import org.apache.axis2.AxisFault;

import cricketModel.CricketFactory;

/*
 * This is the adaptor class.
 * Created by: Ananya Behera
 * Modified On: 21.05.2013
 */

public class SOAPServer5 extends Server implements Runnable{

	// uses the new service
	private SOAP5 soap5server;
	// creates a thread that this server can execute on
	private Thread thread5 = new Thread(this, "5");
	
	/*
	 * creates an object of this server type and instantiates
	 * the delay time to the appropriate number of sleep seconds.
	 */
	public SOAPServer5(int delayTime) throws AxisFault {
		
		this.delayTime = delayTime;
		soap5server = new SOAP5();
		matchesFromServer = new HashMap<String, Match>();
		factory = new CricketFactory();
		thread5.start();
	}
	
	/*
	 * inserts a match into the hashmap so that we know which matches
	 * this server is responsible for updating.
	 */
	public void insertMatch(String matchId, Match aMatch){
		
		matchesFromServer.put(matchId, aMatch);
	}

	/*
	 * removes a match from the hashmap. the server will no longer 
	 * update the match or ball details if it has been removed.
	 */
	public void removeMatch(String matchId){
		
		if(matchesFromServer.containsKey(matchId)){
			matchesFromServer.remove(matchId);
		}
	}
	
	/*
	 * the body of work done by this server. it iterates through all the
	 * matches, updates the relevant data and then sleeps for the 30s.
	 */
	public void run(){
		
		while(true){
			try{
				Iterator<String> i = matchesFromServer.keySet().iterator();
				// only update match data on matches being viewed
				while(i.hasNext()){
					String id = i.next();
					String[] lastBall = this.getLastEvent(id);
					MatchEvent lastEvent = factory.createMatchEvent(lastBall);
					matchesFromServer.get(id).setLastEvent(lastEvent);
					matchesFromServer.get(id).notified(this.getFields());
				}
				long SleepTime = delayTime * 1000L;
				Thread.sleep(SleepTime);
			} catch (InterruptedException ie) {
				System.err.println("InterruptedException: " + ie.getMessage());
				ie.printStackTrace();
			} catch (RemoteException re) {
				System.err.println("RemoteException: " + re.getMessage());
				re.printStackTrace();
			}
		}
	}
	
	/*
	 * returns matchIDs of every match that it retrieves data for
	 */
	@Override
	public String[] getMatchIDs() throws RemoteException {

		return soap5server.getMatchIDs();
	}

	/*
	 * returns the details of a particular match based on its
	 * matchids
	 */
	@Override
	public String[] getMatchDetails(String ID) throws RemoteException {
		
		return soap5server.getMatchDetails(ID);
	}

	/*
	 * returns the details of the last event that happened in the match
	 */
	@Override
	public String[] getLastEvent(String id) throws RemoteException {
		
		String[] temp = soap5server.getLastBall(id);
		String[] temp2 = new String[temp.length];		
		String[] data = temp[1].split("\\.");
		String score = temp[temp.length-1] +"/"+temp[temp.length -2];

		for(int i = 0; i < temp2.length; i++)
		{
			if( i == 0)
			{
				temp2[i] = temp[i];
			}
			else if(i == 1)
			{
				temp2[1] = data[0];
				temp2[2] = data[1];	
			}
			else if ( i >2 && i != temp2.length-1)
			{
				temp2[i] = temp[i-1];
			}
			else if (i == temp2.length-1)
			{
				temp2[temp2.length-1] = score;
			}
		}
		return temp2;
	}

	/*
	 * returns the fields that are stored for every event.
	 */
	@Override
	public String[] getFields() throws RemoteException {
		
		return soap5server.getBallFieldLabels();
	}
}
