package csu.lzw.lmediaserver.util;

/**
 * Created by allenzwli on 2017/5/3.
 */
public class StaticConfig {

    //The disk path on the depoly server where to save the music file
    public static final String BASE_LOCAL_MUSIC_FILE_PATH="/Users/allenzwli/Documents/songlib/";

    public static final String BASE_LOCAL_VIDEO_FILE_PATH="/Users/allenzwli/Documents/videolib/";

    public static final String BASE_LOCAL_ALBUM_PIC_PATH="/Users/allenzwli/Documents/songpic/";

    //The url prefix when saving the info about a music
    public static final String MUSIC_FILE_URL_PREFIX="http://127.0.0.1:8080/songlib/";

    public static final String ALBUM_PICTURE_URL_PREFIX="http://127.0.0.1:8080/songpic/";

    public static final String VIDEO_FILE_URL_PREFIX="http://127.0.0.1:8080/videolib/";

    //The count of music songs when responsing once
    public static final int MUSIC_PER_PAGE_SIZE=10;

    public static final int VIDEO_PER_PAGE_SIZE=10;
}
