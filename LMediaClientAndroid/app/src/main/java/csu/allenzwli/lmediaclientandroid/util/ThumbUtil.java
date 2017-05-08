package csu.allenzwli.lmediaclientandroid.util;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class ThumbUtil {
    public static void saveVideoThumbBitmap(String originalFileUrl,String thumbPicSavePath){
        File file=new File(thumbPicSavePath);
        if(!file.exists()) {
            FFmpegMediaMetadataRetriever fmmr = new FFmpegMediaMetadataRetriever();
            try {
                fmmr.setDataSource(originalFileUrl);
                Bitmap bitmap = fmmr.getFrameAtTime(4000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                if(bitmap!=null){
                    file.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                fmmr.release();
            }
        }

    }
}
