package csu.lzw.lmediaserver.util;

import com.mpatric.mp3agic.*;
import csu.lzw.lmediaserver.pojo.Song;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by allenzwli on 2017/4/11.
 */
public class MediaUtil {

    /**
     * 获取MP3歌曲名、歌手、时长信息
     *
     * @param file
     * @return Song
     */
    public static Song getMP3Info(File file) {
        Song song = new Song();
        try {
            Mp3File mp3file = new Mp3File(file);
            song.setDuration(mp3file.getLengthInMilliseconds());
            if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                song.setSongName(id3v1Tag.getTitle());
                song.setArtist(id3v1Tag.getArtist());
                song.setAlbum(id3v1Tag.getAlbum());

            } else if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                song.setSongName(id3v2Tag.getTitle());
                song.setArtist(id3v2Tag.getArtist());
                song.setAlbum(id3v2Tag.getAlbum());
                song.setPictureUrl(saveAlbumPicture(id3v2Tag));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }

        return song;
    }

    /**
     * 保存歌曲中的专辑封面
     *
     * @param id3v2Tag
     * @return 封面URl
     */
    private static String saveAlbumPicture(ID3v2 id3v2Tag){
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
