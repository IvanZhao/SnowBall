package com.ivan.snowball.model;

import com.ivan.snowball.utils.Utils.Hurts;

import android.content.Context;
import android.graphics.Bitmap;

public abstract class Obstacle extends Element {

    protected int mHeight;
    protected int mLeft;
    protected Hurts mType;
    protected ObstacleCallBackListener mListener;
    protected Ground mGround;

    public Obstacle(Context c, Bitmap image, int canvasH, int canvasW,
            Ground ground, ObstacleCallBackListener listener) {
        super(c, image, canvasH, canvasW);
        mListener = listener;
        mGround = ground;
    }

    public void hurtYou(Ball ball) {
        ball.gotHurt(mType);
    }
}
