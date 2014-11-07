package beans;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

public abstract class AbstractChart implements Chart {
	protected JFreeChart chart = null;
	
	public AbstractChart(String chartTitle, Report report){
		 Dataset dataset = createDataset(report);
	     chart = createChart(dataset, chartTitle);
	     ChartPanel chartPanel = new ChartPanel(chart);
	     chartPanel.setPreferredSize(new java.awt.Dimension(300, 300));
	}
	
	public JFreeChart getChart(){
		return this.chart;
	}
	
	public abstract Dataset createDataset (Report report);
	
	public abstract JFreeChart createChart(Dataset dataset, String title);
}
