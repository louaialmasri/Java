import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.LinkedList;

import org.testng.annotations.Test;

public class BSplineTest {

	/**
	 * Periodisch uniforme Basisfunktion N_{1,2}
	 * 
	 * @param t aus [1,2]
	 * @return N_{1,2}(t)
	 */
	private double periodic_lin1(double t) {
		return t - 1.0;
	}

	/**
	 * Periodisch uniforme Basisfunktion N_{1,2}
	 * 
	 * @param t aus [2,3]
	 * @return N_{1,2}(t)
	 */
	private double periodic_lin2(double t) {
		return 3.0 - t;
	}

	// N_{1,3}

	/**
	 * Periodisch uniforme Basisfunktion N_{1,3}
	 * 
	 * @param t aus [1,2]
	 * @return N_{1,3}(t)
	 */
	private double periodic_quad1(double t) {
		return 0.5 * (t - 1.0) * (t - 1.0);
	}

	/**
	 * Periodisch uniforme Basisfunktion N_{1,3}
	 * 
	 * @param t aus [2,3]
	 * @return N_{1,3}(t)
	 */
	private double periodic_quad2(double t) {
		return 0.75 - (t - 2.5) * (t - 2.5);
	}

	/**
	 * Periodisch uniforme Basisfunktion N_{1,3}
	 * 
	 * @param t aus [3,4]
	 * @return N_{1,3}(t)
	 */
	private double periodic_quad3(double t) {
		return 0.5 * (4.0 - t) * (4.0 - t);
	}

	/**
	 * Testet, ob die N_{i,k} für periodische uniforme B-Splines richtig berechnet
	 * werden.
	 */
	@Test
	public void testNikPeriodicBSpline() {
		// Parameter points ist hier unwichtig, da nur Konstruktion der N_{i,k}
		// überprüft wird, auch h wird ignoriert.
		BSpline bspline = new BSpline(null, 3, 0.001) {
			{
				this.knotVector = new double[] { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0 };
			}
		};
		// Skript s. "Darstellung Basisfunktionen" N_{1,k} für Knotenvektor T = [0, 1,
		// 2, 3, 4, 5, 6, 7] und unterschiedliche k

		// N_{1,1}
		assertEquals("Stückweise konstante Basisfunktion", 1.0, bspline.nik(1, 1, 1.667), 1e-10);
		assertEquals("Stückweise konstante Basisfunktion", 0.0, bspline.nik(1, 1, 2.333), 1e-10);

		// N_{1,2}
		for (double t = 1.0; t <= 2.0; t += 0.2) {
			assertEquals("Stückweise lineare Basisfunktion t = " + t, periodic_lin1(t), bspline.nik(1, 2, t), 1e-10);
		}
		for (double t = 2.0; t <= 3.0; t += 0.2) {
			assertEquals("Stückweise lineare Basisfunktion t = " + t, periodic_lin2(t), bspline.nik(1, 2, t), 1e-10);
		}

		// N_{1,3}
		for (double t = 1.0; t <= 2.0; t += 0.2) {
			assertEquals("Stückweise quadratische Basisfunktion t = " + t, periodic_quad1(t), bspline.nik(1, 3, t),
					1e-10);
		}
		for (double t = 2.0; t <= 3.0; t += 0.2) {
			assertEquals("Stückweise quadratische Basisfunktion t = " + t, periodic_quad2(t), bspline.nik(1, 3, t),
					1e-10);
		}
		for (double t = 3.0; t <= 4.0; t += 0.2) {
			assertEquals("Stückweise quadratische Basisfunktion t = " + t, periodic_quad3(t), bspline.nik(1, 3, t),
					1e-10);
		}
	}

	// Die expliziten nicht-periodisch uniformen Basisfunktionen (nicht vollständig,
	// rechte Hälfte von N_{2,4} fehlt, N_{3,4} und N_{4,4} komplett.

	/**
	 * Nicht-Periodisch uniforme Basisfunktion N_{0,4}
	 * 
	 * @param t aus [0,1]
	 * @return N_{0,4}(t)
	 */
	private double nonperiodic_cub1(double t) {
		return (1.0 - t) * (1.0 - t) * (1.0 - t);
	}

	/**
	 * Nicht-Periodisch uniforme Basisfunktion N_{1,4}
	 * 
	 * @param t aus [0,1]
	 * @return N_{1,4}(t)
	 */
	private double nonperiodic_cub2(double t) {
		return 0.25 * t * (7.0 * t * t - 18.0 * t + 12.0);
	}

	/**
	 * Nicht-Periodisch uniforme Basisfunktion N_{1,4}
	 * 
	 * @param t aus [1,2]
	 * @return N_{1,4}(t)
	 */
	private double nonperiodic_cub3(double t) {
		return 0.25 * (2.0 - t) * (2.0 - t) * (2.0 - t);
	}

