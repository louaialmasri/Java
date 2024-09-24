import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class BezierPanel extends JPanel {

	/** Dimension des Zeichen-Panels */
	private int width = 500;
	private int height = 500;

	/**
	 * Eine Liste von vier Point2D-Objekten Dies sind die Stütz- und Kontrollpunkte
	 */
	private List<Point> points = new LinkedList<Point>();

	/** wird nach dem vierten Punkt auf false gesetzt */
	private boolean setPoints = true;
	/** Falls !setPoints und Benutzer selektiert Stützpunkt */
	private boolean dragging = false;
	/** Index des selektierten Stützpunkts */
	private int picked;

	/** Konstruktor des Panels */
	public BezierPanel() {
		setPreferredSize(new Dimension(width, height));

		/** Koordinaten eines Mausklicks abfragen */
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if (setPoints) {
					// Modus: Punkt setzen
					int x = evt.getX();
					int y = evt.getY();
					// Falls einer der bereits gesetzten Punkte nochmals geklickt wird, Modus "Punkt
					// setzen" beenden
					for (int p = 0; p < points.size(); p++) {
						double dx = x - points.get(p).x;
						double dy = y - points.get(p).y;
						// Radius der Selektion: 6
						if (dx * dx + dy * dy <= 36.0) {
							// Punkt nochmals geklickt
							setPoints = false;
							break;
						}
					}
					// Noch im "Punkt setzen"-Modus. Punkt zur Liste hinzufügen.
					if (setPoints) {
						points.add(new Point(x, y));
					}
				}
				if (!setPoints) {
					// Nutzer kann mit Drag und Drop einen Kontrollpunkt verschieben

					// Ermittle, ob Benutzer einen Kontrollpunkt selektiert
					int x = evt.getX();
					int y = evt.getY();
					for (int p = 0; p < points.size(); p++) {
						double dx = x - points.get(p).x;
						double dy = y - points.get(p).y;
						// Radius der Selektion: 6
						if (dx * dx + dy * dy <= 36.0) {
							dragging = true;
							picked = p;
						}
					}
				}
			}

			// Selektion Kontrollpunkt freigeben.
			public void mouseReleased(MouseEvent evt) {
				dragging = false;
				picked = -1;
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			// Selektierten Kontrollpunkt verschieben.
			public void mouseDragged(MouseEvent evt) {
				if (dragging) {
					points.get(picked).x = evt.getX();
					points.get(picked).y = evt.getY();
				}
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
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		graphics.setColor(Color.WHITE);

		// Zeichnen des Kontrollpolygons
		for (int p = 0; p < points.size(); p++) {
			// Zeichne kleine Quadrate um gesetzte Punkte
			g2d.drawRect((int) points.get(p).x - 2, (int) points.get(p).y - 2, 4, 4);

			// Verbinde die Punkte
			if (p > 0) {
				g2d.drawLine((int) points.get(p - 1).x, (int) points.get(p - 1).y, (int) points.get(p).x,
						(int) points.get(p).y);
			}
		}
		// Zeichnen der Bézier-Kurve
		if (!setPoints) {
			graphics.setColor(Color.RED);
			(new Bezier(points, 0.01)).render(graphics);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Bézier-Kurve");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BezierPanel bezier = new BezierPanel();
		frame.add(bezier);
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
