package jaggu;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DataPreprocessing.LogReturn;
import DataPreprocessing.Original;
import DataPreprocessing.TermDoc;
import MatrixFactorization.ClosingPrice;
import MatrixFactorization.RecMF;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JScrollBar;
import java.awt.Choice;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.demo.DateChooserPanel;

import javax.swing.JTextArea;

import sun.util.calendar.LocalGregorianCalendar.Date;




public class Gui extends JFrame {
	private JPanel datapre;
	private JPanel main;
	private JPanel newsarticles;
	private JMenuItem mntmNewMenuItem;
	private JButton btnFindTopWords;
	private JButton btnZScoreMatrix;
	private JButton btnOk_1;
	private JPanel stockprices;
	private JMenuItem mntmSelectStockClosing;
	private JButton btnLogReturn; 
	private JButton btnOk_2; 
	private JPanel matrixFactorization;
	private JDateChooser dateChooser;
	
	
	private JComboBox comboBox;
	private JTextArea textArea;
	
	public Gui() {
		super("Stock Price Prediction");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		main = new JPanel();
		getContentPane().add(main, "name_614928165772405");
		main.setLayout(null);
		
		JButton btnNewButton = new JButton("Data Preprossing");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				datapre.setVisible(true);
				main.setVisible(false);
			}
		});
		btnNewButton.setBounds(101, 55, 223, 44);
		main.add(btnNewButton);
		
		JButton btnImplementation = new JButton("Implementation");
		btnImplementation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				matrixFactorization.setVisible(true);
				main.setVisible(false);
				/*RecMF recmf = new RecMF();
				try {
					recmf.inputMF();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		});
		btnImplementation.setBounds(101, 139, 223, 44);
		main.add(btnImplementation);
		
		datapre = new JPanel();
		getContentPane().add(datapre, "name_614928200023801");
		datapre.setLayout(null);
		
		JButton btnSelectNewsarticles = new JButton("Select  NewsArticles");
		btnSelectNewsarticles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newsarticles.setVisible(true);
				datapre.setVisible(false);
			}
		});
		btnSelectNewsarticles.setBounds(141, 44, 199, 48);
		datapre.add(btnSelectNewsarticles);
		
		JButton btnSelectStockPrices = new JButton("Select Stock Prices");
		btnSelectStockPrices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stockprices.setVisible(true);
				datapre.setVisible(false);
			}
		});
		btnSelectStockPrices.setBounds(141, 130, 199, 48);
		datapre.add(btnSelectStockPrices);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.setVisible(true);
				datapre.setVisible(false);
			}
		});
		btnOk.setBounds(289, 212, 89, 23);
		datapre.add(btnOk);
		
		newsarticles = new JPanel();
		getContentPane().add(newsarticles, "name_88181037105013");
		newsarticles.setLayout(null);
		
		mntmNewMenuItem = new JMenuItem("Select NewArticles");
		mntmNewMenuItem.setBounds(62, 49, 240, 47);
		newsarticles.add(mntmNewMenuItem);
		
		btnFindTopWords = new JButton("Find Top Words");
		btnFindTopWords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnFindTopWords.setBounds(165, 129, 219, 39);
		newsarticles.add(btnFindTopWords);
		
		btnZScoreMatrix = new JButton("Z Score  Matrix");
		btnZScoreMatrix.setBounds(165, 191, 219, 39);
		newsarticles.add(btnZScoreMatrix);
		
		btnOk_1 = new JButton("OK");
		btnOk_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				datapre.setVisible(true);
				newsarticles.setVisible(false);
			}
		});
		btnOk_1.setBounds(335, 241, 89, 23);
		newsarticles.add(btnOk_1);
		
		stockprices = new JPanel();
		getContentPane().add(stockprices, "name_90687281835496");
		stockprices.setLayout(null);
		
		mntmSelectStockClosing = new JMenuItem("Select Stock Closing prices");
		mntmSelectStockClosing.setBounds(77, 60, 247, 28);
		stockprices.add(mntmSelectStockClosing);
		
		btnLogReturn = new JButton("Log Return");
		btnLogReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogReturn.setBounds(204, 115, 205, 40);
		stockprices.add(btnLogReturn);
		
		btnOk_2 = new JButton("OK");
		btnOk_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.setVisible(true);
				stockprices.setVisible(false);
			}
		});
		btnOk_2.setBounds(320, 209, 89, 23);
		stockprices.add(btnOk_2);
		
		matrixFactorization = new JPanel();
		getContentPane().add(matrixFactorization, "name_174195288670698");
		matrixFactorization.setLayout(null);
		
		//comboBox = new JComboBox();
		String[] filename = {"Choose stock","TCS","INFOSYS"};
		comboBox = new JComboBox(filename);
		comboBox.setBounds(149, 40, 104, 26);
		
		matrixFactorization.add(comboBox);
		
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBounds(81, 40, 118, 26);
		matrixFactorization.add(lblStock);
		
		 dateChooser = new JDateChooser();
		dateChooser.setBounds(149, 92, 104, 20);
		matrixFactorization.add(dateChooser);
	
		
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(81, 98, 46, 14);
		matrixFactorization.add(lblDate);
		
		JButton btnClosingPrice = new JButton("Closing Price");
		btnClosingPrice.setBounds(225, 157, 152, 26);
		matrixFactorization.add(btnClosingPrice);
		
		textArea = new JTextArea();
		textArea.setBounds(103, 173, 85, 22);
		matrixFactorization.add(textArea);
		
		
		

		HandlerClass handler = new HandlerClass();
		mntmNewMenuItem.addActionListener(handler);
		HandlerClassStock handlerstock = new HandlerClassStock();
		mntmSelectStockClosing.addActionListener(handlerstock);
		HandlerClassClosing handlerclosing = new HandlerClassClosing();
		btnClosingPrice.addActionListener(handlerclosing);
	}
	public class HandlerClass implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			
			final TermDoc td = new TermDoc();
			final Original org = new Original();
			if(mntmNewMenuItem.isArmed())
			{
				JFileChooser fc = new JFileChooser();
				 fc.setMultiSelectionEnabled(true);
				int i = fc.showOpenDialog(null);
				if(i == JFileChooser.APPROVE_OPTION)
				{
					File[] f = fc.getSelectedFiles();
					td.set(f);
					org.set(f);
					btnFindTopWords.addActionListener(new ActionListener() { 
					   public void actionPerformed(ActionEvent e) { 
					   	try {
								td.input();
								JOptionPane.showMessageDialog(null, "Top Words Caluculated");
								
							} catch (IOException e1) {
								e1.printStackTrace();
							}
					   } 
					});
					btnZScoreMatrix.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try {
								org.inputMongo();
								JOptionPane.showMessageDialog(null, "Z-Score Caluculated");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					});
					
					
				}
			}
		}
	}
	public class HandlerClassStock implements ActionListener
	{

		
		public void actionPerformed(ActionEvent e) {
			final LogReturn lr = new LogReturn();
			if(mntmSelectStockClosing.isArmed()){
				JFileChooser fc = new JFileChooser();
				int i = fc.showOpenDialog(null);
				if(i == JFileChooser.APPROVE_OPTION)
				{
					File f = fc.getSelectedFile();
					lr.set(f);
					btnLogReturn.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								lr.inputlog();
								JOptionPane.showMessageDialog(null, "Log Return Caluculated");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
					
				}
				
			}
			
		}
		
	}
	public class HandlerClassClosing implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			RecMF recmf = new RecMF();
			String name = (String) comboBox.getSelectedItem();
			//String strDate = DateFormat.getDateInstance().format(date);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String date = sdf.format(dateChooser.getDate().getTime());
			//System.out.print(date);
			textArea.append(name);
			ClosingPrice cp = new ClosingPrice();
			cp.setStock(name);
			cp.setDate(date);
			try {
				recmf.inputMF();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}
	}
}
