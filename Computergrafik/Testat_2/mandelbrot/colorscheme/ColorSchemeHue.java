package colorscheme;
import java.awt.Color;

/**
 * Anzahl der Interation bestimmt Farbton (Hue).
 */
public class ColorSchemeHue extends ColorScheme {	
	public ColorSchemeHue(int maxiter) {
		super(maxiter);
	}

	@Override
	public Color colorForNumIterations(int iter) {
		if (iter == maxiter) {
			return Color.BLACK;
		} else {
			return Color.getHSBColor((float) iter / maxiter, 0.9f, 0.9f);
		}
	}
}