	/**
	 * Nicht-Periodisch uniforme Basisfunktion N_{2,4}
	 * 
	 * @param t aus [0,1]
	 * @return N_{2,4}(t)
	 */
	private double nonperiodic_cub4(double t) {
		return 0.5 * t * t * (3.0 - 2.0 * t);
	}

	/**
	 * Testet, ob die N_{i,k} für nicht-periodische uniforme B-Splines richtig
	 * berechnet werden.
	 */
	@Test
	public void testNikNonPeriodicBSpline() {
		// Skript s. "Einfluss Knotenvektor: Nicht-Periodische uniforme
		// B-Splinefunktionen" für Knotenvektor T = [0,0,0,0,1,2,2,2,2] und k = 4

		// Parameter points ist hier unwichtig, da nur Konstruktion der N_{i,k}
		// überprüft wird, auch h wird ignoriert.
		BSpline bspline = new BSpline(null, 4, 0.001) {
			{
				this.knotVector = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 2.0, 2.0, 2.0, 2.0 };
			}
		};
		// N_{0,4}
		for (double t = 0.0; t <= 1.0; t += 0.2) {
			assertEquals("Stückweise kubische Basisfunktion t = " + t, nonperiodic_cub1(t), bspline.nik(0, 4, t),
					1e-10);
		}
		// N_{1,4}
		for (double t = 0.0; t <= 1.0; t += 0.2) {
			assertEquals("Stückweise kubische Basisfunktion t = " + t, nonperiodic_cub2(t), bspline.nik(1, 4, t),
					1e-10);
		}
		for (double t = 1.0; t <= 2.0; t += 0.2) {
			assertEquals("Stückweise kubische Basisfunktion t = " + t, nonperiodic_cub3(t), bspline.nik(1, 4, t),
					1e-10);
		}
		// N_{2,4}
		for (double t = 0.0; t <= 1.0; t += 0.2) {
			assertEquals("Stückweise kubische Basisfunktion t = " + t, nonperiodic_cub4(t), bspline.nik(2, 4, t),
					1e-10);
		}
	}

	/**
	 * Testet, ob die Knotenvektoren richtig erstellt werden.
	 */
	@Test
	public void testKnotVectorPeriodicUniformBSpline() {
		@SuppressWarnings("serial")
		List<Point> points = new LinkedList<Point>() {
			{
				// Pseudo-Punkte
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
			}
		};
		// nur Zahl der Punkte wichtig, Parameter h wird ignoriert
		BSpline periodic = new PeriodicUniformBSpline(points, 3, 0.001);
		assertArrayEquals("Knotenvektor periodische uniforme B-Spline",
				new double[] { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0 },
				periodic.knotVector, 1e-10);
	}

	/**
	 * Testet, ob die Knotenvektoren richtig erstellt werden.
	 */
	@Test
	public void testKnotVectorNonPeriodicUniformBSpline() {
		@SuppressWarnings("serial")
		List<Point> points = new LinkedList<Point>() {
			{
				// Pseudo-Punkte
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
				this.add(new Point(0.0, 0.0));
			}
		};
		// nur Zahl der Punkte wichtig, Parameter h wird ignoriert
		BSpline nonPeriodic = new NonPeriodicUniformBSpline(points, 4, 0.001);
		assertArrayEquals("Knotenvektor nicht-periodische uniforme B-Spline",
				new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 6.0, 6.0, 6.0 },
				nonPeriodic.knotVector, 1e-10);
		
		// TODO: zus. Test mit k=5
	}

	@Test
	public void testPeriodicUniformBSpline() {
		@SuppressWarnings("serial")
		List<Point> points = new LinkedList<Point>() {
			{
				this.add(new Point(0.0, 0.0));
				this.add(new Point(1.0, -1.0));
				this.add(new Point(1.0, 1.0));
				this.add(new Point(-1.0, 1.0));
				this.add(new Point(0.0, 2.0));
				this.add(new Point(2.0, 1.0));
			}
		};
		// Parameter h wird ignoriert
		BSpline periodic = new PeriodicUniformBSpline(points, 4, 0.001);
		double[] testParams = { 3.0, 3.5, 4.0, 4.5, 5.0, 5.5, 6.0 };
		Point[] expected = { new Point(0.8333333333333333, -0.5), new Point(0.9375, 0.020833333333333332),
				new Point(0.6666666666666666, 0.6666666666666666), new Point(0.020833333333333315, 0.9791666666666666),
				new Point(-0.5, 1.1666666666666665), new Point(-0.4166666666666667, 1.4791666666666667),
				new Point(0.16666666666666666, 1.6666666666666667) };
		for (int tp = 0; tp < testParams.length; tp++) {
			Point p = periodic.bSpline(testParams[tp]);
			assertEquals("x-Koordinate Punkt auf periodischer uniformer B-Spline für t = " + testParams[tp],
					expected[tp].x, p.x, 1e-10);
			assertEquals("y-Koordinate Punkt auf periodischer uniformer B-Spline für t = " + testParams[tp],
					expected[tp].y, p.y, 1e-10);
		}
	}
}
