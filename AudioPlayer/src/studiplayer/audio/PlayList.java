package studiplayer.audio;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PlayList extends LinkedList<AudioFile> {

	private int currentPosition;
    private boolean randomOrder;
    
    public PlayList(String pathname) {
        this();
        loadFromM3U(pathname);
    }
    
    public PlayList() {
        currentPosition = 0;
    }

    public void setCurrent(int position) {
        if (position >= 0 && position < size()) {
            currentPosition = position;
        } else {
            throw new IllegalArgumentException("ungültige position");
        }
    }

    public int getCurrent() {
        return currentPosition;
    }
    
    public AudioFile getCurrentAudioFile() {
        if (currentPosition >= 0 && currentPosition < size()) {
            return get(currentPosition);
        } else {
            return null;
        }
    }
    
    public void changeCurrent() {
        currentPosition++;
        if (currentPosition >= size()) {
            currentPosition = 0; // auf anfang der liste zurücksetzen, rotation
            if (randomOrder) {
                Collections.shuffle(this); // liste neu mischen, falls zufallmodus an ist
            }
        }
    }
    
    public void setRandomOrder(boolean randomOrder) {
        this.randomOrder = randomOrder;
        if (randomOrder) {
            Collections.shuffle(this);
        }
        else {
        	
        }
    }
    
    public void saveAsM3U(String pathname) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathname))) {
            for (AudioFile audioFile : this) {
                writer.write(audioFile.getPathname());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void loadFromM3U(String pathname) {
        clear(); // bestehende liste leer machen

        try (BufferedReader reader = new BufferedReader(new FileReader(pathname))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (!line.isEmpty() && !line.startsWith("#")) {
                    try {
                        AudioFile audioFile = AudioFileFactory.getInstance(line);
                        add(audioFile);
                    } catch (NotPlayableException e) {
                        e.printStackTrace();
                        // Fehler beim Einlesen überspringen und mit der nächsten Datei fortfahren
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Lesen von M3U file: " + e.getMessage());
        }
    }
    
    public void sort(SortCriterion order) {
        Comparator<AudioFile> comparator;

        switch (order) {
            case AUTHOR:
                comparator = new AuthorComparator();
                break;
            case TITLE:
                comparator = new TitleComparator();
                break;
            case ALBUM:
                comparator = new AlbumComparator();
                break;
            case DURATION:
                comparator = new DurationComparator();
                break;
            default:
                return;
        }

        Collections.sort(this, comparator);
    }
}