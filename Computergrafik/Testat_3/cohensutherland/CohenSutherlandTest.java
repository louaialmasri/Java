import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

public class CohenSutherlandTest {

	@Test
	public void testOutputCodes() {
		CohenSutherland cohenSutherland = new CohenSutherland(null, 50, 100, 250, 200);

		//@formatter:off
		int[][] testCases = { 
				{150, 125, 0},
				{ 150, 205, Area.GTYMAX },
				{ 150, 99, Area.LTYMIN },
				{ 258, 150, Area.GTXMAX },
				{ 36, 151, Area.LTXMIN },
				{ 0, 0, Area.LTXMIN | Area.LTYMIN },
				{ 25, 207, Area.LTXMIN | Area.GTYMAX },
				{ 1920, 1080, Area.GTXMAX | Area.GTYMAX },
				{ 251, 99, Area.GTXMAX | Area.LTYMIN }, 
				};
		//@formatter:on

		for (int tc = 0; tc < testCases.length; tc++) {
			assertEquals("Falscher Outputcode für x=" + testCases[tc][0] + ", y=" + testCases[tc][1], testCases[tc][2],
					cohenSutherland.outputCode(testCases[tc][0], testCases[tc][1]));
		}
	}
	
	@Test
	public void testHorizontalVerticalClipLine() {
		BufferedGraphics bufferedGraphics = new BufferedGraphics(15, 15);
		bufferedGraphics.setColor(Color.BLACK);
		CohenSutherland cohenSutherland = new CohenSutherland(bufferedGraphics, 5, 5, 10, 10);
		cohenSutherland.clipLine(5, 2, 5, 12);
		cohenSutherland.clipLine(2, 5, 12, 5);
		cohenSutherland.clipLine(1, 1, 14, 1);
		String expected = 
				"...............\n" + 
			    "...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				".....XXXXXX....\n" + 
				".....X.........\n" + 
				".....X.........\n" + 
				".....X.........\n" + 
				".....X.........\n" + 
				".....X.........\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n";
		assertEquals("Clippen vertikal/horizontal fehlgeschlagen", expected, bufferedGraphics.toString());
	}
	
	@Test
	public void testDiagonalClipLine() {
		BufferedGraphics bufferedGraphics = new BufferedGraphics(15, 15);
		bufferedGraphics.setColor(Color.BLACK);
		CohenSutherland cohenSutherland = new CohenSutherland(bufferedGraphics, 5, 5, 10, 10);
		cohenSutherland.clipLine(0, 2, 12, 14);
		cohenSutherland.clipLine(0, 14, 14, 0);
		String expected = 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				".........X.....\n" + 
				"........X......\n" + 
				".....X.X.......\n" + 
				"......X........\n" + 
				".....X.X.......\n" + 
				"........X......\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n" + 
				"...............\n";
		assertEquals("Clippen diagonale Linie fehlgeschlagen", expected, bufferedGraphics.toString());
	}
}
