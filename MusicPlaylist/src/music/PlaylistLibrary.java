package music;

import java.util.*;

/**
 * This class represents a library of song playlists.
 *
 * An ArrayList of Playlist objects represents the various playlists 
 * within one's library.
 * 
 * @author Jeremy Hui
 * @author Vian Miranda
 */
public class PlaylistLibrary {

    private ArrayList<Playlist> songLibrary; // contains various playlists

    /**
     * DO NOT EDIT!
     * Constructor for Library.
     * 
     * @param songLibrary passes in ArrayList of playlists
     */
    public PlaylistLibrary(ArrayList<Playlist> songLibrary) {
        this.songLibrary = songLibrary;
    }

    /**
     * DO NOT EDIT!
     * Default constructor for an empty library. 
     */
    public PlaylistLibrary() {
        this(null);
    }

    /**
     * This method reads the songs from an input csv file, and creates a 
     * playlist from it.
     * Each song is on a different line.
     * 
     * 1. Open the file using StdIn.setFile(filename);
     * 
     * 2. Declare a reference that will refer to the last song of the circular linked list.
     * 
     * 3. While there are still lines in the input file:
     *      1. read a song from the file
     *      2. create an object of type Song with the song information
     *      3. Create a SongNode object that holds the Song object from step 3.2.
     *      4. insert the Song object at the END of the circular linked list (use the reference from step 2)
     *      5. increase the count of the number of songs
     * 
     * 4. Create a Playlist object with the reference from step (2) and the number of songs in the playlist
     * 
     * 5. Return the Playlist object
     * 
     * Each line of the input file has the following format:
     *      songName,artist,year,popularity,link
     * 
     * To read a line, use StdIn.readline(), then use .split(",") to extract 
     * fields from the line.
     * 
     * If the playlist is empty, return a Playlist object with null for its last, 
     * and 0 for its size.
     * 
     * The input file has Songs in decreasing popularity order.
     * 
     * DO NOT implement a sorting method, PRACTICE add to end.
     * 
     * @param filename the playlist information input file
     * @return a Playlist object, which contains a reference to the LAST song 
     * in the ciruclar linkedlist playlist and the size of the playlist.
     */
    public Playlist createPlaylist(String filename) {
        //1
        Playlist playlist = new Playlist();
        //2
        StdIn.setFile(filename);
        //3
        SongNode last = null;
        //4
        while (!StdIn.isEmpty()) {
            //a
            //Uses the code provided in the instructions
            String[] data = StdIn.readLine().split(",");
            String name = data[0];
            String artist = data[1];
            int year = Integer.parseInt(data[2]);
            int pop = Integer.parseInt(data[3]);
            String link = data[4];
            //b
            Song newSong = new Song(name, artist, year, pop, link);
            //c
            SongNode add = new SongNode(newSong, null);
            //d
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

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you. 
     * 
     * Adds a new playlist into the song library at a certain index.
     * 
     * 1. Calls createPlayList() with a file containing song information.
     * 2. Adds the new playlist created by createPlayList() into the songLibrary.
     * 
     * Note: initialize the songLibrary if it is null
     * 
     * @param filename the playlist information input file
     * @param playlistIndex the index of the location where the playlist will 
     * be added 
     */
    public void addPlaylist(String filename, int playlistIndex) {
        
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null ) {
            songLibrary = new ArrayList<Playlist>();
        }
        if ( playlistIndex >= songLibrary.size() ) {
            songLibrary.add(createPlaylist(filename));
        } else {
            songLibrary.add(playlistIndex, createPlaylist(filename));
        }        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * This method is already implemented for you.
     * 
     * It takes a playlistIndex, and removes the playlist located at that index.
     * 
     * @param playlistIndex the index of the playlist to remove
     * @return true if the playlist has been deleted
     */
    public boolean removePlaylist(int playlistIndex) {
        /* DO NOT UPDATE THIS METHOD */

        if ( songLibrary == null || playlistIndex >= songLibrary.size() ) {
            return false;
        }

        songLibrary.remove(playlistIndex);
            
        return true;
    }
    
    /** 
     * 
     * Adds the playlists from many files into the songLibrary
     * 
     * 1. Initialize the songLibrary
     * 
     * 2. For each of the filenames
     *       add the playlist into songLibrary
     * 
     * The playlist will have the same index in songLibrary as it has in
     * the filenames array. For example if the playlist is being created
     * from the filename[i] it will be added to songLibrary[i]. 
     * Use the addPlaylist() method. 
     * 
     * @param filenames an array of the filenames of playlists that should be 
     * added to the library
     */
    public void addAllPlaylists(String[] filenames) {
        songLibrary = new ArrayList<Playlist>();
        for (int i = 0; i < filenames.length; i++) {
            addPlaylist(filenames[i], i);
        }
    }

    /**
     * This method adds a song to a specified playlist at a given position.
     * 
     * The first node of the circular linked list is at position 1, the 
     * second node is at position 2 and so forth.
     * 
     * Return true if the song can be added at the given position within the 
     * specified playlist (and thus has been added to the playlist), false 
     * otherwise (and the song will not be added). 
     * 
     * Increment the size of the playlist if the song has been successfully
     * added to the playlist.
     * 
     * @param playlistIndex the index where the playlist will be added
     * @param position the position inthe playlist to which the song 
     * is to be added 
     * @param song the song to add
     * @return true if the song can be added and therefore has been added, 
     * false otherwise. 
     */
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

    /**
     * This method removes a song at a specified playlist, if the song exists. 
     *
     * Use the .equals() method of the Song class to check if an element of 
     * the circular linkedlist matches the specified song.
     * 
     * Return true if the song is found in the playlist (and thus has been 
     * removed), false otherwise (and thus nothing is removed). 
     * 
     * Decrease the playlist size by one if the song has been successfully
     * removed from the playlist.
     * 
     * @param playlistIndex the playlist index within the songLibrary where 
     * the song is to be added.
     * @param song the song to remove.
     * @return true if the song is present in the playlist and therefore has 
     * been removed, false otherwise.
     */
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
                  //CHANGE SIZE AND SET INDEX TO NEW
                  removeFrom.setSize(removeFrom.getSize() - 1);
                  songLibrary.set(playlistIndex, removeFrom);
                  return true;
              } else {
                  prev.setNext(ptr.getNext());
                  ptr.setNext(null);
                  //CHANGE SIZE AND SET INDEX TO NEW
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

    /**
     * This method reverses the playlist located at playlistIndex
     * 
     * Each node in the circular linked list will point to the element that 
     * came before it.
     * 
     * After the list is reversed, the playlist located at playlistIndex will 
     * reference the first SongNode in the original playlist (new last).
     * 
     * @param playlistIndex the playlist to reverse
     */
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

    /**
     * This method merges two playlists.
     * 
     * Both playlists have songs in decreasing popularity order. The resulting 
     * playlist will also be in decreasing popularity order.
     * 
     * You may assume both playlists are already in decreasing popularity 
     * order. If the songs have the same popularity, add the song from the 
     * playlist with the lower playlistIndex first.
     * 
     * After the lists have been merged:
     *  - store the merged playlist at the lower playlistIndex
     *  - remove playlist at the higher playlistIndex 
     * 
     * 
     * @param playlistIndex1 the first playlist to merge into one playlist
     * @param playlistIndex2 the second playlist to merge into one playlist
     */
    public void mergePlaylists(int playlistIndex1, int playlistIndex2) {
        //repurposed method 1
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

    /**
     * This method shuffles a specified playlist using the following procedure:
     * 
     * 1. Create a new playlist to store the shuffled playlist in.
     * 
     * 2. While the size of the original playlist is not 0, randomly generate a number 
     * using StdRandom.uniformInt(1, size+1). Size contains the current number
     * of items in the original playlist.
     * 
     * 3. Remove the corresponding node from the original playlist and insert 
     * it into the END of the new playlist (1 being the first node, 2 being the 
     * second, etc). 
     * 
     * 4. Update the old playlist with the new shuffled playlist.
     *    
     * @param index the playlist to shuffle in songLibrary
     */
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

    /**
     * This method sorts a specified playlist using linearithmic sort.
     * 
     * Set the playlist located at the corresponding playlistIndex
     * in decreasing popularity index order.
     * 
     * This method should  use a sort that has O(nlogn), such as with merge sort.
     * 
     * @param playlistIndex the playlist to shuffle
     */
    public void sortPlaylist ( int playlistIndex ) {

        // WRITE YOUR CODE HERE
        
    }

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Plays playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     * @param repeats number of times to repeat playlist
     * @throws InterruptedException
     */
    public void playPlaylist(int playlistIndex, int repeats) {
        /* DO NOT UPDATE THIS METHOD */

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

    /**
     * ****DO NOT**** UPDATE THIS METHOD
     * Prints playlist by index; can use this method to debug.
     * 
     * @param playlistIndex the playlist to print
     */
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

    /*
     * Used to get and set objects.
     * DO NOT edit.
     */
     public ArrayList<Playlist> getPlaylists() { return songLibrary; }
     public void setPlaylists(ArrayList<Playlist> p) { songLibrary = p; }
}
