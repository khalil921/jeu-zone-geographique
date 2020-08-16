package zoneGeographique;

import interfaceGraphique.FenetreChoixEmplacement;
import interfaceGraphique.FenetreChoixEmplacementIntrus;
import interfaceGraphique.FenetreChoixEmplacementRobots;
import interfaceGraphique.FenetreJeux;
import interfaceGraphique.FenetreLancement;
import interfaceGraphique.FenetreResultat;
import interfaceGraphique.FenetreSaisie;
import interfaceGraphique.FenetreSaisieNomJoueur;

public class Jeux {

	private static FenetreLancement fenetreLancement;
	private static FenetreSaisie fenetreSaisie;
	private static FenetreSaisieNomJoueur fenetreSaisieNomJoueur1;
	private static FenetreChoixEmplacement fenetreChoixEmplacement;
	private static FenetreChoixEmplacementRobots fenetreChoixEmplacementRobots;
	private static FenetreSaisieNomJoueur fenetreSaisieNomJoueur2;
	private static FenetreChoixEmplacementIntrus fenetreChoixEmplacementIntrus;
	private static FenetreJeux fenetreJeux;
	private static FenetreResultat fenetreResultat;

	public static void main(String[] args) {
		lancer_jeux();
	}

	public static void lancer_jeux() {
		fenetreLancement = new FenetreLancement(false);
	}

	public static void relancer_partie() {
		ZoneGeographique zoneGeo = fenetreResultat.getZoneGeo();
		fenetreResultat = null;
		zoneGeo.reset_partie();
		fenetreChoixEmplacement = new FenetreChoixEmplacement(zoneGeo, false);

	}

	public static void nouvelle_partie(boolean garderJoueurs) {
		if (!garderJoueurs) {
			fenetreResultat = null;
			System.out
					.println("MB :" + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
							/ (1024 * 1024));

			fenetreLancement = new FenetreLancement(false);
		} else {
			String nomJ1 = fenetreResultat.getZoneGeo().getNomJoueur1();
			String nomJ2 = fenetreResultat.getZoneGeo().getNomJoueur2();

			fenetreResultat = null;
			// System.gc();

			fenetreLancement = new FenetreLancement(true);
			fenetreLancement.setNomJ1(nomJ1);
			fenetreLancement.setNomJ2(nomJ2);
		}

	}

	public static void partie_rapide(boolean estNouvellePartie) {
		if (!estNouvellePartie) {
			fenetreSaisieNomJoueur1 = new FenetreSaisieNomJoueur(new ZoneGeographique(13, 13, 3, 7, 3, 4), true, null);
			fenetreLancement = null;
		} else {
			ZoneGeographique zoneGeo = new ZoneGeographique(13, 13, 3, 7, 3, 4);
			zoneGeo.setNomJoueur1(fenetreLancement.getNomJ1());
			zoneGeo.setNomJoueur2(fenetreLancement.getNomJ2());
			fenetreLancement = null;

			fenetreChoixEmplacement = new FenetreChoixEmplacement(zoneGeo, false);
		}
	}

	public static void partie_config(boolean estNouvellePartie) {
		if (!estNouvellePartie) {
			fenetreSaisie = new FenetreSaisie(false);
			fenetreLancement = null;
		} else {
			fenetreSaisie = new FenetreSaisie(true);
			fenetreSaisie.setNomJ1(fenetreLancement.getNomJ1());
			fenetreSaisie.setNomJ2(fenetreLancement.getNomJ2());
			fenetreLancement = null;
		}
	}

	public static void passer_saisie_joueur1() {
		fenetreSaisieNomJoueur1 = new FenetreSaisieNomJoueur(
				new ZoneGeographique(fenetreSaisie.getN(), fenetreSaisie.getM(), fenetreSaisie.get_nbSorties(),
						fenetreSaisie.get_nbSacArgent(), fenetreSaisie.get_nbCh(), fenetreSaisie.get_nbObstacles()),
				true, null);
		fenetreSaisie = null;
	}

	public static void passer_choix_emplacements(boolean garderJoueurs) {
		if (!garderJoueurs) {
			fenetreChoixEmplacement = new FenetreChoixEmplacement(fenetreSaisieNomJoueur1.getZoneGeo(), true);
			fenetreSaisieNomJoueur1 = null;
		} else {
			ZoneGeographique zoneGeo = new ZoneGeographique(fenetreSaisie.getN(), fenetreSaisie.getM(),
					fenetreSaisie.get_nbSorties(), fenetreSaisie.get_nbSacArgent(), fenetreSaisie.get_nbCh(),
					fenetreSaisie.get_nbObstacles());
			zoneGeo.setNomJoueur1(fenetreSaisie.getNomJ1());
			zoneGeo.setNomJoueur2(fenetreSaisie.getNomJ2());
			fenetreChoixEmplacement = new FenetreChoixEmplacement(zoneGeo, false);
		}
	}

	public static void passer_choix_robots(boolean estNouvellePartie) {
		fenetreChoixEmplacementRobots = new FenetreChoixEmplacementRobots(fenetreChoixEmplacement.getZoneGeo(),
				fenetreChoixEmplacement.getButtons(), estNouvellePartie);
		fenetreChoixEmplacement = null;
	}

	public static void passer_saisie_joueur2() {
		fenetreSaisieNomJoueur2 = new FenetreSaisieNomJoueur(fenetreChoixEmplacementRobots.getZoneGeo(), false,
				fenetreChoixEmplacementRobots.getButtons());
		fenetreChoixEmplacementRobots = null;

	}

	public static void passer_choix_intrus(boolean estNouvellePartie) {
		if (estNouvellePartie) {
			fenetreChoixEmplacementIntrus = new FenetreChoixEmplacementIntrus(fenetreSaisieNomJoueur2.getZoneGeo(),
					fenetreSaisieNomJoueur2.getButtons());
			fenetreSaisieNomJoueur2 = null;
		} else {
			fenetreChoixEmplacementIntrus = new FenetreChoixEmplacementIntrus(
					fenetreChoixEmplacementRobots.getZoneGeo(), fenetreChoixEmplacementRobots.getButtons());
			fenetreChoixEmplacementRobots = null;
		}

	}

	public static void commencer_jeux() {
		fenetreJeux = new FenetreJeux(fenetreChoixEmplacementIntrus.getZoneGeo(),
				fenetreChoixEmplacementIntrus.getButtons());
		fenetreChoixEmplacementIntrus = null;
	}

	public static void afficher_resultat() {
		fenetreResultat = new FenetreResultat(fenetreJeux.getZoneGeo());
		fenetreJeux = null;
	}

}
