package gui;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import utils.ApplicationUtils;
import engine.TwitterManager;

public class StreamDialog extends JDialog {
	private JPanel panel;
	public static JLabel lblNewLabel;
	public static JLabel lblNewLabel_1;
	public static JTextArea textArea;
	public static JButton btn_stop;
	
	public StreamDialog(Window mf) {
		super(mf, "Coletando Tweets", ModalityType.MODELESS);
		this.setSize(244, 141);
		this.setLocationRelativeTo(mf);
		panel = new JPanel();
		panel.setBounds(100, 100, 244, 141);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(85, 22, 116, 14);
		panel.add(lblNewLabel);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		btn_stop = new JButton("Parar Coleta");
		btn_stop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TwitterManager.stopListener();
				close();
			}
		});
		btn_stop.setBounds(55, 50, 133, 42);
		btn_stop.setEnabled(false);
		panel.add(btn_stop);

		this.getContentPane().add(panel);
		this.setVisible(true);
	}
	
	private void close(){
		this.dispose();
		MainWindow.makeAllComponentsEnabled();
		MainWindow.populateNumbers();
	}
}
