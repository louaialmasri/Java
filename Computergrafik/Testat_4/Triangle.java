import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Triangle extends JPanel {

	/** Dimension des Zeichen-Panels */
	private int width = 500;
	private int height = 500;

	/**
	 * Einfache Klasse, die einen 2D-Punkt spezifiziert.
	 */
	class Point {
		public int x;
		public int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	/** Eine Liste mit Point2D */
	List<Point> points = new LinkedList<Point>();

	/** wird nach dem dritten Punkt auf false gesetzt */
	boolean setTriangle = true;
	/** wird nach dem vierten Punkt auf false gesetzt */
	boolean setPoints = true;

	/** Konstruktor des Panels */
	public Triangle() {
		setPreferredSize(new Dimension(width, height));

		/** Koordinaten eines Mausklicks abfragen */
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if (setPoints) {
					int x = evt.getX();
					int y = evt.getY();
					points.add(new Point(x, y));
				}
				// Nach drei Punkten ist das Dreieck fertig
				if (points.size() == 3)
					setTriangle = false;
				// Nach vier Punkten werden keine Mausklickkoordinaten mehr
				// ermittelt.
				if (points.size() == 4)
					setPoints = false;
			}
		});
	}


	/**
	 * Inhalt des Zeichen-Panels.
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		this.setBackground(Color.BLACK);

		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.WHITE);

		// TODO: Hier Ihr Code...
		// Bitte mit Methoden arbeiten!
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Dreiecksoperationen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Triangle triang = new Triangle();
		frame.add(triang);
		frame.pack();
		frame.setVisible(true);
		while (true) {
			// Neuzeichnen anstoßen
			frame.repaint();
			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
