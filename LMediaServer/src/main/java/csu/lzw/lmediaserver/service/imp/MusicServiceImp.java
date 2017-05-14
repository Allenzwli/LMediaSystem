package csu.lzw.lmediaserver.service.imp;

import csu.lzw.lmediaserver.mapper.MusicMapper;
import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.service.MusicService;
import csu.lzw.lmediaserver.util.Base64Util;
import csu.lzw.lmediaserver.util.MediaUtil;
import csu.lzw.lmediaserver.util.StaticConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allenzwli on 2017/4/12.
 */
@Service("MusicService")
@Transactional(rollbackFor = Exception.class)
public class MusicServiceImp implements MusicService {

    @Resource
    private MusicMapper mMusicMapper;

    public Map saveSong(MultipartFile songFile, int adminId) {
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(songFile!=null&&!songFile.isEmpty()){
            String originalFileName=songFile.getOriginalFilename().trim();
            String savedFileName= Base64Util.getBase64(originalFileName);
            String realPath= StaticConfig.BASE_LOCAL_MUSIC_FILE_PATH+savedFileName;
            String songType=originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
            if(songType.equals(".mp3")) {
                File file = new File(realPath);
                try {
                    songFile.transferTo(file);
                } catch (IOException e) {
                    //e.printStackTrace();
                    resultMap.put("data",3);
                    resultMap.put("msg",e.toString());
                }
                Song song= MediaUtil.getMP3Info(file);
                song.setFileSize(file.length());
                song.setFileUrl(StaticConfig.MUSIC_FILE_URL_PREFIX+savedFileName);
                song.setAdminId(adminId);
                song.setFileName(originalFileName);
                mMusicMapper.saveSong(song);
                resultMap.put("data",0);
                resultMap.put("msg","歌曲:"+originalFileName+" 已上传成功");
            } else {
                resultMap.put("data",1);
                resultMap.put("msg","上传失败，文件类型应为mp3");
            }
        }else{
            resultMap.put("data",2);
            resultMap.put("msg","上传失败，上传的文件不能为空");
        }
        return resultMap;
    }

    public List<Song> getSongsPerPage(int page) {
        return mMusicMapper.getSongsByStartAndLength(StaticConfig.MUSIC_PER_PAGE_SIZE*(page-1),StaticConfig.MUSIC_PER_PAGE_SIZE,null,null,null);
    }

    public Map getSongsFuzzy(String fuzzy, String orderCol, String orderDir, int startIndex, int length) {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("pageData",mMusicMapper.getSongsByStartAndLength(startIndex,length,fuzzy,orderCol,orderDir));
        resultMap.put("total",mMusicMapper.getSongsAllCount(fuzzy));
        return resultMap;
    }

    public Map getSongsAdvance(String fileName, String artist, String songName, String album, String orderCol, String orderDir, int startIndex, int length) {
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("total",mMusicMapper.getSongsAllCountAdvanced(songName,artist,fileName,album));
        resultMap.put("pageData",mMusicMapper.getSongsByStartAndLengthAdvanced(startIndex,length,songName,artist,fileName,album,orderCol,orderDir));
        return resultMap;
    }

    public Map deleteSongs(String needDeleteJsonArray) {
        Map<String,Object> resultMap=new HashMap<String, Object>();
        List<Song> needDeleteSongList=null;
        try {
            needDeleteSongList=new ObjectMapper().readValue(needDeleteJsonArray,new TypeReference<List<Song>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("data",1);
            resultMap.put("msg","删除失败，json转换失败");
        }
        int idArray[]=new int[needDeleteSongList.size()];
        for(int i=0;i<needDeleteSongList.size();i++){
            idArray[i]=needDeleteSongList.get(i).getId();
            File file=new File(StaticConfig.BASE_LOCAL_MUSIC_FILE_PATH+needDeleteSongList.get(i).getFileName());
            if(file.exists()) {
                file.delete();
            } else{
                resultMap.put("data",2);
                resultMap.put("msg","文件已不在服务器上");
            }

        }
        mMusicMapper.deleteSongs(idArray);
        resultMap.put("data",0);
        resultMap.put("msg","删除成功");
        return resultMap;
    }



    public List<Song> getAllSongs() {
        return mMusicMapper.getAllSongs();
    }

}
