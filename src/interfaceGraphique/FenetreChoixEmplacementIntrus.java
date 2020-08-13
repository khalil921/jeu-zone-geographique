package interfaceGraphique;

import zoneGeographique.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreChoixEmplacementIntrus extends JFrame implements ActionListener {
	private ZoneGeographique zoneGeo;
	private static JLabel topLabel, bottomLabel;
	private JButton button;

	public FenetreChoixEmplacementIntrus(ZoneGeographique zone) {
		setLayout(new BorderLayout());
		zoneGeo = zone;
		zoneGeo.set_etat("choixEmplacementsintrus");
		add(zoneGeo, BorderLayout.CENTER);
		JPanel topPanel = new JPanel();

		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;

		topLabel = new JLabel("Choisir les emplacements des intrus (" + zoneGeo.getNbintruschoisi() + "/"
				+ zoneGeo.getnbCharacteres() + ")");
		topLabel.setForeground(new Color(167,32,147));
		topLabel.setFont(new Font("Arial", Font.BOLD, 17));
		topPanel.add(topLabel);
		bottomLabel = new JLabel("		");
		bottomLabel.setForeground(Color.red.darker());
		bottomLabel.setFont(new Font("Arial", Font.BOLD, 15));

		button = new JButton("Commencer le jeu");
		button.setFont(new Font("Aerial", Font.BOLD, 17));
		button.setBackground(new Color(32, 74, 135));
		button.setForeground(Color.WHITE);
		button.addActionListener(this);

		bottomPanel.add(bottomLabel, gbc);

		gbc.gridy = 1;
		bottomPanel.add(button, gbc);

		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
		
		
		
		int hauteurMax = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height * 9/10;
		int largeurMax = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width * 9/10;
		int tailleCarreau = Math.min(hauteurMax / zoneGeo.get_nb_lignes(), largeurMax / zoneGeo.get_nb_colonnes() );
		setSize(tailleCarreau * zoneGeo.get_nb_colonnes(), tailleCarreau * zoneGeo.get_nb_lignes() + tailleCarreau);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Jeu multi-joueurs pour la surveillance d une zone geographique");
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (zoneGeo.getNbintruschoisi() < zoneGeo.getnbCharacteres()) {
			bottomLabel.setText("Vous n'avez pas encore choisi les " + zoneGeo.getnbCharacteres() + " intrus");
		} else {
			// commencer le jeux ...
			zoneGeo.enregistrer_choix(); // creation des objets de type Robot et Intrus
			zoneGeo.set_etat("tourJoueur2");
			// setVisible(false);
			FenetreJeux fenetreJeux = new FenetreJeux(zoneGeo);
			dispose();
		}
	}

	public static void setTopLabel(String s) {
		topLabel.setText(s);
	}

	public static void setBottomLabel(String s) {
		bottomLabel.setText(s);
	}
}