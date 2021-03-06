/*
 * This is the adaptee class to be used in the Adapter pattern to 
 * implement the new server.
 * Created By:  Ananya Behera
 * Modified on: 21/5/2013
 */


package server;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import cricketWorldCupService.CricketWorldCup2011ServiceTwoStub;
import cricketWorldCupService.CricketWorldCup2011ServiceTwoStub.GetLastBall;
import cricketWorldCupService.CricketWorldCup2011ServiceTwoStub.GetMatchDetails;

public class SOAP5{

public static CricketWorldCup2011ServiceTwoStub CWCService;
	
	public SOAP5() throws AxisFault {
		CWCService = new CricketWorldCup2011ServiceTwoStub();

	}
	
	public String[] getMatchIDs() throws RemoteException {
		
		return CWCService.getMatchIDs().get_return();
	}
	
	public String[] getMatchDetails(String ID) throws RemoteException {
		
		GetMatchDetails MatchDetailsRequest = new GetMatchDetails();
		MatchDetailsRequest.setMatchID(ID);
		return CWCService.getMatchDetails(MatchDetailsRequest).get_return();
	}
	
	public String[] getLastBall(String id) throws RemoteException {

		GetLastBall LastBallRequest = new GetLastBall();
		LastBallRequest.setMatchID(id);
		return CWCService.getLastBall(LastBallRequest).get_return();
	}
	
	public String[] getBallFieldLabels() throws RemoteException {
		return CWCService.getBallFieldLabels().get_return();
	}
	
	
}
