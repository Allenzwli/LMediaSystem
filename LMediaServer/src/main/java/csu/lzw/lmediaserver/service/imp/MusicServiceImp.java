package csu.lzw.lmediaserver.service.imp;

import csu.lzw.lmediaserver.mapper.MusicMapper;
import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.service.MusicService;
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

    public List<Song> getAllSongs() {
        return mMusicMapper.getAllSongs();
    }
}
