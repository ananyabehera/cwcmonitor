package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cricketModel.CricketFactory;

import server.SOAP30;
import server.SOAPServer5;
import server.Server;
import view.GraphView;
import view.TextView;
import view.SelectMatchScreen;
import matchFactory.Match;
import matchFactory.MatchFactory;

/*
 * Controller is the driver of the CricketWorldCup Monitor system.
 * the controller implements an ActionListener which allows it to 
 * watch for changes iadn the MonitorView and SelectMatchScreen objects
 * which handle the users input.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	24052013
 */
public class Controller implements ActionListener, ListSelectionListener {

	@SuppressWarnings("unused")
	private static Controller controller;
	private static Server server5, server30, currServer;
	private static MatchFactory factory;
	private static HashMap<String, Match> matches;
	private static SelectMatchScreen view;
	private JComboBox cb;
	private Match tempMatch;
	private String comboSelection;
	
	public static void main(String[] args) throws Exception{
	
		server30 = new SOAP30(30);
		server5 = new SOAPServer5(5);
		factory = new CricketFactory();
		String[] serverOptions = {"30s Server", "5s Server"};
		view = new SelectMatchScreen(serverOptions);
		// controller must be declared after the view
		controller = new Controller();
		matches = new HashMap<String, Match>();
	}
	
	/*
	 * constructs a Controller object that creates all the necessary
	 * objects for the CricketWorldCup system.
	 */
	public Controller() throws Exception{
		
		view.comboActionListener(this);
	}
	
	/*
	 * create and remove Cricket Monitors based on the users input.
	 * each new monitor is opened in it's own JFrame.
	 */
	public void actionPerformed(ActionEvent e){
	
		String actionCmd = e.getActionCommand();
		
		// user selected a match from the combo box
		if(actionCmd.contains("comboBox")){
			comboBoxChanging(actionCmd, e);
		}
		// user chose to open a MonitorView frame
		if(actionCmd.contains("openTextual")){
			createTextMonitor();
		}
		// user chose to open a GraphicalView frame
		if(actionCmd.contains("openGraphical")){
			createGraphicalMonitor();
		}
		// user chose to close a MonitorView OR GraphicalView frame
		if(actionCmd.contains("close")){
			closeMonitor(e);
		}
	}
	
	/*
	 * helper function to create a textual monitor
	 */
	private void createTextMonitor(){
		if(tempMatch != null){
			// attach an observer to the match
			TextView newObserver = new TextView(tempMatch);
			tempMatch.attach(newObserver);

			// attach to our list of matches (so we can close with close button)
			matches.put(tempMatch.getMatchID(), tempMatch);
			
			// find the appropriate server
			currServer = findServer();
			
			// insert match into servers list of matches that will use its services
			currServer.insertMatch(tempMatch.getMatchID(), tempMatch);
			
			// make the button close the frame
			newObserver.buttonActionListener(this);
		}
	}
	
	/*
	 * helper function to create a graphical monitor
	 */
	private void createGraphicalMonitor(){
		if(tempMatch != null){
			// attach an observer to the match
			GraphView newObserver = new GraphView(tempMatch);
			tempMatch.attach(newObserver);
			
			// attach to our list of matches (so we can close with close button)
			matches.put(tempMatch.getMatchID(), tempMatch);
			
			// find the appropriate server
			currServer = findServer();
			
			// insert match into servers list of matches that will use its services
			currServer.insertMatch(tempMatch.getMatchID(), tempMatch);
			
			// make the button close the frame
			newObserver.buttonActionListener(this);
		}
	}
	
	/*
	 * updates the currServer attribute to the appropriate server
	 */
	private Server findServer(){
		
		// swap to appropriate server
		if(comboSelection.contains("30")){
			currServer = server30;
		}else{
			currServer = server5;
		}
		return currServer;
	}
	
	/*
	 * helper function to close a monitor
	 */
	private void closeMonitor(ActionEvent e){
		
		// extract matchID from button command to get the match object
		String temp = e.getActionCommand();
		String[] delimited = temp.split(" ");
		Match tempMatch = matches.get(delimited[1]);
		
		// detach observer from match and remove from HashMap of matches being viewed
		tempMatch.detach(tempMatch.getMatchObserver(Integer.parseInt(delimited[2])));
		
		// HACKY FIX: tries to remove from both, removeMatch() function checks if exists
		server30.removeMatch(delimited[1]);
		server5.removeMatch(delimited[1]);
		
		// dispose of the JFrame
		Component component = (Component) e.getSource();
        JFrame frame = (JFrame) SwingUtilities.getRoot(component);
        frame.dispose();
	}
	
	/*
	 * helper function to extract user selection from combo box
	 */
	private void comboBoxChanging(String actionCmd, ActionEvent e){
		
		// extract the match that was selected in JComboBox
		cb = (JComboBox)e.getSource();	
		comboSelection = (String) cb.getSelectedItem();
		
		// find the appropriate server
		currServer = findServer();
		
		try {
			view.populateMatches(currServer.getMatchIDs());
		} catch (RemoteException re) {
			System.err.println("RemoteException: " + re.getMessage());
			re.printStackTrace();
		}
		
		//attach Listener to newly created list
		view.listSelectionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		JList tempList = (JList) e.getSource();
		String tempId = (String) tempList.getSelectedValue();
		Match newCricketMatch = null;
		
		// find the appropriate server
		currServer = findServer();
				
		try {
			newCricketMatch = factory.createMatch(tempId, currServer.getMatchDetails(tempId));
		} catch (RemoteException re) {
			System.err.println("RemoteException: " + re.getMessage());
			re.printStackTrace();
		}
		
		// make sure we are keeping up with users changed seletion
		tempMatch = newCricketMatch;
	}
}
