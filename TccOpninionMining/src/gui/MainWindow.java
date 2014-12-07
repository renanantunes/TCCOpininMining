package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import main.MainClass;
import utils.ApplicationUtils;
import utils.Constants;
import beans.Report;
import engine.FileChooserDirectory;
import engine.ReportManager;
import forms.MainWindowForm;

public class MainWindow {

	private JFrame frame;
	private static JTextField TF_Search;
	private static JTextField TF_qtd;
	private static JRadioButton rdbtnPorBusca; 
	private static JRadioButton rdbtnStream;
	private static JButton BTN_Search;
	private static MainWindowForm mwf;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 890, 625);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel.setBounds(10, 11, 419, 119);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		TF_Search = new JTextField();
		TF_Search.setBounds(10, 26, 399, 25);
		panel.add(TF_Search);
		TF_Search.setColumns(10);
		
		JLabel lblTermosSeparadosPor = new JLabel("Termos separados por virgula");
		lblTermosSeparadosPor.setBounds(10, 11, 183, 14);
		panel.add(lblTermosSeparadosPor);
		lblTermosSeparadosPor.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblTipoDeColeta = new JLabel("Tipo de coleta:");
		lblTipoDeColeta.setBounds(10, 62, 89, 14);
		panel.add(lblTipoDeColeta);
		
		rdbtnPorBusca = new JRadioButton("Busca");
		rdbtnPorBusca.setBounds(186, 58, 70, 23);
		panel.add(rdbtnPorBusca);
		
		rdbtnStream = new JRadioButton("Stream");
		rdbtnStream.setBounds(105, 58, 79, 23);
		panel.add(rdbtnStream);
		
		ButtonGroup searchRadioGroup = new ButtonGroup();
		searchRadioGroup.add(rdbtnStream);
		searchRadioGroup.add(rdbtnPorBusca);
		
		BTN_Search = new JButton("Coletar");
		BTN_Search.setBounds(320, 85, 89, 23);
		panel.add(BTN_Search);
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(10, 89, 77, 14);
		panel.add(lblQuantidade);
		
		TF_qtd = new JTextField();
		TF_qtd.setBounds(93, 88, 43, 20);
		panel.add(TF_qtd);
		TF_qtd.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		panel_1.setBounds(439, 11, 425, 119);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		final JLabel lblQuantidadeColetada = new JLabel("Quantidade Coletada:");
		lblQuantidadeColetada.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblQuantidadeColetada.setBounds(10, 11, 184, 25);
		panel_1.add(lblQuantidadeColetada);
		
		final JLabel LBL_quantidade_coletada = new JLabel("");
		LBL_quantidade_coletada.setFont(new Font("Tahoma", Font.BOLD, 15));
		LBL_quantidade_coletada.setBounds(181, 16, 46, 14);
		panel_1.add(LBL_quantidade_coletada);
		
		JPanel tabela = new JPanel();
		tabela.setBounds(10, 37, 405, 59);
		panel_1.add(tabela);
		tabela.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_3 = new JPanel();
		tabela.add(panel_3);
		panel_3.setBorder(new LineBorder(null, 0));
		panel_3.setBackground(SystemColor.menu);
		panel_3.setLayout(null);
		
		JPanel panel_green = new JPanel();
		panel_green.setBounds(7, 7, 15, 15);
		panel_green.setBackground(new Color(50, 205, 50));
		panel_3.add(panel_green);
		panel_green.setBorder(new LineBorder(null, 0, true));
		
		JLabel lblNewLabel = new JLabel("Positivo");
		lblNewLabel.setBounds(0, 0, 135, 29);
		panel_3.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JPanel panel_4 = new JPanel();
		tabela.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel label = new JLabel("Negativo");
		label.setBounds(0, 0, 135, 29);
		panel_4.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(null, 0, true));
		panel_5.setBackground(Color.RED);
		panel_5.setBounds(7, 7, 15, 15);
		panel_4.add(panel_5);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		tabela.add(panel_6);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(null, 0, true));
		panel_7.setBackground(new Color(255, 255, 0));
		panel_7.setBounds(7, 7, 15, 15);
		panel_6.add(panel_7);
		
		JLabel lblNeutro = new JLabel("Neutro");
		lblNeutro.setBounds(0, 0, 135, 29);
		panel_6.add(lblNeutro);
		lblNeutro.setHorizontalAlignment(SwingConstants.CENTER);
		lblNeutro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		final JLabel LBL_positive = new JLabel("");
		LBL_positive.setHorizontalAlignment(SwingConstants.CENTER);
		tabela.add(LBL_positive);
		LBL_positive.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		final JLabel LBL_Neutral = new JLabel("");
		LBL_Neutral.setHorizontalAlignment(SwingConstants.CENTER);
		tabela.add(LBL_Neutral);
		LBL_Neutral.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		final JLabel LBL_Negative = new JLabel("");
		LBL_Negative.setHorizontalAlignment(SwingConstants.CENTER);
		tabela.add(LBL_Negative);
		LBL_Negative.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel_2.setBounds(10, 141, 854, 395);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(TableHandler.getTable());
		scrollPane.setBounds(10, 7, 834, 378);
		
		panel_2.add(scrollPane);
		
		JButton btnRelatorio = new JButton("Gerar Relatório");
		btnRelatorio.setBounds(745, 550, 120, 25);
		frame.getContentPane().add(btnRelatorio);
		
		btnRelatorio.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0){
				String savePath = FileChooserDirectory.initialize();
				ApplicationUtils.populateTermsStats(); 

				Report report = ApplicationUtils.createReport(mwf);
				boolean success = ReportManager.generateReport(savePath, report);
				if(success){
					JOptionPane.showMessageDialog(null, "Relatório exportado com sucesso em:\n"+savePath+File.separator+"Relatorio", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Erro ao criar relatório", "Erro", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		BTN_Search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(validate()){
					if(rdbtnStream.isSelected()){
						makeAllComponentsDisabled();
					}
					mwf = new MainWindowForm();
					if(rdbtnPorBusca.isSelected()){
						mwf.setFetchType(Constants.QUERYTYPE);
						mwf.setQuantity(Integer.parseInt(TF_qtd.getText()));
					}else if(rdbtnStream.isSelected()){
						StreamDialog d = new StreamDialog(frame);
						mwf.setFetchType(Constants.STREAMTYPE);
					}
					
					mwf.setKeyWords(TF_Search.getText());
					MainClass.initializeWork(mwf);

					mwf = ApplicationUtils.getRatingCount();

					LBL_positive.setText(""+mwf.getPositive());
					LBL_Negative.setText(""+mwf.getNeutral());
					LBL_Neutral.setText(""+mwf.getNegative());
					
					int total = mwf.getNegative()+mwf.getPositive()+mwf.getNeutral();
					LBL_quantidade_coletada.setText(""+total);

					ApplicationUtils.populateTable();
			
				}
			}
		});
	}
	
	public boolean validate(){
		if(TF_Search.getText().length()<1){
			JOptionPane.showMessageDialog(null, "Insira o(s) termo(s) a ser pesquisado", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!rdbtnPorBusca.isSelected() && !rdbtnStream.isSelected()){
			JOptionPane.showMessageDialog(null, "Selecione um tipo de busca", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(rdbtnPorBusca.isSelected() && TF_qtd.getText().length()<1){
			JOptionPane.showMessageDialog(null, "Insira a quantidade a ser coletada", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	public void makeAllComponentsDisabled(){
		BTN_Search.setEnabled(false);
		TF_Search.setEditable(false);
		rdbtnPorBusca.setEnabled(false);
		rdbtnStream.setEnabled(false);
		TF_qtd.setEditable(false);
	}
	
	public static void makeAllComponentsEnabled(){
		BTN_Search.setEnabled(true);
		TF_Search.setEditable(true);
		rdbtnPorBusca.setEnabled(true);
		rdbtnStream.setEnabled(true);
		TF_qtd.setEditable(true);
	}
	
	public static MainWindowForm getMainWindowForm(){
		return mwf;
	}
}
