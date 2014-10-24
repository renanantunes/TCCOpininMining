package gui;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable{
	private static String[] columnNames = {"Classificação", "Tweet"};
	private static DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	
//	private static Object[][] data = {{"",""}};
	 
	public Table() {
		super(model);
        //final JTable table = new JTable(data, columnNames);
        this.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.setFillsViewportHeight(true);
        
	}
	
//	public void setData(Object[][]data){
//		this.data = data;
//	}
	public void addRow(Object[] rowData){
		model.addRow(rowData);
//		model.fireTableRowsInserted(model.getRowCount(), model.getRowCount());
		model.setValueAt("Classificação",this.getRowCount(),0);
	}

}
