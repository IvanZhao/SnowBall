package com.ivan.snowball.model;

import com.ivan.snowball.R;
import com.ivan.snowball.utils.Utils;
import com.ivan.snowball.utils.Utils.Hurts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Tree extends Obstacle {

    public Tree(Context c, int canvasH, int canvasW,
            Ground ground, ObstacleCallBackListener listener) {
        super(c, null, canvasH, canvasW, ground, listener);
        mHeight = (int)(Utils.MAX_HEIGHT);
        if(mHeight >= Utils.MAX_HEIGHT / 2) {
            mType = Hurts.BIG_TREE;
        } else {
            mType = Hurts.SMALL_TREE;
        }
        init();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mImage, mLeft, 
                mCanvasHeight - mImage.getHeight() -
                mGround.getFloorHeight(), paint);
    }

    @Override
    public void move() {
        mLeft--;
        if(mLeft <= -mImage.getWidth()) {
            mListener.onMoveOut(this);
        }
    }

    @Override
    public void init() {
        mImage = Utils.scaleBitmapByHeight(mContext,
                R.drawable.tree, mHeight);
        mLeft = mCanvasWidth;
    }

}
