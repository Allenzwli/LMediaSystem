package csu.allenzwli.lmediaclientandroid.util;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

import java.io.File;
import java.io.FileOutputStream;

import csu.allenzwli.lmediaclientandroid.model.Video;
import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class ThumbUtil {
    public static void saveVideoThumbBitmap(String originalFileUrl,String thumbPicSavePath){
        File file=new File(thumbPicSavePath);
        if(!file.exists()) {
            FFmpegMediaMetadataRetriever fmmr = new FFmpegMediaMetadataRetriever();
            FileOutputStream fOut=null;
            try {
                fmmr.setDataSource(originalFileUrl);
                Bitmap bitmap = fmmr.getFrameAtTime();
                if (bitmap != null) {
                    Bitmap b2 = fmmr
                            .getFrameAtTime(
                                    4000000,
                                    FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                    if (b2 != null) {
                        bitmap = b2;
                    }
                    if (bitmap.getWidth() > 320) {
                        bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                                320, 240,
                                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                    }
                }
                file.createNewFile();
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                fmmr.release();
            }
        }

    }
}
