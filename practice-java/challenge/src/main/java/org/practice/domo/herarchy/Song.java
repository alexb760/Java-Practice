package org.practice.domo.herarchy;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private String name;
    private Song nextSong;

    public Song(String name) {
        this.name = name;
    }

    public void setNextSong(Song nextSong) {
        this.nextSong = nextSong;
    }

    public boolean isInRepeatingPlaylist() {
        Song slow = this;
        Song fast = this.nextSong;

        while (fast != null && fast.nextSong != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.nextSong;
            fast = fast.nextSong.nextSong;
        }

        return false;
    }


    public static void main(String[] args) {
        Song first = new Song("Hello");
        Song second = new Song("Eye of the tiger");
        List<Integer> dd = new ArrayList<>();

        first.setNextSong(second);
        second.setNextSong(first);

        System.out.println(first.isInRepeatingPlaylist());
    }
}
