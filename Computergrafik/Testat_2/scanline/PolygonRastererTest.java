import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

public class PolygonRastererTest {
	private static String expected = 
			"..........\n" + 
			"..........\n" + 
			"..XXXXXX..\n" + 
			"..XXXXXX..\n" + 
			"..XX..XX..\n" + 
			"..XX..XXX.\n" + 
			"..XXXXXX..\n" + 
			"..XXXXXX..\n" + 
			"..........\n" + 
			"..........\n";
	
	@Test
	public void testQuadrat() {
		BufferedGraphics bufferedGraphics = new BufferedGraphics(10, 10);
		bufferedGraphics.setColor(Color.RED);
		PolygonRasterer rasterPolygon = new PolygonRasterer(10);
		Polygon square1 = new Polygon(new int[][] { { 2, 2 }, { 7, 2 }, {8, 5}, { 7, 8 }, { 2, 8 } });
		rasterPolygon.addEdges(square1.getEdges());
		Polygon square2 = new Polygon(new int[][] { { 3, 4 }, { 6, 4 }, { 6, 6 }, { 3, 6 } });
		rasterPolygon.addEdges(square2.getEdges());
		rasterPolygon.setGraphics(bufferedGraphics);
		rasterPolygon.rasterize();
		assertEquals("Polygonrasterung liefert falsche Ergebnisse", expected, bufferedGraphics.toString());
	}
}
