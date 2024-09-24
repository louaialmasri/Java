package colorscheme;
import java.awt.Color;

/**
 * Anzahl der Interation bestimmt Sättigung (Saturation).
 */
public class ColorSchemeSaturation extends ColorScheme {	
	public ColorSchemeSaturation(int maxiter) {
		super(maxiter);
	}

	@Override
	public Color colorForNumIterations(int iter) {
		if (iter == maxiter) {
			return Color.BLACK;
		} else {
			return Color.getHSBColor(0.9f, (float) iter / maxiter, 0.9f);
		}
	}
}
