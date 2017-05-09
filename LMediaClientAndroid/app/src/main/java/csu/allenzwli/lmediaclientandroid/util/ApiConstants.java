package csu.allenzwli.lmediaclientandroid.util;

import android.os.Environment;

import csu.allenzwli.lmediaclientandroid.base.LApplication;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class ApiConstants {
    public class Integers {
        public static final long PAGE_LAZY_LOAD_DELAY_TIME_MS =200 ;
    }

    public class ApiUrl{
        private static final String REMOTE_IP="http://182.254.216.85:8080";
        public static final String ONLINE_MUSIC_LIST=REMOTE_IP+"/LMediaServer/musics/list";
        public static final String ONLINE_VIDEO_LIST=REMOTE_IP+"/LMediaServer/videos/list";

        public static final String ADMIN_MANAGE=REMOTE_IP+"/LMediaServer/";
    }

}
