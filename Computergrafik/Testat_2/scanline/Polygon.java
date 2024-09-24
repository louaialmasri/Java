import java.util.LinkedList;

/**
 * Über Pixelkoordinaten spezifiziertes Polynom.
 * 
 * Erstes und letztes Koordnatenpaar werden zyklisch verbunden
 */
public class Polygon {
	/**
	 * Die Koordinaten-Päärchen: coordinates[i][0] ist x- und coordinates[i][1]
	 * y-Koordinate.
	 */
	protected int[][] coordinates;

	public Polygon() {
	}

	public Polygon(int[][] coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Kanten in der Datenstruktur für den Scanline-Algorithmus zurückliefern.
	 * 
	 * @return Kanten für Edge Table
	 */
	public LinkedList<Edge> getEdges() {
		LinkedList<Edge> ret = new LinkedList<>();
		int length = coordinates.length;
		for (int i = 0; i < length; i++) {
			// Horizontale Kanten ignorieren
			if (coordinates[i][1] == coordinates[(i + 1) % length][1]) {
				continue;
			}
			// Bei aufeinanderfolgenden Kanten, bei denen der y-Wert monoton steigt oder
			// fällt, maxy der unteren Kante um 1 reduzieren
			if (coordinates[i][1] <= coordinates[(i + 1) % length][1]
					&& coordinates[(i + 1) % length][1] <= coordinates[(i + 2) % length][1]) {
				ret.add(new Edge(coordinates[i][0], coordinates[i][1], coordinates[(i + 1) % length][0],
						coordinates[(i + 1) % length][1], true));
			} else if (coordinates[i][1] <= coordinates[(i + length - 1) % length][1]
					&& coordinates[(i + 1) % length][1] <= coordinates[i][1]) {
				ret.add(new Edge(coordinates[i][0], coordinates[i][1], coordinates[(i + 1) % length][0],
						coordinates[(i + 1) % length][1], true));
			} else {
				ret.add(new Edge(coordinates[i][0], coordinates[i][1], coordinates[(i + 1) % length][0],
						coordinates[(i + 1) % length][1]));
			}
		}
		return ret;
	}
}
