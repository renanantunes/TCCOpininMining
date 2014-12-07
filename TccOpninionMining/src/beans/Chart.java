package beans;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;

public interface Chart {
	
	Dataset createDataset (Report report, String type);
   
	JFreeChart createChart(Dataset dataset, String title, String type);

    JFreeChart getChart();
    
    String getChartTyper();

}
