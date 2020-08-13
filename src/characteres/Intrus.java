package characteres;

import position.*;

public class Intrus extends Charactere {

	private int nbSacsArgent; // 0,1 ou 2
	private boolean estLibre;
	private boolean estDansLaZone;
	private Position posSac1; // position du 1er sac d argent
	private Position posSac2; // position du 1er sac d argent

	public Intrus(int x, int y) {
		super(x, y);
		posSac1 = null;
		posSac2 = null;
		estDansLaZone = true;
		estLibre = true;

		nbSacsArgent = 0;
	}

	public void recupererArgent(Position p) {
		if (nbSacsArgent == 0)
			posSac1 = p;
		else if (nbSacsArgent == 1)
			posSac2 = p;
		nbSacsArgent++;
	}

	public void retournerSac2() {
		if (nbSacsArgent == 2) {
			nbSacsArgent--;
			posSac2 = null;
		}
	}

	public void retournerSac1() {
		if (nbSacsArgent == 1) {
			nbSacsArgent--;
			posSac1 = null;
		}
	}

	public int getNbSacsArgent() {
		return nbSacsArgent;
	}

	public void quitter_la_zone() {
		estDansLaZone = false;
	}

	public boolean est_dans_la_zone() {
		return estDansLaZone;
	}

	public Position getPosSac1() {
		return posSac1;
	}

	public void se_fait_attrapper() {
		estLibre = false;
	}

	public boolean est_libre() {
		return estLibre;
	}

	public Position getPosSac2() {
		return posSac2;
	}

}
