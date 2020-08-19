package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import zoneGeographique.Jeux;
import zoneGeographique.ZoneGeographique;

public class FenetreChoixEmplacementRobots extends JFrame implements ActionListener {
	private ZoneGeographique zoneGeo;
	private static JLabel topLabel, bottomLabel;
	private JButton button;
	private JButton[][] buttons;

	private boolean estNouvellePartie;

	public FenetreChoixEmplacementRobots(ZoneGeographique zone, JButton[][] buttons, boolean estNouvellePartie) {

		this.estNouvellePartie = estNouvellePartie;
		setLayout(new BorderLayout());
		zoneGeo = zone;
		zoneGeo.set_etat("choixEmplacementsrobots");

		this.buttons = buttons;

		JPanel grid = new JPanel(new GridLayout(zoneGeo.get_nb_lignes(), zoneGeo.get_nb_colonnes()));
		for (int i = 0; i < zoneGeo.get_nb_lignes(); i++) {
			for (int j = 0; j < zoneGeo.get_nb_colonnes(); j++) {
				buttons[i][j].addActionListener(this);
				grid.add(buttons[i][j]);
			}
		}

		add(grid, BorderLayout.CENTER);

		JPanel topPanel = new JPanel();

		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		topLabel = new JLabel("Choisir les emplacements des robots (" + zoneGeo.getnbRobotsChoisi() + "/"
				+ zoneGeo.getnbCharacteres() + ")");
		topLabel.setForeground(new Color(17, 133, 224));
		topLabel.setFont(new Font("Arial", Font.BOLD, 17));
		topPanel.add(topLabel);
		bottomLabel = new JLabel("		");
		bottomLabel.setForeground(Color.red.darker());
		bottomLabel.setFont(new Font("Arial", Font.BOLD, 13));

		button = new JButton("Confirmer");
		button.setFont(new Font("Aerial", Font.BOLD, 13));
		button.setBackground(new Color(32, 74, 135));
		button.setForeground(Color.WHITE);
		button.addActionListener(this);

		gbc.gridx = 0;
		gbc.gridy = 0;
		bottomPanel.add(bottomLabel, gbc);

		gbc.gridy = 1;
		bottomPanel.add(button, gbc);

		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);

		int hauteurMax = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height * 9
				/ 10;
		int largeurMax = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width * 9
				/ 10;
		int tailleCarreau = Math.min(hauteurMax / zoneGeo.get_nb_lignes(), largeurMax / zoneGeo.get_nb_colonnes());
		setSize(tailleCarreau * zoneGeo.get_nb_colonnes(), tailleCarreau * zoneGeo.get_nb_lignes() + tailleCarreau);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Jeu multi-joueurs pour la surveillance d une zone geographique");
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			if (zoneGeo.getnbRobotsChoisi() < zoneGeo.getnbCharacteres()) {
				bottomLabel.setText("Vous n'avez pas encore choisi les " + zoneGeo.getnbCharacteres() + " robots");
			} else {
				if (estNouvellePartie) {
					// lancer la fenetre de saisie du nom du deuxieme joueuer
					Jeux.passer_saisie_joueur2();
				} else {
					Jeux.passer_choix_intrus(estNouvellePartie);
				}
				dispose();
			}
		}

		for (int i = 0; i < zoneGeo.get_nb_lignes(); i++) {
			for (int j = 0; j < zoneGeo.get_nb_colonnes(); j++) {
				if ((e.getSource() == buttons[i][j]) && (zoneGeo.get_etat() == "choixEmplacementsrobots")

						&& ((zoneGeo.get_case(i, j) == 0) || (zoneGeo.get_case(i, j) == 1))) {
					if (zoneGeo.Bonne_Position_Robot(i, j)) {
						zoneGeo.choixEmplacementrobots(e, i, j, buttons);
					} else if ((!zoneGeo.Bonne_Position_Robot(i, j))
							&& (zoneGeo.getnbRobotsChoisi() < zoneGeo.getnbCharacteres())) {
						bottomLabel.setText(
								"La position de ce robot est au bord de la zone geographique ou bien proche d'une sortie");
					}
				}
			}
		}
	}

	public static void setTopLabel(String s) {
		topLabel.setText(s);
	}

	public static void setBottomLabel(String s) {
		bottomLabel.setText(s);
	}

	public ZoneGeographique getZoneGeo() {
		return zoneGeo;
	}

	public JButton[][] getButtons() {
		return buttons;
	}
}
