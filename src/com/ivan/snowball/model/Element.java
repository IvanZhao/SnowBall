package com.ivan.snowball.model;

import com.ivan.snowball.utils.SnowBallUtil;
import com.ivan.snowball.utils.SnowBallUtil.Gears;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Element {
    protected Bitmap mImage = null;
    protected Gears mSpeedGear = Gears.NORMAL;
    protected int mCanvasHeight = 0;
    protected int mCanvasWidth = 0;
    protected int mImageWidth = 0;

    public Element(Bitmap image, int canvasH, int canvasW) {
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
        return SnowBallUtil.GameSpeed[mSpeedGear.ordinal()];
    }

    public void moveAndDraw(Canvas canvas, Paint paint) {
        draw(canvas, paint);
        move();
    }

    public abstract void draw(Canvas canvas, Paint paint);

    public abstract void move();
}
