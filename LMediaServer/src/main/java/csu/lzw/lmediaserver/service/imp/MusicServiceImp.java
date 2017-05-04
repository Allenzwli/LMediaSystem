package csu.lzw.lmediaserver.service.imp;

import csu.lzw.lmediaserver.mapper.MusicMapper;
import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.service.MusicService;
import csu.lzw.lmediaserver.util.StaticConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by allenzwli on 2017/4/12.
 */
@Service("MusicService")
@Transactional(rollbackFor = Exception.class)
public class MusicServiceImp implements MusicService {

    @Resource
    private MusicMapper mMusicMapper;

    public void saveSong(Song song) {
        mMusicMapper.saveSong(song);
    }

    public List<Song> getSongsPerPage(int page) {
        return mMusicMapper.getSongsByStartAndLength(StaticConfig.MUSIC_PER_PAGE_SIZE*(page-1),StaticConfig.MUSIC_PER_PAGE_SIZE);
    }

    public List<Song> getSongsByStartIndexAndLength(int startIndex, int length) {

        return mMusicMapper.getSongsByStartAndLength(startIndex,length);
    }

    public int getSongAllCount() {

        return mMusicMapper.getSongsAllCount();
    }

}
