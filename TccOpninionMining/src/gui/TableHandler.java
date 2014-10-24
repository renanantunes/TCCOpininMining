package gui;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class TableHandler 
{
	private static JTable table = new JTable();
	private static DefaultTableModel tableModel = new DefaultTableModel(0, 0);
	private static String[] columnNames = {"Classificação", "Tweet"};
	
	private TableHandler()
	{
	}
	
	public static JTable getTable()
	{
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setModel(tableModel);
        tableModel.setColumnIdentifiers(columnNames);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setResizable(false);
		columnModel.getColumn(0).setMaxWidth(100);
		columnModel.getColumn(0).setPreferredWidth(85);
        
        return table;
	}
	
	public static void addRow(Object[] data)
	{
		//Se for adicionar mesmo imagem na table ao invés do nome escrito da classificação, link interessante
		//http://stackoverflow.com/questions/4941372/how-to-insert-image-into-jtable-cell
		tableModel.addRow(data);
	}
}
