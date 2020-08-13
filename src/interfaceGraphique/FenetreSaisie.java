package interfaceGraphique;

import zoneGeographique.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FenetreSaisie extends JFrame implements ActionListener {

	private int n, m, nbSorties, nbSacArgent, nbCh, nbobstacles;

	private JPanel saisie, saisieCenter;
	private JButton button;
	private JTextField ligneText, colonneText, nbcText, nbSortieText, nbSacArgentText, nbobstaclesText, nomJText;
	private JLabel errorLabel;

	public FenetreSaisie() {

		saisie = new JPanel(new BorderLayout());

		JPanel infoPanel = new JPanel();
		JLabel infoLabel = new JLabel("Saisir les informations necessaire pour commencer le jeux");
		infoLabel.setForeground(Color.blue.darker().darker());
		infoLabel.setFont(new Font("Arial", Font.BOLD, 17));
		infoPanel.add(infoLabel);

		saisie.add(infoPanel, BorderLayout.NORTH);

		JLabel tailleGrilleLabel = new JLabel("Saisir la taille de la grille :");
		tailleGrilleLabel.setBounds(40, 20, 200, 25);
		tailleGrilleLabel.setFont(new Font("Arial", Font.BOLD, 14));

		JLabel ligneLabel = new JLabel("Nombre de lignes de la zone geographique   (12-20)");
		ligneLabel.setBounds(40, 50, 450, 25);
		ligneLabel.setFont(new Font("Arial", Font.BOLD, 14));

		
		JLabel colonneLabel = new JLabel("Nombre de colonnes de la zone geographique   (12-30)");
		colonneLabel.setBounds(40, 80, 420, 25);
		colonneLabel.setFont(new Font("Arial", Font.BOLD, 14));


		ligneText = new JTextField(20);
		ligneText.setBounds(460, 50, 100, 25);
		colonneText = new JTextField(20);
		colonneText.setBounds(460, 80, 100, 25);

		JLabel nbcLabel = new JLabel("Saisir le nombre de robots / intrus   (1-4)"); // nbre de robots/intus
		nbcLabel.setBounds(40, 140, 450, 25);
		nbcLabel.setFont(new Font("Arial", Font.BOLD, 14));

		
		nbcText = new JTextField(20);
		nbcText.setBounds(460, 140, 100, 25);

		JLabel nbSortieLabel = new JLabel("Saisir le nombre de sorties de la zone (1-5)");
		nbSortieLabel.setBounds(40, 170, 450, 25);
		nbSortieLabel.setFont(new Font("Arial", Font.BOLD, 14));


		nbSortieText = new JTextField(20);
		nbSortieText.setBounds(460, 170, 100, 25);

		JLabel nbSacArgentLabel = new JLabel("Saisir le nombre de sacs d'argent");
		nbSacArgentLabel.setBounds(40, 200, 450, 25);
		nbSacArgentLabel.setFont(new Font("Arial", Font.BOLD, 14));


		nbSacArgentText = new JTextField(20);
		nbSacArgentText.setBounds(460, 200, 100, 25);

		JLabel nbobstaclesLabel = new JLabel("Saisir le nombre de obstacles de la zone (1-5)");
		nbobstaclesLabel.setBounds(40, 240, 450, 25);
		nbobstaclesLabel.setFont(new Font("Arial", Font.BOLD, 14));


		nbobstaclesText = new JTextField(20);
		nbobstaclesText.setBounds(460, 240, 100, 25);

		errorLabel = new JLabel("");
		errorLabel.setBounds(40, 280, 750, 25);
		errorLabel.setForeground(Color.red.darker());	
		errorLabel.setFont(new Font("Arial", Font.BOLD, 12));


		button = new JButton("Suivant");
		button.setFont(new Font("Aerial", Font.BOLD, 17));
		button.setBackground(new Color(32, 74, 135));
		button.setForeground(Color.WHITE);
		button.setBounds(50, 330, 150, 30);
		button.addActionListener(this);

		saisieCenter = new JPanel(null);
		saisieCenter.add(tailleGrilleLabel);
		saisieCenter.add(ligneLabel);
		saisieCenter.add(ligneText);
		saisieCenter.add(colonneLabel);
		saisieCenter.add(colonneText);

		saisieCenter.add(nbcLabel);
		saisieCenter.add(nbcText);

		saisieCenter.add(nbSortieLabel);
		saisieCenter.add(nbSortieText);
		saisieCenter.add(nbSacArgentLabel);
		saisieCenter.add(nbSacArgentText);

		saisieCenter.add(nbobstaclesLabel);
		saisieCenter.add(nbobstaclesText);

		saisieCenter.add(errorLabel);
		saisieCenter.add(button);

		saisie.add(saisieCenter, BorderLayout.CENTER);

		add(saisie);

		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Jeu multi-joueurs pour la surveillance d une zone geographique");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			String nbLignesString = ligneText.getText();
			String nbColonnesString = colonneText.getText();
			String nbCharString = nbcText.getText();
			String nbSortieString = nbSortieText.getText();
			String nbSacString = nbSacArgentText.getText();
			String nbobstaclesString = nbobstaclesText.getText();

			try {
				// peut generer une exception
				n = Integer.parseInt(nbLignesString);
				m = Integer.parseInt(nbColonnesString);
				nbCh = Integer.parseInt(nbCharString);
				nbSorties = Integer.parseInt(nbSortieString);
				nbSacArgent = Integer.parseInt(nbSacString);
				nbobstacles = Integer.parseInt(nbobstaclesString);

				// 10 <= n,m <= 30 ; 0 < nbch <=4 ; 0 < nbSorties < 6 ;
				if ((n < 12) || (n > 20) || (m < 12) || (m > 30) || (nbCh <= 0) || (nbCh > 4) || (nbSorties < 1)
						|| (nbSorties > 5) || (nbobstacles < 1) || (nbobstacles > 5)) {
					errorLabel.setText("Erreur lors de la saisie, réessayez!");
				} else if ((nbCh == 1) && (nbSacArgent != 3) && (nbSacArgent != 2)) {
					errorLabel.setText(
							"Erreur lors de la saisie,si le nombre de caractere egale 1 il faut que il faut que les sacs d'argents entre [2-3]");
				} else if ((nbCh == 2) && (nbSacArgent < (2 * nbCh)) || (nbSacArgent > (3 * nbCh))) {
					errorLabel.setText(
							"Si le nombre des caractéres egale 2 donc il faut que les sacs d'argents entre [4-6]");
				} else if ((nbCh == 3) && (nbSacArgent < (2 * nbCh)) || (nbSacArgent > (3 * nbCh))) {
					errorLabel.setText(
							"Si le nombre des caractéres egale 3 donc il faut que les sacs d'argents entre [6-9]");
				} else if ((nbCh == 4) && (nbSacArgent < (2 * nbCh)) || (nbSacArgent > (3 * nbCh))) {
					errorLabel.setText(
							"Si le nombre des caractéres egale 4 donc il faut que les sacs d'argents entre [8-12]");
				} else {
					errorLabel.setForeground(Color.green);
					errorLabel.setText("Saisie reessite");
					// setVisible(false);
					FenetreSaisieNomJoueur fenetreSaisieJoueur1 = new FenetreSaisieNomJoueur(
							new ZoneGeographique(n, m, nbSorties, nbSacArgent, nbCh, nbobstacles), true);
					dispose();
				}
			} catch (NumberFormatException x) {
				errorLabel.setText("Erreur lors de la saisie, reessayez!");
			}
		}

	}

}
