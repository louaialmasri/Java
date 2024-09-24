import java.awt.Graphics;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Rastert Polygone mit Scanline-Verfahren.
 * 
 * Zur Vereinfachung nehmen wir an, dass sich Polygone immer komplett im
 * Viewport befinden, den Rand also nicht schneiden.
 */
public class PolygonRasterer {
	/** Zum Zeichnen */
	private Graphics graphics;
	/** Höhe des Zeichenfensters */
	private int height;
	/** Die Edge Table */
	private LinkedList<Edge> edgeTable = new LinkedList<>();
	/** Die Active Edge Table */
	private LinkedList<Edge> activeEdgeTable = new LinkedList<>();

	public PolygonRasterer(int height) {
		this.height = height;
	}


	// // Zusammenarbeit mit tip0698@thi.de und mab1193@thi.de
	public void rasterize() {
		// for all y ← 0,1, . . . do
		//Kanten mit ymin = y werden in AET aufgenommen.
		//Sortiere AET bzgl. xSchnitt.
		//Fülle Pixel zwischen Paaren von x-Koordinaten
		//aus der AET.
		//Entferne Kanten mit ymax = y aus AET.
		//Aktualisiere für alle Kanten der AET die
		//xSchnitt-Werte: xSchnitt ← xSchnitt + 1/m
		for (int y = 0; y < height; y++) {
			for (int i = 0; i < edgeTable.size(); i++) {
				Edge edge = edgeTable.get(i);
				if (edge.getyMin() == y) {
					// Kopie des Edge-Objekts erstellen
					Edge edgeCopy = new Edge(edge);
					activeEdgeTable.add(edgeCopy);
				}
			}
			activeEdgeTable.sort(Comparator.comparingDouble(Edge::getxIntersect));

			for (int i = 0; i < activeEdgeTable.size(); i++) {
				int x1 = (int) activeEdgeTable.get(i).getxIntersect();
				int x2 = (int) activeEdgeTable.get(i += 1).getxIntersect();
				for (int j = x1; j <= x1; j++) {
					graphics.drawLine(x1, y, x2, y); // y = y0 = y1
				}
			}

			Iterator<Edge> iterator = activeEdgeTable.iterator();
			while (iterator.hasNext()) {
				Edge edge = iterator.next();
				if (edge.getyMax() == y) {
					iterator.remove();
				}
			}

			for (Edge edge : activeEdgeTable) {
				edge.setxIntersect(edge.getxIntersect() + edge.getmReci());
			}
		}
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	public void addEdges(LinkedList<Edge> edges) {
		edgeTable.addAll(edges);
	}
}
