package interfaceGraphique;

import zoneGeographique.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreJeux extends JFrame {

	private static ZoneGeographique zoneGeo;
	private JLabel bottomLabel;
	private static JLabel topLabel;

	public FenetreJeux(ZoneGeographique zone) {

		setLayout(new BorderLayout());

		zoneGeo = zone;
		add(zoneGeo, BorderLayout.CENTER);

		JPanel topPanel = new JPanel();

		topLabel = new JLabel("Tour Joueur 2 : "+ zoneGeo.getNomJoueur2());
		topLabel.setForeground(new Color(167,32,147));
		topLabel.setFont(new Font("Arial", Font.BOLD, 17));

		topPanel.add(topLabel);

		JPanel bottomPanel = new JPanel(new FlowLayout());

		bottomLabel = new JLabel("");
		bottomLabel.setForeground(Color.red.darker());
		bottomPanel.add(bottomLabel);
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

	public static void setTopLabel(String s) {
		if (s.equals("tourJ1")) {
			topLabel.setText("Tour Joueur 1 : "+ zoneGeo.getNomJoueur1());
			topLabel.setForeground(new Color(17,133,224));
		}
		else if (s.equals("tourJ2")) {
			topLabel.setText("Tour Joueur 2 : "+ zoneGeo.getNomJoueur2());
			topLabel.setForeground(new Color(167,32,147));
		}
		else if (s.equals("fin")) {
			topLabel.setText("Fin du jeux ! ");
			topLabel.setForeground(Color.black);
		}
	}
}
