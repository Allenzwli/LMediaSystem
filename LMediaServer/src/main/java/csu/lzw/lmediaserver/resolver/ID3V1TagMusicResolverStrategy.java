package csu.lzw.lmediaserver.resolver;

import com.mpatric.mp3agic.*;
import csu.lzw.lmediaserver.pojo.Song;

import java.io.File;
import java.io.IOException;

/**
 * Created by allenzwli on 2017/5/16.
 */
public class ID3V1TagMusicResolverStrategy implements IMusicResolver {

    private Mp3File mMp3File;

    public ID3V1TagMusicResolverStrategy(File f){
        try {
            mMp3File=new Mp3File(f);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
    }

    public String getSongName() {
        if (mMp3File.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mMp3File.getId3v1Tag();
            return id3v1Tag.getTitle();
        }
        return null;
    }

    public String getArtist() {
        if (mMp3File.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mMp3File.getId3v1Tag();
            return id3v1Tag.getTitle();
        }
        return null;
    }

    public String getAlbum() {
        if (mMp3File.hasId3v1Tag()) {
            ID3v1 id3v1Tag = mMp3File.getId3v1Tag();
            return id3v1Tag.getAlbum();
        }
        return null;
    }

    public String getPictureUrl() {
        return null;
    }

    public long getDuration() {
        return mMp3File.getLengthInMilliseconds();
    }

}
