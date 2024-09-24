package colorscheme;

import java.awt.Color;

/**
 * Implementiert ein Farbschema zum Einfärben der Mandelbrot-Menge.
 */
public abstract class ColorScheme {
	protected int maxiter;

	/**
	 * Um eine Fallunterscheidung (Mandelbort ja/nein) durchführen zu können, müssen
	 * wir die Maximalzahl der Iterationen kennen.
	 * 
	 * @param maxiter Maximalzahl Iterationen
	 */
	public ColorScheme(int maxiter) {
		this.maxiter = maxiter;
	}

	/**
	 * Liefere einen Farbwert in Abhängigkeit von der Zahl der Iterationsschritte
	 * @param iter Anzahl Iterationsschritte
	 * @return Color-Objekt
	 */
	public abstract Color colorForNumIterations(int iter);
}
