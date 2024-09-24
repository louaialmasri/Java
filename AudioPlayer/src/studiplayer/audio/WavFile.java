package studiplayer.audio;
import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {

	public WavFile() {
		super();
	}

	public WavFile(String s) throws NotPlayableException{
		super(s);
		this.readAndSetDurationFromFile(this.getPathname());
	}

	public static long computeDuration(long numberOfFrames, float frameRate) {
	    float durationInSeconds = (float) numberOfFrames / frameRate;
	    long durationInMicroseconds = (long) (durationInSeconds * 1000000);
	    return durationInMicroseconds;
	}

	public void readAndSetDurationFromFile(String pathname) throws NotPlayableException {
	    try {
	        WavParamReader.readParams(pathname);
	        frameRate = WavParamReader.getFrameRate();
			numberOfFrames = WavParamReader.getNumberOfFrames();
			this.duration = computeDuration(numberOfFrames, frameRate);
	    } catch (RuntimeException e) {
	        throw new NotPlayableException(pathname, "Dauer konnte nicht abgelesen werden", e);
	    }
	}

	public String toString() {
	    String artist = super.getAuthor();
	    String title = super.getTitle();
	    String duration = getFormattedDuration();
	    return artist + " - " + title + " - " + duration;
	}

	public String[] fields() {
		String[] fields = new String[4];
		fields[0] = this.author;
		fields[1] = this.title;
		fields[2] = "";
		fields[3] = this.getFormattedDuration();
		return fields;
	}
}