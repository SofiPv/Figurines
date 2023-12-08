package models;

public class Punto {

	public Double x;
	public Double y;

	public Punto() {
		this(0d, 0d);
	}

	public Punto(Punto other) {
		this(other.x, other.y);
	}

	public Punto(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public void setLocation(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Double distance(Punto other) {

		double aux = Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2);

		return Math.sqrt(aux);
	}

	public Double distance(Double x, Double y) {
		return this.distance(new Punto(x, y));
	}

}
