/**
 * Konstanten für die Bits im Output-Code.
 * 
 * Über bitweises OR können damit Bits im Output-Code gesetzt und über bitweises
 * AND Bits abgefragt werden.
 */
public interface Area {
	static final int LTXMIN = 1;
	static final int GTXMAX = 2;
	static final int LTYMIN = 4;
	static final int GTYMAX = 8;
}