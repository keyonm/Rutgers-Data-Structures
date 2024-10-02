package music;

import java.util.*;

public class PlaylistLibrary {

    private ArrayList<Playlist> songLibrary;
    public PlaylistLibrary(ArrayList<Playlist> songLibrary) {
        this.songLibrary = songLibrary;
    }

    public PlaylistLibrary() {
        this(null);
    }

    public Playlist createPlaylist(String filename) {
        Playlist playlist = new Playlist();
        StdIn.setFile(filename);
        SongNode last = null;
        while (!StdIn.isEmpty()) {
            String[] data = StdIn.readLine().split(",");
            String name = data[0];
            String artist = data[1];
            int year = Integer.parseInt(data[2]);
            int pop = Integer.parseInt(data[3]);
            String link = data[4];
            Song newSong = new Song(name, artist, year, pop, link);
            SongNode add = new SongNode(newSong, null);
            if (last == null) {
                last = new SongNode(newSong, null);
                last.setNext(last);
                playlist.setLast(last);

            } else {
                SongNode temp = new SongNode(newSong, last.getNext());
                last.setNext(temp);
                last = temp;
                playlist.setLast(last);
            }

            playlist.setSize(playlist.getSize() + 1);
        }

        return playlist;
    }

    public void addPlaylist(String filename, int playlistIndex) {
        if ( songLibrary == null ) {
            songLibrary = new ArrayList<Playlist>();
        }
        if ( playlistIndex >= songLibrary.size() ) {
            songLibrary.add(createPlaylist(filename));
        } else {
            songLibrary.add(playlistIndex, createPlaylist(filename));
        }        
    }

    public boolean removePlaylist(int playlistIndex) {

        if ( songLibrary == null || playlistIndex >= songLibrary.size() ) {
            return false;
        }

        songLibrary.remove(playlistIndex);
            
        return true;
    }

    public void addAllPlaylists(String[] filenames) {
        songLibrary = new ArrayList<Playlist>();
        for (int i = 0; i < filenames.length; i++) {
            addPlaylist(filenames[i], i);
        }
    }

    public boolean insertSong(int playlistIndex, int position, Song song) {
        if (position < 1) {
            return false;
        } else if (position > songLibrary.get(playlistIndex).getSize() + 1) {
            return false;
        } else if (songLibrary.get(playlistIndex).getSize() == 0) {
            SongNode node = new SongNode(song, null);
            node.setNext(node);
            songLibrary.get(playlistIndex).setLast(node);
            songLibrary.get(playlistIndex).setSize(songLibrary.get(playlistIndex).getSize() + 1);
            return true;
        } else if (position == songLibrary.get(playlistIndex).getSize() + 1){
            SongNode node = new SongNode(song,songLibrary.get(playlistIndex).getLast().getNext());
            songLibrary.get(playlistIndex).getLast().setNext(node);
            songLibrary.get(playlistIndex).setLast(node);
            songLibrary.get(playlistIndex).setSize(songLibrary.get(playlistIndex).getSize() + 1);
            return true;
        } else {
            SongNode ptr = songLibrary.get(playlistIndex).getLast();
            SongNode prev = null;
            int count = 0;
            while (count != position) {
                prev = ptr;
                ptr = ptr.getNext();
                count++;
            }
            SongNode node = new SongNode(song, ptr);
            prev.setNext(node);
            songLibrary.get(playlistIndex).setSize(songLibrary.get(playlistIndex).getSize() + 1);
            return true;
        }
    }


