package interfaceGraphique;

import zoneGeographique.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreResultat extends JFrame implements ActionListener {

	private ZoneGeographique zoneGeo;
	private JPanel panel;
	private JButton newGameButton, exitButton;

	public FenetreResultat(ZoneGeographique zone) {
		zoneGeo = zone;

		setLayout(new BorderLayout());
		panel = new JPanel(null);

		JLabel gagnantLabel = new JLabel("Le joueur " + zoneGeo.get_num_gagnant() + " " + " ( "
				+ zoneGeo.get_nom_joueur_gagnant() + " ) a gagner le jeux");
		gagnantLabel.setBounds(40, 40, 400, 30);
		gagnantLabel.setFont(new Font("Aerial", Font.BOLD, 15));
		panel.add(gagnantLabel);

		set_icon_gagnant();

		add(panel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout());

		exitButton = new JButton("Quitter");
		exitButton.setFont(new Font("Aerial", Font.BOLD, 17));
		exitButton.setBackground(new Color(32, 74, 135));
		exitButton.setForeground(Color.WHITE);
		exitButton.addActionListener(this);
		bottomPanel.add(exitButton);

		newGameButton = new JButton("Nouvelle partie");
		newGameButton.setFont(new Font("Aerial", Font.BOLD, 17));
		newGameButton.setBackground(new Color(32, 74, 135));
		newGameButton.setForeground(Color.WHITE);
		newGameButton.addActionListener(this);
		bottomPanel.add(newGameButton);

		add(bottomPanel, BorderLayout.SOUTH);
		setSize(700, 450);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Jeu multi-joueurs pour la surveillance d une zone geographique");
		setVisible(true);

	}

	public void set_icon_gagnant() {
		Image img = null;
		if (zoneGeo.get_num_gagnant() == 1)
			img = new ImageIcon(getClass().getResource("/icones/robot.png")).getImage();
		else if (zoneGeo.get_num_gagnant() == 2)
			img = new ImageIcon(getClass().getResource("/icones/thieve.png")).getImage();
		img = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(img);
		JLabel label = new JLabel();
		label.setIcon(icon);
		label.setBounds(420, 40, 200, 200);

		panel.add(label);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton) {
			System.exit(0);
		}
		if (e.getSource() == newGameButton) {

			// MainClass premiereFenetre = new MainClass();

			try {
				Runtime.getRuntime().exec("java -jar jeux-zone-geographique.jar");
				System.exit(-1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

}
