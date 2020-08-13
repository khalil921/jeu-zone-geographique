package characteres;


public class Robot extends Charactere {

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

	public boolean is_selected() {
		return selected;
	}
}
