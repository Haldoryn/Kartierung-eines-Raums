package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.PointImage;
import de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI.WindowActionListener;
/**
 * The Window class
 * @author dh10hcn
 *
 */
public class MainWindow {

	private JFrame frame;
	public static JTextArea textPane = new JTextArea();
	public static PointImage drawImage = new PointImage();
	
	/**
	 * Show the maiWindow
	 */
	public void Show()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});			
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
		btnVerbinden.addActionListener(new WindowActionListener());
		btnVerbinden.setActionCommand("Connect");
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
		btnStart.addActionListener(new WindowActionListener());
		btnStart.setActionCommand("Start");
		panel1.add(btnStart);
		
		//button stop
		JButton btnStopp = new JButton("Stopp");
		sl_panel1.putConstraint(SpringLayout.NORTH, btnStopp, 0, SpringLayout.NORTH, btnStart);
		sl_panel1.putConstraint(SpringLayout.WEST, btnStopp, 6, SpringLayout.EAST, btnStart);
		springLayout.putConstraint(SpringLayout.NORTH, btnStopp, 0, SpringLayout.NORTH, btnStart);
		springLayout.putConstraint(SpringLayout.WEST, btnStopp, 6, SpringLayout.EAST, btnStart);
		btnStopp.addActionListener(new WindowActionListener());
		btnStopp.setActionCommand("Stopp");
		panel1.add(btnStopp);
		
		//button abort
		JButton btnAbort = new JButton("Abbrechen");
		sl_panel1.putConstraint(SpringLayout.NORTH, btnAbort, 6, SpringLayout.SOUTH, btnStart);
		sl_panel1.putConstraint(SpringLayout.WEST, btnAbort, 0, SpringLayout.WEST, btnStart);
		springLayout.putConstraint(SpringLayout.NORTH, btnAbort, 6, SpringLayout.SOUTH, btnStart);
		springLayout.putConstraint(SpringLayout.WEST, btnAbort, 0, SpringLayout.WEST, btnStart);
		btnAbort.addActionListener(new WindowActionListener());
		btnAbort.setActionCommand("Abort");
		panel1.add(btnAbort);

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
		JButton btnForward = new JButton(iconForward);
		btnForward.setMnemonic(KeyEvent.VK_UP);
		btnForward.addActionListener(new WindowActionListener());
		btnForward.setActionCommand("Forward");
		panel2.add(btnForward);
		
		//next five placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		
		//the button left
		ImageIcon iconLeft = new ImageIcon("res/PfeilLinks.png");
		JButton btnLeft = new JButton(iconLeft);
		btnLeft.setMnemonic(KeyEvent.VK_LEFT);
		btnLeft.addActionListener(new WindowActionListener());
		btnLeft.setActionCommand("Left");
		panel2.add(btnLeft);
		
		//button to scan
		ImageIcon iconScan = new ImageIcon("res/Fadenkreuzlaser.png");
		JButton btnScan = new JButton(iconScan);
		btnScan.setMnemonic(KeyEvent.VK_END);
		btnScan.addActionListener(new WindowActionListener());
		btnScan.setActionCommand("Scan");
		panel2.add(btnScan);
		
		//button right
		ImageIcon iconRight = new ImageIcon("res/PfeilRechts.png");
		JButton btnRight = new JButton(iconRight);
		btnRight.setMnemonic(KeyEvent.VK_RIGHT);
		btnRight.addActionListener(new WindowActionListener());
		btnRight.setActionCommand("Right");
		panel2.add(btnRight);
		
		//next five placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		
		//the button backwards
		ImageIcon iconBackwards = new ImageIcon("res/PfeilRunter.png");
		JButton btnBackward = new JButton(iconBackwards);
		btnBackward.setMnemonic(KeyEvent.VK_DOWN);
		btnBackward.addActionListener(new WindowActionListener());
		btnBackward.setActionCommand("Backwards");
		panel2.add(btnBackward);

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
		textPane.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, textPane, 6, SpringLayout.SOUTH, labelcommand);
		springLayout.putConstraint(SpringLayout.WEST, textPane, 0, SpringLayout.WEST, labelIP);
		springLayout.putConstraint(SpringLayout.SOUTH, textPane, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textPane, 0, SpringLayout.EAST, tabbedPane);
		frame.getContentPane().add(textPane);
		
		//the image with the points
		drawImage.buildUI();
        frame.add("Center", drawImage);
		springLayout.putConstraint(SpringLayout.NORTH, drawImage, 6, SpringLayout.SOUTH, labeldraw);
		springLayout.putConstraint(SpringLayout.WEST, drawImage, 6, SpringLayout.EAST, tabbedPane);
		springLayout.putConstraint(SpringLayout.SOUTH, drawImage, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, drawImage, -10, SpringLayout.EAST, frame.getContentPane());
		frame.setVisible(true);
		
	}
	
}






