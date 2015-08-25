package engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;

import utils.Constants;
import beans.AbstractChart;
import beans.Report;
import beans.Terms;
import charts.DualAxisChart;
import charts.PieChart;
import engine.files.PDFHandler;

public class ReportManager {
	private static List<JFreeChart> chartList = new ArrayList<JFreeChart>();
	public static boolean generateReport(String path, Report report){
		boolean success = true;
		
		AbstractChart chart = new PieChart("Gráfico geral de resultados obtidos", report, Constants.CHARTTYPE_GENERAL);
		addChartToList(chart.getChart());
		chart = new DualAxisChart("Gráfico de resultado absoluto por termo", report, Constants.CHARTTYPE_ABSOLUTESCORE);
		addChartToList(chart.getChart());
		chart = new DualAxisChart("Gráfico de Tweets por categoria e por data", report, Constants.CHARTTYPE_DATE_NATSCORE);
		addChartToList(chart.getChart());
		chart = new DualAxisChart("Gráfico de resultado absoluto por data", report, Constants.CHARTTYPE_DATE_ABSSCORE);
		addChartToList(chart.getChart());
		
		if(report.getTerms().size()>1){
			for (Terms term : report.getTerms()) {
				report.setTermToReport(term);
				
				chart = new PieChart("Gráfico de resultado de Tweets coletados para o termo " + term.getName(), report, Constants.CHARTTYPE_TERM_PIECHART);
				addChartToList(chart.getChart());
				report.setTermToReport(term);
				chart = new DualAxisChart("Gráfico de resultado absoluto para o termo " + term.getName(), report, Constants.CHARTTYPE_TERM_ABSSCORE);
				addChartToList(chart.getChart());
				report.setTermToReport(term);
				chart = new DualAxisChart("Gráfico de resultado de Tweets coletados por data para o termo " + term.getName(), report, Constants.CHARTTYPE_TERM_DATE_NATSCORE);
				addChartToList(chart.getChart());
				report.setTermToReport(term);
				chart = new DualAxisChart("Gráfico de resultado absoluto por data para o termo "+ term.getName(), report, Constants.CHARTTYPE_TERM_DATE_ABSSCORE);
				addChartToList(chart.getChart());
			}
		}

		path = path + File.separator + "Relatorio";
		success = PDFHandler.createPDF(path+".pdf", chartList, report);
		
		return success;
	}
	
	private static void addChartToList(JFreeChart chart){
		chartList.add(chart);
	}
}
