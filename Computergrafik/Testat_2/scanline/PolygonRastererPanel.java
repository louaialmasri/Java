import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class PolygonRastererPanel extends JPanel {

	/** initiale Dimension des Zeichen-Panels (GKOS) */
	private int width = 1000;
	private int height = 1000;

	/** Der genutzte PolygonRasterer */
	private PolygonRasterer polygonRasterer;


	/** Konstruktor für das Zeichen-Panel */
	public PolygonRastererPanel() {
		this.setPreferredSize(new Dimension(width, height));
		this.polygonRasterer = new PolygonRasterer(height);
		// Testfälle zum Ein-/Auskommentieren
		zigzag();
//		cg();
//		stars();
	}

	/**
	 * Eine Zickzack-Linie
	 */
	@SuppressWarnings("unused")
	private void zigzag() {
		Polygon polygon = new Polygon(new int[][] { { 100, 100 }, { 300, 500 }, { 500, 100 }, { 700, 300 },
				{ 700, 500 }, { 500, 300 }, { 300, 700 }, { 100, 300 } });
		polygonRasterer.addEdges(polygon.getEdges());
	}

	/**
	 * Der Schriftzug "cg"
	 */
	@SuppressWarnings("unused")
	private void cg() {
		Polygon charc = new Polygon(new int[][] { { 200, 100 }, { 300, 100 }, { 400, 200 }, { 200, 200 }, { 200, 400 },
				{ 400, 400 }, { 300, 500 }, { 200, 500 }, { 100, 400 }, { 100, 200 }, });
		polygonRasterer.addEdges(charc.getEdges());
		Polygon chargext = new Polygon(new int[][] { { 600, 100 }, { 800, 100 }, { 800, 600 }, { 700, 700 },
				{ 600, 700 }, { 500, 600 }, { 700, 600 }, { 700, 500 }, { 600, 500 }, { 500, 400 }, { 500, 200 } });
		polygonRasterer.addEdges(chargext.getEdges());
		Polygon chargint = new Polygon(new int[][] { { 600, 200 }, { 700, 200 }, { 700, 400 }, { 600, 400 }, });
		polygonRasterer.addEdges(chargint.getEdges());

	}
	
	/**
	 * Sich zum Teil schneidende Sterne
	 */
	@SuppressWarnings("unused")
	private void stars() {
		Polygon star1 = new Star(500.0, 500.0, 100.0, 300.0);
		polygonRasterer.addEdges(star1.getEdges());
		Polygon star2 = new Star(350.0, 200.0, 100.0, 33.0);
		polygonRasterer.addEdges(star2.getEdges());
		Polygon star3 = new Star(750.0, 430.0, 120.0, 40.0);
		polygonRasterer.addEdges(star3.getEdges());
		Polygon star4 = new Star(500.0, 500.0, 90.0, 30.0);
		polygonRasterer.addEdges(star4.getEdges());
	}
	
	/**
	 * Inhalt des Zeichen-Panels.
	 * 
	 * @param graphics Zum Zeichnen verwendetes Graphics-Objekt.
	 */
	public void paintComponent(Graphics graphics) {
		this.setBackground(Color.BLACK);
		super.paintComponent(graphics);
		graphics.setColor(Color.RED);
		polygonRasterer.setGraphics(graphics);
		polygonRasterer.rasterize();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Polygon-Rasterung");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new PolygonRastererPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
