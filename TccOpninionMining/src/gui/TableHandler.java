package gui;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableHandler 
{
	private static JTable table = new JTable();
	private static DefaultTableModel dtm = new DefaultTableModel(0, 0);
	private static String[] columnNames = {"Classificação", "Tweet"};
	
	private TableHandler()
	{
	}
	
	public static JTable getTable()
	{
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setModel(dtm);
        dtm.setColumnIdentifiers(columnNames);
        
        return table;
	}
	
	public static void addRow(Object[] data)
	{
		dtm.addRow(data);
	}
}
