package com.ivan.snowball.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Ground extends Element {
    private int mLeft0 = 0;
    private int mLeft1 = 0;
    private int mLeft2 = 0;

    public Ground(Bitmap groundImage, int canvasH, int canvasW) {
        super(groundImage, canvasH, canvasW);
        mLeft0 = 0;
        mLeft1 = mImageWidth - 1;
        mLeft2 = mImageWidth * 2 - 2;
    }

    public int getFloorHeight() {
        return mImage.getHeight();
    }

    @Override
    public void move() {
        int speed = getGameSpeed();
        mLeft0 -= speed;
        mLeft1 -= speed;
        mLeft2 -= speed;
        if(mLeft0 <= -mImageWidth) {
            mLeft0 = mImageWidth * 2 - speed;
        }
        if(mLeft1 <= -mImageWidth) {
            mLeft1 = mImageWidth * 2 - speed;
        }
        if(mLeft2 <= -mImageWidth) {
            mLeft2 = mImageWidth * 2 - speed;
        }
        if(mLeft0 > mLeft2 + mImageWidth) {
            mLeft0 = mLeft2 + mImageWidth;
        }
        if(mLeft1 > mLeft0 + mImageWidth) {
            mLeft1 = mLeft0 + mImageWidth;
        }
        if(mLeft2 > mLeft1 + mImageWidth) {
            mLeft2 = mLeft1 + mImageWidth;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Log.d("IVANDEBUG", "---------------------------------------------");
        Log.d("IVANDEBUG", "pic1 left: " + mLeft0 + ", right: " + (mLeft0 + mImage.getWidth()));
        Log.d("IVANDEBUG", "pic2 left: " + mLeft1 + ", right: " + (mLeft1 + mImage.getWidth()));
        Log.d("IVANDEBUG", "pic3 left: " + mLeft2 + ", right: " + (mLeft2 + mImage.getWidth()));
        canvas.drawBitmap(mImage, mLeft0,
                mCanvasHeight - mImage.getHeight(), paint);
        canvas.drawBitmap(mImage, mLeft1,
                mCanvasHeight - mImage.getHeight(), paint);
        canvas.drawBitmap(mImage, mLeft2,
                mCanvasHeight - mImage.getHeight(), paint);
    }
}
