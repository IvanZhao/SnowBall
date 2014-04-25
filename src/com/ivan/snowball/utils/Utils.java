package com.ivan.snowball.utils;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Utils {
    public static enum Gears{STOP, NORMAL, HIGH};
    public static final int[] GameSpeed = {0, 3, 24};
    public static final float G = -9.8f;

    public static enum Hurts{DRY_LAND, WATER, FALL, SMALL_TREE, BIG_TREE,
        VILLAGE};
    public static final int[] HurtValue = {1, 2, 10, 20, 50, 80};

    public static final int MAX_LIFE = 200;
    public static final int INIT_LIFE = 80;
    public static final int MIN_LIFE = 30;

    public static final int GROW_DISTANCE = 100;
    public static final int SAFE_SPEED = 6;
    public static final double SPEED_REDUCE_RATE = 0.5;
    public static final double SECOND = 1000.0;
    public static final float ENLARGE_RATE = 100.0f;

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
