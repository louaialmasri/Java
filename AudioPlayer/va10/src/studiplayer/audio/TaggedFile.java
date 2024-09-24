package studiplayer.audio;
import java.util.Map;
import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile {

	private String album = new String();

	public TaggedFile() {
		super();
	}

	public TaggedFile(String s) throws NotPlayableException{
		super(s);
		album = "";
		readAndStoreTags(getPathname());
	}

	public String getAlbum() {
		return album;
	}

	public void readAndStoreTags(String pathname) throws NotPlayableException {
	    try {
	        Map<String, Object> tagMap = TagReader.readTags(pathname);
	        for (Map.Entry<String, Object> entry : tagMap.entrySet()) {
		        String key = entry.getKey();
		        Object value = entry.getValue();

		        if (key == null || key.trim().isEmpty()) {
		            continue;
		        }

		        if (key.equals("title")) {
		            title = String.valueOf(value).trim();
		        } else if (key.equals("author")) {
		            author = String.valueOf(value).trim();
		        } else if (key.equals("album")) {
		            album = String.valueOf(value).trim();
		        } else if (key.equals("duration")) {
		            duration = Long.parseLong(String.valueOf(value).trim());
		        }
		    }
	    } catch (RuntimeException e) {
	        throw new NotPlayableException(pathname, "Failed to read tags", e);
	    }
	}

	public String toString() {

		if (this.getAlbum().isEmpty()) {

			return super.toString() + " - " + getFormattedDuration();
		} else {

			return super.toString() + " - " + album + " - " + getFormattedDuration();
		}
	}

	public String[] fields() {
		String[] fields = new String[4];
		fields[0] = author;
		fields[1] = title;
		fields[2] = album;
		fields[3] = this.getFormattedDuration();
		return fields;
	}
}