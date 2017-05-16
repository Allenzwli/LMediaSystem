package csu.lzw.lmediaserver.resolver;

import csu.lzw.lmediaserver.pojo.Song;

/**
 * Created by allenzwli on 2017/5/16.
 * 使用策略模式，分离业务逻辑与具体的解析算法，减少耦合
 */
public class MusicResolver {

    private IMusicResolver mMusicResolver;

    public MusicResolver(IMusicResolver i){
        mMusicResolver=i;
    }

    public void setMusicResolver(IMusicResolver i){
        mMusicResolver=i;
    }

    public String performGetSongName(){
        return mMusicResolver.getSongName();
    }

    public String performGetArtist(){
        return mMusicResolver.getArtist();
    }

    public String performGetAlbum(){
        return mMusicResolver.getAlbum();
    }

    public String performGetPictureUrl(){
        return mMusicResolver.getPictureUrl();
    }

    public long performGetDuration(){
        return mMusicResolver.getDuration();
    }

    public Song performGetSongBean() {
        if(mMusicResolver!=null){
            Song song=new Song();
            song.setSongName(mMusicResolver.getSongName());
            song.setArtist(mMusicResolver.getArtist());
            song.setAlbum(mMusicResolver.getAlbum());
            song.setDuration(mMusicResolver.getDuration());
            song.setPictureUrl(mMusicResolver.getPictureUrl());
            return song;
        }
        return null;
    }

}
