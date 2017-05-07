package csu.lzw.lmediaserver.mapper;

import csu.lzw.lmediaserver.pojo.Song;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by allenzwli on 2017/4/12.
 */
@Repository
public interface MusicMapper {
    void saveSong(Song song);
    List<Song> getSongsByStartAndLength
            (@Param("start")int start, @Param("length") int length, @Param("fuzzy")String fuzzy,@Param("orderCol")String orderCol,@Param("orderDir")String orderDir);
    int getSongsAllCount(@Param("fuzzy")String fuzzy);
    List<Song> getSongsByStartAndLengthAdvanced
            (@Param("start")int start,@Param("length")int length,@Param("songName")String songName,@Param("artist")String artist,@Param("fileName")String fileName,@Param("album")String album,@Param("orderCol")String orderCol,@Param("orderDir")String orderDir);
    int getSongsAllCountAdvanced(@Param("songName")String songName,@Param("artist")String artist,@Param("fileName")String fileName,@Param("album")String album);
    void deleteSongs(int[] idArray);
    List<Song> getAllSongs();
}
