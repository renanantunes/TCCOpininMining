package charts;

import java.awt.Color;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import utils.Categories;
import utils.Constants;
import beans.AbstractChart;
import beans.Report;

public class PieChart extends AbstractChart {
	public PieChart(String chartTitle, Report report, String type){
		 super(chartTitle, report, type);
	}

	@Override
	public JFreeChart createChart(Dataset dataset, String title, String type) {
        
        JFreeChart chart = ChartFactory.createPieChart(title,       
            (PieDataset) dataset,          
            true,                  
            true,
            false);

        PiePlot plot = (PiePlot) chart.getPlot();
        
        plot.setSectionPaint(Categories.POSITIVE.getCategoryName(), Color.green);
        plot.setSectionPaint(Categories.NEGATIVE.getCategoryName(), Color.red);
        plot.setSectionPaint(Categories.NEUTRAL.getCategoryName(), Color.yellow);
        plot.setExplodePercent(Categories.POSITIVE.getCategoryName(), 0.05);
        plot.setExplodePercent(Categories.NEGATIVE.getCategoryName(), 0.05);
        plot.setExplodePercent(Categories.NEUTRAL.getCategoryName(), 0.05);
        plot.setSimpleLabels(true);
        
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setBackgroundAlpha(0);
        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
            plot.setLabelGenerator(gen);
        return chart;
	}

	@Override
	public Dataset createDataset(Report report, String type) {
        DefaultPieDataset result = new DefaultPieDataset();
        if(type.equals(Constants.CHARTTYPE_GENERAL)){
        	 result.setValue(Categories.POSITIVE.getCategoryName(), report.getTweetsPerCategory()[0]);
             result.setValue(Categories.NEGATIVE.getCategoryName(), report.getTweetsPerCategory()[1]);
             result.setValue(Categories.NEUTRAL.getCategoryName(), report.getTweetsPerCategory()[2]);
        }else if(type.equals(Constants.CHARTTYPE_TERM_PIECHART)){
        	result.setValue(Categories.POSITIVE.getCategoryName(), report.getTermToReport().getNatScore()[0]);
            result.setValue(Categories.NEGATIVE.getCategoryName(), report.getTermToReport().getNatScore()[1]);
            result.setValue(Categories.NEUTRAL.getCategoryName(), report.getTermToReport().getNatScore()[2]);
        }
       
        return result;  
	}

	@Override
	public String getChartTyper() {
		return "pieChart";
	}
}
