/**
 * Sternen-Polygon
 */
public class Star extends Polygon {
	/**
	 * By the power of math erzeugtes Sternen-Polygon
	 * @param midx x-Koordinate Mittelpunkt
	 * @param midy y-Koordinate Mittelpunkt
	 * @param rInner Enfernung der inneren Eckpunkte vom Mittelpunkt
	 * @param rOuter Enfernung der äußeren Eckpunkte vom Mittelpunkt
	 */
	public Star(double midx, double midy, double rInner, double rOuter) {
		this.coordinates = new int[10][2];
		double ang = 2.0 * Math.PI / 5.0;
		for (int cusp = 0; cusp < 5; cusp++) {
			this.coordinates[2 * cusp][0] = (int) (Math.cos(cusp * ang) * rOuter + midx + 0.5);
			this.coordinates[2 * cusp][1] = (int) (Math.sin(cusp * ang) * rOuter + midy + 0.5);
			this.coordinates[2 * cusp + 1][0] = (int) (Math.cos((cusp + 0.5) * ang) * rInner + midx + 0.5);
			this.coordinates[2 * cusp + 1][1] = (int) (Math.sin((cusp + 0.5) * ang) * rInner + midy + 0.5);
		}
	}
}
