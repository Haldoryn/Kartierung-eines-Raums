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
			MainWindow.textPane.append("Verbinden gedrückt\n");
			break;
			
		case "Start":
			MainWindow.textPane.append("Start gedrückt\n");
			break;
			
		case "Stopp":
			MainWindow.textPane.append("Stopp gedrückt\n");
			break;
			
		case "Abort":
			MainWindow.textPane.append("Abbrechen gedrückt\n");
			break;
			
		case "Forward":
			MainWindow.textPane.append("Vor gedrückt\n");
			break;
			
		case "Backwards":
			MainWindow.textPane.append("Zurück gedrückt\n");
			break;
			
		case "Left":
			MainWindow.textPane.append("Links gedrückt\n");
			break;
			
		case "Right":
			MainWindow.textPane.append("Rechts gedrückt\n");
			break;
			
		case "Scan":
			for(int i = 0; i < 500; i++)
			{
			Random random = new Random();
			int[] point = {random.nextInt(1000), random.nextInt(1000)};
			MainWindow.drawImage.newPoint(point);
			}
			MainWindow.textPane.append("500 Zufälligen Punkt mit der Grenze 1000 zugefügt\n");
			break;

		default:
			MainWindow.textPane.append("Unbekannten Knopf gedrückt\n");
			break;
		}
	}

}