package csu.lzw.lmediaserver.mapper;

import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.pojo.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by allenzwli on 2017/5/6.
 */
@Repository
public interface VideoMapper {
    void saveVideo(Video video);
    List<Video> getVideosByStartAndLength
            (@Param("start")int start, @Param("length") int length, @Param("fuzzy")String fuzzy, @Param("orderCol")String orderCol, @Param("orderDir")String orderDir);
    int getVideosAllCount(@Param("fuzzy")String fuzzy);
    void deleteVideos(int[] idArray);
    List<Video> getAllVideos();
}
