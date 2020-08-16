package interfaceGraphique;

import java.awt.Color;
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
import javax.swing.JTextField;

import zoneGeographique.Jeux;
import zoneGeographique.ZoneGeographique;

public class FenetreSaisieNomJoueur extends JFrame implements ActionListener {

	private JPanel saisieNomJ;
	private JTextField nomJText;
	private JLabel errorLabel;
	private JButton button;
	private ZoneGeographique zoneGeo;
	private String nomJ;
	private JButton[][] buttons = null;

	private boolean Joueur1; // permet de tester si la saisie est
								// pour le premier ou deuxieme joueur

	public FenetreSaisieNomJoueur(ZoneGeographique zone, boolean Joueur1, JButton[][] buttons) {

		this.Joueur1 = Joueur1;

		zoneGeo = zone;
		this.buttons = buttons;

		saisieNomJ = new JPanel(null);
		JLabel joueurLabel = new JLabel("Joueur 2 : saisir votre nom :");
		if (Joueur1) {
			joueurLabel.setText("Joueur 1 : saisir votre nom :");
		}
		joueurLabel.setFont(new Font("Arial", Font.BOLD, 14));

		joueurLabel.setBounds(40, 40, 250, 25);
		nomJText = new JTextField(40);
		nomJText.setBounds(40, 80, 200, 25);

		Image img = null;
		if (Joueur1)
			img = new ImageIcon(getClass().getResource("/icones/robot.png")).getImage();
		else
			img = new ImageIcon(getClass().getResource("/icones/thieve.png")).getImage();
		img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		Icon icon = new ImageIcon(img);
		JLabel label = new JLabel();
		label.setIcon(icon);
		label.setBounds(360, 40, 100, 100);

		saisieNomJ.add(label);

		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.red.darker());
		errorLabel.setBounds(40, 120, 500, 25);

		button = new JButton("Suivant");
		button.setFont(new Font("Aerial", Font.BOLD, 15));
		button.setBackground(new Color(32, 74, 135));
		button.setForeground(Color.WHITE);
		button.setBounds(330, 160, 150, 30);
		button.addActionListener(this);

		saisieNomJ.add(joueurLabel);
		saisieNomJ.add(nomJText);
		saisieNomJ.add(button);
		saisieNomJ.add(errorLabel);

		add(saisieNomJ);

		setSize(550, 280);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Jeu multi-joueurs pour la surveillance d une zone geographique");
		setVisible(true);

	}

	public ZoneGeographique getZoneGeo() {
		return zoneGeo;
	}

	public JButton[][] getButtons() {
		return buttons;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		nomJ = nomJText.getText();
		if (nomJ.equals("")) {
			errorLabel.setText("Le nom ne peut pas etre vide, reessayez");
		} else if (nomJ.length() > 20) {
			errorLabel.setText("Le nom choisi est tres long, reessayez");

		} else {
			errorLabel.setForeground(Color.green.darker());
			errorLabel.setText("Saisie faite avec succés, cliquez suivant pour continuer");
			// fenetre choix emplacements intrus ...
			if (!Joueur1) {
				zoneGeo.setNomJoueur2(nomJ);
				Jeux.passer_choix_intrus(true);

			} else {
				zoneGeo.setNomJoueur1(nomJ);
				Jeux.passer_choix_emplacements(false);
			}
			dispose();
		}
	}
}