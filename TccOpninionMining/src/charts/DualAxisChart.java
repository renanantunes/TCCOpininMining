package charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import utils.ApplicationUtils;
import beans.AbstractChart;
import beans.Report;
import beans.Terms;

public class DualAxisChart extends AbstractChart{

	public DualAxisChart(String chartTitle, Report report) {
		super(chartTitle, report);
	}
	
	@Override
	public Dataset createDataset(Report report) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        int i = 0;
        for (Terms category : report.getTerms()) {
        	double[]score = category.getAbsoluteScore();
        	for(i=0; i < score.length; i++){
        		dataset.addValue(score[i], ApplicationUtils.getCategoryName(i), category.getName());
        	}
			
		}

        return dataset;
	}

	@Override
	public JFreeChart createChart(Dataset dataset, String title) {
		// create the chart...
        chart = ChartFactory.createBarChart(
            title,        // chart title
            "Termo",               // domain axis label
            "Classifica‹o",                  // range axis label
            (CategoryDataset) dataset,                 // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips?
            false                     // URL generator?  Not required...
        );
        
        return chart;
	}
}
