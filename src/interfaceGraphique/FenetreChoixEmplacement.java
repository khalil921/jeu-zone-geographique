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

public class FenetreChoixEmplacement extends JFrame implements ActionListener {

	private ZoneGeographique zoneGeo;
	private static JLabel topLabel, bottomLabel;
	private JButton configAutoButton;
	private JButton confirmButton;
	private JButton configManuelleButton;

	public FenetreChoixEmplacement(ZoneGeographique zone) {

		setLayout(new BorderLayout());

		zoneGeo = zone;
		add(zoneGeo, BorderLayout.CENTER);

		JPanel topPanel = new JPanel();

		JPanel bottomPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		topLabel = new JLabel(
				"Veuillez choisir une configuration du jeux");
		topLabel.setForeground(new Color(17,133,224));
		topLabel.setFont(new Font("Arial", Font.BOLD, 17));
		topPanel.add(topLabel);

		bottomLabel = new JLabel("				");
		bottomLabel.setForeground(Color.red.darker());
		bottomLabel.setFont(new Font("Arial", Font.BOLD, 15));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		bottomPanel.add(bottomLabel, gbc);

		configAutoButton = new JButton("Configuration automatique");
		configAutoButton.setFont(new Font("Aerial", Font.BOLD, 17));
		configAutoButton.setBackground(new Color(32, 74, 135));
		configAutoButton.setForeground(Color.WHITE);
		configAutoButton.addActionListener(this);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		bottomPanel.add(configAutoButton, gbc);

		confirmButton = new JButton("Confirmer");
		confirmButton.setFont(new Font("Aerial", Font.BOLD, 17));
		confirmButton.setBackground(new Color(32, 74, 135));
		confirmButton.setForeground(Color.WHITE);
		confirmButton.addActionListener(this);

		gbc.gridx = 2;
		gbc.gridy = 1;
		bottomPanel.add(confirmButton, gbc);

		configManuelleButton = new JButton("Configuration manuelle");
		configManuelleButton.setFont(new Font("Aerial", Font.BOLD, 17));
		configManuelleButton.setBackground(new Color(32, 74, 135));
		configManuelleButton.setForeground(Color.WHITE);
		configManuelleButton.addActionListener(this);

		gbc.gridx = 1;
		gbc.gridy = 1;
		bottomPanel.add(configManuelleButton, gbc);

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
		if (e.getSource() == configAutoButton) {
			bottomLabel.setText("		");
			zoneGeo.Choix_aleatoire();
		} else if (e.getSource() == confirmButton) {
			if (zoneGeo.get_etat() == "DebutConfiguration") {
				bottomLabel.setText("Choisir une configuration automatique ou manuelle");
			} else {
				if ((zoneGeo.get_nb_sorties_choisies() < zoneGeo.get_nb_sorties())) {
					bottomLabel.setText(
							"Vous n'avez pas encore choisi les " + zoneGeo.get_nb_sorties() + " sorties");
				} else if (zoneGeo.get_nb_argent_choisi() < zoneGeo.get_nb_argent()) {
					bottomLabel.setText(
							"Vous n'avez pas encore choisi les " + zoneGeo.get_nb_argent() + " sacs d'argent");
				} else if (zoneGeo.get_nb_obstacles_choisi() < zoneGeo.get_nb_obstacles()) {
					bottomLabel.setText(
							"Vous n'avez pas encore choisi les " + zoneGeo.get_nb_obstacles() + " obstacles");
				} else {
					FenetreChoixEmplacementRobots fenetreRobots = new FenetreChoixEmplacementRobots(zoneGeo);
					dispose();
				}
			}
		} else if (e.getSource() == configManuelleButton) {
			setTopLabel("Choisir les emplaceoments des sorties (" + zoneGeo.get_nb_sorties_choisies() + "/"
					+ zoneGeo.get_nb_sorties() + ")");
			zoneGeo.set_etat("choixEmplacementsSorties");
		}
	}

	public static void setTopLabel(String s) {
		topLabel.setText(s);
	}

	public static void setBottomLabel(String s) {
		bottomLabel.setText(s);
	}

}