package zoneGeographique;

import position.*;
import characteres.*;
import interfaceGraphique.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ZoneGeographique extends JPanel implements ActionListener {

	private int n, m, nbSorties, nbSacArgent, nbCharacteres, nbObstacles; // n = nombre de lignes , m = nombre de
																			// colonnes
	private int nbSortiesChoisies = 0;
	private int nbSacsArgentChoisi = 0;
	private int nbObstaclesChoisi = 0;
	private int nbRobotsChoisi = 0;
	private int nbintruschoisi = 0;

	private String nomJoueur1, nomJoueur2;
	private int joueurGagnant;

	private int[][] cases;
	private JPanel grid;
	/*
	 * 0 - vide, 1 - robot, 2 - intrus, 3 - source d'argent, 4 - obstacle, 5 -
	 * sortie
	 */

	/*
	 * -3 argent volee -2 intrus attrappé
	 */

	private JButton[][] buttons;

	private String etatJeux = "DebutConfiguration";

	private Robot[] robots;
	private Intrus[] intrus;

	private Position[] prochainsMouvements;

	private int nbArgentVole = 0; // nombre de sacs d argents volé

	private int nbIntrusAttrappes = 0;
	private int nbIntrusEchappes = 0;

	public ZoneGeographique(int nbL, int nbC, int nbSorties, int nbSacArgent, int nbChar, int nbObstacles) {

		setLayout(new BorderLayout());

		n = nbL;
		m = nbC;
		this.nbSorties = nbSorties;
		this.nbSacArgent = nbSacArgent;
		nbCharacteres = nbChar;
		this.nbObstacles = nbObstacles;
		nomJoueur1 = "";
		nomJoueur2 = "";

		cases = new int[n][m];
		buttons = new JButton[n][m];
		grid = new JPanel(new GridLayout(n, m));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setBorderPainted(true);
				buttons[i][j].setBackground(Color.white);
				buttons[i][j].addActionListener(this);

				grid.add(buttons[i][j]);
			}
		}
		add(grid, BorderLayout.CENTER);
	}

	public void set_case(int i, int j, String val) {
		if (val.equals("sortie")) {
			cases[i][j] = 5;
			Image sortieImg = new ImageIcon(getClass().getResource("/icones/exit.png")).getImage();
			int min = Math.min(buttons[i][j].getWidth(), buttons[i][j].getHeight());
			sortieImg = sortieImg.getScaledInstance(min, min, Image.SCALE_SMOOTH);
			Icon sortieIcon = new ImageIcon(sortieImg);
			buttons[i][j].setIcon(sortieIcon);
			validate();
		}
		if (val.equals("robot")) {
			cases[i][j] = 1;
			Image robotImg = new ImageIcon(getClass().getResource("/icones/robot.png")).getImage();
			robotImg = robotImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon robotIcon = new ImageIcon(robotImg);
			buttons[i][j].setIcon(robotIcon);
			validate();
		}
		if (val.equals("argent")) {
			cases[i][j] = 3;
			Image ArgentImg = new ImageIcon(getClass().getResource("/icones/money.jpeg")).getImage();
			ArgentImg = ArgentImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon ArgentIcon = new ImageIcon(ArgentImg);
			buttons[i][j].setIcon(ArgentIcon);
			validate();
		}
		if (val.equals("argentVolee")) {
			cases[i][j] = -3;
			Image ArgentImg = new ImageIcon(getClass().getResource("/icones/stolenMoney.png")).getImage();
			ArgentImg = ArgentImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon ArgentIcon = new ImageIcon(ArgentImg);
			buttons[i][j].setIcon(ArgentIcon);
			validate();
		}
		if (val.equals("vide")) {
			cases[i][j] = 0;
			buttons[i][j].setIcon(null);
			validate();
			buttons[i][j].setBackground(Color.white);
		}
		if (val.equals("obstacle")) {
			cases[i][j] = 4;
			Image obstacleImg = new ImageIcon(getClass().getResource("/icones/wall.png")).getImage();
			obstacleImg = obstacleImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon obstacleIcon = new ImageIcon(obstacleImg);
			buttons[i][j].setIcon(obstacleIcon);
			validate();
		}
		if (val.equals("intrus")) {
			cases[i][j] = 2;
			Image intrusImg = new ImageIcon(getClass().getResource("/icones/thieve0.png")).getImage();
			intrusImg = intrusImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon intrusIcon = new ImageIcon(intrusImg);
			buttons[i][j].setIcon(intrusIcon);
			validate();
		}
		if (val.equals("intrus1")) {
			cases[i][j] = 2;
			Image intrusImg = new ImageIcon(getClass().getResource("/icones/thieve1.png")).getImage();
			intrusImg = intrusImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon intrusIcon = new ImageIcon(intrusImg);
			buttons[i][j].setIcon(intrusIcon);
			validate();
		}
		if (val.equals("intrus2")) {
			cases[i][j] = 2;
			Image intrusImg = new ImageIcon(getClass().getResource("/icones/thieve2.png")).getImage();
			intrusImg = intrusImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon intrusIcon = new ImageIcon(intrusImg);
			buttons[i][j].setIcon(intrusIcon);
			validate();
		}
		if (val.equals("intrusAttrappe")) {
			cases[i][j] = -2;
			Image intrusImg = new ImageIcon(getClass().getResource("/icones/caughtThieve.png")).getImage();
			intrusImg = intrusImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon intrusIcon = new ImageIcon(intrusImg);
			buttons[i][j].setIcon(intrusIcon);
			validate();
		}

		if (val == "highlightedJ1") {
			// buttons[i][j].setBackground(Color.blue.brighter().brighter().brighter());
			Image img = new ImageIcon(getClass().getResource("/icones/selectRobot.png")).getImage();
			img = img.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(), Image.SCALE_SMOOTH);
			Icon icon = new ImageIcon(img);
			buttons[i][j].setIcon(icon);
			validate();
		}
		if (val == "highlightedJ2") {
			Image img = new ImageIcon(getClass().getResource("/icones/selectIntrus.png")).getImage();
			img = img.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(), Image.SCALE_SMOOTH);
			Icon icon = new ImageIcon(img);
			buttons[i][j].setIcon(icon);
			validate();
		}
		if (val == "highlightedJ2Exit") {
			Image img = new ImageIcon(getClass().getResource("/icones/selectedExit.png")).getImage();
			img = img.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(), Image.SCALE_SMOOTH);
			Icon icon = new ImageIcon(img);
			buttons[i][j].setIcon(icon);
			validate();
		}
		if (val.equals("restore")) {
			buttons[i][j].setIcon(null);
			validate();
		}
	}

	public void choix_aleatoire_sorties() {
		int i, j;
		for (int k = 0; k < nbSorties; k++) {
			do {
				i = genererInt(n);
				j = genererInt(m);
			} while (!Bonne_Position_Sortie(i, j));
			set_case(i, j, "sortie");
		}
	}

	public boolean Bonne_Position_Sortie(int x, int y) {
		boolean bool = true;
		if (cases[x][y] == 0) {
			if ((x != 0) && (y != 0) && (x != n - 1) && (y != m - 1)) {
				bool = false;
			}
		} else {
			bool = false;
		}
		return bool;
	}

	public void choix_aleatoire_sacs_argents() {
		int i, j;
		for (int k = 0; k < nbSacArgent; k++) {
			do {
				i = genererInt(n);
				j = genererInt(m);
			} while (!Bonne_Position_Sac_Argent(i, j));
			set_case(i, j, "argent");
		}
	}

	public void choix_aleatoire_obstacles() {
		int i, j;
		for (int k = 0; k < nbObstacles; k++) {
			do {
				i = genererInt(n);
				j = genererInt(m);
			} while (!Bonne_Position_Obstacle(i, j));
			set_case(i, j, "obstacle");
		}
	}

	public boolean Bonne_Position_Obstacle(int x, int y) {
		boolean bool = true;
		if (cases[x][y] != 0) {
			bool = false;
		} else {
			if ((((x == 1) || (x == n - 2)) && ((cases[x - 1][y] == 5) || (cases[x + 1][y] == 5)))
					|| (((y == 1) || (y == m - 2)) && ((cases[x][y - 1] == 5) || cases[x][y + 1] == 5))) {
				if (((x == 0) && (y == 1) && (cases[1][0] == 0)) || ((x == 1) && (y == 0) && (cases[0][1] == 0))) {
					bool = true;
				} else if (((x == n - 2) && (y == 0) && (cases[n - 1][1] == 0))
						|| ((x == n - 1) && (y == 1) && (cases[n - 2][0] == 0))) {
					bool = true;
				} else if (((x == n - 1) && (y == m - 2) && (cases[n - 2][m - 1] == 0))
						|| ((x == n - 2) && (y == m - 1) && (cases[n - 1][m - 2] == 0))) {
					bool = true;
				} else if (((x == 1) && (y == m - 1) && (cases[0][m - 2] == 0))
						|| ((x == 0) && (y == m - 2) && (cases[1][m - 1] == 0))) {
					bool = true;
				} else {
					bool = false;
				}
			} else {
				for (int k = (x - 1); k < (x + 2); k++) {
					for (int f = (y - 1); f < (y + 2); f++) {
						if ((Position_existe(k, f)) && (cases[k][f] == 3)) {
							bool = false;
						}
					}
				}
			}
		}
		return bool;
	}

	public boolean Position_existe(int i, int j) {
		if ((i >= 0) && (i < n) && (j >= 0) && (j < m)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean Bonne_Position_Sac_Argent(int x, int y) {
		boolean bool = true;
		for (int k = (x - 1); k < (x + 2); k++) {
			for (int f = (y - 1); f < (y + 2); f++) {
				if ((Position_existe(k, f) && (cases[k][f] != 0))
						|| ((k == 0) || (f == 0) || (k == n - 1) || (f == m - 1))) {
					bool = false;
				}
			}
		}
		return bool;
	}

	public boolean Bonne_Position_Robot(int x, int y) {
		boolean bool = true;
		if ((cases[x][y] == 0) || (cases[x][y] == 2) || (cases[x][y] == 1)) {
			for (int k = (x - 1); k < (x + 2); k++) {
				for (int f = (y - 1); f < (y + 2); f++) {
					if ((Position_existe(k, f)
							&& (((cases[k][f] == 2) && (k != x) && (f != y) && (nbRobotsChoisi < nbCharacteres))
									|| (cases[k][f] == 5)))
							|| (((k == 0) || (f == 0) || (k == n - 1) || (f == m - 1))
									&& (nbRobotsChoisi < nbCharacteres))) {
						bool = false;
					}
				}
			}
		} else {
			bool = false;
		}
		return bool;
	}

	public boolean Bonne_Position_Intrus(int x, int y) {
		boolean bool = true;
		if (cases[x][y] == 0 || cases[x][y] == 2 || cases[x][y] == 1) {
			if ((x != 0) && (y != 0) && (x != n - 1) && (y != m - 1) && (cases[x][y] != 1)) {
				bool = false;
			}
		} else {
			bool = false;
		}
		return bool;
	}

	public int genererInt(int borneSup) {
		Random random = new Random();
		int nb;
		nb = random.nextInt(borneSup);
		return nb;
	}

	public void Choix_aleatoire() {/*
		if (etatJeux == "DebutConfiguration") {
			choix_aleatoire_sorties();
			choix_aleatoire_sacs_argents();
			choix_aleatoire_obstacles();
			FenetreChoixEmplacement.setTopLabel("Confirmer cette configuration ou choisir une autre");
			etatJeux = "";
			nbSortiesChoisies = nbSorties;
			nbSacsArgentChoisi = nbSacArgent;
			nbObstaclesChoisi = nbObstacles;
		} else {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					set_case(i, j, "vide");
				}
			}
			choix_aleatoire_sorties();
			choix_aleatoire_sacs_argents();
			choix_aleatoire_obstacles();
			FenetreChoixEmplacement.setTopLabel("Confirmer votre choix ou choisir une autre configuration");
			etatJeux = "";
			nbSortiesChoisies = nbSorties;
			nbSacsArgentChoisi = nbSacArgent;
			nbObstaclesChoisi = nbObstacles;
		}*/
		if (!etatJeux.equals("DebutConfiguration")) {
			for (int i = 0; i < n; i++) 
				for (int j = 0; j < m; j++) 
					set_case(i, j, "vide");
		}
		choix_aleatoire_sorties();
		choix_aleatoire_sacs_argents();
		choix_aleatoire_obstacles();
		FenetreChoixEmplacement.setTopLabel("Confirmer cette configuration ou choisir une autre");
		etatJeux = "";
		nbSortiesChoisies = nbSorties;
		nbSacsArgentChoisi = nbSacArgent;
		nbObstaclesChoisi = nbObstacles;
		
			
	}

	private void choixEmplacementintrus(ActionEvent e, int i, int j) {
		if ((nbintruschoisi < nbCharacteres) && (cases[i][j] == 0)) {
			set_case(i, j, "intrus");
			nbintruschoisi++;

			if (nbintruschoisi == nbCharacteres) {
				FenetreChoixEmplacementIntrus.setTopLabel("commencer le jeux ou bien modifier l emplacement des intrus");
				FenetreChoixEmplacementIntrus.setBottomLabel("		");
			} else {
				FenetreChoixEmplacementIntrus.setTopLabel(
						"Choisir les emplacements des intrus (" + nbintruschoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementIntrus.setBottomLabel("		");
			}
		} else if (cases[i][j] == 2) {
			set_case(i, j, "vide");
			if (nbintruschoisi == nbCharacteres) {
				nbintruschoisi--;
				FenetreChoixEmplacementIntrus.setTopLabel(
						"Choisir les emplacements des intrus (" + nbintruschoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementIntrus.setBottomLabel("		");
				etatJeux = "choixEmplacementsintrus";
			} else {
				nbintruschoisi--;
				FenetreChoixEmplacementIntrus.setTopLabel(
						"Choisir les emplacements des intrus (" + nbintruschoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementIntrus.setBottomLabel("		");
			}

		}
	}
	
	private void choixEmplacementrobots(ActionEvent e, int i, int j) {
		if ((nbRobotsChoisi < nbCharacteres) && (cases[i][j] == 0)) {
			set_case(i, j, "robot");
			nbRobotsChoisi++;

			if (nbRobotsChoisi == nbCharacteres) {
				FenetreChoixEmplacementRobots
						.setTopLabel("Confirmer votre choix ou bien modifier l emplacement des robots");
				FenetreChoixEmplacementRobots.setBottomLabel("		");
			} else {
				FenetreChoixEmplacementRobots.setTopLabel(
						"Choisir les emplacements des robots (" + nbRobotsChoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementRobots.setBottomLabel("		");
			}
		} else if (cases[i][j] == 1) {
			set_case(i, j, "vide");
			if (nbRobotsChoisi == nbCharacteres) {
				nbRobotsChoisi--;
				FenetreChoixEmplacementRobots.setTopLabel(
						"Choisir les emplacements des robots (" + nbRobotsChoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementRobots.setBottomLabel("		");
				etatJeux = "choixEmplacementsrobots";
			} else {
				nbRobotsChoisi--;
				FenetreChoixEmplacementRobots.setTopLabel(
						"Choisir les emplacements des robots (" + nbRobotsChoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementRobots.setBottomLabel("		");
			}

		}
	}

	public void choixEmplacementobstacles(ActionEvent e, int i, int j) {
		if ((nbObstaclesChoisi < nbObstacles) && (cases[i][j] == 0)) {
			set_case(i, j, "obstacle");
			nbObstaclesChoisi++;
			if (nbObstaclesChoisi == nbObstacles) {
				if (nbSortiesChoisies < nbSorties) {
					FenetreChoixEmplacement.setTopLabel(
							"Choisir les emplacements des sorties (" + nbSortiesChoisies + "/" + nbSorties + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeux = "choixEmplacementsSorties";
				} else if (nbSacsArgentChoisi < nbSacArgent) {
					FenetreChoixEmplacement.setTopLabel("Choisir les emplacements des sac d'argents ("
							+ nbSacsArgentChoisi + "/" + nbSacArgent + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeux = "choixEmplacementsSacArgent";
				} else {
					FenetreChoixEmplacement.setTopLabel("Confirmer votre choix");
					FenetreChoixEmplacement.setBottomLabel("		");
				}
			} else {
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des obstacles (" + nbObstaclesChoisi + "/" + nbObstacles + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
			}
		} else if (cases[i][j] == 4) {
			set_case(i, j, "vide");
			if (nbObstaclesChoisi == nbObstacles) {
				nbObstaclesChoisi--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des obstacles (" + nbObstaclesChoisi + "/" + nbObstacles + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
				etatJeux = "choixEmplacementsobstacle";
			} else {
				nbObstaclesChoisi--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des obstacles (" + nbObstaclesChoisi + "/" + nbObstacles + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
			}

		} else if (cases[i][j] == 5) {
			choixEmplacementSorties(e, i, j);
		} else if (cases[i][j] == 3) {
			choixEmplacementArgents(e, i, j);
		}
	}

	public void choixEmplacementSorties(ActionEvent e, int i, int j) {
		if ((nbSortiesChoisies < nbSorties) && ((i == 0) || (j == 0) || (i == n - 1) || (j == m - 1))
				&& (cases[i][j] == 0)) {
			set_case(i, j, "sortie");
			nbSortiesChoisies++;
			if (nbSortiesChoisies == nbSorties) {
				if (nbSacsArgentChoisi < nbSacArgent) {
					FenetreChoixEmplacement.setTopLabel("Choisir les emplacements des sac d'argents ("
							+ nbSacsArgentChoisi + "/" + nbSacArgent + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeux = "choixEmplacementsSacArgent";
				} else if (nbObstaclesChoisi < nbObstacles) {
					FenetreChoixEmplacement.setTopLabel(
							"Choisir les emplacements des obstacles (" + nbObstaclesChoisi + "/" + nbObstacles + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeux = "choixEmplacementsobstacle";
				} else {
					FenetreChoixEmplacement.setTopLabel("Confirmer votre choix");
					FenetreChoixEmplacement.setBottomLabel("		");
				}
			} else {
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sorties (" + nbSortiesChoisies + "/" + nbSorties + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
			}
		} else if (cases[i][j] == 5) {
			set_case(i, j, "vide");
			if (nbSortiesChoisies == nbSorties) {
				nbSortiesChoisies--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sorties (" + nbSortiesChoisies + "/" + nbSorties + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
				etatJeux = "choixEmplacementsSorties";
			} else {
				nbSortiesChoisies--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sorties (" + nbSortiesChoisies + "/" + nbSorties + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
			}
		} else if (cases[i][j] == 3) {
			choixEmplacementArgents(e, i, j);
		} else if (cases[i][j] == 4) {
			choixEmplacementobstacles(e, i, j);
		} else if ((i != 0) && (j != 0) && (i != n - 1) && (j != m - 1)&&(nbSortiesChoisies < nbSorties))
			FenetreChoixEmplacement.setBottomLabel("Les sorties doivent etre au bord de la zone geographique");
	}

	public void choixEmplacementArgents(ActionEvent e, int i, int j) {
		if ((nbSacsArgentChoisi < nbSacArgent) && (cases[i][j] == 0)) {
			set_case(i, j, "argent");
			nbSacsArgentChoisi++;

			if (nbSacsArgentChoisi == nbSacArgent) {
				if (nbSortiesChoisies < nbSorties) {
					FenetreChoixEmplacement.setTopLabel(
							"Choisir les emplacements des sorties (" + nbSortiesChoisies + "/" + nbSorties + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeux = "choixEmplacementsSorties";
				}
				if (nbObstaclesChoisi < nbObstacles) {
					FenetreChoixEmplacement.setTopLabel(
							"Choisir les emplacements des obstacles (" + nbObstaclesChoisi + "/" + nbObstacles + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeux = "choixEmplacementsobstacle";
				} else {
					FenetreChoixEmplacement.setTopLabel("Confirmez votre choix");
					FenetreChoixEmplacement.setBottomLabel("		");
				}
			} else {
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sac d'argents (" + nbSacsArgentChoisi + "/" + nbSacArgent + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
			}
		} else if (cases[i][j] == 3) {
			set_case(i, j, "vide");
			if (nbSacsArgentChoisi == nbSacArgent) {
				nbSacsArgentChoisi--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sac d'argents (" + nbSacsArgentChoisi + "/" + nbSacArgent + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
				etatJeux = "choixEmplacementsSacArgent";
			} else {
				nbSacsArgentChoisi--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sac d'argents (" + nbSacsArgentChoisi + "/" + nbSacArgent + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
			}
		} else if (cases[i][j] == 5) {
			choixEmplacementSorties(e, i, j);
		} else if (cases[i][j] == 4) {
			choixEmplacementobstacles(e, i, j);
		}
	}

	public void enregistrer_choix() {

		robots = new Robot[nbCharacteres];
		int k = 0;
		intrus = new Intrus[nbCharacteres];
		int l = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (cases[i][j] == 1) {
					robots[k] = new Robot(i, j);
					k++;
				}
				if (cases[i][j] == 2) {
					intrus[l] = new Intrus(i, j);
					l++;
				}
			}
		}

	}

	public int get_nb_lignes() {
		return n;
	}

	public int get_nb_colonnes() {
		return m;
	}

	public int get_nb_sorties() {
		return nbSorties;
	}

	public int get_nb_sorties_choisies() {
		return nbSortiesChoisies;
	}

	public int get_nb_argent() {
		return nbSacArgent;
	}

	public int get_nb_argent_choisi() {
		return nbSacsArgentChoisi;
	}

	public int get_nb_obstacles() {
		return nbObstacles;
	}

	public int get_nb_obstacles_choisi() {
		return nbObstaclesChoisi;
	}
	
	public String get_etat() {
		return etatJeux;
	}

	public void set_etat(String s) {
		etatJeux = s;
	}

	public int getnbCharacteres() {
		return nbCharacteres;
	}

	public void setnbCharacteres(int nbCharacteres) {
		this.nbCharacteres = nbCharacteres;
	}

	public int getnbRobotsChoisi() {
		return nbRobotsChoisi;
	}

	public void setnbRobotsChoisi(int nbRobotsChoisi) {
		this.nbRobotsChoisi = nbRobotsChoisi;
	}

	public int getNbintruschoisi() {
		return nbintruschoisi;
	}

	public void setNbintruschoisi(int nbintruschoisi) {
		this.nbintruschoisi = nbintruschoisi;
	}

	public void setNomJoueur1(String s) {
		nomJoueur1 = s;
	}

	public void setNomJoueur2(String s) {
		nomJoueur2 = s;
	}

	public String getNomJoueur1() {
		return nomJoueur1;
	}

	public String getNomJoueur2() {
		return nomJoueur2;
	}

	public int getNumRobot(int i, int j) {
		int x = -1;
		for (int k = 0; k < nbCharacteres; k++) {
			if ((robots[k].getX() == i) && (robots[k].getY() == j)) {
				x = k;
			}
		}
		return x;
	}

	public int getNumIntrus(int i, int j) {
		int x = -1;
		for (int k = 0; k < nbCharacteres; k++) {
			if ((intrus[k].getX() == i) && (intrus[k].getY() == j)) {
				x = k;
			}
		}
		return x;
	}

	public boolean est_mouvement_possible_intrus(Intrus in, int x, int y) {

		boolean possible = true;

		if ((cases[x][y] != 0) && (cases[x][y] != 5))
			possible = false;
		if ((cases[x][y] == 5) && (in.getNbSacsArgent() == 0))
			possible = false;

		Position[] adjacentes = get_cases_adjacentes(x, y);
		for (int i = 0; i < 8; i++)
			if (adjacentes[i] != null) {
				if (cases[adjacentes[i].getX()][adjacentes[i].getY()] == 1)
					possible = false;
			}

		return possible;
	}

	public boolean est_mouvement_possible_robot(int x, int y) {
		boolean possible = true;
		if (Position_existe(x, y))
			if (cases[x][y] != 0)
				possible = false;

		return possible;

	}

	public Position[] get_mouvements_possibles(Intrus in) {

		Position[] mouvements = new Position[4];
		int x = in.getX();
		int y = in.getY();

		if ((Position_existe(x + 1, y)) && (est_mouvement_possible_intrus(in, x + 1, y))) {
			mouvements[0] = new Position(x + 1, y);
		} else {
			mouvements[0] = null;
		}

		if ((Position_existe(x, y + 1)) && (est_mouvement_possible_intrus(in, x, y + 1))) {
			mouvements[1] = new Position(x, y + 1);
		} else {
			mouvements[1] = null;
		}

		if ((Position_existe(x - 1, y)) && (est_mouvement_possible_intrus(in, x - 1, y))) {
			mouvements[2] = new Position(x - 1, y);
		} else {
			mouvements[2] = null;
		}

		if ((Position_existe(x, y - 1)) && (est_mouvement_possible_intrus(in, x, y - 1))) {
			mouvements[3] = new Position(x, y - 1);
		} else {
			mouvements[3] = null;
		}

		return mouvements;
	}

	public Position[] get_mouvements_possibles(Robot R) {

		Position[] mouvements = new Position[4];
		int x = R.getX();
		int y = R.getY();

		if ((Position_existe(x + 1, y)) && (est_mouvement_possible_robot(x + 1, y))) {
			mouvements[0] = new Position(x + 1, y);
		} else {
			mouvements[0] = null;
		}

		if ((Position_existe(x, y + 1)) && (est_mouvement_possible_robot(x, y + 1))) {
			mouvements[1] = new Position(x, y + 1);
		} else {
			mouvements[1] = null;
		}

		if ((Position_existe(x - 1, y)) && (est_mouvement_possible_robot(x - 1, y))) {
			mouvements[2] = new Position(x - 1, y);
		} else {
			mouvements[2] = null;
		}

		if ((Position_existe(x, y - 1)) && (est_mouvement_possible_robot(x, y - 1))) {
			mouvements[3] = new Position(x, y - 1);
		} else {
			mouvements[3] = null;
		}

		return mouvements;

	}

	public void deselect(Charactere C) {

		if (C.is_selected()) {
			int x = C.getX();
			int y = C.getY();
			for (int i = 0; i < 4; i++) {
				if (prochainsMouvements[i] != null) {
					if (cases[prochainsMouvements[i].getX()][prochainsMouvements[i].getY()] == 5)
						set_case(prochainsMouvements[i].getX(), prochainsMouvements[i].getY(), "sortie");
					else
						set_case(prochainsMouvements[i].getX(), prochainsMouvements[i].getY(), "restore");
				}
			}
			C.set_selected(false);
			if (etatJeux.equals("tourJoueur2Mouvement")) {
				etatJeux = "tourJoueur2";
			}
			if (etatJeux.equals("tourJoueur1Mouvement")) {
				etatJeux = "tourJoueur1";
			}
			prochainsMouvements = null;
		}
	}

	public void montrer_mouvemeent_possibles(Intrus in, Position[] mv) {
		for (int k = 0; k < nbCharacteres; k++) {
			if (intrus[k].is_selected()) {
				deselect(intrus[k]);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (mv[i] != null) {
				if (cases[mv[i].getX()][mv[i].getY()] == 5)
					set_case(mv[i].getX(), mv[i].getY(), "highlightedJ2Exit");
				else
					set_case(mv[i].getX(), mv[i].getY(), "highlightedJ2");
			}
		}
		prochainsMouvements = mv;
		etatJeux = "tourJoueur2Mouvement";
	}

	public void montrer_mouvemeent_possibles(Robot R, Position[] mv) {
		for (int k = 0; k < nbCharacteres; k++) {
			if (robots[k].is_selected()) {
				deselect(robots[k]);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (mv[i] != null) {
				set_case(mv[i].getX(), mv[i].getY(), "highlightedJ1");
			}
		}
		prochainsMouvements = mv;
		etatJeux = "tourJoueur1Mouvement";
	}

	public void annuler_montrer_mouvemeent_possibles(Intrus in, Position[] mv) {
		for (int i = 0; i < 4; i++) {
			if (!mv[i].equals(null)) {
				set_case(mv[i].getX(), mv[i].getY(), "restore");
			}
		}
	}

	public boolean est_un_prochain_mouvement(int x, int y) {
		boolean r = false;
		if (prochainsMouvements != null)
			for (int i = 0; i < 4; i++)
				if (prochainsMouvements[i] != null)
					if ((prochainsMouvements[i].getX() == x) && (prochainsMouvements[i].getY() == y))
						r = true;
		return r;
	}

	public Intrus get_selected_intrus() {
		Intrus X = null;
		for (int i = 0; i < nbCharacteres; i++)
			if (intrus[i].is_selected())
				X = intrus[i];

		return X;
	}

	public Robot get_selected_robot() {
		Robot X = null;
		for (int i = 0; i < nbCharacteres; i++)
			if (robots[i].is_selected())
				X = robots[i];

		return X;
	}

	public boolean meme_position(int x, int y, int i, int j) {
		boolean egaux = true;
		if (x != i)
			egaux = false;
		if (y != j)
			egaux = false;
		return egaux;
	}

	public Position[] get_cases_adjacentes(int x, int y) {
		Position[] casesAdjacentes = new Position[8];

		int k = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (!meme_position(i, j, x, y)) {
					if (Position_existe(i, j))
						casesAdjacentes[k] = new Position(i, j);
					else
						casesAdjacentes[k] = null;
					k++;
				}
			}
		}

		return casesAdjacentes;
	}

	public void attrapper_argent(Intrus in, Position p) {
		if (cases[p.getX()][p.getY()] == 3) {
			set_case(p.getX(), p.getY(), "argentVolee");
			in.recupererArgent(p);
		}
	}

	public void restaurer_argent(Intrus in) {
		if (in.getNbSacsArgent() == 2) {
			set_case(in.getPosSac2().getX(), in.getPosSac2().getY(), "argent");
			in.retournerSac2();
		}
		if (in.getNbSacsArgent() == 1) {
			set_case(in.getPosSac1().getX(), in.getPosSac1().getY(), "argent");
			in.retournerSac1();
		}
	}

	public void attrapper_intrus(Robot R, Position p) {
		if (cases[p.getX()][p.getY()] == 2) {
			set_case(p.getX(), p.getY(), "intrusAttrappe");
			R.AttraperIntrus();
			restaurer_argent(intrus[getNumIntrus(p.getX(), p.getY())]);
			nbIntrusAttrappes++;
		}
	}

	public void verifier_argent(Intrus in) {
		boolean existe = false;
		if (in.getNbSacsArgent() < 2) {
			Position[] adjacentes = get_cases_adjacentes(in.getX(), in.getY());
			for (int i = 0; i < 8; i++)
				if (adjacentes[i] != null)
					if ((cases[adjacentes[i].getX()][adjacentes[i].getY()] == 3) && (!existe)) {
						existe = true;
						attrapper_argent(in, adjacentes[i]);
						if (in.getNbSacsArgent() == 0)
							set_case(in.getX(), in.getY(), "intrus");
						else if (in.getNbSacsArgent() == 1)
							set_case(in.getX(), in.getY(), "intrus1");
						else if (in.getNbSacsArgent() == 2)
							set_case(in.getX(), in.getY(), "intrus2");
					}
		}
	}

	public void verifier_intrus(Robot R) {

		boolean existe = false;
		Position[] adjacentes = get_cases_adjacentes(R.getX(), R.getY());
		for (int i = 0; i < 8; i++)
			if (adjacentes[i] != null)
				if ((cases[adjacentes[i].getX()][adjacentes[i].getY()] == 2) && (!existe)) {
					attrapper_intrus(R, adjacentes[i]);
					intrus[getNumIntrus(adjacentes[i].getX(), adjacentes[i].getY())].se_fait_attrapper();
					existe = true;
				}
	}

	public void move_intrus(Intrus in, int x, int y) {
		deselect(in);
		int i = in.getX();
		int j = in.getY();
		if (cases[x][y] == 0) {
			if (in.getNbSacsArgent() == 0)
				set_case(x, y, "intrus");
			else if (in.getNbSacsArgent() == 1)
				set_case(x, y, "intrus1");
			else if (in.getNbSacsArgent() == 2)
				set_case(x, y, "intrus2");

			set_case(i, j, "vide");

			in.move(x, y);

			verifier_argent(in);
		}

		else if ((cases[x][y] == 5) && (in.getNbSacsArgent() > 0)) {
			set_case(i, j, "vide");
			in.quitter_la_zone();
			nbArgentVole += in.getNbSacsArgent();
			nbIntrusEchappes++;
		}

		if (nbIntrusEchappes + nbIntrusAttrappes == nbCharacteres) {
			etatJeux = "finJeux";
			FenetreJeux.setTopLabel("fin");
			set_gagnant();
			afficher_resultats();
		} else {
			etatJeux = "tourJoueur1";
			FenetreJeux.setTopLabel("tourJ1");
		}
	}

	public boolean existe_mouvement_possible_intrus() {
		boolean existe = false;
		for (int i = 0; i < nbCharacteres; i++) {
			if ((intrus[i].est_dans_la_zone()) && (intrus[i].est_libre())) {
				Position[] mv = get_mouvements_possibles(intrus[i]);
				for (int k = 0; k < 4; k++)
					if (mv[k] != null)
						existe = true;
			}
		}
		return existe;
	}

	public void move_robot(Robot R, int x, int y) {
		deselect(R);
		int i = R.getX();
		int j = R.getY();

		set_case(x, y, "robot");
		set_case(i, j, "vide");
		R.move(x, y);

		verifier_intrus(R);

		if ((!existe_mouvement_possible_intrus()) || (nbIntrusAttrappes+nbIntrusEchappes==nbCharacteres)){
			etatJeux = "finJeux";
			FenetreJeux.setTopLabel("fin");
			set_gagnant();
			afficher_resultats();
		}
		else {
			etatJeux = "tourJoueur2";
			FenetreJeux.setTopLabel("tourJ2");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if ((e.getSource() == buttons[i][j]) && (cases[i][j] < 3)
						&& ((etatJeux == "choixEmplacementsrobots") || (etatJeux == "choixEmplacementsintrus"))) {
					if ((nbRobotsChoisi < nbCharacteres) || (nbintruschoisi < nbCharacteres) || (cases[i][j] == 2)
							|| (cases[i][j] == 1)) {
						if (etatJeux == "choixEmplacementsrobots") {
							if (Bonne_Position_Robot(i, j)) {
								choixEmplacementrobots(e, i, j);
							} else if((!Bonne_Position_Robot(i, j))&&(nbRobotsChoisi < nbCharacteres)) {
								FenetreChoixEmplacementRobots.setBottomLabel(
										"La position de ce robot est au bord de la zone geographique ou bien proche d'une sortie");
							}
						} else if ((etatJeux == "choixEmplacementsintrus")) {
							if (Bonne_Position_Intrus(i, j)) {
								choixEmplacementintrus(e, i, j);
							} else if((!Bonne_Position_Intrus(i, j))&&(nbintruschoisi < nbCharacteres)) {
								FenetreChoixEmplacementIntrus
										.setBottomLabel("Les intrus doivent etre au bord de la zone geogeraphique");
							}
						}
					}
				} else if (e.getSource() == buttons[i][j]) {
					if (etatJeux == "choixEmplacementsSorties") {
						choixEmplacementSorties(e, i, j);
					} else if (etatJeux == "choixEmplacementsSacArgent") {
						if (cases[i][j] == 0) {
							if (Bonne_Position_Sac_Argent(i, j)) {
								choixEmplacementArgents(e, i, j);
							} else if((!Bonne_Position_Sac_Argent(i, j))&&(nbSacsArgentChoisi < nbSacArgent)) {
								FenetreChoixEmplacement.setBottomLabel(
										"Emplacement invalide, choisir une autre position");
							}
						} else {
							choixEmplacementArgents(e, i, j);
						}
					} else if (etatJeux == "choixEmplacementsobstacle") {
						if (cases[i][j] == 0) {
							if (Bonne_Position_Obstacle(i, j)) {
								choixEmplacementobstacles(e, i, j);
							} else if((!Bonne_Position_Obstacle(i, j))&&(nbObstaclesChoisi < nbObstacles)){
								FenetreChoixEmplacement.setBottomLabel(
										"Emplacement invalide, cet obstacle est directement devant une sortie ou bien proche d'une source d'argent");
							}
						} else {
							choixEmplacementobstacles(e, i, j);
						}
					}
				}

				// lancement du jeux ...
				// tour Joueur 2 (intrus)
				if ((e.getSource() == buttons[i][j]) && (cases[i][j] == 2)
						&& ((etatJeux == "tourJoueur2") || (etatJeux.equals("tourJoueur2Mouvement")))) {
					if (intrus[getNumIntrus(i, j)].is_selected()) {
						deselect(intrus[getNumIntrus(i, j)]);

					} else {
						montrer_mouvemeent_possibles(intrus[getNumIntrus(i, j)],
								get_mouvements_possibles(intrus[getNumIntrus(i, j)]));
						intrus[getNumIntrus(i, j)].set_selected(true);
					}
				}
				if ((e.getSource() == buttons[i][j]) && (etatJeux.equals("tourJoueur2Mouvement"))
						&& (est_un_prochain_mouvement(i, j))) {
					move_intrus(get_selected_intrus(), i, j);
				}

				// tour Joueur 1 (robots)
				if ((e.getSource() == buttons[i][j]) && (cases[i][j] == 1)
						&& ((etatJeux == "tourJoueur1") || (etatJeux.equals("tourJoueur1Mouvement")))) {
					if (robots[getNumRobot(i, j)].is_selected()) {
						deselect(robots[getNumRobot(i, j)]);

					} else {
						montrer_mouvemeent_possibles(robots[getNumRobot(i, j)],
								get_mouvements_possibles(robots[getNumRobot(i, j)]));
						robots[getNumRobot(i, j)].set_selected(true);
					}
				}
				if ((e.getSource() == buttons[i][j]) && (etatJeux.equals("tourJoueur1Mouvement"))
						&& (est_un_prochain_mouvement(i, j))) {
					move_robot(get_selected_robot(), i, j);
				}
			}
		}
	}

	public void set_gagnant() {
		joueurGagnant = 0;
		
		if (etatJeux.equals("finJeux")) {
			if ((nbIntrusAttrappes == nbCharacteres) || (nbIntrusEchappes == 0) )
				joueurGagnant = 1;
			else
				joueurGagnant = 2;
		}		
	}

	public int get_num_gagnant() {
		return joueurGagnant;
	}

	public String get_nom_joueur_gagnant() {
		String gagnant = "";
		if (joueurGagnant == 1)
			gagnant = nomJoueur1;
		else if (joueurGagnant == 2)
			gagnant = nomJoueur2;
		return gagnant;
	}

	public void afficher_resultats() {
		// fenetre resultat
		FenetreResultat fenetreResultat = new FenetreResultat(this);
	}

}