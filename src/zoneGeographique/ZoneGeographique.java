package zoneGeographique;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import caracteres.Caractere;
import caracteres.Intrus;
import caracteres.Robot;
import interfaceGraphique.FenetreChoixEmplacement;
import interfaceGraphique.FenetreChoixEmplacementIntrus;
import interfaceGraphique.FenetreChoixEmplacementRobots;
import interfaceGraphique.FenetreJeu;
import position.Position;

public class ZoneGeographique {

	private int n, m, nbSorties, nbSacArgent, nbCharacteres, nbObstacles; // n = nombre de lignes , m = nombre de
																			// colonnes
	private int nbSortiesChoisies = 0;
	private int nbSacsArgentChoisi = 0;
	private int nbObstaclesChoisi = 0;
	private int nbRobotsChoisi = 0;
	private int nbintruschoisi = 0;

	private String nomJoueur1, nomJoueur2;
	private int joueurGagnant = 0;

	private int[][] cases;

	boolean premierMouvement = true;
	long timerStart; // timer pour calculer la duree du jeu

	/*
	 * 0 - vide, 1 - robot, 2 - intrus, 3 - source d'argent, 4 - obstacle, 5 -
	 * sortie
	 */

	/*
	 * -3 argent volee -2 intrus attrappé
	 */

	private String etatJeu = "DebutConfiguration";

	private Robot[] robots;
	private Intrus[] intrus;

	private Position[] prochainsMouvements;

	private int nbArgentVole = 0; // nombre de sacs d argents volé

	private int nbIntrusAttrappes = 0;
	private int nbIntrusEchappes = 0;

	public ZoneGeographique(int nbL, int nbC, int nbSorties, int nbSacArgent, int nbChar, int nbObstacles) {

		n = nbL;
		m = nbC;
		this.nbSorties = nbSorties;
		this.nbSacArgent = nbSacArgent;
		nbCharacteres = nbChar;
		this.nbObstacles = nbObstacles;
		nomJoueur1 = "";
		nomJoueur2 = "";

		cases = new int[n][m];

	}

