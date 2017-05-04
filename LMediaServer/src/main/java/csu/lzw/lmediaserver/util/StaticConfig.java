package csu.lzw.lmediaserver.util;

/**
 * Created by allenzwli on 2017/5/3.
 */
public class StaticConfig {

    //The disk path on the depoly server where to save the music file
    public static final String BASE_LOCAL_MUSIC_FILE_PATH="/Users/allenzwli/Documents/songlib/";

    //The url prefix when saving the info about a music
    public static final String MUSIC_FILE_URL_PREFIX="localhost:8080/songlib/";

    //The count of music songs when responsing once
    public static final int MUSIC_PER_PAGE_SIZE=10;
}
