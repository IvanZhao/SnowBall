package com.ivan.snowball.model;

import com.ivan.snowball.R;
import com.ivan.snowball.model.inf.ObstacleCallBackListener;
import com.ivan.snowball.utils.Utils;
import com.ivan.snowball.utils.Utils.Hurts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Tree extends Obstacle {

    public Tree(Context c, int canvasH, int canvasW,
            Ground ground, Ball ball, ObstacleCallBackListener listener) {
        super(c, null, canvasH, canvasW, ground, ball, listener);
        double x = 0.3 + 0.7 * Math.random();
        mHeight = (int)(x * Utils.MAX_HEIGHT);
        if(mHeight >= Utils.MAX_HEIGHT / 2) {
            mType = Hurts.BIG_TREE;
        } else {
            mType = Hurts.SMALL_TREE;
        }
        init();
    }

    @Override
    public void init() {
        mImage = Utils.scaleBitmapByHeight(mContext,
                R.drawable.tree, mHeight);
        mLeft = mCanvasWidth;
    }

}
