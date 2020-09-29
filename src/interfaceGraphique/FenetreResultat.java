package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import zoneGeographique.Jeux;
import zoneGeographique.ZoneGeographique;

public class FenetreResultat extends JFrame implements ActionListener {

	private ZoneGeographique zoneGeo;
	private JPanel panel;
	private JButton nouvellePartieButton, nouvellePartieJoueursButton, relancerPartieButton, exitButton;

	public FenetreResultat(ZoneGeographique zone) {

		zoneGeo = zone;

		long timeEnd = System.currentTimeMillis();
		long timeDelta = timeEnd - zoneGeo.getTimeDebutJeux();
		double duree = timeDelta / 1000.0;

		setLayout(new BorderLayout());
		panel = new JPanel(null);

		JLabel gagnantLabel = new JLabel("Le joueur " + zoneGeo.get_num_gagnant() + " " + " ( "
				+ zoneGeo.get_nom_joueur_gagnant() + " ) a gagner le jeux");
		gagnantLabel.setBounds(40, 40, 400, 30);
		gagnantLabel.setFont(new Font("Aerial", Font.BOLD, 15));
		panel.add(gagnantLabel);

		JLabel infoLabel = new JLabel("");
		infoLabel.setBounds(40, 70, 400, 30);
		infoLabel.setFont(new Font("Aerial", Font.BOLD, 15));
		panel.add(infoLabel);
		if (zoneGeo.get_num_gagnant() == 1) {
			infoLabel.setText("Vous avez attrappe tous les intrus");
		} else if (zoneGeo.get_num_gagnant() == 2) {
			infoLabel.setText("Vous avez vole " + zoneGeo.getNbArgentVole() + " sacs d argent");
		}
		JLabel dureeJeuxLabel = new JLabel("Duree du jeux : " + format_duree(duree));
		dureeJeuxLabel.setBounds(40, 100, 400, 30);
		dureeJeuxLabel.setFont(new Font("Aerial", Font.BOLD, 15));
		panel.add(dureeJeuxLabel);

		set_icon_gagnant();

		add(panel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout());

		nouvellePartieButton = new JButton("Changer Joueurs");
		nouvellePartieButton.setFont(new Font("Aerial", Font.BOLD, 13));
		nouvellePartieButton.setBackground(new Color(32, 74, 135));
		nouvellePartieButton.setForeground(Color.WHITE);
		nouvellePartieButton.addActionListener(this);
		bottomPanel.add(nouvellePartieButton);

		nouvellePartieJoueursButton = new JButton("Nouvelle Partie");
		nouvellePartieJoueursButton.setFont(new Font("Aerial", Font.BOLD, 13));
		nouvellePartieJoueursButton.setBackground(new Color(32, 74, 135));
		nouvellePartieJoueursButton.setForeground(Color.WHITE);
		nouvellePartieJoueursButton.addActionListener(this);
		bottomPanel.add(nouvellePartieJoueursButton);

		relancerPartieButton = new JButton("Relancer partie");
		relancerPartieButton.setFont(new Font("Aerial", Font.BOLD, 13));
		relancerPartieButton.setBackground(new Color(32, 74, 135));
		relancerPartieButton.setForeground(Color.WHITE);
		relancerPartieButton.addActionListener(this);
		bottomPanel.add(relancerPartieButton);

		exitButton = new JButton("Quitter");
		exitButton.setFont(new Font("Aerial", Font.BOLD, 13));
		exitButton.setBackground(new Color(32, 74, 135));
		exitButton.setForeground(Color.WHITE);
		exitButton.addActionListener(this);
		bottomPanel.add(exitButton);

		add(bottomPanel, BorderLayout.SOUTH);
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Jeu multi-joueurs pour la surveillance d une zone geographique");
		setVisible(true);

		/*
		 * // prints used memory in console .... remove later System.out.println("MB :"
		 * + (double) (Runtime.getRuntime().totalMemory() -
		 * Runtime.getRuntime().freeMemory()) / (1024 * 1024));
		 */

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

	public ZoneGeographique getZoneGeo() {
		return zoneGeo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton) {
			System.exit(0);
		}
		if (e.getSource() == relancerPartieButton) {
			dispose();
			Jeux.relancer_partie();
		}
		if (e.getSource() == nouvellePartieButton) {
			dispose();
			Jeux.nouvelle_partie(false);
		}
		if (e.getSource() == nouvellePartieJoueursButton) {
			dispose();
			Jeux.nouvelle_partie(true);
		}
	}

	public String format_duree(double duree) {
		String s;
		if (duree < 60) {
			s = String.valueOf((int)duree) + " secondes";
		} else {
			int min = (int) (duree / 60);
			int sec =  (int) duree - 60 * min;
			s = String.valueOf(min) + " minutes et " + String.valueOf(sec) + " secondes";
		}
		return s;
	}

}
