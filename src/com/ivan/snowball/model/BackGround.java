package com.ivan.snowball.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BackGround extends Element {
    private int mLeft0 = 0;
    private int mLeft1 = 0;

    public BackGround(Context c, Bitmap image,
            int canvasH, int canvasW) {
        super(c, image, canvasH, canvasW);
        mLeft0 = 0;
        mLeft1 = mImage.getWidth();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mImage, mLeft0, 0, paint);
        canvas.drawBitmap(mImage, mLeft1, 0, paint);
    }

    @Override
    public void move() {
        int speed = getGameSpeed() / 3;
        mLeft0 -= speed;
        mLeft1 -= speed;
        int pageWidth = mImage.getWidth();
        if(mLeft0 <= -pageWidth) {
            mLeft0 = pageWidth;
        } 
        if(mLeft1 <= -pageWidth) {
            mLeft1 = pageWidth;
        }
    }

    @Override
    public void init() {
        mLeft0 = 0;
        mLeft1 = mImage.getWidth();
    }

}
