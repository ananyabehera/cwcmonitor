package view;

import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import matchFactory.Match;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JButton;

public class GraphView extends Monitor {
	
	private JFrame frame;
	private JPanel totalGUI, btnPanel, graphPanel;
	private Match monitoredMatch;
	private XYSeries runRateSeries;
	private XYSeriesCollection dataset;
	private JButton btnCloseMonitor;
	private JFreeChart chart;
	private double[] rates;
	private double run_rate;
	private int flag;
	private int ballCount;
	private int over;
	private double runsOver;
	private int overcount;
	
	/*
	 * creates an object of type GraphView which is a graphical
	 * monitor for the CWC11 replays.
	 */
	public GraphView(Match monitoredMatch){
		
		this.monitoredMatch = monitoredMatch;
		this.id = count++;
		rates = new double[3];
		initialize();
		addContent();
	}

	/*
	 * initliazes the GUI. creates frames, panels and widgets.
	 */
	private void initialize(){
		
		// setup the JFrame
		frame = new JFrame();
		frame.setTitle(monitoredMatch.getMatchID());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setSize(624, 411);
		
		// setup bottom level panel to house all panels
		totalGUI = new JPanel();
		//totalGUI.setBounds(6, 6, 612, 293);
		frame.getContentPane().add(totalGUI);
		totalGUI.setLayout(null);
		
		// setup panel to house the graph
		graphPanel = new JPanel();
		graphPanel.setBounds(6, 6, 612, 336);
		frame.getContentPane().add(graphPanel);
		
		// setup panel to house the close button
		btnPanel = new JPanel();
		btnPanel.setBounds(6, 346, 612, 40);
		frame.getContentPane().add(btnPanel);
		btnPanel.setLayout(null);
		
		// attach a stop button that will stop monitoring the match
		btnCloseMonitor = new JButton("Close Monitor");
		btnCloseMonitor.setBounds(489, 6, 117, 29);
		btnCloseMonitor.setActionCommand("close" + " " + monitoredMatch.getMatchID() + " " + this.id);
		btnPanel.add(btnCloseMonitor);
	}
	
	/*
	 * adds the chart to the appropriate JPanels.
	 */
	private void addContent(){
		
		// This will create the dataset 
        XYDataset dataset = createDataset();
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset);
        graphPanel.setLayout(null);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(6, 6, 600, 324);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        graphPanel.add(chartPanel);
	}
	
	/*
	 * creates a dataset required by the JFreeChart.
	 */
	private XYDataset createDataset() {
		
		runRateSeries = new XYSeries("Run Rate");
        dataset = new XYSeriesCollection();
        dataset.addSeries(runRateSeries);
        return dataset;
    }
	
	/*
	 * updates the JFreeChart with the moving average run rate.
	 */
	@Override
	public void update(String[] fields) {

		ballCount = Integer.parseInt(monitoredMatch.getLastEvent().getEventDetails()[2]);
		if (ballCount == 1){
			// we are at the start of an over
			over = Integer.parseInt(monitoredMatch.getLastEvent().getEventDetails()[1]);
			// add runs this ball to over tally
			runsOver += Double.parseDouble(monitoredMatch.getLastEvent().getEventDetails()[5]);
		}
		else if (ballCount == 2 || ballCount == 3 || ballCount ==4 || ballCount ==5){
			// add runs this ball to over tally
			runsOver += Double.parseDouble(monitoredMatch.getLastEvent().getEventDetails()[5]);
		}
		else if (ballCount == 6){
			runsOver += Double.parseDouble(monitoredMatch.getLastEvent().getEventDetails()[5]);
			run_rate = (runsOver / ballCount);
			
			// reset the run count
			runsOver = 0;
			
			// not the start of new innings
			if (over != 0){
				rates[flag++] = run_rate;
				flag = flag % 3;
				overcount++;
			}
			
			// now we plot the points on the graph
			if (overcount == 0) {
				runRateSeries.add(0,0);
			} else if (overcount == 1) {
				runRateSeries.add(over, rates[0]);
			} else if (overcount ==2) {
				runRateSeries.add(over,((rates[0]+rates[1])/2));
			} else {
				runRateSeries.add(over, ((rates[0]+rates[1]+rates[2])/3));
			}
			
			// innings is finished so clear the graph
			if (over == 50)
				runRateSeries.clear();
		}
		// redraw the chart
		chart.fireChartChanged();
	}

	/*
	 * creates a JFreeChart object.
	 */
	private JFreeChart createChart(XYDataset dataset) {
        
		chart = ChartFactory.createXYLineChart(
	            "Moving Average Run Rate",      // chart title
	            "Overs",                      // x axis label
	            "Average Run Rate",                      // y axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL,
	            true,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );
		return chart;
	}

	/*
	 * returns the ID of a view object. used for closing of views.
	 */
	@Override
	public int getID() {
		
		return id;
	}

	/*
	 * attaches a listener (controller) to the button for closing.
	 */
	public void buttonActionListener(ActionListener al) {
		
		btnCloseMonitor.addActionListener(al);
	}
}
