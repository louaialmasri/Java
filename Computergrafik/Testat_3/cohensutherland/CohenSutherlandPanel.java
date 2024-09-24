import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CohenSutherlandPanel extends JPanel {

	/** Dimension des Zeichen-Panels */
	private int width = 800;
	private int height = 800;

	public CohenSutherlandPanel() {
		this.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * Inhalt des Zeichen-Panels.
	 * 
	 * @param graphics Grafik-Kontext
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		this.setBackground(Color.BLACK);

		// Anti-Aliasing
		((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Abstand des Clipping-Rahmens vom Fensterahmen
		int border = 200;

		// Dimension des Clipping-Rechtecks
		int xmin = border;
		int xmax = width - border;
		int ymin = border;
		int ymax = height - border;

		// Clipping-Rechteck in rot zeichnen
		graphics.setColor(Color.RED);
		graphics.drawRect(border, border, width - 2 * border, height - 2 * border);

		// geclippte Linien in weiß mit etwas breiterem Strich
		graphics.setColor(Color.WHITE);
		((Graphics2D) graphics).setStroke(new BasicStroke(2));

		CohenSutherland cohenSutherland = new CohenSutherland(graphics, xmin, ymin, xmax, ymax);
		// Zeichne einzelne Linie (xA, yA) -- (xE, yE).
		// Modifizieren Sie die Koordinaten um verschiedene Lagen von Linie zu Clip-Rechteck zu testen.
		cohenSutherland.clipLine(400, 300, 500, 500);
		
		// Wenn alles geht, können Sie auch das probieren.
		// Generiere 100 Linien über zufällige Anfangs- und Endpunkte.
		int nrLines = 100;
		Random r = new Random(0xBAADF00D);
		for (int i = 0; i < nrLines; i++) {
			int xA = (int) (r.nextInt(width));
			int yA = (int) (r.nextInt(height));
			int xE = (int) (r.nextInt(width));
			int yE = (int) (r.nextInt(height));
			graphics.setColor(Color.WHITE);
			graphics.drawLine(xA, yA, xE, yE);
			graphics.setColor(Color.GREEN);
			cohenSutherland.clipLine(xA, yA, xE, yE);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Cohen-Sutherland");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CohenSutherlandPanel cohenSutherland = new CohenSutherlandPanel();
		frame.add(cohenSutherland);
		frame.pack();
		frame.setVisible(true);
	}
}
