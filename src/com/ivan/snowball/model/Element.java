package com.ivan.snowball.model;

import com.ivan.snowball.utils.Utils;
import com.ivan.snowball.utils.Utils.Gears;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Element {
    protected Context mContext = null;
    protected Bitmap mImage = null;
    protected Gears mSpeedGear = Gears.NORMAL;
    protected int mCanvasHeight = 0;
    protected int mCanvasWidth = 0;
    protected int mImageWidth = 0;

    public Element(Context c, Bitmap image, int canvasH, int canvasW) {
        mContext = c;
        mImage = image;
        mCanvasHeight = canvasH;
        mCanvasWidth = canvasW;
        mImageWidth = mImage.getWidth();
    }

    public void setGameSpeed(Gears gear) {
        mSpeedGear = gear;
    }

    public Gears getSpeedGear() {
        return mSpeedGear;
    }

    public int getGameSpeed() {
        return Utils.GameSpeed[mSpeedGear.ordinal()];
    }

    public void moveAndDraw(Canvas canvas, Paint paint) {
        draw(canvas, paint);
        move();
    }

    public abstract void draw(Canvas canvas, Paint paint);

    public abstract void move();

    public abstract void init();
}
