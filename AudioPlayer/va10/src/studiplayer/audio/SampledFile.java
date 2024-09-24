package studiplayer.audio;
import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile {
	protected long duration;
	protected long numberOfFrames;
	protected float frameRate;

	public SampledFile() {
		super();
	}

	public SampledFile(String s) throws NotPlayableException{
		super(s);
	}

	public void play() throws NotPlayableException {
	    try {
	        BasicPlayer.play(getPathname());
	    } catch (RuntimeException e) {
	        throw new NotPlayableException(getPathname(), "Audiodatei konnte nicht abgespielt werden", e);
	    }
	}

	public void togglePause() {
		BasicPlayer.togglePause();

	}

	public void stop() {
		BasicPlayer.stop();
	}

	public String getFormattedDuration() {
		return TaggedFile.timeFormatter(this.duration);
	}

	public String getFormattedPosition() {
        long positionMicros = BasicPlayer.getPosition();

        // Check if positionMicros is valid
        if (positionMicros < 0) {
            return "";
        }
        return timeFormatter(positionMicros);
    }

	public static String timeFormatter(long microtime) {
        String neededTime = "";
    	if (microtime < 0) {
            throw new RuntimeException("Negative Zeitangabe!");
        }
        long totalSeconds = microtime / 1000000;
        if (totalSeconds > 5999) {
            throw new RuntimeException("Zeitangabe überschreitet maximalen Wert");
        }
        long seconds = ((microtime / 1000000) % 60);
        long minutes = ((microtime / 1000000) / 60);
        neededTime = String.format("%02d:%02d", minutes, seconds);
        return neededTime;
    }
}