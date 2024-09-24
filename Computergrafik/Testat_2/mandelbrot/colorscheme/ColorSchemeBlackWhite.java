package colorscheme;

import java.awt.Color;

/**
 * Färbe Mandelbortpunkte schwarz, alles andere weiß.
 */
public class ColorSchemeBlackWhite extends ColorScheme {

	public ColorSchemeBlackWhite(int maxiter) {
		super(maxiter);
	}

	@Override
	public Color colorForNumIterations(int iter) {
		if (iter == maxiter) {
			return Color.BLACK;
		} else {
			return Color.WHITE;
		}
	}

}
