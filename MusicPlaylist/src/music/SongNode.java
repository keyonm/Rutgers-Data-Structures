package music;

public class SongNode {
    private Song        song;
    private SongNode    next;

    public SongNode(Song song, SongNode next) {
        this.song = song;
        this.next = next;
    }


    public SongNode() {
        this(null, null);
    }

    public Song getSong() { return song; }
    public void setSong(Song s) { song = s; }

    public SongNode getNext() { return next; }
    public void setNext(SongNode n) { next = n; }
    
}
