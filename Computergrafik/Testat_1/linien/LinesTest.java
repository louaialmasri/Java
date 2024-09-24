import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

public class LinesTest {

	private static int[] endPoints = { 0, 1, 2, 4, 8, 14, 19, };
	
	private static String expected = 
			"XXXXXXXXXXXXXXXXXXXX\n" + 
			".XXXXXXXXXXXXXXXXXXX\n" + 
			"..XXXXXX..XXXXX....X\n" + 
			"...X.X..XX.....XXXX.\n" + 
			"....X.X...XX.......X\n" + 
			".....X.XX...XXX.....\n" + 
			"......X..X.....XX...\n" + 
			".......X..X......XX.\n" + 
			"........X..XX......X\n" + 
			".........X...X......\n" + 
			"..........X...X.....\n" + 
			"...........X...XX...\n" + 
			"............X....X..\n" + 
			".............X....X.\n" + 
			"..............X....X\n" + 
			"...............X....\n" + 
			"................X...\n" + 
			".................X..\n" + 
			"..................X.\n" + 
			"...................X\n";

	@Test
	public void testDrawLineEquation() {
		BufferedGraphics bufferedGraphics = new BufferedGraphics(20, 20);
		bufferedGraphics.setColor(Color.red);
		Lines lineDrawer = new Lines(bufferedGraphics);
		for (int ep : endPoints) {
			lineDrawer.drawLineEquation(0, 0, 19, ep);
		}
		assertEquals("Zeichnen mit Geradengleichung", expected, bufferedGraphics.toString());
	}

	@Test
	public void testDrawDda() {
		BufferedGraphics bufferedGraphics = new BufferedGraphics(20, 20);
		bufferedGraphics.setColor(Color.red);
		Lines lineDrawer = new Lines(bufferedGraphics);
		for (int ep : endPoints) {
			lineDrawer.drawDda(0, 0, 19, ep);
		}		
		assertEquals("Zeichnen mit DDA", expected, bufferedGraphics.toString());
	}

	@Test
	public void testDrawBresenham() {
		BufferedGraphics bufferedGraphics = new BufferedGraphics(20, 20);
		bufferedGraphics.setColor(Color.red);
		Lines lineDrawer = new Lines(bufferedGraphics);
		for (int ep : endPoints) {
			lineDrawer.drawBresenham(0, 0, 19, ep);
		}
		assertEquals("Zeichnen mit Bresenham", expected, bufferedGraphics.toString());
	}

}
