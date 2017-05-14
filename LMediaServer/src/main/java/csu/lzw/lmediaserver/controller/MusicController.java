package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Admin;
import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.service.AdminService;
import csu.lzw.lmediaserver.service.MusicService;
import csu.lzw.lmediaserver.util.Base64Util;
import csu.lzw.lmediaserver.util.MD5Util;
import csu.lzw.lmediaserver.util.MediaUtil;
import csu.lzw.lmediaserver.util.StaticConfig;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
@RequestMapping("/musics")
public class MusicController {

    private Logger mLogger = Logger.getLogger(MusicController.class);

    @Resource
    private MusicService mMusicService;

    @Resource
    private AdminService mAdminService;

    @RequestMapping("/manage")
    public String musicManageDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null){
            return "login";
        }
        return "music_manage";
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Song> getSongsList(){
                return mMusicService.getAllSongs();//全部
    }

    @ResponseBody
    @RequestMapping("/get")
    public Map getSongsByStartAndLength(int startIndex,int pageSize,String fuzzy,String orderColumn,String orderDir,boolean fuzzySearch,String fileName,String songName,String artist,String album){
        if(fuzzySearch){
            //模糊搜索
            return mMusicService.getSongsFuzzy(fuzzy,orderColumn,orderDir,startIndex,pageSize);
        }
        else {
            //高级搜索
            return mMusicService.getSongsAdvance(fileName,artist,songName,album,orderColumn,orderDir,startIndex,pageSize);
        }
    }

    @RequestMapping("/add")
    public String musicAddDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null){
            return "login";
        }
        return "music_add";
    }



    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map musicUpload(@RequestParam(value = "songFile", required = false) MultipartFile songFile,int adminId,String token){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(!mAdminService.validAdminToken(token,adminId)){
            resultMap.put("data",4);
            resultMap.put("msg","上传失败，无效的管理员身份");
        }else{
            resultMap=mMusicService.saveSong(songFile,adminId);
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Map musicDelete(@RequestParam(value = "needDeleteJsonArray")String needDeleteJsonArray, String token, int adminId){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(!mAdminService.validAdminToken(token,adminId)){
            resultMap.put("data",3);
            resultMap.put("msg","删除失败，无效的管理员身份");
        }else{
            resultMap=mMusicService.deleteSongs(needDeleteJsonArray);
        }
        return resultMap;
    }
}
