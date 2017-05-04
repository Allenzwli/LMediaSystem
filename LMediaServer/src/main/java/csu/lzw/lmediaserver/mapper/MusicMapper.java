package csu.lzw.lmediaserver.mapper;

import csu.lzw.lmediaserver.pojo.Song;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by allenzwli on 2017/4/12.
 */
@Repository
public interface MusicMapper {
    void saveSong(Song song);
    List<Song> getSongsByStartAndLength(@Param("start")int start,@Param("length") int length);
    int getSongsAllCount();
}
