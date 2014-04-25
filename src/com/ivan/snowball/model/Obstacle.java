package com.ivan.snowball.model;

import com.ivan.snowball.utils.Utils.Hurts;

import android.content.Context;
import android.graphics.Bitmap;

public abstract class Obstacle extends Element {

    protected int mHeight;
    protected Hurts mType;
    protected static final int MAX_HEIGHT = 100;

    public Obstacle(Context c, Bitmap image, int canvasH, int canvasW) {
        super(c, image, canvasH, canvasW);
    }

    public void hurtYou(Ball ball) {
        ball.gotHurt(mType);
    }
}
