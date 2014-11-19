package engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;

import beans.Report;
import charts.DualAxisChart;
import charts.PieChart;
import engine.files.PDFHandler;

public class ReportManager {
	public static boolean generateReport(String path, Report report){
		boolean success = true;
		List<JFreeChart> chartList = new ArrayList<JFreeChart>();
		PieChart mainChart = new PieChart("", report);
		chartList.add(mainChart.getChart());
		DualAxisChart barChart = new DualAxisChart("", report);
		chartList.add(barChart.getChart());
		
		path = path + File.separator + "Relatorio";
		success = PDFHandler.createPDF(path, chartList);
		
		return success;
	}
}
