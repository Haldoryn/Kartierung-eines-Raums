package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.ImageConsumer;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

/**
 * The Window class
 * 
 * @author dh10hcn
 *
 */
public class MainWindow 
{
	private JFrame frame;
	public JTextArea textPane = new JTextArea();
	public JLabel image = new JLabel();

	private List<IConnectEventListener> onConnectListeners = new LinkedList<>();
	private List<IStartEventListener> onStartListeners = new LinkedList<>();
	private List<IStopEventListener> onStopListeners = new LinkedList<>();
	private List<ISaveEventListener> onSaveListeners = new LinkedList<>();
	private List<IScanEventListener> onScanListeners = new LinkedList<>();
	private List<IForwardEventListener> onForwardListeners = new LinkedList<>();
	private List<IBackwardEventListener> onBackwardListeners = new LinkedList<>();
	private List<ILeftEventListener> onLeftListeners = new LinkedList<>();
	private List<IRightEventListener> onRightListeners = new LinkedList<>();
	
	public void setImage(Image newImage)
	{
		if (!SwingUtilities.isEventDispatchThread()) 
		{
			SwingUtilities.invokeLater(new Runnable() 
			{
				@Override
				public void run() 
				{
					setImage(newImage);
				}
			});
		}
		this.image.setIcon(new ImageIcon(newImage.getScaledInstance(image.getWidth(), image.getHeight(),  Image.SCALE_DEFAULT)));
	}

	public void addOnConnectEventListener(IConnectEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onConnectListeners.add(listener);
	}

	public void removeOnConnectEventListener(IConnectEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onConnectListeners.remove(listener);
	}

	public void addOnStartEventListener(IStartEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onStartListeners.add(listener);
	}

	public void removeOnStartEventListener(IStartEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onStartListeners.remove(listener);
	}

	public void addOnStopEventListener(IStopEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onStopListeners.add(listener);
	}

	public void removeOnStopEventListener(IStopEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onStopListeners.remove(listener);
	}
	
	public void addOnSaveEventListener(ISaveEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onSaveListeners.add(listener);
	}

	public void removeOnSaveEventListener(ISaveEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onSaveListeners.remove(listener);
	}
	
	public void addOnScanEventListener(IScanEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onScanListeners.add(listener);
	}

	public void removeOnScanEventListener(IScanEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onScanListeners.remove(listener);
	}
	
	public void addOnForwardEventListener(IForwardEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onForwardListeners.add(listener);
	}

	public void removeOnForwardEventListener(IForwardEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onForwardListeners.remove(listener);
	}
	
	public void addOnBackwardEventListener(IBackwardEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onBackwardListeners.add(listener);
	}

	public void removeOnBackwardEventListener(IBackwardEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onBackwardListeners.remove(listener);
	}
	
	public void addOnLeftEventListener(ILeftEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onLeftListeners.add(listener);
	}

	public void removeOnLeftEventListener(ILeftEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onLeftListeners.remove(listener);
	}
	
	public void addOnRightEventListener(IRightEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onRightListeners.add(listener);
	}

	public void removeOnRightEventListener(IRightEventListener listener) 
	{
		if (listener == null) 
		{
			throw new IllegalArgumentException("The 'listener' argument must not be null");
		}
		onRightListeners.remove(listener);
	}




	public void ClearLog() 
	{	
		if (!SwingUtilities.isEventDispatchThread()) 
		{
			SwingUtilities.invokeLater(new Runnable() 
			{
				@Override
				public void run() 
				{
					textPane.setText("");
				}
			});
			return;
		}
		textPane.setText("");
	}

	public void addLogEntry(String message) 
	{
		if (message == null) 
		{
			throw new IllegalArgumentException("The 'message' argument must not be null");
		}
		
		if (!SwingUtilities.isEventDispatchThread()) 
		{
			SwingUtilities.invokeLater(new Runnable() 
			{
				@Override
				public void run() 
				{
					addLogEntry(message);
				}
			});
			return;
		}
		
		if(textPane.getText().length() > 10000)
		{
			textPane.setText(textPane.getText().substring(8000) + "\n" + message);
		}	
		else
		{
			textPane.setText(textPane.getText() + "\n" + message);
		}
		
	}

