package gui;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.TwitterManager;

public class StreamDialog extends JDialog {
	private JPanel panel;
	public static JLabel lblNewLabel;
	public static JLabel lblNewLabel_1;
	
	public StreamDialog(Window mf) {
		super(mf, "Coletando Tweets", ModalityType.MODELESS);
		this.setSize(650, 200);
		panel = new JPanel();
		panel.setBounds(100, 100, 654, 209);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("Buscando...");
		lblNewLabel.setBounds(272, 11, 89, 14);
		panel.add(lblNewLabel);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		JButton btn_stop = new JButton("Parar Coleta");
		btn_stop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TwitterManager.stopListener();
				close();
			}
		});
		btn_stop.setBounds(495, 118, 133, 42);
		panel.add(btn_stop);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(10, 65, 618, 42);
		panel.add(lblNewLabel_1);
		this.getContentPane().add(panel);
		this.setVisible(true);
	}
	
	private void close(){
		this.dispose();
	}
}
