package colorscheme;
import java.awt.Color;

/**
 * Anzahl der Interation bestimmt Intensität (Brightness oder Intensity oder Value).
 */
public class ColorSchemeBrightness extends ColorScheme {	
	public ColorSchemeBrightness(int maxiter) {
		super(maxiter);
	}

	@Override
	public Color colorForNumIterations(int iter) {
		if (iter == maxiter) {
			return Color.BLACK;
		} else {
			return Color.getHSBColor(0.9f, 0.9f, (float) iter / maxiter);
		}
	}
}
