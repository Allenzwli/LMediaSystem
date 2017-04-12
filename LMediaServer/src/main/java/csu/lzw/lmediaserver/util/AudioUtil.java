package csu.lzw.lmediaserver.util;

import csu.lzw.lmediaserver.pojo.Song;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by allenzwli on 2017/4/11.
 */
public class AudioUtil {

    /**
     * 获取MP3歌曲名、歌手、时长信息
     * @param mp3File
     * @return
     */
    public static Song getMP3Info(File mp3File) {
        Song song = new Song();
        try {
            MP3File file = new MP3File(mp3File);
            MP3AudioHeader audioHeader = (MP3AudioHeader)file.getAudioHeader();
            song.setSongName(file.displayStructureAsPlainText());

            //其它的属性之后在研究这个库的API来扩充
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        return song;
    }


    /**
     * 获取MP3封面图片
     * @param mp3File
     * @return
     */
    public static byte[] getMP3Image(File mp3File) {
        byte[] imageData = null;
        try {
            MP3File mp3file = new MP3File(mp3File);
            AbstractID3v2Tag tag = mp3file.getID3v2Tag();
            AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
            FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
            imageData = body.getImageData();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return imageData;
    }



    /**
     *获取mp3图片并将其保存至指定路径下
     * @param mp3File mp3文件对象
     * @param mp3ImageSavePath mp3图片保存位置（默认mp3ImageSavePath +"\" mp3File文件名 +".jpg" ）
     * @param cover 是否覆盖已有图片
     * @return 生成图片全路径
     */
    public static String saveMP3Image(File mp3File, String mp3ImageSavePath, boolean cover) {
        //生成mp3图片路径
        String mp3FileLabel = getFileLabel(mp3File.getName());
        String mp3ImageFullPath = mp3ImageSavePath + ("\\" + mp3FileLabel + ".jpg");

        //若为非覆盖模式，图片存在则直接返回（不再创建）
        if( !cover ) {
            File tempFile = new File(mp3ImageFullPath) ;
            if(tempFile.exists()) {
                return mp3ImageFullPath;
            }
        }

        //生成mp3存放目录
        File saveDirectory = new File(mp3ImageSavePath);
        saveDirectory.mkdirs();

        //获取mp3图片
        byte imageData[] = getMP3Image(mp3File);
        //若图片不存在，则直接返回null
        if (null == imageData || imageData.length == 0) {
            return null;
        }
        //保存mp3图片文件
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mp3ImageFullPath);
            fos.write(imageData);
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return mp3ImageFullPath;
    }


    /**
     * 仅返回文件名（不包含.类型）
     * @param fileName
     * @return
     */
    private static String getFileLabel(String fileName) {
        int indexOfDot = fileName.lastIndexOf(".");
        fileName = fileName.substring(0,(indexOfDot==-1?fileName.length():indexOfDot));
        return fileName;
    }
    private static String toGB2312(String s) {
        try {
            return new String(s.getBytes("ISO-8859-1"), "gb2312");
        } catch (Exception e) {
            return s;
        }
    }
}