    public boolean removeSong(int playlistIndex, Song song) {
        Playlist removeFrom = songLibrary.get(playlistIndex);
        SongNode ptr = removeFrom.getLast().getNext();
        SongNode prev = removeFrom.getLast();
        if (removeFrom.getLast().getNext().getSong().equals(song) && removeFrom.getLast().getSong().equals(song)) {
            removeFrom.setLast(null);
            removeFrom.setSize(removeFrom.getSize() - 1);
            songLibrary.set(playlistIndex, removeFrom);
            songLibrary.get(playlistIndex).setLast(null);
            return true;
        } else if (removeFrom.getSize() == 0 || removeFrom.getLast() == null) {
            return false;
        } else if (removeFrom.getSize() == 1 && !removeFrom.getLast().getSong().equals(song)) {
            return false;
        } else if (removeFrom.getSize() == 1 && removeFrom.getLast().getSong().equals(song)) {
            removeFrom.setLast(null);
            removeFrom.setSize(removeFrom.getSize() - 1);
            songLibrary.set(playlistIndex, removeFrom);
            songLibrary.get(playlistIndex).setLast(null);
        }

        for (int i = 0; i < removeFrom.getSize(); i++) {
            if (song.equals(ptr.getSong())) {
              if (i == removeFrom.getSize() - 1) {
                  prev.setNext(ptr.getNext());
                  removeFrom.setLast(prev);
                  ptr.setNext(null);
                  removeFrom.setSize(removeFrom.getSize() - 1);
                  songLibrary.set(playlistIndex, removeFrom);
                  return true;
              } else {
                  prev.setNext(ptr.getNext());
                  ptr.setNext(null);
                  removeFrom.setSize(removeFrom.getSize() - 1);
                  songLibrary.set(playlistIndex, removeFrom);
                  return true;
              }
            }

            prev = ptr;
            ptr = ptr.getNext();
        }

        return false;
    }

    public void reversePlaylist(int playlistIndex) {
        if (songLibrary.get(playlistIndex).getLast() == null) {
            return;
        }

        SongNode prev = null;
        SongNode ptr = songLibrary.get(playlistIndex).getLast();
        songLibrary.get(playlistIndex).setLast(ptr.getNext());

        while (ptr != null) {
            SongNode next = ptr.getNext();
            ptr.setNext(prev);
            prev = ptr;
            ptr = next;
        }
    }


    public void mergePlaylists(int playlistIndex1, int playlistIndex2) {
        Playlist one = songLibrary.get(playlistIndex1);
        Playlist two = songLibrary.get(playlistIndex2);
        Playlist merged = new Playlist();

        SongNode last = null;

        while (one.getSize() > 0 && two.getSize() > 0) {
            SongNode max = null;
            if (one.getLast().getNext().getSong().getPopularity() > two.getLast().getNext().getSong().getPopularity()) {
                max = new SongNode(one.getLast().getNext().getSong(), null);
                one.getLast().setNext(one.getLast().getNext().getNext());
                one.setSize(one.getSize() - 1);
            } else if (one.getLast().getNext().getSong().getPopularity() < two.getLast().getNext().getSong().getPopularity()) {
                max = new SongNode(two.getLast().getNext().getSong(), null);
                two.getLast().setNext(two.getLast().getNext().getNext());
                two.setSize(two.getSize() - 1);
            } else {
                if (playlistIndex1 < playlistIndex2) {
                    max = new SongNode(one.getLast().getNext().getSong(), null);
                    one.getLast().setNext(one.getLast().getNext().getNext());
                    one.setSize(one.getSize() - 1);
                } else {
                    max = new SongNode(two.getLast().getNext().getSong(), null);
                    two.getLast().setNext(two.getLast().getNext().getNext());
                    two.setSize(two.getSize() - 1);
                }
            }

            if (last == null) {
                last = new SongNode(max.getSong(), null);
                last.setNext(last);

            } else {
                SongNode temp = new SongNode(max.getSong(), last.getNext());
                last.setNext(temp);
                last = temp;
            }

            merged.setSize(merged.getSize() + 1);
        }

        while (one.getSize() > 0) {
            SongNode max = new SongNode(one.getLast().getNext().getSong(), null);
            one.getLast().setNext(one.getLast().getNext().getNext());
            one.setSize(one.getSize() - 1);

            if (last == null) {
                last = new SongNode(max.getSong(), null);
                last.setNext(last);

            } else {
                SongNode temp = new SongNode(max.getSong(), last.getNext());
                last.setNext(temp);
                last = temp;
            }

            merged.setSize(merged.getSize() + 1);
        }

        while (two.getSize() > 0) {
            SongNode max = new SongNode(two.getLast().getNext().getSong(), null);
            two.getLast().setNext(two.getLast().getNext().getNext());
            two.setSize(two.getSize() - 1);

            if (last == null) {
                last = new SongNode(max.getSong(), null);
                last.setNext(last);

            } else {
                SongNode temp = new SongNode(max.getSong(), last.getNext());
                last.setNext(temp);
                last = temp;
            }

            merged.setSize(merged.getSize() + 1);
        }

        merged.setLast(last);
        songLibrary.remove(Math.max(playlistIndex1, playlistIndex2));
        songLibrary.set(Math.min(playlistIndex1, playlistIndex2), merged);
    }


