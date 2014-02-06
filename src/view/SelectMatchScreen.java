package view;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

/*
 * A simple GUI screen that will render all the matches in the 
 * cricket world up in a drop down box that the user can then select
 * from. Once user has selected a match from the JComboBox, they must select
 * a server to watch the match through, using the JRadioButtons. Then they 
 * select which type of monitor they want, either text or graphical using
 * the JButtons. A screen will then open displaying relevant match info.
 * Each button has an ActionListener (currently Controller.java) that waits
 * for user to select options and responds accordingly.
 * 
 * @author		Andreas Limberopoulos and Ananya Behera
 * @modified	26-05-2013
 */
public class SelectMatchScreen {

	private JPanel totalGUI, comboPanel, btnPanel, matchListPanel;
	private JComboBox serverComboBox;
	private JButton btnText, btnGraph;
	private JList matchList;
	private JScrollPane scrollPane;
	
	/*
	 * constructs a SelectMatchScreen object used to pick matches
	 * that the user would like to monitor.
	 */
	public SelectMatchScreen(String[] serverArray){
		
		initialise(serverArray);
	}

	/*
	 * creates a JFrame to house the GUI. Then creates a content 
	 * pane and attaches it to the JFrame.
	 */
	public void initialise(String[] serverArray){
	
		// create the JFrame
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("CWC 2011 Monitor");
        frame.setAlwaysOnTop(true);
        frame.setSize(345, 210);

        // Set the content pane and attach to the JFrame
        frame.setContentPane(createContentPane(serverArray));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	
	/*
	 * create the bottom layered content pane (totalGUI) then
	 * attaches several other JPanels to totalGUI. GUI widgets
	 * are then added to the new JPanels to form the interface.
	 */
	private JPanel createContentPane(String[] serverArray){
		
		// lowest level panel to house all panels
		totalGUI = new JPanel();
		totalGUI.setLayout(null);
		totalGUI.setOpaque(true);
		
		// PANEL: panel for the dropdown box
		comboPanel = new JPanel();
		comboPanel.setBounds(6, 6, 164, 75);
		comboPanel.setLayout(new GridLayout(0, 1, 0, 0));
		totalGUI.add(comboPanel);
		
		// PANEL: button panel
		btnPanel = new JPanel();
		btnPanel.setBounds(175, 6, 164, 75);
		totalGUI.add(btnPanel);
			        
		// matchListPanel is scrollable
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 86, 333, 96);
		totalGUI.add(scrollPane);
				
		// PANEL: panel for the radio buttons to select server
		matchListPanel = new JPanel();
		scrollPane.setViewportView(matchListPanel);
			
		// WIDGET: combo box of server options
		serverComboBox = new JComboBox(serverArray);
		serverComboBox.setMaximumRowCount(30);
		serverComboBox.setActionCommand("comboBox");
		comboPanel.add(serverComboBox);

		// WIDGET: create button to select graphical monitor
		btnGraph = new JButton("Graph Monitor");
		btnGraph.setActionCommand("openGraphical");
		btnPanel.add(btnGraph);
				
		// WIDGET: create button to select text monitor
		btnText = new JButton("Text Monitor");
		btnText.setActionCommand("openTextual");
		btnPanel.add(btnText);
		
		return totalGUI;
	}

	public void populateMatches(String[] matchIds){

		// WIDGET: list of all matches for that server
		matchListPanel.removeAll(); // clear panel 
		matchList = new JList(matchIds); // add list
		matchList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		matchList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		matchList.setFixedCellHeight(30);
		matchList.setFixedCellWidth(320);
		matchList.setVisibleRowCount(-1);
		matchListPanel.add(matchList);
		totalGUI.updateUI(); // redraw GUI
	}

	/*
	 * adds action listeners to all the GUI widgets that the user
	 * will be interacting with. this method is called from the
	 * controller class which acts as our ActionListener.
	 */
	public void comboActionListener(ActionListener al) {
		
		serverComboBox.addActionListener(al);
		btnGraph.addActionListener(al);
		btnText.addActionListener(al);
	}
	
	public void listSelectionListener(ListSelectionListener lsl){
		
		matchList.addListSelectionListener(lsl);
	}
}
