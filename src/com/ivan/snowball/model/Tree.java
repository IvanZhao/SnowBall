package com.ivan.snowball.model;

import com.ivan.snowball.utils.Utils.Hurts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Tree extends Obstacle {

    public Tree(Context c, Bitmap image, int canvasH, int canvasW) {
        super(c, image, canvasH, canvasW);
        mHeight = (int)(Math.random() * MAX_HEIGHT);
        if(mHeight >= MAX_HEIGHT / 2) {
            mType = Hurts.BIG_TREE;
        } else {
            mType = Hurts.SMALL_TREE;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        // TODO Auto-generated method stub

    }

    @Override
    public void move() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

}