    public void shufflePlaylist(int playlistIndex) {
        Playlist shuffled = new Playlist();
        while (songLibrary.get(playlistIndex).getSize() != 0) {
            int ran = StdRandom.uniformInt(songLibrary.get(playlistIndex).getSize() + 1) + 1;
            SongNode ptr = songLibrary.get(playlistIndex).getLast().getNext();
            SongNode prev = songLibrary.get(playlistIndex).getLast();
            int count = 1;
            while (count < ran) {
                prev = ptr;
                ptr = ptr.getNext();
                count++;
            }
            if (count == songLibrary.get(playlistIndex).getSize()) {
                songLibrary.get(playlistIndex).setLast(prev);
            }
            prev.setNext(ptr.getNext());
            ptr.setNext(null);
            songLibrary.get(playlistIndex).setSize(songLibrary.get(playlistIndex).getSize() - 1);

            if (shuffled.getLast() == null) {
                shuffled.setLast(ptr);
                shuffled.getLast().setNext(shuffled.getLast());
            } else {
                ptr.setNext(shuffled.getLast().getNext());
                shuffled.getLast().setNext(ptr);
                shuffled.setLast(ptr);
            }

            shuffled.setSize(shuffled.getSize() + 1);
        }

        songLibrary.set(playlistIndex, shuffled);
    }


    public void sortPlaylist ( int playlistIndex ) {
        //TODO
    }


    public void playPlaylist(int playlistIndex, int repeats) {
        final String NO_SONG_MSG = " has no link to a song! Playing next...";
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("Nothing to play.");
            return;
        }

        SongNode ptr = songLibrary.get(playlistIndex).getLast().getNext(), first = ptr;

        do {
            StdOut.print("\r" + ptr.getSong().toString());
            if (ptr.getSong().getLink() != null) {
                StdAudio.play(ptr.getSong().getLink());
                for (int ii = 0; ii < ptr.getSong().toString().length(); ii++)
                    StdOut.print("\b \b");
            }
            else {
                StdOut.print(NO_SONG_MSG);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex) {
                    ex.printStackTrace();
                }
                for (int ii = 0; ii < NO_SONG_MSG.length(); ii++)
                    StdOut.print("\b \b");
            }

            ptr = ptr.getNext();
            if (ptr == first) repeats--;
        } while (ptr != first || repeats > 0);
    }


    public void printPlaylist(int playlistIndex) {
        StdOut.printf("%nPlaylist at index %d (%d song(s)):%n", playlistIndex, songLibrary.get(playlistIndex).getSize());
        if (songLibrary.get(playlistIndex).getLast() == null) {
            StdOut.println("EMPTY");
            return;
        }
        SongNode ptr;
        for (ptr = songLibrary.get(playlistIndex).getLast().getNext(); ptr != songLibrary.get(playlistIndex).getLast(); ptr = ptr.getNext() ) {
            StdOut.print(ptr.getSong().toString() + " -> ");
        }
        if (ptr == songLibrary.get(playlistIndex).getLast()) {
            StdOut.print(songLibrary.get(playlistIndex).getLast().getSong().toString() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    public void printLibrary() {
        if (songLibrary.size() == 0) {
            StdOut.println("\nYour library is empty!");
        } else {
                for (int ii = 0; ii < songLibrary.size(); ii++) {
                printPlaylist(ii);
            }
        }
    }


     public ArrayList<Playlist> getPlaylists() { return songLibrary; }
     public void setPlaylists(ArrayList<Playlist> p) { songLibrary = p; }
}
