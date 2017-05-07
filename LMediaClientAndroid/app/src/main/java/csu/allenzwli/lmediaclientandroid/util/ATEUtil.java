package csu.allenzwli.lmediaclientandroid.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.TypedValue;

import csu.allenzwli.lmediaclientandroid.R;

/**
 * Created by allenzwli on 2017/5/7.
 */

public class ATEUtil {

    public static Drawable getDefaultAlbumDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.icon_album_dark);
    }

}
