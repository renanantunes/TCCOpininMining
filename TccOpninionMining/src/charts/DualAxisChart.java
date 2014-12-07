package charts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import utils.ApplicationUtils;
import utils.Constants;
import beans.AbstractChart;
import beans.Report;
import beans.Terms;
import beans.Tweet;

public class DualAxisChart extends AbstractChart{

	public DualAxisChart(String chartTitle, Report report, String type) {
		super(chartTitle, report, type);
	}
	
	@Override
	public Dataset createDataset(Report report, String type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
		switch(type){
			case Constants.CHARTTYPE_ABSOLUTESCORE: 
				dataset = (DefaultCategoryDataset) createDataSetPerAbsScore(report, type);
				break;
			case Constants.CHARTTYPE_DATE_NATSCORE: 
				dataset = (DefaultCategoryDataset) createDataSetPerDateNat(report, type);
				break;
			case Constants.CHARTTYPE_DATE_ABSSCORE: 
				dataset = (DefaultCategoryDataset) createDataSetPerDateAbs(report, type);
				break;
			case Constants.CHARTTYPE_TERM_ABSSCORE:
				dataset = (DefaultCategoryDataset) createDataSetPerAbsScore(report, type);
				break;
			case Constants.CHARTTYPE_TERM_DATE_NATSCORE:
				dataset = (DefaultCategoryDataset) createDataSetPerDateNat(report, type);
				break;
			case Constants.CHARTTYPE_TERM_DATE_ABSSCORE:
				dataset = (DefaultCategoryDataset) createDataSetPerDateAbs(report, type);
				break;
		}

        return dataset;
	}

	@Override
	public JFreeChart createChart(Dataset dataset, String title, String type) {
		String axisX = "Termo";
		String axisY = "Classificação";
		
		switch(type){
		case Constants.CHARTTYPE_DATE_NATSCORE: 
			axisX = "Data";
			axisY = "Tweets Coletados";
			break;
		case Constants.CHARTTYPE_DATE_ABSSCORE:
			axisX = "Data";
			break;
		}
		
		// create the chart...
        chart = ChartFactory.createBarChart(
            title,        // chart title
            axisX,               // domain axis label
            axisY,                  // range axis label
            (CategoryDataset) dataset,                 // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips?
            false                     // URL generator?  Not required...
        );
        
        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainGridlinePaint(Color.blue.darker());
        plot.setRangeGridlinePaint(Color.white);
        plot.setDrawSharedDomainAxis(false);
        
        ((BarRenderer)plot.getRenderer()).setBarPainter(new StandardBarPainter());

        BarRenderer r = (BarRenderer)chart.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0, Color.green);
        r.setSeriesPaint(1, Color.red);
        r.setSeriesPaint(2, Color.yellow);
        
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);

        return chart;
	}
	
	public Dataset createDataSetPerDateNat(Report report, String type){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Map<Date, List<Tweet>> tweetList = null;
		if(type.equals(Constants.CHARTTYPE_DATE_NATSCORE)){
			tweetList = ApplicationUtils.getTweetsPerDate(report.getTweets());
		}else if(type.equals(Constants.CHARTTYPE_TERM_DATE_NATSCORE)){
			tweetList = ApplicationUtils.getTweetsPerDate(report.getTermToReport().getTweets());
		}
		
		List<Date> dateList = new ArrayList<Date>();
		dateList.addAll(tweetList.keySet());
		Collections.sort(dateList);
				
		for (Date date : dateList) {
			Collection<Tweet> tweets = tweetList.get(date);
			int [] score = ApplicationUtils.getRatingAvg(tweets);
			for(int i=0; i < score.length; i++){
        		dataset.addValue(score[i], ApplicationUtils.getCategoryName(i), ApplicationUtils.parseDateToString(date));
        	}
		}

		
		return dataset;
	}
	
	public Dataset createDataSetPerDateAbs(Report report, String type){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Map<Date, List<Tweet>> tweetList = null;
		
		if(type.equals(Constants.CHARTTYPE_DATE_ABSSCORE)){
			tweetList = ApplicationUtils.getTweetsPerDate(report.getTweets());
		}else if(type.equals(Constants.CHARTTYPE_TERM_DATE_ABSSCORE)){
			tweetList = ApplicationUtils.getTweetsPerDate(report.getTermToReport().getTweets());
		}
		
		List<Date> dateList = new ArrayList<Date>();
		dateList.addAll(tweetList.keySet());
		Collections.sort(dateList);
				
		for (Date date : dateList) {
			Collection<Tweet> tweets = tweetList.get(date);
			double [] score = ApplicationUtils.getAbsScoreAvg(tweets);
			for(int i=0; i < score.length; i++){
        		dataset.addValue(score[i], ApplicationUtils.getCategoryName(i), ApplicationUtils.parseDateToString(date));
        	}
		}
		
		return dataset;
	}
	
	public Dataset createDataSetPerAbsScore(Report report, String type){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if(type.equals(Constants.CHARTTYPE_ABSOLUTESCORE)){
			for (Terms category : report.getTerms()) {
	        	double[]score = category.getAbsoluteScore();
	        	for(int i=0; i < score.length; i++){
	        		dataset.addValue(score[i], ApplicationUtils.getCategoryName(i), category.getName());
	        	}
				
			}
		}else if(type.equals(Constants.CHARTTYPE_TERM_ABSSCORE)){
			Terms term = report.getTermToReport();
			double[]score = term.getAbsoluteScore();
        	for(int i=0; i < score.length; i++){
        		dataset.addValue(score[i], ApplicationUtils.getCategoryName(i), term.getName());
        	}
		}
		
		
		return dataset;
	}
	
	@Override
	public String getChartTyper() {
		return "axisChart";
	}

}
