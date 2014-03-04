package com.ivan.snowball.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

public class Ball extends Element {
    private Point mPosition = null;
    private Point mBlockPosition = null;
    private Ground mGround = null;
    private float mBallRotate = 0;

    public Ball(Bitmap ballImage, Ground ground, int canvasH, int canvasW) {
        super(ballImage, canvasH, canvasW);
        mGround = ground;
        mBlockPosition = new Point(mCanvasHeight / 5,
                mCanvasHeight - mGround.getFloorHeight() -
                ballImage.getHeight());
        mPosition = mBlockPosition;
    }

    private float calcBallRotateSpeed(int r) {
        Double x = 360 * getGameSpeed() / 2 / Math.PI / r;
        return x.floatValue();
    }

    public void jump() {
        
    }

    @Override
    public void move() {
        mBallRotate = mBallRotate + 
                calcBallRotateSpeed(mImage.getHeight() / 2);
        if(mBallRotate >= 360) {
            mBallRotate = mBallRotate - 360;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Matrix matrix = new Matrix();
        matrix.postTranslate(mPosition.x, mPosition.y);
        matrix.postRotate(mBallRotate,
                mPosition.x + mImage.getWidth() / 2,
                mPosition.y + mImage.getHeight() / 2);
        canvas.drawBitmap(mImage, matrix, paint);
    }
}
