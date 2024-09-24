import java.awt.Graphics;

import colorscheme.ColorScheme;
import colorscheme.ColorSchemeBlackNull;

public class Mandelbrot {
	/** Graphics-Objekt zum Zeichnen */
	private Graphics graphics;
	/** Fensterdimensionen */
	private int width;
	private int height;
	/** Minimal- und Maximalkoordinaten des logischen Koordinatensystems */
	private double xMin;
	private double yMin;
	private double xMax;
	private double yMax;
	/**
	 * Maximalzahl der Iterationen pro Bildpunkt, falls erreicht, handelt es sich um
	 * einen Punkt der Mandelbrotmenge
	 */
	private int maxiter;
	/** Farbschema */
	private ColorScheme colorScheme;

	/**
	 * Initialisiere den Mandelbrot-Renderer.
	 * 
	 * @param graphics    Graphics-Objekt zum Zeichnen
	 * @param width       Breite Fenster
	 * @param height      Höhe Fenster
	 * @param xMin        minimale logische x-Koordinate
	 * @param yMin        minimale logische y-Koordinate
	 * @param xMax        maximale logische x-Koordinate
	 * @param yMax        maximale logische y-Koordinate
	 * @param maxiter     maximale Zahl der Iterationen
	 * @param colorScheme ein Farbschema aus dem Paket colorscheme
	 */
	public Mandelbrot(Graphics graphics, int width, int height, double xMin, double yMin, double xMax, double yMax,
			int maxiter, ColorScheme colorScheme) {
		this.graphics = graphics;
		this.width = width;
		this.height = height;
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.maxiter = maxiter;
		this.colorScheme = colorScheme;
	}

	/**
	 * Methode zum Zeichnen eines Pixels.
	 * 
	 * HACK: Zeichne Pixel als Linie der Länge 0. Es gibt in Java2D keine Methode
	 * zum Zeichnen eines einzelnen Pixels!
	 * 
	 * @param g Grafik-Kontext
	 * @param x x-Koordinate
	 * @param y y-Koordinate
	 */
	private void setPixel(Graphics g, int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height)
			g.drawLine(x, y, x, y);
	}

	/**
	 * Wandle GKOS-Koordinate in LKOS-Koordinate um.
	 * 
	 * @param px GKOS-Koordinate
	 * @return LKOS-Koordinate
	 */
	double transformPx(int px) {
		double x = (px / (width/(xMax - xMin)) + xMin);
		return x;
	}

	/**
	 * Wandle GKOS-Koordinate in LKOS-Koordinate um.
	 * 
	 * @param py GKOS-Koordinate
	 * @return LKOS-Koordinate
	 */
	double transformPy(int py) {
		double y = (py / (height/(yMin - yMax)) + yMax);
		return y;
	}

	/**
	 * Zeichnen der Mandelbrotmenge
	 */

	// Zusammenarbeit mit tip0698@thi.de und mab1193@thi.de
	public void render() {
		// Iterieren über komplexe Zahlenebene
		double z_re;
		double z_im;
		double c_re, c_im;

		for (int px = 0; px < width; px++) {
			for (int py = 0; py < height; py++) {
				// Zähler für die Anzahl der Iterationen
				int iter = 0;
				// TODO 1: Hier aus px und py Real- und Imaginärteil einer komplexen Zahl c
				// ausrechnen.
				// Real- und Imaginärteil von z initialisieren (s. Angabe).

				z_re = 0;
				z_im = 0;

				c_re = transformPx(px);
				c_im = transformPy(py);

				// TODO 2: Hier Schleife einfügen, die solange ausgeführt wird, wie |z|*|z| < 4 und
				// Maximalzahl Iterationen noch nicht überschritten. Im Schleifenrumpf soll die
				// komplexe Zahl z nach der Formel z = z*z + c aktualisiert werden.
				while ((z_re*z_re + z_im*z_im) < 4 && iter < maxiter) {
					double z1_re = (z_re*z_re) - (z_im*z_im);
					double z1_im = 2*(z_re*z_im);
					z_re = z1_re + c_re;
					z_im = z1_im + c_im;
					iter++;
				}

				graphics.setColor(colorScheme.colorForNumIterations(iter));
				setPixel(graphics, px, py);
			}
		}
	}
}