package com.ivan.snowball.model;

import java.util.Timer;
import java.util.TimerTask;

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
    private float mJumpSpeed = 20;
    private boolean mJumping = false;
    private boolean mDowning = false;

    public Ball(Bitmap ballImage, Ground ground, int canvasH, int canvasW) {
        super(ballImage, canvasH, canvasW);
        mGround = ground;
        mBlockPosition = new Point(mCanvasHeight / 5,
                mCanvasHeight - mGround.getFloorHeight() -
                ballImage.getHeight());
        mPosition = new Point(mBlockPosition.x, mBlockPosition.y);
    }

    private float calcBallRotateSpeed(int r) {
        Double x = 360 * getGameSpeed() / 2 / Math.PI / r;
        return x.floatValue();
    }

    public void jump() {
        if(mJumping || mDowning) {
            return;
        }
        mJumping = true;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                mJumping = false;
                mDowning = true;
                while(mPosition.y < mBlockPosition.y) {
                    mPosition.y++;
                }
                mDowning = false;
            }
        };
        timer.schedule(task, 4000);
        new Thread() {

            @Override
            public void run() {
                try {
                    while(mJumping) {
                        mPosition.y--;
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
