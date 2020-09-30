package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import zoneGeographique.Jeu;

public class FenetreLancement extends JFrame implements ActionListener {

	private JTextArea infoJeu; // presentation du jeu et des regles
	private JButton partieRapide, partieConfig;
	private JPanel bottomPanel;

	private boolean garderJoueurs;
	private String nomJ1, nomJ2;

	public FenetreLancement(boolean garderJoueurs) {

		/*
		 * fenetre contenant une presentation du jeu et de ses regles et permettant de
		 * lancer une partie rapide ou bien configurer une partie
		 */

		nomJ1 = "";
		nomJ2 = "";
		this.garderJoueurs = garderJoueurs;
		setLayout(new BorderLayout());

		infoJeu = new JTextArea("\n\nLe jeu necessite deux joueurs.\n"
				+ "Le premier contrôle les robots dans le but de protéger les sources d'argents et emprisonner les intrus.\n"
				+ "Les intrus sont contrôlés par le deuxième joueur essayant de voler les sources et s'enfuir à travers les sorties.\n"
				+ "Le mouvement des caractères dépend des obstacles placés dans la zone.\n" + "\n"
				+ " - Un intrus ne peut s'échapper que s'il a réssi à attraper deux sources d'argents.");
		infoJeu.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		infoJeu.setLineWrap(true);
		infoJeu.setMargin(new Insets(20, 20, 20, 20));
		infoJeu.setWrapStyleWord(true);
		infoJeu.setEditable(false);

		add(infoJeu, BorderLayout.CENTER);

		partieRapide = new JButton("Partie rapide");
		partieRapide.setFont(new Font("Aerial", Font.BOLD, 13));
		partieRapide.setBackground(new Color(32, 74, 135));
		partieRapide.setForeground(Color.WHITE);
		partieRapide.addActionListener(this);

		partieConfig = new JButton("Configurer une partie");
		partieConfig.setFont(new Font("Aerial", Font.BOLD, 13));
		partieConfig.setBackground(new Color(32, 74, 135));
		partieConfig.setForeground(Color.WHITE);
		partieConfig.addActionListener(this);

		bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.add(partieRapide);
		bottomPanel.add(partieConfig);
		add(bottomPanel, BorderLayout.SOUTH);

		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Jeu multi-joueurs pour la surveillance d une zone geographique");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == partieConfig) {
			// configurer une partie
			this.setVisible(false);
			Jeu.partie_config(garderJoueurs);
		}
		if (e.getSource() == partieRapide) {
			dispose();
			Jeu.partie_rapide(garderJoueurs);
		}
	}

	public void setNomJ1(String s) {
		nomJ1 = s;
	}

	public void setNomJ2(String s) {
		nomJ2 = s;
	}

	public String getNomJ1() {
		return nomJ1;
	}

	public String getNomJ2() {
		return nomJ2;
	}

}
