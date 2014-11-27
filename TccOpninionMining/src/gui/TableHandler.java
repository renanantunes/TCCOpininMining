package gui;

import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import utils.Categories;
import utils.Constants;

public class TableHandler 
{
	private static JTable table = new JTable(){
		public Class getColumnClass(int column)
        {
            return getValueAt(0, column).getClass();
        }
	};
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
		ImageIcon img = null;
		switch (data[0].toString()) {
		case Constants.POSITIVE: 
			img = new ImageIcon("img"+File.separator+"green.png");
			data[0] = img;
			break;
		case Constants.NEGATIVE:
			img = new ImageIcon("img"+File.separator+"red.png");
			data[0] = img;
			break;
		case Constants.NEUTRAL:
			img = new ImageIcon("img"+File.separator+"yellow.png");
			data[0] = img;
			break;
		default:
			System.err.println("a que ponto chegamos");
			break;
		}
		
		tableModel.addRow(data);
	}

}
