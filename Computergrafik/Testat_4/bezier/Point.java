/**
 * Einfache Klasse, die einen 2D-Punkt als Paar von zwei double's spezifiziert.
 */
public class Point {
	/*
	 * Der direkte Zugriff auf die Attribute ist -- abweichend von den üblichen
	 * Gepflogenheiten des Software Engineerings -- erlaubt.
	 */

	/** x-Koordinate */
	public double x;
	/** y-Koordinate */
	public double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Generiere neuen Punkt als Linearkombination alpha*p1+beta*p2 zweier Punkte
	 * p1,p2.
	 * 
	 * @param alpha Skalar, mit dem der erste Punkt multipliziert wird
	 * @param p1    erster Punkt
	 * @param beta  Skalar, mit dem der zweite Punkt multipliziert wird
	 * @param p2    zweiter Punkt
	 * @return Punkt alpha*p1+beta*p2
	 */
	public Point(double alpha, Point p1, double beta, Point p2) {
		this(alpha * p1.x + beta * p2.x, alpha * p1.y + beta * p2.y);
	}
	
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}