package csu.lzw.lmediaserver.service;

import csu.lzw.lmediaserver.pojo.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by allenzwli on 2017/4/12.
 */
public interface MusicService {
    Map saveSong(MultipartFile songFile, int adminId);

    List<Song> getSongsPerPage(int page);

    Map getSongsFuzzy(String fuzzy,String orderCol,String orderDir,int startIndex, int length);

    Map getSongsAdvance(String fileName,String artist,String songName,String album,String orderCol,String orderDir,int startIndex, int length);

    Map deleteSongs(String needDeleteJsonArray);

    List<Song> getAllSongs();
}