	public void reset_partie() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				cases[i][j] = 0;
			}
		}

		robots = null;
		intrus = null;
		prochainsMouvements = null;

		etatJeu = "DebutConfiguration";

		nbArgentVole = 0;
		nbIntrusAttrappes = 0;
		nbIntrusEchappes = 0;

		nbSortiesChoisies = 0;
		nbSacsArgentChoisi = 0;
		nbObstaclesChoisi = 0;
		nbRobotsChoisi = 0;
		nbintruschoisi = 0;

		joueurGagnant = 0;
		premierMouvement = true;

	}

	public void set_case(int i, int j, String val, JButton[][] buttons) {
		if (val.equals("sortie")) {
			cases[i][j] = 5;
			Image sortieImg = new ImageIcon(getClass().getResource("/icones/exit.png")).getImage();
			int min = Math.min(buttons[i][j].getWidth(), buttons[i][j].getHeight());
			sortieImg = sortieImg.getScaledInstance(min, min, Image.SCALE_SMOOTH);
			Icon sortieIcon = new ImageIcon(sortieImg);
			buttons[i][j].setIcon(sortieIcon);
			buttons[i][j].validate();
		}
		if (val.equals("robot")) {
			cases[i][j] = 1;
			Image robotImg = new ImageIcon(getClass().getResource("/icones/robot.png")).getImage();
			robotImg = robotImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon robotIcon = new ImageIcon(robotImg);
			buttons[i][j].setIcon(robotIcon);
			buttons[i][j].validate();
		}
		if (val.equals("argent")) {
			cases[i][j] = 3;
			Image ArgentImg = new ImageIcon(getClass().getResource("/icones/money.jpeg")).getImage();
			ArgentImg = ArgentImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon ArgentIcon = new ImageIcon(ArgentImg);
			buttons[i][j].setIcon(ArgentIcon);
			buttons[i][j].validate();
		}
		if (val.equals("argentVolee")) {
			cases[i][j] = -3;
			Image ArgentImg = new ImageIcon(getClass().getResource("/icones/stolenMoney.png")).getImage();
			ArgentImg = ArgentImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon ArgentIcon = new ImageIcon(ArgentImg);
			buttons[i][j].setIcon(ArgentIcon);
			buttons[i][j].validate();
		}
		if (val.equals("vide")) {
			cases[i][j] = 0;
			buttons[i][j].setIcon(null);
			buttons[i][j].validate();
			buttons[i][j].setBackground(Color.white);
		}
		if (val.equals("obstacle")) {
			cases[i][j] = 4;
			Image obstacleImg = new ImageIcon(getClass().getResource("/icones/wall.png")).getImage();
			obstacleImg = obstacleImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon obstacleIcon = new ImageIcon(obstacleImg);
			buttons[i][j].setIcon(obstacleIcon);
			buttons[i][j].validate();
		}
		if (val.equals("intrus")) {
			cases[i][j] = 2;
			Image intrusImg = new ImageIcon(getClass().getResource("/icones/thieve0.png")).getImage();
			intrusImg = intrusImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon intrusIcon = new ImageIcon(intrusImg);
			buttons[i][j].setIcon(intrusIcon);
			buttons[i][j].validate();
		}
		if (val.equals("intrus1")) {
			cases[i][j] = 2;
			Image intrusImg = new ImageIcon(getClass().getResource("/icones/thieve1.png")).getImage();
			intrusImg = intrusImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon intrusIcon = new ImageIcon(intrusImg);
			buttons[i][j].setIcon(intrusIcon);
			buttons[i][j].validate();
		}
		if (val.equals("intrus2")) {
			cases[i][j] = 2;
			Image intrusImg = new ImageIcon(getClass().getResource("/icones/thieve2.png")).getImage();
			intrusImg = intrusImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon intrusIcon = new ImageIcon(intrusImg);
			buttons[i][j].setIcon(intrusIcon);
			buttons[i][j].validate();
		}
		if (val.equals("intrusAttrappe")) {
			cases[i][j] = -2;
			Image intrusImg = new ImageIcon(getClass().getResource("/icones/caughtThieve.png")).getImage();
			intrusImg = intrusImg.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(),
					Image.SCALE_SMOOTH);
			Icon intrusIcon = new ImageIcon(intrusImg);
			buttons[i][j].setIcon(intrusIcon);
			buttons[i][j].validate();
		}

		if (val == "highlightedJ1") {
			// buttons[i][j].setBackground(Color.blue.brighter().brighter().brighter());
			Image img = new ImageIcon(getClass().getResource("/icones/selectRobot.png")).getImage();
			img = img.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(), Image.SCALE_SMOOTH);
			Icon icon = new ImageIcon(img);
			buttons[i][j].setIcon(icon);
			buttons[i][j].validate();
		}
		if (val == "highlightedJ2") {
			Image img = new ImageIcon(getClass().getResource("/icones/selectIntrus.png")).getImage();
			img = img.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(), Image.SCALE_SMOOTH);
			Icon icon = new ImageIcon(img);
			buttons[i][j].setIcon(icon);
			buttons[i][j].validate();
		}
		if (val == "highlightedJ2Exit") {
			Image img = new ImageIcon(getClass().getResource("/icones/selectedExit.png")).getImage();
			img = img.getScaledInstance(buttons[i][j].getWidth(), buttons[i][j].getHeight(), Image.SCALE_SMOOTH);
			Icon icon = new ImageIcon(img);
			buttons[i][j].setIcon(icon);
			buttons[i][j].validate();
		}
		if (val.equals("restore")) {
			buttons[i][j].setIcon(null);
			buttons[i][j].validate();
		}
	}

	public int get_case(int i, int j) {
		return cases[i][j];
	}

	public void paint_zone(JButton[][] buttons) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				switch (cases[i][j]) {
				case 0:
					set_case(i, j, "vide", buttons);
					break;
				case 1:
					set_case(i, j, "robot", buttons);
					break;
				case 2:
					set_case(i, j, "intrus", buttons);
					break;
				case 3:
					set_case(i, j, "argent", buttons);
					break;
				case 4:
					set_case(i, j, "obstacle", buttons);
					break;
				case 5:
					set_case(i, j, "sortie", buttons);
					break;
				default:
					break;
				}
			}
		}

	}

	public void choix_aleatoire_sorties(JButton[][] buttons) {
		int i, j;
		for (int k = 0; k < nbSorties; k++) {
			do {
				i = genererInt(n);
				j = genererInt(m);
			} while (!Bonne_Position_Sortie(i, j));
			set_case(i, j, "sortie", buttons);
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

	public void choix_aleatoire_sacs_argents(JButton[][] buttons) {
		int i, j;
		for (int k = 0; k < nbSacArgent; k++) {
			do {
				i = genererInt(n);
				j = genererInt(m);
			} while (!Bonne_Position_Sac_Argent(i, j));
			set_case(i, j, "argent", buttons);
		}
	}

	public void choix_aleatoire_obstacles(JButton[][] buttons) {
		int i, j;
		for (int k = 0; k < nbObstacles; k++) {
			do {
				i = genererInt(n);
				j = genererInt(m);
			} while (!Bonne_Position_Obstacle(i, j));
			set_case(i, j, "obstacle", buttons);
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
		if (cases[x][y] == 0 || cases[x][y] == 2) {
			if ((x != 0) && (y != 0) && (x != n - 1) && (y != m - 1)) {
				bool = false;
			} else if (((x == 0) && (Position_existe(x, (y + 1))) && (cases[x][y + 1] != 0))
					&& ((x == 0) && (Position_existe(x, (y - 1))) && (cases[x][y - 1] != 0))
					&& ((x == 0) && (Position_existe((x + 1), y)) && (cases[x + 1][y] != 0))) {
				bool = false;

			} else if (((x == (n - 1)) && (Position_existe(x, (y + 1))) && (cases[x][y + 1] != 0))
					&& ((x == (n - 1)) && (Position_existe(x, (y - 1))) && (cases[x][y - 1] != 0))
					&& ((x == (n - 1)) && (Position_existe((x + 1), y)) && (cases[x - 1][y] != 0))) {
				bool = false;

			} else if (((y == 0) && (Position_existe((x + 1), y)) && (cases[x + 1][y] != 0))
					&& ((y == 0) && (Position_existe((x - 1), y)) && (cases[x - 1][y] != 0))
					&& ((y == 0) && (Position_existe(x, (y + 1)) && (cases[x][y + 1] != 0)))) {
				bool = false;

			} else if (((y == (m - 1)) && (Position_existe((x + 1), y)) && (cases[x + 1][y] != 0))
					&& ((y == (m - 1)) && (Position_existe((x - 1), y)) && (cases[x - 1][y] != 0))
					&& ((y == (m - 1)) && (Position_existe(x, (y - 1)) && (cases[x][y - 1] != 0)))) {
				bool = false;

			}
		}
		return bool;
	}

	public int genererInt(int borneSup) {
		Random random = new Random();
		int nb;
		nb = random.nextInt(borneSup);
		return nb;
	}

	public void Choix_aleatoire(JButton[][] buttons) {

		if (!etatJeu.equals("DebutConfiguration")) {
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					set_case(i, j, "vide", buttons);
		}
		choix_aleatoire_sorties(buttons);
		choix_aleatoire_sacs_argents(buttons);
		choix_aleatoire_obstacles(buttons);
		FenetreChoixEmplacement.setTopLabel("Confirmer cette configuration ou choisir une autre");
		etatJeu = "";
		nbSortiesChoisies = nbSorties;
		nbSacsArgentChoisi = nbSacArgent;
		nbObstaclesChoisi = nbObstacles;

	}

	public void choixEmplacementintrus(ActionEvent e, int i, int j, JButton[][] buttons) {
		if ((nbintruschoisi < nbCharacteres) && (cases[i][j] == 0)) {
			set_case(i, j, "intrus", buttons);
			nbintruschoisi++;

			if (nbintruschoisi == nbCharacteres) {
				FenetreChoixEmplacementIntrus.setTopLabel("commencer le jeu ou bien modifier l emplacement des intrus");
				FenetreChoixEmplacementIntrus.setBottomLabel("		");
			} else {
				FenetreChoixEmplacementIntrus.setTopLabel(
						"Choisir les emplacements des intrus (" + nbintruschoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementIntrus.setBottomLabel("		");
			}
		} else if (cases[i][j] == 2) {
			set_case(i, j, "vide", buttons);
			if (nbintruschoisi == nbCharacteres) {
				nbintruschoisi--;
				FenetreChoixEmplacementIntrus.setTopLabel(
						"Choisir les emplacements des intrus (" + nbintruschoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementIntrus.setBottomLabel("		");
				etatJeu = "choixEmplacementsintrus";
			} else {
				nbintruschoisi--;
				FenetreChoixEmplacementIntrus.setTopLabel(
						"Choisir les emplacements des intrus (" + nbintruschoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementIntrus.setBottomLabel("		");
			}

		}
	}

	public void choixEmplacementrobots(ActionEvent e, int i, int j, JButton[][] buttons) {
		if ((nbRobotsChoisi < nbCharacteres) && (cases[i][j] == 0)) {
			set_case(i, j, "robot", buttons);
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
			set_case(i, j, "vide", buttons);
			if (nbRobotsChoisi == nbCharacteres) {
				nbRobotsChoisi--;
				FenetreChoixEmplacementRobots.setTopLabel(
						"Choisir les emplacements des robots (" + nbRobotsChoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementRobots.setBottomLabel("		");
				etatJeu = "choixEmplacementsrobots";
			} else {
				nbRobotsChoisi--;
				FenetreChoixEmplacementRobots.setTopLabel(
						"Choisir les emplacements des robots (" + nbRobotsChoisi + "/" + nbCharacteres + ")");
				FenetreChoixEmplacementRobots.setBottomLabel("		");
			}

		}
	}

	public void choixEmplacementobstacles(ActionEvent e, int i, int j, JButton[][] buttons) {
		if ((nbObstaclesChoisi < nbObstacles) && (cases[i][j] == 0)) {
			set_case(i, j, "obstacle", buttons);
			nbObstaclesChoisi++;
			if (nbObstaclesChoisi == nbObstacles) {
				if (nbSortiesChoisies < nbSorties) {
					FenetreChoixEmplacement.setTopLabel(
							"Choisir les emplacements des sorties (" + nbSortiesChoisies + "/" + nbSorties + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeu = "choixEmplacementsSorties";
				} else if (nbSacsArgentChoisi < nbSacArgent) {
					FenetreChoixEmplacement.setTopLabel("Choisir les emplacements des sac d'argents ("
							+ nbSacsArgentChoisi + "/" + nbSacArgent + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeu = "choixEmplacementsSacArgent";
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
			set_case(i, j, "vide", buttons);
			if (nbObstaclesChoisi == nbObstacles) {
				nbObstaclesChoisi--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des obstacles (" + nbObstaclesChoisi + "/" + nbObstacles + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
				etatJeu = "choixEmplacementsobstacle";
			} else {
				nbObstaclesChoisi--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des obstacles (" + nbObstaclesChoisi + "/" + nbObstacles + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
			}

		} else if (cases[i][j] == 5) {
			choixEmplacementSorties(e, i, j, buttons);
		} else if (cases[i][j] == 3) {
			choixEmplacementArgents(e, i, j, buttons);
		}
	}

	public void choixEmplacementSorties(ActionEvent e, int i, int j, JButton[][] buttons) {
		if ((nbSortiesChoisies < nbSorties) && ((i == 0) || (j == 0) || (i == n - 1) || (j == m - 1))
				&& (cases[i][j] == 0)) {
			set_case(i, j, "sortie", buttons);
			nbSortiesChoisies++;
			if (nbSortiesChoisies == nbSorties) {
				if (nbSacsArgentChoisi < nbSacArgent) {
					FenetreChoixEmplacement.setTopLabel("Choisir les emplacements des sac d'argents ("
							+ nbSacsArgentChoisi + "/" + nbSacArgent + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeu = "choixEmplacementsSacArgent";
				} else if (nbObstaclesChoisi < nbObstacles) {
					FenetreChoixEmplacement.setTopLabel(
							"Choisir les emplacements des obstacles (" + nbObstaclesChoisi + "/" + nbObstacles + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeu = "choixEmplacementsobstacle";
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
			set_case(i, j, "vide", buttons);
			if (nbSortiesChoisies == nbSorties) {
				nbSortiesChoisies--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sorties (" + nbSortiesChoisies + "/" + nbSorties + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
				etatJeu = "choixEmplacementsSorties";
			} else {
				nbSortiesChoisies--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sorties (" + nbSortiesChoisies + "/" + nbSorties + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
			}
		} else if (cases[i][j] == 3) {
			choixEmplacementArgents(e, i, j, buttons);
		} else if (cases[i][j] == 4) {
			choixEmplacementobstacles(e, i, j, buttons);
		} else if ((i != 0) && (j != 0) && (i != n - 1) && (j != m - 1) && (nbSortiesChoisies < nbSorties))
			FenetreChoixEmplacement.setBottomLabel("Les sorties doivent etre au bord de la zone geographique");
	}

	public void choixEmplacementArgents(ActionEvent e, int i, int j, JButton[][] buttons) {
		if ((nbSacsArgentChoisi < nbSacArgent) && (cases[i][j] == 0)) {
			set_case(i, j, "argent", buttons);
			nbSacsArgentChoisi++;

			if (nbSacsArgentChoisi == nbSacArgent) {
				if (nbSortiesChoisies < nbSorties) {
					FenetreChoixEmplacement.setTopLabel(
							"Choisir les emplacements des sorties (" + nbSortiesChoisies + "/" + nbSorties + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeu = "choixEmplacementsSorties";
				}
				if (nbObstaclesChoisi < nbObstacles) {
					FenetreChoixEmplacement.setTopLabel(
							"Choisir les emplacements des obstacles (" + nbObstaclesChoisi + "/" + nbObstacles + ")");
					FenetreChoixEmplacement.setBottomLabel("		");
					etatJeu = "choixEmplacementsobstacle";
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
			set_case(i, j, "vide", buttons);
			if (nbSacsArgentChoisi == nbSacArgent) {
				nbSacsArgentChoisi--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sac d'argents (" + nbSacsArgentChoisi + "/" + nbSacArgent + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
				etatJeu = "choixEmplacementsSacArgent";
			} else {
				nbSacsArgentChoisi--;
				FenetreChoixEmplacement.setTopLabel(
						"Choisir les emplacements des sac d'argents (" + nbSacsArgentChoisi + "/" + nbSacArgent + ")");
				FenetreChoixEmplacement.setBottomLabel("		");
			}
		} else if (cases[i][j] == 5) {
			choixEmplacementSorties(e, i, j, buttons);
		} else if (cases[i][j] == 4) {
			choixEmplacementobstacles(e, i, j, buttons);
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
		return etatJeu;
	}

	public void set_etat(String s) {
		etatJeu = s;
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

	public Intrus getIntrus(int i, int j) {
		return intrus[getNumIntrus(i, j)];
	}

	public Robot getRobot(int i, int j) {
		return robots[getNumRobot(i, j)];
	}

	public boolean est_mouvement_possible_intrus(Intrus in, int x, int y) {

		boolean possible = true;

		if ((cases[x][y] != 0) && (cases[x][y] != 5))
			possible = false;
		if ((cases[x][y] == 5) && ((in.getNbSacsArgent() == 0) || (in.getNbSacsArgent() == 1)))
			possible = false;

		Position[] adjacentes = get_cases_adjacentes(x, y);
		for (int i = 0; i < 8; i++)
			if (adjacentes[i] != null) {
				if ((cases[adjacentes[i].getX()][adjacentes[i].getY()] == 1) && (cases[x][y] != 5))
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

	public void deselect(Caractere C, JButton[][] buttons) {

		if (C.is_selected()) {
			for (int i = 0; i < 4; i++) {
				if (prochainsMouvements[i] != null) {
					if (cases[prochainsMouvements[i].getX()][prochainsMouvements[i].getY()] == 5)
						set_case(prochainsMouvements[i].getX(), prochainsMouvements[i].getY(), "sortie", buttons);
					else
						set_case(prochainsMouvements[i].getX(), prochainsMouvements[i].getY(), "restore", buttons);
				}
			}
			C.set_selected(false);
			if (etatJeu.equals("tourJoueur2Mouvement")) {
				etatJeu = "tourJoueur2";
			}
			if (etatJeu.equals("tourJoueur1Mouvement")) {
				etatJeu = "tourJoueur1";
			}
			prochainsMouvements = null;
		}
	}

	public void deselect_intrus(int i, int j, JButton[][] buttons) {
		deselect(intrus[getNumIntrus(i, j)], buttons);
	}

	public void deselect_robot(int i, int j, JButton[][] buttons) {
		deselect(robots[getNumRobot(i, j)], buttons);
	}

	public void set_intrus_selected(int i, int j) {
		intrus[getNumIntrus(i, j)].set_selected(true);
	}

	public void set_robot_selected(int i, int j) {
		robots[getNumRobot(i, j)].set_selected(true);
	}

	public void montrer_mouvemeent_possibles(Intrus in, Position[] mv, JButton[][] buttons) {
		for (int k = 0; k < nbCharacteres; k++) {
			if (intrus[k].is_selected()) {
				deselect(intrus[k], buttons);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (mv[i] != null) {
				if (cases[mv[i].getX()][mv[i].getY()] == 5)
					set_case(mv[i].getX(), mv[i].getY(), "highlightedJ2Exit", buttons);
				else
					set_case(mv[i].getX(), mv[i].getY(), "highlightedJ2", buttons);
			}
		}
		prochainsMouvements = mv;
		etatJeu = "tourJoueur2Mouvement";
	}

	public void montrer_mouvemeent_possibles(Robot R, Position[] mv, JButton[][] buttons) {
		for (int k = 0; k < nbCharacteres; k++) {
			if (robots[k].is_selected()) {
				deselect(robots[k], buttons);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (mv[i] != null) {
				set_case(mv[i].getX(), mv[i].getY(), "highlightedJ1", buttons);
			}
		}
		prochainsMouvements = mv;
		etatJeu = "tourJoueur1Mouvement";
	}

	public void annuler_montrer_mouvemeent_possibles(Intrus in, Position[] mv, JButton[][] buttons) {
		for (int i = 0; i < 4; i++) {
			if (!mv[i].equals(null)) {
				set_case(mv[i].getX(), mv[i].getY(), "restore", buttons);
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

		if (Position_existe(x - 1, y))
			casesAdjacentes[0] = new Position(x - 1, y);
		else
			casesAdjacentes[0] = null;

		if (Position_existe(x, y - 1))
			casesAdjacentes[1] = new Position(x, y - 1);
		else
			casesAdjacentes[1] = null;

		if (Position_existe(x + 1, y))
			casesAdjacentes[2] = new Position(x + 1, y);
		else
			casesAdjacentes[2] = null;

		if (Position_existe(x, y + 1))
			casesAdjacentes[3] = new Position(x, y + 1);
		else
			casesAdjacentes[3] = null;

		if ((!Position_existe(x - 1, y - 1))
				|| ((Position_existe(x - 1, y - 1) && (cases[casesAdjacentes[0].getX()][casesAdjacentes[0].getY()] != 0)
						&& (cases[casesAdjacentes[1].getX()][casesAdjacentes[1].getY()] != 0))))
			casesAdjacentes[4] = null;
		else
			casesAdjacentes[4] = new Position(x - 1, y - 1);

		if ((!Position_existe(x - 1, y + 1))
				|| ((Position_existe(x - 1, y + 1) && (cases[casesAdjacentes[0].getX()][casesAdjacentes[0].getY()] != 0)
						&& (cases[casesAdjacentes[3].getX()][casesAdjacentes[3].getY()] != 0))))
			casesAdjacentes[5] = null;
		else
			casesAdjacentes[5] = new Position(x - 1, y + 1);

		if ((!Position_existe(x + 1, y - 1))
				|| ((Position_existe(x + 1, y - 1) && (cases[casesAdjacentes[2].getX()][casesAdjacentes[2].getY()] != 0)
						&& (cases[casesAdjacentes[1].getX()][casesAdjacentes[1].getY()] != 0))))
			casesAdjacentes[6] = null;
		else
			casesAdjacentes[6] = new Position(x + 1, y - 1);

		if ((!Position_existe(x + 1, y + 1))
				|| ((Position_existe(x + 1, y + 1) && (cases[casesAdjacentes[2].getX()][casesAdjacentes[2].getY()] != 0)
						&& (cases[casesAdjacentes[3].getX()][casesAdjacentes[3].getY()] != 0))))
			casesAdjacentes[7] = null;
		else
			casesAdjacentes[7] = new Position(x + 1, y + 1);

		return casesAdjacentes;

	}

	public void attrapper_argent(Intrus in, Position p, JButton[][] buttons) {
		if (cases[p.getX()][p.getY()] == 3) {
			set_case(p.getX(), p.getY(), "argentVolee", buttons);
			in.recupererArgent(p);
		}
	}

	public void restaurer_argent(Intrus in, JButton[][] buttons) {
		if (in.getNbSacsArgent() == 2) {
			set_case(in.getPosSac2().getX(), in.getPosSac2().getY(), "argent", buttons);
			in.retournerSac2();
		}
		if (in.getNbSacsArgent() == 1) {
			set_case(in.getPosSac1().getX(), in.getPosSac1().getY(), "argent", buttons);
			in.retournerSac1();
		}
	}

	public void attrapper_intrus(Robot R, Position p, JButton[][] buttons) {
		if (cases[p.getX()][p.getY()] == 2) {
			set_case(p.getX(), p.getY(), "intrusAttrappe", buttons);
			R.AttraperIntrus();
			restaurer_argent(intrus[getNumIntrus(p.getX(), p.getY())], buttons);
			nbIntrusAttrappes++;
		}
	}

	public void verifier_argent(Intrus in, JButton[][] buttons) {
		boolean existe = false;
		if (in.getNbSacsArgent() < 2) {
			Position[] adjacentes = get_cases_adjacentes(in.getX(), in.getY());
			for (int i = 0; i < 8; i++)
				if (adjacentes[i] != null)
					if ((cases[adjacentes[i].getX()][adjacentes[i].getY()] == 3) && (!existe)) {
						existe = true;
						attrapper_argent(in, adjacentes[i], buttons);
						if (in.getNbSacsArgent() == 0)
							set_case(in.getX(), in.getY(), "intrus", buttons);
						else if (in.getNbSacsArgent() == 1)
							set_case(in.getX(), in.getY(), "intrus1", buttons);
						else if (in.getNbSacsArgent() == 2)
							set_case(in.getX(), in.getY(), "intrus2", buttons);
					}
		}
	}

	public void verifier_intrus(Robot R, JButton[][] buttons) {

		boolean existe = false;
		Position[] adjacentes = get_cases_adjacentes(R.getX(), R.getY());
		for (int i = 0; i < 8; i++)
			if (adjacentes[i] != null)
				if ((cases[adjacentes[i].getX()][adjacentes[i].getY()] == 2) && (!existe)) {
					attrapper_intrus(R, adjacentes[i], buttons);
					intrus[getNumIntrus(adjacentes[i].getX(), adjacentes[i].getY())].se_fait_attrapper();
					existe = true;
				}
	}

	public void move_intrus(Intrus in, int x, int y, JButton[][] buttons) {
		deselect(in, buttons);
		int i = in.getX();
		int j = in.getY();

		if (cases[x][y] == 0) {
			if (in.getNbSacsArgent() == 0)
				set_case(x, y, "intrus", buttons);
			else if (in.getNbSacsArgent() == 1)
				set_case(x, y, "intrus1", buttons);
			else if (in.getNbSacsArgent() == 2)
				set_case(x, y, "intrus2", buttons);

			set_case(i, j, "vide", buttons);

			in.move(x, y);

			verifier_argent(in, buttons);
		}

		else if ((cases[x][y] == 5) && (in.getNbSacsArgent() > 0)) {
			set_case(i, j, "vide", buttons);
			in.quitter_la_zone();
			nbArgentVole += in.getNbSacsArgent();
			nbIntrusEchappes++;
		}

		if (premierMouvement) {
			timerStart = System.currentTimeMillis();
			premierMouvement = false;
		}

		if (nbIntrusEchappes + nbIntrusAttrappes == nbCharacteres) {
			etatJeu = "finJeu";
			FenetreJeu.setTopLabel("fin");
			set_gagnant();
		} else {
			etatJeu = "tourJoueur1";
			FenetreJeu.setTopLabel("tourJ1");
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

	public void move_robot(Robot R, int x, int y, JButton[][] buttons) {
		deselect(R, buttons);
		int i = R.getX();
		int j = R.getY();

		set_case(x, y, "robot", buttons);
		set_case(i, j, "vide", buttons);
		R.move(x, y);

		verifier_intrus(R, buttons);

		if ((!existe_mouvement_possible_intrus()) || (nbIntrusAttrappes + nbIntrusEchappes == nbCharacteres)) {
			etatJeu = "finJeu";
			FenetreJeu.setTopLabel("fin");
			set_gagnant();
			// afficher_resultats();
		} else {
			etatJeu = "tourJoueur2";
			FenetreJeu.setTopLabel("tourJ2");
		}
	}

	public void set_gagnant() {
		joueurGagnant = 0;

		if (etatJeu.equals("finJeu")) {
			if ((nbIntrusAttrappes == nbCharacteres) || (nbIntrusEchappes == 0))
				joueurGagnant = 1;
			else
				joueurGagnant = 2;
		}
	}

	public int get_num_gagnant() {
		return joueurGagnant;
	}

	public int getNbArgentVole() {
		return nbArgentVole;
	}

	public long getTimeDebutJeu() {
		return timerStart;
	}

	public String get_nom_joueur_gagnant() {
		String gagnant = "";
		if (joueurGagnant == 1)
			gagnant = nomJoueur1;
		else if (joueurGagnant == 2)
			gagnant = nomJoueur2;
		return gagnant;
	}

}