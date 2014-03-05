package com.ivan.snowball.model;

import com.ivan.snowball.utils.SnowBallUtil;
import com.ivan.snowball.utils.SnowBallUtil.Hurts;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

public class Ball extends Element {
    private int mLife = 100;
    private Point mPosition = null;
    private Point mBlockPosition = null;
    private Ground mGround = null;
    private float mBallRotate = 0;
    private float v0 = 10;
    private boolean mJumping = false;

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

    public int getLife() {
        return mLife;
    }

    public void gotHurt(Hurts hurt) {
        mLife -= SnowBallUtil.HurtValue[hurt.ordinal()];
        if(mLife < SnowBallUtil.MIN_LIFE) {
            dead();
        }
    }

    public void dead() {
    }

    public void jump() {
        if(mJumping) {
            return;
        }
        mJumping = true;
        new Thread() {

            @Override
            public void run() {
                try {
                    long t0 = System.currentTimeMillis();
                    double t;
                    Double s = 0.0;
                    boolean start = false;
                    boolean jumping = true;
                    boolean rebounding = true;
                    while(jumping) {
                        t = (System.currentTimeMillis() - t0) / 1000.0;
                        s = (v0 * t + SnowBallUtil.G * t * t) * 100;
                        if(!start && s.intValue() > 0) {
                            start = true;
                        }
                        if(s.intValue() <= 0) {
                            mPosition.y = mBlockPosition.y;
                        } else {
                            mPosition.y = mBlockPosition.y - s.intValue();
                        }
                        if(start && s.intValue() <= 0) {
                            jumping = false;
                        }
                    }
                    t0 = System.currentTimeMillis();
                    start = false;
                    while(rebounding) {
                        t = (System.currentTimeMillis() - t0) / 1000.0;
                        s = (v0 / 3 * t + SnowBallUtil.G * t * t) * 100;
                        if(!start && s.intValue() > 0) {
                            start = true;
                        }
                        if(s.intValue() <= 0) {
                            mPosition.y = mBlockPosition.y;
                        } else {
                            mPosition.y = mBlockPosition.y - s.intValue();
                        }
                        if(start && s.intValue() <= 0) {
                            rebounding = false;
                        }
                    }
                    mJumping = false;
                    gotHurt(Hurts.FALL);
                } catch(Exception e) {
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