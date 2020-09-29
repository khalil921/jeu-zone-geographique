package caracteres;


public class Robot extends Caractere {

	private int nbIntrusAttrappes;

	public Robot(int x, int y) {
		super(x, y);
		nbIntrusAttrappes = 0;
	}

	public void AttraperIntrus() {
		nbIntrusAttrappes++;
	}

	public int getNbIntrusAttrappes() {
		return nbIntrusAttrappes;
	}
}
