package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {
    @Override
    public int compare(AudioFile af1, AudioFile af2) {
        if (af1 instanceof SampledFile && af2 instanceof SampledFile) {
            SampledFile sampledFile1 = (SampledFile) af1;
            SampledFile sampledFile2 = (SampledFile) af2;
            
            String duration1 = sampledFile1.getFormattedDuration();
            String duration2 = sampledFile2.getFormattedDuration();
            
            // Compare the formatted durations
            return duration1.compareTo(duration2);
        } else {
            return 0;
        }
    }
}