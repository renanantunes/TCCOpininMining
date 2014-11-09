package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import utils.Categories;
import beans.AbstractChart;
import beans.Report;

public class PieChart extends AbstractChart {
	public PieChart(String chartTitle, Report report){
		 super(chartTitle, report);
	}

	@Override
	public JFreeChart createChart(Dataset dataset, String title) {
        
        JFreeChart chart = ChartFactory.createPieChart(title,       
            (PieDataset) dataset,          
            true,                  
            true,
            false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setBackgroundAlpha(0);
        return chart;
	}

	@Override
	public Dataset createDataset(Report report) {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue(Categories.POSITIVE.getCategoryName(), report.getTweetsPerCategory()[0]);
        result.setValue(Categories.NEGATIVE.getCategoryName(), report.getTweetsPerCategory()[1]);
        result.setValue(Categories.NEUTRAL.getCategoryName(), report.getTweetsPerCategory()[2]);
        return result;  
	}
}
