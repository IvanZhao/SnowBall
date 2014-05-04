package com.ivan.snowball.model;

import com.ivan.snowball.model.inf.ObstacleCallBackListener;
import com.ivan.snowball.utils.Utils.Hurts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Obstacle extends Element {

    protected int mHeight;
    protected int mLeft;
    protected Hurts mType;
    protected ObstacleCallBackListener mListener;
    protected Ground mGround;
    protected Ball mBall;
    private boolean mAlreadyHurt = false;

    private int mTop;

    public Obstacle(Context c, Bitmap image, int canvasH, int canvasW,
            Ground ground, Ball ball, ObstacleCallBackListener listener) {
        super(c, image, canvasH, canvasW);
        mListener = listener;
        mGround = ground;
        mBall = ball;
        mAlreadyHurt = false;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        mTop = mCanvasHeight - mImage.getHeight() -
                mGround.getFloorHeight();
        canvas.drawBitmap(mImage, mLeft, mTop, paint);
    }

    @Override
    public void move() {
        mLeft -= getGameSpeed();
        if(mBall.getRight() > mLeft && mBall.getBottom() > mTop) {
            hurtYou(mBall);
        }
        if(mLeft <= -mImage.getWidth()) {
            mListener.onMoveOut(this);
            mImage.recycle();
        }
    }

    public void hurtYou(Ball ball) {
        if(mAlreadyHurt) {
            return;
        }
        ball.gotHurt(mType);
        mAlreadyHurt = true;
    }
}
