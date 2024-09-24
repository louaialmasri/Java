import java.awt.Graphics;
import java.util.List;

class Bezier {
	// TODO: Definieren Sie benötigte Attribute hier.
	private List<Point> points;
	private double stepSize;

	/**
	 * Berechnet Beziér-Kurven. Der Grad der Beziér-Kurve ist über die Zahl der
	 * Kontrollpunkte festgelegt.
	 * 
	 * @param points Kontrollpunkte.
	 * @param h      Schrittweite beim Zeichnen der Beziér-Kurve
	 */
	Bezier(List<Point> points, double h) {
		this.points = points;
		this.stepSize = h;
	}

	/**
	 * Berechne ein Punkt-Objekt, das die zweidimensionale Koordinate der
	 * Bézier-Kurve für einen gegebenen Parameterwert errechnet.
	 * 
	 * @param t Kurvenparameter
	 * @return Koordinate der Bézier-Kurve
	 */
	Point casteljau(double t) {
		int n = points.size() - 1;
		Point[][] matrix = new Point[n + 1][n + 1];

		// die erste Spalte der Matrix mit den Kontrollpunkten
		for (int i = 0; i <= n; i++) {
			matrix[i][0] = points.get(i);
		}

		// Casteljau-Algorithmus iterativ
		for (int j = 1; j <= n; j++) {
			for (int i = 0; i <= n - j; i++) {
				double alpha = 1 - t;
				double beta = t;
				Point p1 = matrix[i][j - 1];
				Point p2 = matrix[i + 1][j - 1];
				matrix[i][j] = new Point(alpha, p1, beta, p2);
			}
		}

		// Der gesuchte Punkt ist in der obersten Zeile der Matrix
		return matrix[0][n];
	}

	/**
	 * Zeichne eine Bezier-Kurve mit Stütz- und Kontrollpunkten aus points.
	 * 
	 * @param graphics Grafikobjekt
	 */
	void render(Graphics graphics) {
		if (points == null || points.size() < 2) {
			return; // Nichts zu zeichnen, wenn es weniger als 2 Kontrollpunkte gibt
		}

		// die Kurve Punkt für Punkt
		Point prevPoint = points.get(0); // Erster Kontrollpunkt
		for (double t = stepSize; t <= 1.0; t += stepSize) {
			Point currentPoint = casteljau(t);
			graphics.drawLine((int) prevPoint.x, (int) prevPoint.y, (int) currentPoint.x, (int) currentPoint.y);
			prevPoint = currentPoint;
		}
		// den letzten berechneten Punkt mit dem letzten Kontrollpunkt verbinden
		Point lastPoint = points.get(points.size() - 1);
		graphics.drawLine((int) prevPoint.x, (int) prevPoint.y, (int) lastPoint.x, (int) lastPoint.y);
	}
}
