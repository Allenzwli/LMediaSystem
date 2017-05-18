package csu.allenzwli.lmediaclientandroid.util;

import java.util.ArrayList;
import java.util.List;

import csu.allenzwli.lmediaclientandroid.model.Song;

/**
 * Created by allenzwli on 2017/5/8.
 */

public class PlayListSingleton {

    private static PlayListSingleton instance;

    private PlayListSingleton(){
        mSongLists=new ArrayList<Song>();
    }

    public static PlayListSingleton getInstance(){
        if(null==instance){
            synchronized (PlayListSingleton.class){
                if(instance==null){
                    instance=new PlayListSingleton();
                }
            }

        }
        return instance;
    }

    private List<Song> mSongLists;

    private String mCurrentSongUrl;

    private int mCurrentSongIndex;

    private Song mcurrentSong;

    public Song getMcurrentSong() {
        return mSongLists.get(mCurrentSongIndex);
    }

    public List<Song> getmSongLists() {
        return mSongLists;
    }

    public void setmCurrentSongIndex(int mCurrentSongIndex) {
        this.mCurrentSongIndex = mCurrentSongIndex;
    }

    public int getmCurrentSongIndex() {
        return mCurrentSongIndex;
    }

    public void addSongList(List<Song> songList){
        mSongLists.addAll(songList);
    }

    public void addSong(Song song){
        mSongLists.add(song);
    }

    public void resetSongList(){
        mSongLists.clear();
    }

    public void refreshSongList(List<Song> songList){
        mSongLists.clear();
        mSongLists.addAll(songList);
    }

    public void setmCurrentSongUrl(String mCurrentSongUrl) {
        this.mCurrentSongUrl = mCurrentSongUrl;
    }

    public List<Song> getApplicationSongList(){
        return mSongLists;
    }

    public String getmCurrentSongUrl() {
        return mCurrentSongUrl;
    }
}
