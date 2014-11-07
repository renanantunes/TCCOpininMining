package beans;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

public interface Chart {
	
	Dataset createDataset (Report report);
   
	JFreeChart createChart(Dataset dataset, String title);

    JFreeChart getChart();

}
