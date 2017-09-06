package gui.main;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//the window itself
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Steuerung Kartierungsroboter");
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		//the Label IP
		JLabel labelIP = new JLabel("IP:");
		springLayout.putConstraint(SpringLayout.NORTH, labelIP, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, labelIP, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(labelIP);
		
		//the text field to enter IP
		JTextField textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, labelIP);
		springLayout.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, labelIP);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		//the button to commit IP
		JButton btnVerbinden = new JButton("Verbinden");
		springLayout.putConstraint(SpringLayout.NORTH, btnVerbinden, -4, SpringLayout.NORTH, labelIP);
		springLayout.putConstraint(SpringLayout.WEST, btnVerbinden, 6, SpringLayout.EAST, textField);
		frame.getContentPane().add(btnVerbinden);
		
		
		//the tab Pane at the top
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 6, SpringLayout.SOUTH, btnVerbinden);
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 160, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 424, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(tabbedPane);
		
		//the first tab
		JComponent panel1 = new JPanel();
		tabbedPane.addTab("Automatische Kartierung", null, panel1, "Automatische Kartierung");
		SpringLayout sl_panel1 = new SpringLayout();
		panel1.setLayout(sl_panel1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		//button start
		JButton btnStart = new JButton("Start");
		sl_panel1.putConstraint(SpringLayout.NORTH, btnStart, 10, SpringLayout.NORTH, panel1);
		sl_panel1.putConstraint(SpringLayout.WEST, btnStart, 10, SpringLayout.WEST, panel1);
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 10, SpringLayout.NORTH, panel1);
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 10, SpringLayout.WEST, panel1);
		panel1.add(btnStart);
		
		//button stop
		JButton btnStopp = new JButton("Stopp");
		sl_panel1.putConstraint(SpringLayout.NORTH, btnStopp, 0, SpringLayout.NORTH, btnStart);
		sl_panel1.putConstraint(SpringLayout.WEST, btnStopp, 6, SpringLayout.EAST, btnStart);
		springLayout.putConstraint(SpringLayout.NORTH, btnStopp, 0, SpringLayout.NORTH, btnStart);
		springLayout.putConstraint(SpringLayout.WEST, btnStopp, 6, SpringLayout.EAST, btnStart);
		panel1.add(btnStopp);
		
		//button abort
		JButton btnAbbrechen = new JButton("Abbrechen");
		sl_panel1.putConstraint(SpringLayout.NORTH, btnAbbrechen, 6, SpringLayout.SOUTH, btnStart);
		sl_panel1.putConstraint(SpringLayout.WEST, btnAbbrechen, 0, SpringLayout.WEST, btnStart);
		springLayout.putConstraint(SpringLayout.NORTH, btnAbbrechen, 6, SpringLayout.SOUTH, btnStart);
		springLayout.putConstraint(SpringLayout.WEST, btnAbbrechen, 0, SpringLayout.WEST, btnStart);
		panel1.add(btnAbbrechen);

		//the second tab
		JComponent panel2 = new JPanel();
		tabbedPane.addTab("Manuelle Kartierung", null, panel2, "Manuelle Kartierung");
		panel2.setLayout(new GridLayout(3, 7, 0, 0));
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);	
		
		//first three placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		
		//the button forward
		ImageIcon iconForward = new ImageIcon("res/PfeilHoch.png");
		JButton forward = new JButton(iconForward);
		forward.setMnemonic(KeyEvent.VK_UP);
		panel2.add(forward);
		
		//next five placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		
		//the button left
		ImageIcon iconLeft = new ImageIcon("res/PfeilLinks.png");
		JButton left = new JButton(iconLeft);
		left.setMnemonic(KeyEvent.VK_LEFT);
		panel2.add(left);
		
		//button to scan
		ImageIcon iconScan = new ImageIcon("res/Fadenkreuzlaser.png");
		JButton scan = new JButton(iconScan);
		scan.setMnemonic(KeyEvent.VK_END);
		panel2.add(scan);
		
		//button right
		ImageIcon iconRight = new ImageIcon("res/PfeilRechts.png");
		JButton right = new JButton(iconRight);
		right.setMnemonic(KeyEvent.VK_RIGHT);
		panel2.add(right);
		
		//next five placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		
		//the button backwards
		ImageIcon iconBackwards = new ImageIcon("res/PfeilRunter.png");
		JButton backward = new JButton(iconBackwards);
		backward.setMnemonic(KeyEvent.VK_DOWN);
		panel2.add(backward);

		//last three placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());	
		
		//the Label above the place to draw
		JLabel labeldraw = new JLabel("Zeichnung:");
		springLayout.putConstraint(SpringLayout.NORTH, labeldraw, 0, SpringLayout.NORTH, tabbedPane);
		springLayout.putConstraint(SpringLayout.WEST, labeldraw, 6, SpringLayout.EAST, tabbedPane);
		frame.getContentPane().add(labeldraw);
		
		//the Label above the text box
		JLabel labelcommand = new JLabel("Kommandos:");
		springLayout.putConstraint(SpringLayout.NORTH, labelcommand, 6, SpringLayout.SOUTH, tabbedPane);
		springLayout.putConstraint(SpringLayout.WEST, labelcommand, 0, SpringLayout.WEST, tabbedPane);
		frame.getContentPane().add(labelcommand);
		
		//the panel to show some simple commands to the user
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, textPane, 6, SpringLayout.SOUTH, labelcommand);
		springLayout.putConstraint(SpringLayout.WEST, textPane, 0, SpringLayout.WEST, labelIP);
		springLayout.putConstraint(SpringLayout.SOUTH, textPane, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textPane, 0, SpringLayout.EAST, tabbedPane);
		frame.getContentPane().add(textPane);
		
		//the panel for the points
		JPanel panelDraw = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panelDraw, 6, SpringLayout.SOUTH, labeldraw);
		springLayout.putConstraint(SpringLayout.WEST, panelDraw, 6, SpringLayout.EAST, tabbedPane);
		springLayout.putConstraint(SpringLayout.SOUTH, panelDraw, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panelDraw, -10, SpringLayout.EAST, frame.getContentPane());
		
	}
	
	
}
















