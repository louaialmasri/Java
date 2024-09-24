import colorscheme.ColorSchemeBlackNull;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class MandelbrotTest {
	private static String expected1 = 
			"..........\n" + 
			"..........\n" + 
			"..........\n" + 
			"......X...\n" + 
			"......X...\n" + 
			"...XXXXXX.\n" + 
			"...XXXXXX.\n" +
			"X.XXXXXXXX\n" + 
			"XXXXXXXXXX\n" + 
			"XXXXXXXXX.\n";
	
	private static String expected2 =
			"..........\n" + 
			"..........\n" + 
			".....XXX..\n" + 
			"....X..XX.\n" + 
			"..XXXX.XXX\n" + 
			"XXX.......\n" + 
			"XXX......X\n" + 
			".X........\n" + 
			"..........\n" + 
			".........X\n";
	
	@Test
	public void testTransformGKOS2LKOS() {
		int width = 100;
		int height = 100;
		Mandelbrot mandelbrot = new Mandelbrot(null, width, height, -2.0, -1.5, 1.0, 1.5, 100, null);
		assertEquals("Umrechnung GKOS x=0 in LKOS fehlerhaft", -2.0, mandelbrot.transformPx(0), 1e-10);
		assertEquals("Umrechnung GKOS x=width in LKOS fehlerhaft", 1.0, mandelbrot.transformPx(width), 1e-10);
		assertEquals("Umrechnung GKOS y=height in LKOS fehlerhaft", -1.5, mandelbrot.transformPy(height), 1e-10);
		assertEquals("Umrechnung GKOS y=0 in LKOS fehlerhaft", 1.5, mandelbrot.transformPy(0), 1e-10);
		
		int[] coords = {10, 30, 60};
		double[] logx = {-1.7, -1.1, -0.2};
		for (int i = 0; i < coords.length; i++) {
			assertEquals("Umrechnung GKOS x=" + coords[i] + " in LKOS fehlerhaft", logx[i], mandelbrot.transformPx(coords[i]), 1e-10);
		}
		double[] logy = {1.2, 0.6, -0.3};
		for (int i = 0; i < coords.length; i++) {
			assertEquals("Umrechnung GKOS y=" + coords[i] + " in LKOS fehlerhaft", logy[i], mandelbrot.transformPy(coords[i]), 1e-10);
		}
	}
	
	@Test
	public void testMandelbrot() {
		int width = 10;
		int height = 10;
		Graphics bufferedGraphics = new BufferedGraphics(width, height);
		double xMin = -1.0;
		double yMin = -0.2;
		double xMax = 0.5;
		double yMax = 1.3;
		int maxiter = 100;
		
		Mandelbrot mandelbrot = new Mandelbrot(bufferedGraphics, width, height, xMin, yMin, xMax, yMax, maxiter,
				new ColorSchemeBlackNull(maxiter));
		mandelbrot.render();
		assertEquals("Zeichnen der Mandelbrotmenge (>= 100 Iterationen)", expected1, bufferedGraphics.toString());
		Mandelbrot mandelbrot2 = new Mandelbrot(bufferedGraphics, width, height, xMin, yMin, xMax, yMax, maxiter,
				new ColorSchemeBlackNull(maxiter, 5, 20));
		mandelbrot2.render();
		assertEquals("Zeichnen der Menge mit 5 - 20 Iterationsschritten", expected2, bufferedGraphics.toString());
	}
}
