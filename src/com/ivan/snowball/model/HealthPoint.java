package com.ivan.snowball.model;

import com.ivan.snowball.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class HealthPoint extends Element {
    private Ball mBall = null;
    private Rect mRectMax = null;
    private Rect mRectCurrent = null;
    private int mTotalLength;
    private Double mCurrentLength;
    private Paint mPaintBg;
    private Paint mPaintFg;
    private int mColor;
    private Double mRed;
    private Double mGreen;

    public HealthPoint(Context c, int canvasH, int canvasW, Ball ball) {
        super(c, null, canvasH, canvasW);
        mBall = ball;
        mTotalLength = canvasW - 20;
        mRectMax = new Rect(10, 10, canvasW - 10, 30);
        mCurrentLength = (double)mBall.getLife() /
                ((double)Utils.MAX_LIFE - Utils.MIN_LIFE) *
                mTotalLength;
        mRectCurrent = new Rect(10, 10, 10 + mCurrentLength.intValue(), 30);
        mPaintBg = new Paint();
        mPaintFg = new Paint();
        mPaintBg.setColor(Color.BLACK);
        mRed = 255 * ((double)Utils.MIN_LIFE / (double)mBall.getLife());
        mGreen = 255 * ((double)mBall.getLife() / (double)Utils.MAX_LIFE);
        mColor = Color.argb(255, mRed.intValue(), mGreen.intValue(), 0);
        mPaintFg.setColor(mColor);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        mRed = 255 * ((double)Utils.MIN_LIFE / (double)mBall.getLife());
        mGreen = 255 * ((double)mBall.getLife() / (double)Utils.MAX_LIFE);
        mColor = Color.argb(255, mRed.intValue(), mGreen.intValue(), 0);
        mPaintFg.setColor(mColor);
        mCurrentLength = (double)(mBall.getLife() - Utils.MIN_LIFE) /
                (double)(Utils.MAX_LIFE - Utils.MIN_LIFE) *
                mTotalLength;
        mRectCurrent.right = 10 + mCurrentLength.intValue();
        canvas.drawRect(mRectMax, mPaintBg);
        canvas.drawRect(mRectCurrent, mPaintFg);
    }

    @Override
    public void move() {
    }

    @Override
    public void init() {
    }

}
