package csu.lzw.lmediaserver.service;

import csu.lzw.lmediaserver.pojo.Song;

import java.util.List;

/**
 * Created by allenzwli on 2017/4/12.
 */
public interface MusicService {
    void saveSong(Song song);
    List<Song> getSongsPerPage(int page);
    List<Song> getSongsByStartIndexAndLength(String fuzzy,String orderCol,String orderDir,int startIndex, int length);
    int getSongAllCount(String fuzzy);
    List<Song> getSongsByStartIndexAndLengthAdvanced(String fileName,String artist,String songName,String album,String orderCol,String orderDir,int startIndex, int length);
    int getSongAllCountAdvanced(String fileName,String artist,String songName,String album);
    void deleteSongs(int[] idArray);
    List<Song> getAllSongs();
}
