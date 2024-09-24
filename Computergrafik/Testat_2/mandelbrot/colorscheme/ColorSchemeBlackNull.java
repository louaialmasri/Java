package colorscheme;

import java.awt.Color;

/**
 * Für die grafische Ausgabe mit dem JPanel ungeeignetes Farbschema, nur für die
 * JUnit-Tests.
 */
public class ColorSchemeBlackNull extends ColorScheme {
	/** Bereich der Iterationszahlen der angezeigt werden soll */
	private int from;
	private int to;

	public ColorSchemeBlackNull(int maxiter, int from, int to) {
		super(maxiter);
		this.from = from;
		this.to = to;
	}
	
	public ColorSchemeBlackNull(int maxiter) {
		this(maxiter, maxiter, maxiter);
	}

	@Override
	public Color colorForNumIterations(int iter) {
		if (iter >= from && iter <=to) {
			return Color.BLACK;
		} else {
			return null;
		}
	}
}
