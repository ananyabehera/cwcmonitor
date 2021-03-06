package view;

import javax.swing.JFrame;

import matchFactory.Match;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import cricketModel.LastBall;

/*
 * Concrete implementation of an abstract monitor. This is a
 * GUI monitor for a cricket match object. It is notified 
 * through the observer pattern of any changes to the model
 * and the appropriate changes are displayed on the screen.
 * 
 * The last ball data is only updated every 30 seconds to minimize
 * network traffic, so when the program initially boots up and the 
 * user selects a match that they would like to monitor, the ball 
 * data is not present until the next server query.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	13052013
 */
public class TextView extends Monitor {

	private JFrame frame;
	private JPanel totalGUI;
	private JButton unfollowBtn;
	private JLabel descLbl;
	private JPanel ballPanel, descPanel, buttonPanel;
	private JLabel lblInnings;
	private JLabel lblOver;
	private JLabel lblBall;
	private JLabel lblBowler;
	private JLabel lblBatsman;
	private JLabel lblRuns;
	private JLabel lblExtras;
	private JLabel lblBoundary;
	private JLabel lblWicket;
	private JLabel lblComment;
	private JLabel lblScore;
	private JLabel lblInnings_1;
	private JLabel lblOver_1;
	private JLabel lblBall_1;
	private JLabel lblBowler_1;
	private JLabel lblBatsman_1;
	private JLabel lblRuns_1;
	private JLabel lblExtras_1;
	private JLabel lblBoundary_1;
	private JLabel lblWicket_1;
	private JLabel lblComment_1;
	private JLabel lblScore_1;
	private JPanel commentPanel;
	
	/*
	 * constructs a MonitorView object that renders match information
	 * to the users screen.
	 */
	public TextView(Match match){
		
		id = count++; // monitor ID used to detach monitors
		monitoredMatch = match;
		initialise();
	}
	
