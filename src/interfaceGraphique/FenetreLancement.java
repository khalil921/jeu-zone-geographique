package interfaceGraphique;

import zoneGeographique.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FenetreLancement extends JFrame implements ActionListener {

	private JTextArea infoJeux; // pr�sentation du jeux et des regles
	private JButton partieRapide, partieConfig;
	private JPanel bottomPanel;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		FenetreLancement premiereFenetre = new FenetreLancement();
	}

	public FenetreLancement() {

		/*
		 * fenetre contenant une presentation du jeux et de ses regles et permettant de
		 * lancer une partie rapide ou bien configurer une partie
		 */
		setLayout(new BorderLayout());

		infoJeux = new JTextArea("Presentation du jeux  ... add it later");
		infoJeux.setLineWrap(true);
		infoJeux.setEditable(false);
		scrollPane = new JScrollPane(infoJeux);

		add(scrollPane, BorderLayout.CENTER);

		partieRapide = new JButton("Partie rapide");
		partieRapide.setFont(new Font("Aerial", Font.BOLD, 17));
		partieRapide.setBackground(new Color(32, 74, 135));
		partieRapide.setForeground(Color.WHITE);
		partieRapide.addActionListener(this);

		partieConfig = new JButton("Configurer une partie");
		partieConfig.setFont(new Font("Aerial", Font.BOLD, 17));
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
			new FenetreSaisie();
		}
		if (e.getSource() == partieRapide) {
			// this.setVisible(false);
			FenetreSaisieNomJoueur fenetreSaisieNomJoueur1 = new FenetreSaisieNomJoueur(
					new ZoneGeographique(13, 13, 3, 7, 3, 4), true);
			dispose();
		}
	}
}
