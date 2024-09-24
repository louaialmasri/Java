package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {
    @Override
    public int compare(AudioFile af1, AudioFile af2) {
        if (af1 == null || af2 == null) {
            throw new NullPointerException("Eines||oder beide AudioFIles sind null");
        }

        String album1 = null;
        String album2 = null;

        if (af1 instanceof TaggedFile) {
            TaggedFile taggedFile1 = (TaggedFile) af1;
            album1 = taggedFile1.getAlbum();
        }

        if (af2 instanceof TaggedFile) {
            TaggedFile taggedFile2 = (TaggedFile) af2;
            album2 = taggedFile2.getAlbum();
        }

        if (album1 == null && album2 == null) {
            return 0;
        } else if (album1 == null) {
            return -1;
        } else if (album2 == null) {
            return 1;
        } else {
            return album1.compareToIgnoreCase(album2);
        }
    }
}