package gui;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
@Deprecated
public class Table extends JTable{
	private static String[] columnNames = {"Classifica��o", "Tweet"};
	private static DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	
//	private static Object[][] data = {{"",""}};
	@Deprecated
	public Table() {
		super(model);
        //final JTable table = new JTable(data, columnNames);
        this.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.setFillsViewportHeight(true);
        
	}
	
//	public void setData(Object[][]data){
//		this.data = data;
//	}
	@Deprecated
	public void addRow(Object[] rowData){
		model.addRow(rowData);
//		model.fireTableRowsInserted(model.getRowCount(), model.getRowCount());
		model.setValueAt("Classifica��o",this.getRowCount(),0);
	}

}
