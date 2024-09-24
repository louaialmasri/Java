import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

public class BezierTest {
	@Test
	public void testCasteljau() {
		@SuppressWarnings("serial")
		ArrayList<Point> linear = new ArrayList<Point>() {{
			this.add(new Point(0.0, 0.0));
			this.add(new Point(1.0, 1.0));
		}};
		Bezier linearBezier = new Bezier(linear, 0.0);
		double[][] linearTestCases = {
			{0.0, 0.0, 0.0},
			{0.2, 0.2, 0.2},
			{0.4, 0.4, 0.4},
			{0.6, 0.6, 0.6},
			{0.8, 0.8, 0.8},
			{1.0, 1.0, 1.0},			
		};
		for (int tc = 0; tc < linearTestCases.length; tc++) {
			Point point = linearBezier.casteljau(linearTestCases[tc][0]);
			assertEquals("x-Wert lineare Beziérkurve für t = " + linearTestCases[tc][0], linearTestCases[tc][1], point.x, 1e-10);
			assertEquals("y-Wert lineare Beziérkurve für t = " + linearTestCases[tc][0], linearTestCases[tc][2], point.y, 1e-10);
		}
		@SuppressWarnings("serial")
		ArrayList<Point> quadratic = new ArrayList<Point>() {{
			this.add(new Point(0.0, 0.0));
			this.add(new Point(1.0, 0.0));
			this.add(new Point(1.0, 1.0));
		}};
		Bezier quadraticBezier = new Bezier(quadratic, 0.0);		
		double[][] quadraticTestCases = {
				{0.0, 0.0, 0.0},
				{0.2, 0.36, 0.04},
				{0.4, 0.64, 0.16},
				{0.6, 0.84, 0.36},
				{0.8, 0.96, 0.64},
				{1.0, 1.0, 1.0},			
			};
			for (int tc = 0; tc < quadraticTestCases.length; tc++) {
				Point point = quadraticBezier.casteljau(quadraticTestCases[tc][0]);
				assertEquals("x-Wert quadratische Beziérkurve für t = " + quadraticTestCases[tc][0], quadraticTestCases[tc][1], point.x, 1e-10);
				assertEquals("y-Wert quadratische Beziérkurve für t = " + quadraticTestCases[tc][0], quadraticTestCases[tc][2], point.y, 1e-10);
			}
		@SuppressWarnings("serial")
		ArrayList<Point> cubic = new ArrayList<Point>() {{
			this.add(new Point(0.0, 0.0));
			this.add(new Point(1.0, 0.0));
			this.add(new Point(1.0, 1.0));
			this.add(new Point(0.0, 1.0));
		}};
		Bezier cubicBezier = new Bezier(cubic, 0.0);
		double[][] cubicTestCases = {
				{0.0, 0.0, 0.0},
				{0.2, 0.48, 0.104},
				{0.4, 0.72, 0.352},
				{0.6, 0.72, 0.648},
				{0.8, 0.48, 0.896},
				{1.0, 0.0, 1.0},			
			};
			for (int tc = 0; tc < cubicTestCases.length; tc++) {
				Point point = cubicBezier.casteljau(cubicTestCases[tc][0]);
				assertEquals("x-Wert kubische Beziérkurve für t = " + cubicTestCases[tc][0], cubicTestCases[tc][1], point.x, 1e-10);
				assertEquals("y-Wert kubische Beziérkurve für t = " + cubicTestCases[tc][0], cubicTestCases[tc][2], point.y, 1e-10);
			}
	}
	
	@Test
	public void testBezier() {
		@SuppressWarnings("serial")
		ArrayList<Point> points = new ArrayList<Point>() {{
			this.add(new Point(4.0, 4.0));
			this.add(new Point(4.0, 12.0));
			this.add(new Point(12.0, 4.0));
			this.add(new Point(16.0, 12.0));
			this.add(new Point(8.0, 16.0));
		}};
		Bezier bezier = new Bezier(points, 0.10001);
		BufferedGraphics bufferedGraphics = new BufferedGraphics(20, 20);
		bufferedGraphics.setColor(Color.BLACK);
		bezier.render(bufferedGraphics);
		String expected = 
				"....................\n" + 
				"....................\n" + 
				"....................\n" + 
				"....................\n" + 
				"....X...............\n" + 
				"....X...............\n" + 
				"....X...............\n" + 
				".....XXX............\n" + 
				"........XXX.........\n" + 
				"...........X........\n" + 
				"............X.......\n" + 
				"............X.......\n" + 
				"...........X........\n" + 
				"...........X........\n" + 
				"..........X.........\n" + 
				".........X..........\n" + 
				"........X...........\n" + 
				"....................\n" + 
				"....................\n" + 
				"....................\n";
		assertEquals("Beziér-Kurve", expected, bufferedGraphics.toString());
	}
}