	/**
	 * Show the maiWindow
	 */
	public void Show() 
	{
		SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					initialize();

				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public void repaintImage()
	{
		if (!SwingUtilities.isEventDispatchThread()) 
		{
			SwingUtilities.invokeLater(new Runnable() 
			{
				@Override
				public void run() 
				{
					repaintImage();
				}
			});
		}
		image.repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{

		// the window itself
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 500);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Steuerung Kartierungsroboter");
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		// the Label IP
		JLabel labelIP = new JLabel("IP:");
		springLayout.putConstraint(SpringLayout.NORTH, labelIP, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, labelIP, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(labelIP);

		// the text field to enter IP
		JTextField textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, labelIP);
		springLayout.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, labelIP);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		// the button connect
		JButton btnConnect = new JButton("Verbinden");
		springLayout.putConstraint(SpringLayout.NORTH, btnConnect, -4, SpringLayout.NORTH, labelIP);
		springLayout.putConstraint(SpringLayout.WEST, btnConnect, 6, SpringLayout.EAST, textField);
		btnConnect.setActionCommand("Connect");
		btnConnect.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{

				for (IConnectEventListener listener : onConnectListeners) 
				{
					if (textField.getText().indexOf(':') > -1) 
					{
						String[] arr = textField.getText().split(":");
						String host = arr[0];
						int port = Integer.parseInt(arr[1]);
						try 
						{
							InetAddress netAddress = InetAddress.getByName(host);
							listener.onConnect(netAddress, port);
						} catch (Exception ex) 
						{
							JOptionPane.showMessageDialog(null, "Invalid address", "Connection Error", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else
					{
						try 
						{
							InetAddress netAddress = InetAddress.getByName(textField.getText());
							// Default port
							listener.onConnect(netAddress, 9876);
						} catch (Exception ex) 
						{
							JOptionPane.showMessageDialog(null, "Invalid address", "Connection Error", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		frame.getContentPane().add(btnConnect);
		
		// the button save
		JButton btnSave = new JButton("Speichern");
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 0, SpringLayout.NORTH, btnConnect);
		springLayout.putConstraint(SpringLayout.WEST, btnSave, 6, SpringLayout.EAST, btnConnect);
		btnSave.setActionCommand("Save");
		btnSave.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{

				for (ISaveEventListener listener : onSaveListeners) 
				{
					listener.onSave();
				}
			}
		});
		frame.getContentPane().add(btnSave);

		// the tab Pane at the top
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 6, SpringLayout.SOUTH, btnConnect);
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 160, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 424, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(tabbedPane);

		// the first tab
		JComponent panel1 = new JPanel();
		tabbedPane.addTab("Automatische Kartierung", null, panel1, "Automatische Kartierung");
		SpringLayout sl_panel1 = new SpringLayout();
		panel1.setLayout(sl_panel1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		// button start
		JButton btnStart = new JButton("Start");
		sl_panel1.putConstraint(SpringLayout.NORTH, btnStart, 10, SpringLayout.NORTH, panel1);
		sl_panel1.putConstraint(SpringLayout.WEST, btnStart, 10, SpringLayout.WEST, panel1);
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 10, SpringLayout.NORTH, panel1);
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 10, SpringLayout.WEST, panel1);
		btnStart.setActionCommand("Start");
		btnStart.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				for (IStartEventListener listener : onStartListeners) 
				{
					listener.onStart();
				}

			}
		});
		panel1.add(btnStart);

		// button stop
		JButton btnStopp = new JButton("Stopp");
		sl_panel1.putConstraint(SpringLayout.NORTH, btnStopp, 0, SpringLayout.NORTH, btnStart);
		sl_panel1.putConstraint(SpringLayout.WEST, btnStopp, 6, SpringLayout.EAST, btnStart);
		springLayout.putConstraint(SpringLayout.NORTH, btnStopp, 0, SpringLayout.NORTH, btnStart);
		springLayout.putConstraint(SpringLayout.WEST, btnStopp, 6, SpringLayout.EAST, btnStart);
		btnStopp.setActionCommand("Stopp");
		btnStopp.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				for (IStopEventListener listener : onStopListeners) 
				{
					listener.onStop();
				}

			}
		});

		panel1.add(btnStopp);

		// button abort
		JButton btnAbort = new JButton("Abbrechen");
		sl_panel1.putConstraint(SpringLayout.NORTH, btnAbort, 6, SpringLayout.SOUTH, btnStart);
		sl_panel1.putConstraint(SpringLayout.WEST, btnAbort, 0, SpringLayout.WEST, btnStart);
		springLayout.putConstraint(SpringLayout.NORTH, btnAbort, 6, SpringLayout.SOUTH, btnStart);
		springLayout.putConstraint(SpringLayout.WEST, btnAbort, 0, SpringLayout.WEST, btnStart);
		btnAbort.setActionCommand("Abort");
		panel1.add(btnAbort);

		// the second tab
		JComponent panel2 = new JPanel();
		tabbedPane.addTab("Manuelle Kartierung", null, panel2, "Manuelle Kartierung");
		panel2.setLayout(new GridLayout(3, 7, 0, 0));		
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		
		// first three placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());

		// the button forward
		ImageIcon iconForward = new ImageIcon("res/PfeilHoch.png");
		JButton btnForward = new JButton(iconForward);
		btnForward.setMnemonic(KeyEvent.VK_UP);
		btnForward.setActionCommand("Forward");
		btnForward.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{

				for (IForwardEventListener listener : onForwardListeners) 
				{
					listener.onForward();
				}
			}
		});
		panel2.add(btnForward);

		// next five placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());

		// the button left
		ImageIcon iconLeft = new ImageIcon("res/PfeilLinks.png");
		JButton btnLeft = new JButton(iconLeft);
		btnLeft.setMnemonic(KeyEvent.VK_LEFT);
		btnLeft.setActionCommand("Left");
		btnLeft.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{

				for (ILeftEventListener listener : onLeftListeners) 
				{
					listener.onLeft();
				}
			}
		});
		panel2.add(btnLeft);

		// button to scan
		ImageIcon iconScan = new ImageIcon("res/Fadenkreuzlaser.png");
		JButton btnScan = new JButton(iconScan);
		btnScan.setMnemonic(KeyEvent.VK_END);
		btnScan.setActionCommand("Scan");
		btnScan.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				for (IScanEventListener listener : onScanListeners)
				{
					listener.onScan();
				}
			}
		});
		panel2.add(btnScan);

		// button right
		ImageIcon iconRight = new ImageIcon("res/PfeilRechts.png");
		JButton btnRight = new JButton(iconRight);
		btnRight.setMnemonic(KeyEvent.VK_RIGHT);
		btnRight.setActionCommand("Right");
		btnRight.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{

				for (IRightEventListener listener : onRightListeners) 
				{
					listener.onRight();
				}
			}
		});
		panel2.add(btnRight);

		// next five placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());

		// the button backwards
		ImageIcon iconBackwards = new ImageIcon("res/PfeilRunter.png");
		JButton btnBackward = new JButton(iconBackwards);
		btnBackward.setMnemonic(KeyEvent.VK_DOWN);
		btnBackward.setActionCommand("Backwards");
		btnBackward.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{

				for (IBackwardEventListener listener : onBackwardListeners) {
					listener.onBackward();
				}
			}
		});
		panel2.add(btnBackward);

		// last three placeholder
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());
		panel2.add(javax.swing.Box.createGlue());

		// the Label above the place to draw
		JLabel labeldraw = new JLabel("Zeichnung:");
		springLayout.putConstraint(SpringLayout.NORTH, labeldraw, 0, SpringLayout.NORTH, tabbedPane);
		springLayout.putConstraint(SpringLayout.WEST, labeldraw, 6, SpringLayout.EAST, tabbedPane);
		frame.getContentPane().add(labeldraw);

		// the Label above the text box
		JLabel labelcommand = new JLabel("Kommandos:");
		springLayout.putConstraint(SpringLayout.NORTH, labelcommand, 6, SpringLayout.SOUTH, tabbedPane);
		springLayout.putConstraint(SpringLayout.WEST, labelcommand, 0, SpringLayout.WEST, tabbedPane);
		frame.getContentPane().add(labelcommand);
		
		// the panel to show some simple commands to the user
		textPane.setEditable(false);
		
		//ScrollBar for the TextPane
		JScrollPane scrollPane = new JScrollPane(textPane);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, labelcommand);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, labelIP);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, tabbedPane);
		frame.getContentPane().add(scrollPane);

		// the image with the points		
		springLayout.putConstraint(SpringLayout.NORTH, image, 6, SpringLayout.SOUTH, labeldraw);
		springLayout.putConstraint(SpringLayout.WEST, image, 6, SpringLayout.EAST, tabbedPane);
		springLayout.putConstraint(SpringLayout.SOUTH, image, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, image, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(image);

		frame.setVisible(true);
	}

}
