package de.dhbw_stuttgart.hb.inf2016.RaumKartierung.Server.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WindowActionListener implements ActionListener
{
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch (e.getActionCommand()) {
		case "Connect":
			MainWindow.textPane.append("Verbinden gedr�ckt\n");
			break;
			
		case "Start":
			MainWindow.textPane.append("Start gedr�ckt\n");
			break;
			
		case "Stopp":
			MainWindow.textPane.append("Stopp gedr�ckt\n");
			break;
			
		case "Abort":
			MainWindow.textPane.append("Abbrechen gedr�ckt\n");
			break;
			
		case "Forward":
			MainWindow.textPane.append("Vor gedr�ckt\n");
			break;
			
		case "Backwards":
			MainWindow.textPane.append("Zur�ck gedr�ckt\n");
			break;
			
		case "Left":
			MainWindow.textPane.append("Links gedr�ckt\n");
			break;
			
		case "Right":
			MainWindow.textPane.append("Rechts gedr�ckt\n");
			break;
			
		case "Scan":
			for(int i = 0; i < 500; i++)
			{
			Random random = new Random();
			int[] point = {random.nextInt(1000), random.nextInt(1000)};
			MainWindow.drawImage.newPoint(point);
			}
			MainWindow.textPane.append("500 Zuf�lligen Punkt mit der Grenze 1000 zugef�gt\n");
			break;

		default:
			MainWindow.textPane.append("Unbekannten Knopf gedr�ckt\n");
			break;
		}
	}

}