import java.awt.Color;
import java.awt.Graphics;

class Huepfer {

	/** Zum Zeichnen in Panel */
	Graphics graphics;

	/** Breite und Höhe Zeichen-Panel */
	int width, height;

	/** Minimal-/Maximalkoordinaten des logischen Koordinatensystems (LKOS) */
	double xMin, xMax, yMin, yMax;

	/** Hüpfer-Parameter */
	double a, b, c;

	/** Anzahl Punkte */
	int num;

	public Huepfer(Graphics graphics,
				   int width, int height,
				   double xMin, double xMax, double yMin, double yMax,
				   double a, double b, double c,
				   int num) {
		super();
		this.graphics = graphics;
		this.width = width;
		this.height = height;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.a = a;
		this.b = b;
		this.c = c;
		this.num = num;
	}

	/**
	 * Methode zum Zeichnen eines Pixels.
	 *
	 * HACK: Zeichne Pixel als Linie der Länge 0. Es gibt in Java2D keine Methode
	 * zum Zeichnen eines einzelnen Pixels!
	 *
	 * @param graphics Grafik-Kontext
	 * @param x x-Koordinate
	 * @param y y-Koordinate
	 */
	void setPixel(int x, int y) {
		graphics.drawLine(x, y, x, y);
	}

	/**
	 * Wandle LKOS-Koordinate in GKOS-Koordinate um.
	 *
	 * @param x LKOS-Koordinate
	 * @return GKOS-Koordinate
	 */
	int transformX(double x) {
		x = width/(xMax - xMin) * (x - xMin);
		return (int)x;
	}

	/**
	 * Wandle LKOS-Koordinate in GKOS-Koordinate um.
	 *
	 * @param y LKOS-Koordinate
	 * @return GKOS-Koordinate
	 */

	int transformY(double y) {
		y = height/(yMin - xMax) * (y - xMax);
		return (int)y;
	}

	public void render() {
		// Zusammenarbeit mit tip0698@thi.de und mab1193@thi.de
		graphics.setColor(Color.WHITE);
		double x = 0;
		double y = 0;

		for (int i = 0; i <= num; i++) {
			int ScreenX = transformX(x);
			int ScreenY = transformY(y);

			setPixel(ScreenX, ScreenY);

			double xx = y - Math.signum(x) * Math.sqrt(Math.abs(b * x - c));
			double yy = a - x;
			x = xx;
			y = yy;

		}

	}
}