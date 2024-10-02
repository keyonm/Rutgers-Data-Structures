package music;

public class Song {
    private String          songName;
    private String          artist;
    private int             year;
    private int             popularity;
    private String          link;
    private final String    DIRECTORY = "songs/";

    public Song(String songName, String artist, int year, int popularity, String link) {
        this.songName = songName;
        this.artist = artist;
        this.year = year;
        this.popularity = popularity;
        this.link = DIRECTORY + year + "/" + link;
    }


    public Song(String songName, String artist, int year, int popularity) {
        this.songName = songName;
        this.artist = artist;
        this.year = year;
        this.popularity = popularity;
        this.link = null;
    }


    public Song() {
        this(null, null, 0, 0, null);
    }


    @Override
    public String toString() {
        StringBuilder printSong = new StringBuilder();
        printSong.append(songName + " (");
        printSong.append(artist + ", y=" + year + ", p=" + popularity + ")");
        return printSong.toString();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((songName == null) ? 0 : songName.hashCode());
        result = prime * result + ((artist == null) ? 0 : artist.hashCode());
        result = prime * result + year;
        result = prime * result + popularity;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Song other = (Song) obj;
        if (songName == null) {
            if (other.songName != null)
                return false;
        } else if (!songName.equals(other.songName))
            return false;
        if (artist == null) {
            if (other.artist != null)
                return false;
        } else if (!artist.equals(other.artist))
            return false;
        if (year != other.year)
            return false;
        if (popularity != other.popularity)
            return false;
        return true;
    }

    public String getSongName() { return songName; }
    public void setSongName(String s) { songName = s; }

    public String getArtist() { return artist; }
    public void setArtist(String art) { artist = art; }

    public int getYear() { return year; }
    public void setYear(int y) { year = y; }

    public int getPopularity() { return popularity; }
    public void setPopularity(int p) { popularity = p; }

    public String getLink() { return link; }
    public void setLink(String l) { link = DIRECTORY + year + "/" + l; }

    public String getDirectory() { return DIRECTORY; }

}
