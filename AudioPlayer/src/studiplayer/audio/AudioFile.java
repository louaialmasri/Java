package studiplayer.audio;
import java.io.File;

public abstract class AudioFile {
	protected String pathname;
	protected File file;
	protected String author;
	protected String title;
	private String filename;
	protected String album;
    protected long duration;
    protected long numberOfFrames;
    protected float frameRate;

	AudioFile() {

	}
	public AudioFile(String pathname) throws NotPlayableException {
	    super();
	    if (pathname == null || pathname.isEmpty()) {
	        throw new NotPlayableException(pathname, "Invalid pathname");
	    }
	    this.pathname = pathname;
	    try {
	        parsePathname(pathname);
	        parseFilename(getFilename());
	        File file = new File(this.pathname);
	        if (!file.canRead()) {
	            throw new NotPlayableException(pathname, "File is not readable: " + this.pathname);
	        }
	    } catch (Exception e) {
	        throw new NotPlayableException(pathname, "Failed to initialize AudioFile", e);
	    }
	}

	public void parsePathname(String pathname) {
		this.pathname = normalizepathname(pathname);

	    // alle arten von slashes duch einen forward slash ersetzen
	    String normalizedpathname = this.pathname.replaceAll("\\\\+", "/");

	    if (normalizedpathname.endsWith("/")) {
	        this.filename = "";
	    } else {
	        // pfad in komponenten zerlegen immer zwischen 2 slashes
	        String[] pathComponents = normalizedpathname.split("/");
	        String lastComponent = pathComponents[pathComponents.length - 1];
	        // falls letzte komponente leer oder "." oder mit einem forward slash endet, dann soll einen leeren string zurückgegeben werden
	        if (lastComponent.isEmpty() || lastComponent.equals(".") || lastComponent.endsWith("/")) {
	            this.filename = "";
	        }
	        else {
	            this.filename = lastComponent;
	        }
	    }
	    String os = System.getProperty("os.name").toLowerCase();
	    if (os.contains("win")) {
	        this.pathname = this.pathname.replaceAll("[\\\\/]+", "\\\\");

	        if (this.pathname.matches("[a-zA-Z]:\\\\.*")) {
	            String driveLetter = this.pathname.substring(0, 1).toUpperCase();
	            this.pathname = driveLetter + this.pathname.substring(1);
	        }	    
		} else {
			this.pathname = this.pathname.replaceAll("\\\\", "/");
			String driveLetter = "";
			if (this.pathname.length() >= 2 && this.pathname.charAt(1) == (':')) {
				driveLetter = this.pathname.substring(0, this.pathname.indexOf("/")).toUpperCase();
				if (!driveLetter.isBlank()) {
					this.pathname = "/" + driveLetter + this.pathname.substring(2);
				}
			}
			this.pathname = this.pathname.replaceFirst(":", "/").replaceAll("//+", "/");
		}
	}
	public String normalizepathname(String pathname) {
		char separatorChar = System.getProperty("file.separator").charAt(0); 
		this.pathname = pathname.replaceAll("/+", "/"); // alle forward slashes durch die je nach betrieb richtigen slashes ersetzen
		this.pathname = pathname.replaceAll("\\\\+", "\\\\");
		this.pathname = pathname.replaceAll(Character.toString(separatorChar) + "+", Character.toString(separatorChar));// Replace																													// separator
		this.pathname = this.pathname.replace("\\", Character.toString(separatorChar)); 
		if (System.getProperty("os.name").startsWith("Windows") && pathname.matches("[a-zA-Z]:.*")) {
			this.pathname = Character.toUpperCase(pathname.charAt(0)) + pathname.substring(1);
		}
		return this.pathname;
	}
	public void parseFilename(String filename) {
		this.author = "";
		this.title = "";

		if (filename.equals("-")) {
			this.title = "-";
			return;
		}
		if (filename.contains(".")) {
			int extensionIndex = filename.lastIndexOf(".");
			String filenameWithoutExtension = filename.substring(0, extensionIndex);

			int splitterIndex = filenameWithoutExtension.lastIndexOf(" - ");
			if (splitterIndex == -1) {
				this.title = filenameWithoutExtension.trim();
			} else if (splitterIndex == 0) {
				this.author = "";
				this.title = filenameWithoutExtension.substring(3).trim();
			} else {
				this.author = filenameWithoutExtension.substring(0, splitterIndex).trim();
				this.title = filenameWithoutExtension.substring(splitterIndex + 3).trim();
			}
		}
	}
	public String getPathname() {
		return this.pathname;
	}

	public String getFilename() {
		return this.filename;
	}

	public String getAuthor() {
		return this.author;
	}

	public String getTitle() {
		return this.title;
	}
	
	public String getAlbum() {
	    return "";
	}
	
	public void setTitle(String title) {
	    this.title = title;
	}
	
	public void setAuthor(String author) {
	    this.author = author;
	}

	public void setAlbum(String album) {
	    this.album = album;
	}

	public void setDuration(long durationMicros) {
	    this.duration = (int) durationMicros;
	}
	
	@Override
	public String toString() {
		if (getAuthor().isEmpty()) {
			return getTitle();
		} else {
			return getAuthor() + " - " + getTitle();
		}
	}
	
	public abstract void play() throws NotPlayableException;
	public abstract void togglePause();
	public abstract void stop();
	public abstract String getFormattedDuration();
	public abstract String getFormattedPosition();
    public abstract String[] fields();
}