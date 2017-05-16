package csu.lzw.lmediaserver.resolver;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.util.StaticConfig;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by allenzwli on 2017/5/16.
 */
public class ID3V2TagMusicResolverStrategy implements IMusicResolver{

    private Mp3File mMp3File;

    public ID3V2TagMusicResolverStrategy(File f){
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
        if (mMp3File.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mMp3File.getId3v2Tag();
            return id3v2Tag.getTitle();
        }
        return null;
    }

    public String getArtist() {
        if (mMp3File.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mMp3File.getId3v2Tag();
            return id3v2Tag.getArtist();
        }
        return null;
    }

    public String getAlbum() {
        if (mMp3File.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mMp3File.getId3v2Tag();
            return id3v2Tag.getAlbum();
        }
        return null;
    }

    public String getPictureUrl() {
        if (mMp3File.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mMp3File.getId3v2Tag();
            return saveAlbumPicture(id3v2Tag);
        }
        return null;
    }

    public long getDuration() {
        return mMp3File.getLengthInMilliseconds();
    }

    /**
     * 保存歌曲中的专辑封面
     *
     * @param id3v2Tag
     * @return 封面URl
     */
    private String saveAlbumPicture(ID3v2 id3v2Tag){
        StringBuffer albumNameSB = new StringBuffer();
        byte[] imageData = id3v2Tag.getAlbumImage();
        if (imageData != null) {
            String mimeType = id3v2Tag.getAlbumImageMimeType();
            albumNameSB.append(id3v2Tag.getTitle());
            albumNameSB.append("_");
            albumNameSB.append(id3v2Tag.getArtist());
            albumNameSB.append("_");
            albumNameSB.append(id3v2Tag.getAlbum());
            if (mimeType.equals("image/jpg")) {
                albumNameSB.append(".jpg");
            } else if (mimeType.equals("image/jpeg")) {
                albumNameSB.append(".jpeg");
            } else if (mimeType.equals("image/png")) {
                albumNameSB.append(".png");
            }
            File file = new File(StaticConfig.BASE_LOCAL_ALBUM_PIC_PATH+albumNameSB.toString());
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    RandomAccessFile raFile = new RandomAccessFile(file, "rw");
                    raFile.write(imageData);
                    raFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return StaticConfig.ALBUM_PICTURE_URL_PREFIX+albumNameSB.toString();
    }
}
