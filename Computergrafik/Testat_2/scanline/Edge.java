public class Edge {
	/** y-Koordinate des unteren Eckpunkts der Kante */
	private int yMin;
	/** y-Koordinate des oberen Eckpunkts der Kante */
	private int yMax;
	/** Kehrwert der Steigung der Kante */
	private double mReci;
	/**
	 * x-Koordinate Schnitt Scan-Line mit Kante, initial die x-Koordinate von yMin
	 */
	private double xIntersect;

	/**
	 * Generiere Kante
	 * 
	 * @param x0       x-Koordinate Startpunkt
	 * @param y0       y-Koordinate Startpunkt
	 * @param x1       x-Koordinate Endpunkt
	 * @param y1       y-Koordinate Endpunkt
	 * @param diminish
	 */
	public Edge(int x0, int y0, int x1, int y1, boolean diminish) {
		if (y0 < y1) {
			yMin = y0;
			xIntersect = x0;
			yMax = y1;
		} else if (y1 < y0) {
			yMin = y1;
			xIntersect = x1;
			yMax = y0;
		}
		if (x1 == x0) {
			// vertikale Kante
			mReci = 0.0;
		} else {
			// Kehrwert der Steigung
			mReci = (double) (x1 - x0) / (y1 - y0);
		}
		if (diminish) {
			// Bei aufeinanderfolgenden streng monotonen Kanten yMax um 1 verringern, damit
			// keine falsche Parität der Zahl der Schnittpunkte entsteht
			yMax--;
		}
	}

	public Edge(int x0, int y0, int x1, int y1) {
		this(x0, y0, x1, y1, false);
	}

	/**
	 * Copy constructor
	 * 
	 * @param edge
	 */
	public Edge(Edge edge) {
		this.yMin = edge.yMin;
		this.yMax = edge.yMax;
		this.xIntersect = edge.xIntersect;
		this.mReci = edge.mReci;
	}

	public int getYMin() {
		return yMin;
	}

	public int getyMin() {
		return yMin;
	}

	public void setyMin(int yMin) {
		this.yMin = yMin;
	}

	public void setxIntersect(double xIntersect) {
		this.xIntersect = xIntersect;
	}

	public double getmReci() {
		return mReci;
	}

	public int getyMax() {
		return yMax;
	}

	public double getxIntersect() {
		return xIntersect;
	}

	@Override
	public String toString() {
		return "[y: " + yMin + " -- " + yMax + ", xschnitt: " + xIntersect + ", 1/m: " + mReci + "]";
	}
}
