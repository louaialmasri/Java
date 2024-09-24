package studiplayer.audio;
public class AudioFileFactory {

	public static AudioFile getInstance(String pathname) throws NotPlayableException {
		try {
			String lowercasePathname = pathname.toLowerCase();

			if (lowercasePathname.endsWith(".wav")) {
				return new WavFile(pathname);
			} else if (lowercasePathname.endsWith(".mp3") || lowercasePathname.endsWith(".ogg")) {
				return new TaggedFile(pathname);
			}

			throw new RuntimeException("Unknown suffix for AudioFile: \"" + pathname + "\"");
		} catch (Exception e) {
			throw new NotPlayableException(pathname, "AudioFile-Instanz konnte nicht erstellt werden", e);
		}
	}
}
