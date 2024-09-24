import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
 * @author grauschopf
 *
 */

public class BufferedGraphics extends Graphics {

	private Color[][] buffer;
	private Color paintingColor;

	public BufferedGraphics(int width, int height) {
		buffer = new Color[width][height];
	}

	public Color get(int x, int y) {
		return buffer[x][y];
	}

	@Override
	public void clearRect(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clipRect(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void copyArea(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub

	}

	@Override
	public Graphics create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, ImageObserver arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, Color arg3, ImageObserver arg4) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, ImageObserver arg5) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, Color arg5, ImageObserver arg6) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8,
			ImageObserver arg9) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8,
			Color arg9, ImageObserver arg10) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public void drawLine(int x0, int y0, int x1, int y1) {
		boolean finished = false;
		if (!finished) {
			int dx = x1 - x0;
			int dy = y1 - y0;

			if (dx != 0 && dy != 0 && java.lang.Math.abs(dx) - java.lang.Math.abs(dy) != 0) {
				throw new IllegalArgumentException("Dieses drawLine unterstützt nur das Zeichnen von Punkten,"
						+ " waagrechten, vertikalen und diagonalen Linien (Steigung +1 oder -1)");
			}
			if (dx == 0) {
				for (int y = y0; y <= y1; y++) {
					buffer[x0][y] = paintingColor;
				}
			} else if (dy == 0) {
				for (int x = x0; x <= x1; x++) {
					buffer[x][y0] = paintingColor;
				}
			} else {
				int xinc = (dx > 0) ? 1 : -1;
				int yinc = (dy > 0) ? 1 : -1;
				int x = x0;
				int y = y0;
				do {
					buffer[x][y] = paintingColor;
					x += xinc;
					y += yinc;
				} while (x <= x1);
			}
			finished = true;
		}
	}

	@Override
	public void drawOval(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawPolygon(int[] arg0, int[] arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawPolyline(int[] arg0, int[] arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawString(String arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawString(AttributedCharacterIterator arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillOval(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillPolygon(int[] arg0, int[] arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillRect(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub

	}

	@Override
	public Shape getClip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getClipBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FontMetrics getFontMetrics(Font arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setClip(Shape arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClip(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(Color paintingColor) {
		this.paintingColor = paintingColor;
	}

	@Override
	public void setFont(Font arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPaintMode() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setXORMode(Color arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void translate(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * Um die konventionelle Bildschirmorientierung zu erhalten wird die Ausgabe des
	 * buffers spalten- statt zeilenweise vorgenommen.
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(buffer.length * buffer[0].length);
		for (int col = 0; col < buffer[0].length; col++) {
			for (int row = 0; row < buffer[0].length; row++) {
				stringBuilder.append((buffer[row][col] == null) ? '.' : 'X');
			}
			stringBuilder.append('\n');
		}
		return stringBuilder.toString();
	}
}
