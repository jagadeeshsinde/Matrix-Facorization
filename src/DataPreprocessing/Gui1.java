package DataPreprocessing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class Gui1 extends JFrame {

	private JPanel contentPane;
	private JPanel main;
	private JButton btnDataPreprocessing;
	private JPanel panel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui1 frame = new Gui1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		main = new JPanel();
		main.setBounds(0, 0, 434, 250);
		contentPane.add(main);
		main.setLayout(null);
		
		btnDataPreprocessing = new JButton("Data Preprocessing");
		btnDataPreprocessing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_1.setVisible(true);
				main.setVisible(false);
				
			}
		});
		btnDataPreprocessing.setBounds(108, 24, 217, 78);
		main.add(btnDataPreprocessing);
		main.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnDataPreprocessing}));
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 434, 250);
		contentPane.add(panel_1);
		
		JButton btnFreqCount = new JButton("Freq Count");
		panel_1.add(btnFreqCount);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 10, 10);
		contentPane.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 10, 10);
		contentPane.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(0, 0, 10, 10);
		contentPane.add(panel_4);
	}

}
