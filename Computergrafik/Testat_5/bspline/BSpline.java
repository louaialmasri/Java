import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

abstract class BSpline {
	
	/** Ordnung der Spline-Kurve (= Grad + 1) */
	protected int k;

	/** Knotenvektor */
	protected double[] knotVector;

	/** Auflösung der Kurve, Schrittweite, Zeichengenauigkeit */
	private double h;
	
	/**
	 * Eine Liste von Point-Objekten Dies sind die Stütz- und Kontrollpunkte
	 */
	protected List<Point> points;

	/**
	 * B-Spline-Kurve 
	 * 
	 * @param points Kontrollpunkte
	 * @param k Grad der B-Spline + 1
	 * @param h Schrittweite beim Zeichnen der B-Spline
	 */
	BSpline(List<Point> points, int k, double h) {
		this.k = k;
		this.h = h;
		if (points == null) {
			this.points = new LinkedList<>();
		} else {
			this.points = points;
		}
		this.knotVector = new double[this.points.size() + k];
	}


	/**
	 * Zeichne eine B-Spline mit Stütz- und Kontrollpunkten aus points.
	 * 
	 * @param graphics Grafikobjekt
	 */
	// In Zusammenarbeit mit mab1193@thi.de
	void render(Graphics graphics) {
		int n = points.size() - 1;
		double tMin = knotVector[k - 1];
		double tMax = knotVector[n + 1];

		Point prevPoint = bSpline(tMin);

		for (double t = tMin + h; t <= tMax; t += h) {
			Point nextPoint = bSpline(t);
			graphics.drawLine((int) prevPoint.x, (int) prevPoint.y, (int) nextPoint.x, (int) nextPoint.y);
			prevPoint = nextPoint;
		}

		// Ensure the last point is drawn
		Point lastPoint = bSpline(tMax);
		graphics.drawLine((int) prevPoint.x, (int) prevPoint.y, (int) lastPoint.x, (int) lastPoint.y);
	}
	/**
	 * Berechne ein Punkt-Objekt, das die zweidimensionale Koordinate der
	 * BSpline-Kurve für einen gegebenen Parameterwert errechnet.
	 * 
	 * @param t Kurvenparameter
	 * @return 2D-Koordinate der B-Spline-Kurve für Parameter t
	 */
	Point bSpline(double t) {
		int n = points.size() - 1;
		Point result = new Point(0, 0);

		for (int i = 0; i <= n; i++) {
			double nikValue = nik(i, k, t);
			result.x += nikValue * points.get(i).x;
			result.y += nikValue * points.get(i).y;
		}

		return result;
	}
	/**
	 * Berechne den Wert der B-Spline-Basisfunktion N_{i,k}(t) analog zu Casteljau.
	 * 
	 * @param i Index der B-Spline-Basisfunktion N_{i,k}
	 * @param k Grad der B-Spline-Basisfunktion N_{i,k}
	 * @param t Parameter, an dem N_{i,k} ausgewertet wird
	 * @return N_{i,k}(t)
	 */
	private double[][] nikTable;

	double nik(int i, int k, double t) {
		int n = knotVector.length - 1;

		nikTable = new double[n + 1][k + 1];

		// Basis case: k = 1
		for (int j = 0; j <= n; j++) {
			if (knotVector[j] <= t && t < knotVector[j + 1]) {
				nikTable[j][1] = 1;
			} else {
				nikTable[j][1] = 0;
			}
		}

		for (int d = 2; d <= k; d++) {
			for (int j = 0; j <= n - d + 1; j++) {
				double left = 0, right = 0;
				if (nikTable[j][d - 1] != 0) {
					left = (t - knotVector[j]) / (knotVector[j + d - 1] - knotVector[j]) * nikTable[j][d - 1];
				}
				if (nikTable[j + 1][d - 1] != 0) {
					right = (knotVector[j + d] - t) / (knotVector[j + d] - knotVector[j + 1]) * nikTable[j + 1][d - 1];
				}
				nikTable[j][d] = left + right;
			}
		}
		return nikTable[i][k];
	}
}