	/*
	 * initialises the JFrame that houses the monitor information
	 * and calls the createContentPane function to setup a content pane.
	 */
	private void initialise(){
	
		JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Cricket Monitor");
        totalGUI = createContentPane();
        frame.setContentPane(totalGUI);
        
        commentPanel = new JPanel();
        commentPanel.setBounds(1, 219, 593, 97);
        totalGUI.add(commentPanel);
        commentPanel.setLayout(null);
        
        lblComment_1 = new JLabel("Comment:");
        lblComment_1.setBounds(6, 6, 65, 16);
        commentPanel.add(lblComment_1);
        
        lblComment = new JLabel("Updating�");
        lblComment.setBounds(6, 26, 581, 65);
        commentPanel.add(lblComment);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 390);
        frame.setVisible(true);
        createWidgets();
	}
	
	/*
	 * create the bottom layered content pane (totalGUI) that houses
	 * all of the widgets of the application.
	 */
	private JPanel createContentPane(){
		
		// PANEL: content pane
		totalGUI = new JPanel();
		totalGUI.setLayout(null);
		totalGUI.setOpaque(true);
		
		// PANEL: match description
		descPanel = new JPanel();
		descPanel.setBounds(1, 1, 598, 60);
		descPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		totalGUI.add(descPanel);
		
		// PANEL: new ball data
		ballPanel = new JPanel();
		ballPanel.setBounds(1, 73, 598, 134);
		totalGUI.add(ballPanel);
		ballPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		// PANEL: close button
		buttonPanel = new JPanel();
		buttonPanel.setBounds(436, 321, 158, 39);
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 0));
		totalGUI.add(buttonPanel);
		
		return totalGUI;
	}
	
	private void createWidgets(){
		
		// WIDGET: match description
		String matchDetails = monitoredMatch.getMatchDetails().get(0);
		descLbl = new JLabel(matchDetails);
		descPanel.add(descLbl);
		
		lblInnings_1 = new JLabel("Innings:");
		ballPanel.add(lblInnings_1, "2, 2");

		// WIDGET: ball data
		lblInnings = new JLabel("Updating...");
		ballPanel.add(lblInnings, "4, 2");
		
		lblBoundary_1 = new JLabel("Boundary:");
		ballPanel.add(lblBoundary_1, "6, 2");
		
		lblBoundary = new JLabel("Updating�");
		ballPanel.add(lblBoundary, "8, 2");
		
		lblBatsman_1 = new JLabel("Batsman:");
		ballPanel.add(lblBatsman_1, "10, 2");
		
		lblBatsman = new JLabel("Updating...");
		ballPanel.add(lblBatsman, "12, 2");
		
		lblOver_1 = new JLabel("Over:");
		ballPanel.add(lblOver_1, "2, 4");
		
		lblOver = new JLabel("Updating�");
		ballPanel.add(lblOver, "4, 4");
		
		lblWicket_1 = new JLabel("Wicket:");
		ballPanel.add(lblWicket_1, "6, 4");
		
		lblWicket = new JLabel("Updating�");
		ballPanel.add(lblWicket, "8, 4");
		
		lblBowler_1 = new JLabel("Bowler:");
		ballPanel.add(lblBowler_1, "10, 4");
		
		lblBowler = new JLabel("Updating�");
		ballPanel.add(lblBowler, "12, 4");
		
		lblBall_1 = new JLabel("Ball:");
		ballPanel.add(lblBall_1, "2, 6");
		
		lblBall = new JLabel("Updating�");
		ballPanel.add(lblBall, "4, 6");
		
		lblExtras_1 = new JLabel("Extras:");
		ballPanel.add(lblExtras_1, "6, 6");
		
		lblExtras = new JLabel("Updating�");
		ballPanel.add(lblExtras, "8, 6");
		
		lblScore_1 = new JLabel("Score:");
		ballPanel.add(lblScore_1, "10, 6");
		
		lblScore = new JLabel("Updating�");
		ballPanel.add(lblScore, "12, 6");
		
		lblRuns_1 = new JLabel("Runs:");
		ballPanel.add(lblRuns_1, "2, 8");
		
		lblRuns = new JLabel("Updating�");
		ballPanel.add(lblRuns, "4, 8");
		
		// WIDGET: close monitor button
		unfollowBtn = new JButton("Close Monitor");
		unfollowBtn.setActionCommand("close" + " " + monitoredMatch.getMatchID() + " " + this.id);
		buttonPanel.add(unfollowBtn);
	}
	
	/*
	 * adds ActionListener to the button passed by controller to the button
	 */
	public void buttonActionListener(ActionListener al) {
		
		unfollowBtn.addActionListener(al);
	}
	
	/*
	 * update all of the fields in last ball and instructed to do so
	 * when the model changes. 
	 */
	@Override
	public void update(String[] fields) {
		
		LastBall lastBall = (LastBall) monitoredMatch.getLastEvent();
		updateBallData(fields, lastBall);
	}
	
	/*
	 * updates all the labels representing ball data with the latest
	 * information from the model.
	 */
	private void updateBallData(String[] fields, LastBall lastBall) {

		lblInnings.setText(lastBall.getEventDetails()[0]);
		lblBatsman.setText(lastBall.getEventDetails()[4]);
		lblWicket.setText(lastBall.getEventDetails()[8]);
		lblOver.setText(lastBall.getEventDetails()[1]);
		lblRuns.setText(lastBall.getEventDetails()[5]);
		lblComment.setText(lastBall.getEventDetails()[9]);
		lblBall.setText(lastBall.getEventDetails()[2]);
		lblExtras.setText(lastBall.getEventDetails()[6]);
		lblScore.setText(lastBall.getEventDetails()[10]);
		lblBowler.setText(lastBall.getEventDetails()[3]);
		lblBoundary.setText(lastBall.getEventDetails()[7]);
	}

	/*
	 * id is maintained so we can detach observers from the subject they are
	 * observing.
	 */
	@Override
	public int getID() {
		
		return id;
	}
}
