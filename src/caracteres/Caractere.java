package caracteres;

import position.*;

public class Caractere {

	protected int x, y;
	protected boolean selected;

	public Caractere(int x, int y) {
		this.x = x;
		this.y = y;
		selected = false;
	}

	public Position getPosition() {
		return new Position(x, y);
	}

	public int getX() {
		return getPosition().getX();
	}

	public int getY() {
		return getPosition().getY();
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean is_selected() {
		return selected;
	}

	public void set_selected(boolean s) {
		selected = s;
	}

}
