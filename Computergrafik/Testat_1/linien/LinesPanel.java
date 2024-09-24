import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

// TODO: Umbenennen in LinesPanel

@SuppressWarnings("serial")
class LinesPanel extends JPanel {

	/** Dimension des Zeichen-Panels */
	final static int width = 500;
	final static int height = 500;

	/** Konstruktor für das Zeichen-Panel */
	public LinesPanel() {
		this.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * Inhalt des Zeichen-Panels.
	 * 
	 * @param g Grafik-Kontext
	 */
	public void paintComponent(Graphics g) {
		this.setBackground(Color.black);
		super.paintComponent(g);
		
		Lines lineDrawer = new Lines(g);
		
//		// mathematische Geradengleichung
//		g.setColor(Color.red);
//		lineDrawer.drawLineEquation(100, 100, 200, 200);
//		lineDrawer.drawLineEquation(100, 100, 200, 150);
//
//		// DDA
//		g.setColor(Color.green);
//		lineDrawer.drawDda(100, 120, 200, 220);
//		lineDrawer.drawDda(100, 120, 200, 170);
//
//		// Bresenham
//		g.setColor(Color.blue);
//		lineDrawer.drawBresenham(100, 110, 200, 210);
//		lineDrawer.drawBresenham(100, 110, 200, 160);

//		// Überdeckungstests
//		
		// Überdeckung Geradengleichung - DDA
		g.setColor(Color.red);
		lineDrawer.drawLineEquation(200, 100, 300, 200);
		lineDrawer.drawLineEquation(200, 100, 300, 150);

		g.setColor(Color.green);
		lineDrawer.drawDda(200, 100, 300, 200);
		lineDrawer.drawDda(200, 100, 300, 150);
//	
//		// Überdeckung Geradengleichung - Bresenham
//		g.setColor(Color.red);
//		lineDrawer.drawLineEquation(300, 100, 400, 200);
//		lineDrawer.drawLineEquation(300, 100, 400, 150);
//
//		g.setColor(Color.blue);
//		lineDrawer.drawBresenham(300, 100, 400, 200);
//		lineDrawer.drawBresenham(300, 100, 400, 150);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Linienalgorithmen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new LinesPanel());
		frame.pack();
		frame.setVisible(true);
	}
}
