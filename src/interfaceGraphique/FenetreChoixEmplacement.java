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

public class FenetreChoixEmplacement extends JFrame implements ActionListener {

	private ZoneGeographique zoneGeo;
	private static JLabel topLabel, bottomLabel;
	private JButton configAutoButton;
	private JButton confirmButton;
	private JButton configManuelleButton;

	private JPanel grid;
	private JButton[][] buttons;

	private boolean estNouvellePartie;

	public FenetreChoixEmplacement(ZoneGeographique zone, boolean estNouvellePartie) {

		zoneGeo = zone;
		this.estNouvellePartie = estNouvellePartie;

		setLayout(new BorderLayout());

		buttons = new JButton[zoneGeo.get_nb_lignes()][zoneGeo.get_nb_colonnes()];
		grid = new JPanel(new GridLayout(zoneGeo.get_nb_lignes(), zoneGeo.get_nb_colonnes()));
		for (int i = 0; i < zoneGeo.get_nb_lignes(); i++) {
			for (int j = 0; j < zoneGeo.get_nb_colonnes(); j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setBorderPainted(true);
				buttons[i][j].setBackground(Color.white);
				buttons[i][j].addActionListener(this);

				grid.add(buttons[i][j]);
			}
		}

		add(grid, BorderLayout.CENTER);

		JPanel topPanel = new JPanel();

		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		topLabel = new JLabel("Veuillez choisir une configuration du jeux");
		topLabel.setForeground(new Color(17, 133, 224));
		topLabel.setFont(new Font("Arial", Font.BOLD, 17));
		topPanel.add(topLabel);

		bottomLabel = new JLabel("				");
		bottomLabel.setForeground(Color.red.darker());
		bottomLabel.setFont(new Font("Arial", Font.BOLD, 13));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		bottomPanel.add(bottomLabel, gbc);

		configAutoButton = new JButton("Configuration automatique");
		configAutoButton.setFont(new Font("Aerial", Font.BOLD, 13));
		configAutoButton.setBackground(new Color(32, 74, 135));
		configAutoButton.setForeground(Color.WHITE);
		configAutoButton.addActionListener(this);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		bottomPanel.add(configAutoButton, gbc);

		confirmButton = new JButton("Confirmer");
		confirmButton.setFont(new Font("Aerial", Font.BOLD, 13));
		confirmButton.setBackground(new Color(32, 74, 135));
		confirmButton.setForeground(Color.WHITE);
		confirmButton.addActionListener(this);

		gbc.gridx = 2;
		gbc.gridy = 1;
		bottomPanel.add(confirmButton, gbc);

		configManuelleButton = new JButton("Configuration manuelle");
		configManuelleButton.setFont(new Font("Aerial", Font.BOLD, 13));
		configManuelleButton.setBackground(new Color(32, 74, 135));
		configManuelleButton.setForeground(Color.WHITE);
		configManuelleButton.addActionListener(this);

		gbc.gridx = 1;
		gbc.gridy = 1;
		bottomPanel.add(configManuelleButton, gbc);

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
		if (e.getSource() == configAutoButton) {
			bottomLabel.setText("		");
			zoneGeo.Choix_aleatoire(buttons);
		} else if (e.getSource() == confirmButton) {
			if (zoneGeo.get_etat() == "DebutConfiguration") {
				bottomLabel.setText("Choisir une configuration automatique ou manuelle");
			} else {
				if ((zoneGeo.get_nb_sorties_choisies() < zoneGeo.get_nb_sorties())) {
					bottomLabel.setText("Vous n'avez pas encore choisi les " + zoneGeo.get_nb_sorties() + " sorties");
				} else if (zoneGeo.get_nb_argent_choisi() < zoneGeo.get_nb_argent()) {
					bottomLabel
							.setText("Vous n'avez pas encore choisi les " + zoneGeo.get_nb_argent() + " sacs d'argent");
				} else if (zoneGeo.get_nb_obstacles_choisi() < zoneGeo.get_nb_obstacles()) {
					bottomLabel
							.setText("Vous n'avez pas encore choisi les " + zoneGeo.get_nb_obstacles() + " obstacles");
				} else {
					Jeux.passer_choix_robots(estNouvellePartie);
					dispose();
				}
			}
		} else if (e.getSource() == configManuelleButton) {
			setTopLabel("Choisir les emplaceoments des sorties (" + zoneGeo.get_nb_sorties_choisies() + "/"
					+ zoneGeo.get_nb_sorties() + ")");
			zoneGeo.set_etat("choixEmplacementsSorties");
		}

		for (int i = 0; i < zoneGeo.get_nb_lignes(); i++) {
			for (int j = 0; j < zoneGeo.get_nb_colonnes(); j++) {
				if (e.getSource() == buttons[i][j]) {
					if (zoneGeo.get_etat() == "choixEmplacementsSorties") {
						zoneGeo.choixEmplacementSorties(e, i, j, buttons);
					} else if (zoneGeo.get_etat() == "choixEmplacementsSacArgent") {
						if (zoneGeo.get_case(i, j) == 0) {
							if (zoneGeo.Bonne_Position_Sac_Argent(i, j)) {
								zoneGeo.choixEmplacementArgents(e, i, j, buttons);
							} else if ((!zoneGeo.Bonne_Position_Sac_Argent(i, j))
									&& (zoneGeo.get_nb_argent_choisi() < zoneGeo.get_nb_argent())) {
								bottomLabel.setText("Emplacement invalide, choisir une autre position");
							}
						} else {
							zoneGeo.choixEmplacementArgents(e, i, j, buttons);
						}
					} else if (zoneGeo.get_etat() == "choixEmplacementsobstacle") {
						if (zoneGeo.get_case(i, j) == 0) {
							if (zoneGeo.Bonne_Position_Obstacle(i, j)) {
								zoneGeo.choixEmplacementobstacles(e, i, j, buttons);
							} else if ((!zoneGeo.Bonne_Position_Obstacle(i, j))
									&& (zoneGeo.get_nb_obstacles_choisi() < zoneGeo.get_nb_obstacles())) {
								bottomLabel.setText(
										"Emplacement invalide, cet obstacle est directement devant une sortie ou bien proche d'une source d'argent");
							}
						} else {
							zoneGeo.choixEmplacementobstacles(e, i, j, buttons);
						}
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