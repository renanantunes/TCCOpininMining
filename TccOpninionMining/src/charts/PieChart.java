package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import utils.Constants;
import forms.MainWindowForm;

public class PieChart {
	JFreeChart chart = null;
	public PieChart(String chartTitle, MainWindowForm mwf){
		 PieDataset dataset = createDataset(mwf);
	     chart = createChart(dataset, chartTitle);
	     ChartPanel chartPanel = new ChartPanel(chart);
	     chartPanel.setPreferredSize(new java.awt.Dimension(300, 300));
	}
	
    private  PieDataset createDataset (MainWindowForm mwf) {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue(Constants.PT_POSITIVE, mwf.getPositive());
        result.setValue(Constants.PT_NEGATIVE, mwf.getNegative());
        result.setValue(Constants.PT_NEUTRAL, mwf.getNeutral());
        return result;       
    }
    
    private JFreeChart createChart(PieDataset dataset, String title) {
        
        JFreeChart chart = ChartFactory.createPieChart(title,       
            dataset,          
            true,                  
            true,
            false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setBackgroundAlpha(0);
        return chart;
        
    }

    public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
}
