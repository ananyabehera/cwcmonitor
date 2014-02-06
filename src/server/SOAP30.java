package server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;

import matchFactory.Match;
import matchFactory.MatchEvent;

import org.apache.axis2.AxisFault;

import cricketModel.CricketFactory;
import cricketWorldCupService.CricketWorldCup2011ServiceStub;
import cricketWorldCupService.CricketWorldCup2011ServiceStub.GetLastBall;
import cricketWorldCupService.CricketWorldCup2011ServiceStub.GetMatchDetails;

public class SOAP30 extends Server {
	
	// makes use of the old service
	public static CricketWorldCup2011ServiceStub CWCService;
	// creates a thread that this server can execute on
	private Thread thread30 = new Thread(this, "30");
	
	/*
	 * creates an object of this server type and instantiates
	 * the delay time to the appropriate number of sleep seconds.
	 */
	public SOAP30(int delayTime) throws AxisFault {
		
		this.delayTime = delayTime;
		CWCService = new CricketWorldCup2011ServiceStub();
		matchesFromServer = new HashMap<String, Match>();
		factory = new CricketFactory();
		thread30.start();
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
		
		return CWCService.getMatchIDS().get_return();
	}

	/*
	 * returns the details of a particular match based on its
	 * matchids
	 */
	@Override
	public String[] getMatchDetails(String ID) throws RemoteException {
		
		GetMatchDetails MatchDetailsRequest = new GetMatchDetails();
		MatchDetailsRequest.setMatchID(ID);
		return CWCService.getMatchDetails(MatchDetailsRequest).get_return();
	}

	/*
	 * returns the details of the last event that happened in the match
	 */
	@Override
	public String[] getLastEvent(String id) throws RemoteException {

		GetLastBall LastBallRequest = new GetLastBall();
		LastBallRequest.setMatchID(id);
		return CWCService.getLastBall(LastBallRequest).get_return();
	}
	
	/*
	 * returns the fields that are stored for every event.
	 */
	public String[] getFields() throws RemoteException {
		
		return CWCService.getBallFields().get_return();
	}

}
