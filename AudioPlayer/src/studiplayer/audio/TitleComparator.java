package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile> {
    @Override
    public int compare(AudioFile af1, AudioFile af2) {
        if (af1 == null || af2 == null) {
            throw new NullPointerException("Eines||oder beide AudioFIles sind null");
        }

        String title1 = af1.getTitle();
        String title2 = af2.getTitle();

        if (title1 == null && title2 == null) {
            return 0;
        } else if (title1 == null) {
            return -1;
        } else if (title2 == null) {
            return 1;
        } else {
            if (af1 instanceof TaggedFile && af2 instanceof TaggedFile) {
                TaggedFile taggedFile1 = (TaggedFile) af1;
                TaggedFile taggedFile2 = (TaggedFile) af2;
                
                return taggedFile1.getTitle().compareTo(taggedFile2.getTitle());
            } else if (af1 instanceof TaggedFile) {
                return -1;
            } else if (af2 instanceof TaggedFile) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}