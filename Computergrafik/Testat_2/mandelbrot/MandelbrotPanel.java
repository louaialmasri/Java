import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import colorscheme.ColorScheme;
import colorscheme.ColorSchemeHue;

@SuppressWarnings({ "serial", "unused" })
class MandelbrotPanel extends JPanel {

	/** Dimension des Zeichen-Panels (GKOS) */
	private int width = 1000;
	private int height = 1000;

	/** Minimal-/Maximalkoordinaten des logischen Koordinatensystems (LKOS) */
	private double viewport = 3.0;
	private double xMin = -2.0;
	private double xMax = xMin + viewport;
	private double yMin = -1.5;
	private double yMax = yMin + viewport;

	/**
	 * Maximale Zahl von Iterationen. Ein höherer Wert liefert genauere und schönere
	 * Bilder, lässte den Rechenzeitbedarf aber stark anwachsen
	 */
	private int maxiter = 100;
	/** Farbschema */
	private ColorScheme colorScheme = new ColorSchemeHue(maxiter);

	/** Konstruktor für das Zeichen-Panel */
	public MandelbrotPanel() {
		this.setPreferredSize(new Dimension(width, height));
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// Zoomen mit dem numerischen Ziffernblock. 1 = linke untere Ecke, 2 = unten
				// Mitte, 3 = rechte untere Ecke ..., 0 = Herauszoomen
				//
				// Ggf. ist Drücken der NumLock-Taste erforderlich
				if (e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
					viewport *= 2.0;
					xMin -= 0.25 * viewport;
					xMax = xMin + viewport;
					yMin -= 0.25 * viewport;
					yMax = yMin + viewport;
				} else {
					viewport *= 0.5;
					switch (e.getKeyCode()) {
					case KeyEvent.VK_NUMPAD1:
						xMax = xMin + viewport;
						yMax = yMin + viewport;
						break;
					case KeyEvent.VK_NUMPAD2:
						xMin += 0.5 * viewport;
						xMax = xMin + viewport;
						yMax = yMin + viewport;
						break;
					case KeyEvent.VK_NUMPAD3:
						xMin += viewport;
						xMax = xMin + viewport;
						yMax = yMin + viewport;
						break;
					case KeyEvent.VK_NUMPAD4:
						xMax = xMin + viewport;
						yMin += 0.5 * viewport;
						yMax = yMin + viewport;
						break;
					case KeyEvent.VK_NUMPAD5:
						xMin += 0.5 * viewport;
						xMax = xMin + viewport;
						yMin += 0.5 * viewport;
						yMax = yMin + viewport;
						break;
					case KeyEvent.VK_NUMPAD6:
						xMin += viewport;
						xMax = xMin + viewport;
						yMin += 0.5 * viewport;
						yMax = yMin + viewport;
						break;
					case KeyEvent.VK_NUMPAD7:
						xMax = xMin + viewport;
						yMin += viewport;
						yMax = yMin + viewport;
						break;
					case KeyEvent.VK_NUMPAD8:
						xMin += 0.5 * viewport;
						xMax = xMin + viewport;
						yMin += viewport;
						yMax = yMin + viewport;
						break;
					case KeyEvent.VK_NUMPAD9:
						xMin += viewport;
						xMax = xMin + viewport;
						yMin += viewport;
						yMax = yMin + viewport;
						break;
					}
				}
				repaint();
			}
		});
	}

	/**
	 * Inhalt des Zeichen-Panels.
	 * 
	 * @param graphics Zum Zeichnen verwendetes Graphics-Objekt.
	 */
	public void paintComponent(Graphics graphics) {
		this.setBackground(Color.BLACK);
		super.paintComponent(graphics);
		Mandelbrot mandelbrot = new Mandelbrot(graphics, width, height, xMin, yMin, xMax, yMax, maxiter, colorScheme);
		mandelbrot.render();
		this.repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Mandelbrot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new MandelbrotPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
