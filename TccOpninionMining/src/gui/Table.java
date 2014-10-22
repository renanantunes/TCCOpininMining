package gui;

import java.awt.Dimension;

import javax.swing.JTable;

public class Table extends JTable{
	private static String[] columnNames = {"Classificação", "Tweet"};
	
	private static Object[][] data = {{"",""}};
	 
	public Table() {
		super(data, columnNames);
        //final JTable table = new JTable(data, columnNames);
		this.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.setFillsViewportHeight(true);
        
	}
	
	public void setData(Object[][]data){
		this.data = data;
	}

}
