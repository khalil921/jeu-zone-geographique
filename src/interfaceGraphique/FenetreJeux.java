package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import zoneGeographique.Jeux;
import zoneGeographique.ZoneGeographique;

public class FenetreJeux extends JFrame implements ActionListener {

	private static ZoneGeographique zoneGeo;
	private JLabel bottomLabel;
	private static JLabel topLabel;

	private JButton[][] buttons;

	private long timerStart;

	public FenetreJeux(ZoneGeographique zone, JButton[][] buttons) {

		setLayout(new BorderLayout());

		zoneGeo = zone;

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

		topLabel = new JLabel("Tour Joueur 2 : " + zoneGeo.getNomJoueur2());
		topLabel.setForeground(new Color(167, 32, 147));
		topLabel.setFont(new Font("Arial", Font.BOLD, 17));

		topPanel.add(topLabel);

		JPanel bottomPanel = new JPanel(new FlowLayout());

		bottomLabel = new JLabel("");
		bottomLabel.setForeground(Color.red.darker());
		bottomPanel.add(bottomLabel);
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

	public static void setTopLabel(String s) {
		if (s.equals("tourJ1")) {
			topLabel.setText("Tour Joueur 1 : " + zoneGeo.getNomJoueur1());
			topLabel.setForeground(new Color(17, 133, 224));
		} else if (s.equals("tourJ2")) {
			topLabel.setText("Tour Joueur 2 : " + zoneGeo.getNomJoueur2());
			topLabel.setForeground(new Color(167, 32, 147));
		} else if (s.equals("fin")) {
			topLabel.setText("Fin du jeux ! ");
			topLabel.setForeground(Color.black);
		}
	}

	public ZoneGeographique getZoneGeo() {
		return zoneGeo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < zoneGeo.get_nb_lignes(); i++) {
			for (int j = 0; j < zoneGeo.get_nb_colonnes(); j++) {

				// lancement du jeux ...
				// tour Joueur 2 (intrus)
				if (e.getSource() == buttons[i][j]) {
					if ((zoneGeo.get_case(i, j) == 2) && ((zoneGeo.get_etat().equals("tourJoueur2"))
							|| (zoneGeo.get_etat().equals("tourJoueur2Mouvement")))) {
						if (zoneGeo.getIntrus(i, j).is_selected()) {
							zoneGeo.deselect_intrus(i, j, buttons);
							;

						} else {
							zoneGeo.montrer_mouvemeent_possibles(zoneGeo.getIntrus(i, j),
									zoneGeo.get_mouvements_possibles(zoneGeo.getIntrus(i, j)), buttons);
							zoneGeo.set_intrus_selected(i, j);
						}
					}
					if ((zoneGeo.get_etat().equals("tourJoueur2Mouvement"))
							&& (zoneGeo.est_un_prochain_mouvement(i, j))) {
						zoneGeo.move_intrus(zoneGeo.get_selected_intrus(), i, j, buttons);
						if (!zoneGeo.get_nom_joueur_gagnant().equals("")) {
							dispose();
							Jeux.afficher_resultat();
						}
					}

					// tour Joueur 1 (robots)
					if ((zoneGeo.get_case(i, j) == 1) && ((zoneGeo.get_etat().equals("tourJoueur1")
							|| (zoneGeo.get_etat().equals("tourJoueur1Mouvement"))))) {
						if (zoneGeo.getRobot(i, j).is_selected()) {
							zoneGeo.deselect_robot(i, j, buttons);

						} else {
							zoneGeo.montrer_mouvemeent_possibles(zoneGeo.getRobot(i, j),
									zoneGeo.get_mouvements_possibles(zoneGeo.getRobot(i, j)), buttons);
							zoneGeo.set_robot_selected(i, j);
						}
					}
					if ((zoneGeo.get_etat().equals("tourJoueur1Mouvement"))
							&& (zoneGeo.est_un_prochain_mouvement(i, j))) {
						zoneGeo.move_robot(zoneGeo.get_selected_robot(), i, j, buttons);
						if (!zoneGeo.get_nom_joueur_gagnant().equals("")) {
							dispose();
							Jeux.afficher_resultat();
						}
					}
				}
			}
		}

	}
}
