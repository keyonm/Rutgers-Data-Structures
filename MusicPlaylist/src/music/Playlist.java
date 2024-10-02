package music;

public class Playlist {
    private SongNode last;
    private int      size;

    /*
     * Constructor
     */
    public Playlist(SongNode last, int size) {
        this.last = last;
        this.size = size;
    }

    public Playlist() {
        this(null, 0);
    }

    public SongNode getLast() {return last;}
    public void setLast(SongNode last) {this.last = last;}

    public int getSize() {return size;}
    public void setSize(int size) {this.size = size;}
}
