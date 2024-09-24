import java.awt.Graphics;

/**
 * Clipping nach Cohen-Sutherland.
 */
public class CohenSutherland {
	/** Zum Zeichnen */
	private Graphics graphics;

	/** Dimension des Clipping-Rechtecks */
	private int xmin;
	private int xmax;
	private int ymin;
	private int ymax;

	/**
	 * Ctor.
	 * 
	 * @param graphics Zum Zeichnen
	 * @param xmin     minimale x-Koordinate
	 * @param ymin     minimale y-Koordinate
	 * @param xmax     maximale x-Koordinate
	 * @param ymax     maximale y-Koordinate
	 */
	public CohenSutherland(Graphics graphics, int xmin, int ymin, int xmax, int ymax) {
		super();
		this.graphics = graphics;
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}

	/**
	 * Berechne den Cohen-Sutherland-Outputcode für einen Punkt.
	 * 
	 * @formatter:off
	 * viertletztes Bit = 1 <=> y > ymax 
	 * drittletztes Bit = 1 <=> y < ymin
	 * vorletztes Bit = 1 <=> x > xmax 
	 * letztes Bit = 1 <=> x < xmin
	 * @formatter:on
	 * 
	 * Die 4 Bits werden sehr verschwenderisch in einem int untergebracht.
	 * 
	 * Warum kein byte? Die bitweisen Operationen sind für Datentyp byte nicht
	 * definiert! Genauer werden z.B. die Bytes bei byte1 | byte2 zu ints gecastet
	 * und das Ergebnis ist ein int.
	 * Mehr Details: <a href="https://stackoverflow.com/questions/27582233/why-byte-and-short-values-are-promoted-to-int-when-an-expression-is-evaluated">Stack Overflow</a>
	 * 
	 * @param x x-Koordinate Punkt
	 * @param y y-Koordinate Punkt
	 * @return Outputcode
	 */
	int outputCode(int x, int y) {

		int result = 0;

		if (y > ymax) {
			result += Area.GTYMAX;
		}
		else if (y < ymin) {
			result += Area.LTYMIN;
		}
		if (x > xmax) {
			result += Area.GTXMAX;
		}
		else if (x < xmin) {
			result += Area.LTXMIN;
		}
		return result;
	}

	/**
	 * Clipping nach Cohen-Sutherland. Die Linie von (xA,yA) nach (xE,yE) wird an
	 * dem durch die Attribute (xmin,ymin) und (xmax,ymax) definierten Rechteck
	 * geclippt und der sichtbare Teil der Linie gezeichnet.
	 * 
	 * @param xA x-Koordinate Anfangspunkt Linie
	 * @param yA y-Koordinate Anfangspunkt Linie
	 * @param xE x-Koordinate Endpunkt Linie
	 * @param yE y-Koordinate Endpunkt Linie
	 */


	// Zusammenarbeit mit tip0698@thi.de und mab1193@thi.de
	void clipLine(int xA, int yA, int xE, int yE) {
		int K_A = outputCode(xA, yA);
		int K_E = outputCode(xE, yE);

		if ((K_A & K_E) != 0) {
			// Linie ist unsichtbar, daher nichts zu zeichnen
			return;
		}

		if ((K_A | K_E) == 0) {
			// Linie ist vollständig sichtbar, daher zeichne die Linie
			graphics.drawLine(xA, yA, xE, yE);
			return;
		}

		// Berechne die Schnittpunkte mit den Clipping-Kanten
		int x1 = xA, y1 = yA, x2 = xE, y2 = yE;

		if ((K_A & 8) != 0) { // Schnittpunkt mit ymax
			x1 = xA + (xE - xA) * (ymax - yA) / (yE - yA);
			y1 = ymax;
		} else if ((K_A & 4) != 0) { // Schnittpunkt mit ymin
			x1 = xA + (xE - xA) * (ymin - yA) / (yE - yA);
			y1 = ymin;
		} else if ((K_A & 2) != 0) { // Schnittpunkt mit xmax
			y1 = yA + (yE - yA) * (xmax - xA) / (xE - xA);
			x1 = xmax;
		} else if ((K_A & 1) != 0) { // Schnittpunkt mit xmin
			y1 = yA + (yE - yA) * (xmin - xA) / (xE - xA);
			x1 = xmin;
		}

		if ((K_E & 8) != 0) { // Schnittpunkt mit ymax
			x2 = xA + (xE - xA) * (ymax - yA) / (yE - yA);
			y2 = ymax;
		} else if ((K_E & 4) != 0) { // Schnittpunkt mit ymin
			x2 = xA + (xE - xA) * (ymin - yA) / (yE - yA);
			y2 = ymin;
		} else if ((K_E & 2) != 0) { // Schnittpunkt mit xmax
			y2 = yA + (yE - yA) * (xmax - xA) / (xE - xA);
			x2 = xmax;
		} else if ((K_E & 1) != 0) { // Schnittpunkt mit xmin
			y2 = yA + (yE - yA) * (xmin - xA) / (xE - xA);
			x2 = xmin;
		}
		// Rekursiv die geclipte Linie zeichnen
		clipLine(x1, y1, x2, y2);

	}

}
