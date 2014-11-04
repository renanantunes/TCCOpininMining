package engine;

import java.io.File;

import charts.PieChart;
import engine.files.PDFHandler;
import forms.MainWindowForm;

public class ReportManager {
	public static boolean generateReport(String path, MainWindowForm mwf){
		boolean success = true;
		
		PieChart pieChart = new PieChart(mwf.getKeyWords(), mwf);
		path = path + File.separator + mwf.getKeyWords();
		success = PDFHandler.createPDF(path, pieChart.getChart());
		
		return success;
	}
}
