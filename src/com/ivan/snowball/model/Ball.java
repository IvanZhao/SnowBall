package com.ivan.snowball.model;

import com.ivan.snowball.R;
import com.ivan.snowball.model.inf.GameOverListener;
import com.ivan.snowball.utils.Utils;
import com.ivan.snowball.utils.Utils.Hurts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.Log;

public class Ball extends Element {
    private double SPEED0 = 10;
    private int mLife = Utils.INIT_LIFE;
    private int mSize = 0;
    private int mDistance = 0;
    private Point mPosition = null;
    private Point mBlockPosition = null;
    private Ground mGround = null;
    private float mBallRotate = 0;
    private double v0 = 0;
    private double vt = 0;
    private boolean mJumping = false;
    private boolean mTouch = false;
    private Object mLock = new Object();
    private boolean mIsAlive = true;
    private GameOverListener mListener;
    private boolean mShowHurt = false;

    private Thread mJumpThread = new Thread() {

        @Override
        public void run() {
            try {
                mJumping = true;
                long t0 = System.currentTimeMillis();
                double t;
                Double s = 0.0;
                int s0 = 0;
                boolean start = false;
                boolean jumping = true;
                boolean rebounding = true;
                while(jumping) {
                    if(mTouch) {
                        Thread.sleep(1);
                    }
                    synchronized(mLock) {
                        if(mTouch) {
                            mTouch = false;
                            t0 = System.currentTimeMillis();
                            s0 = mBlockPosition.y - mPosition.y;
                        }
                        t = (System.currentTimeMillis() - t0) / Utils.SECOND;
                        s = (v0 * t + Utils.G * t * t) * Utils.ENLARGE_RATE
                                + s0;
                        vt = v0 + 2 * Utils.G * t;
                    }
                    if(!start && mPosition.y < mBlockPosition.y) {
                        start = true;
                    }
                    if(mPosition.y > mBlockPosition.y) {
                        mPosition.y = mBlockPosition.y;
                    } else {
                        mPosition.y = mBlockPosition.y - s.intValue();
                    }
                    if(start && mPosition.y == mBlockPosition.y) {
                        jumping = false;
                    }
                }
                if(Math.abs(vt) <= Utils.SAFE_SPEED) {
                    v0 = SPEED0;
                    vt = 0.0;
                    mTouch = false;
                    mJumping = false;
                    return;
                }
                t0 = System.currentTimeMillis();
                start = false;
                s = 0.0;
                mPosition.y = mBlockPosition.y;
                v0 = Math.abs(vt);
                while(rebounding) {
                    t = (System.currentTimeMillis() - t0) / Utils.SECOND;
                    s = (v0 / 3 * t + Utils.G * t * t) * Utils.ENLARGE_RATE;
                    if(!start && mPosition.y < mBlockPosition.y) {
                        start = true;
                    }
                    if(mPosition.y > mBlockPosition.y) {
                        mPosition.y = mBlockPosition.y;
                    } else {
                        mPosition.y = mBlockPosition.y - s.intValue();
                    }
                    if(start && mPosition.y == mBlockPosition.y) {
                        rebounding = false;
                    }
                }
                v0 = SPEED0;
                vt = 0.0;
                mTouch = false;
                mJumping = false;
                gotHurt(Hurts.FALL);
                mPosition = new Point(mBlockPosition.x, mBlockPosition.y);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    };

    public Ball(Context c, Bitmap ballImage, Ground ground,
            int canvasH, int canvasW) {
        super(c, ballImage, canvasH, canvasW);
        mIsAlive = true;
        mGround = ground;
        mSize = ballImage.getHeight();
        mBlockPosition = new Point(mCanvasHeight / 5,
                mCanvasHeight - mGround.getFloorHeight() -
                ballImage.getHeight());
        mPosition = new Point(mBlockPosition.x, mBlockPosition.y);
    }

    public void setGameOverListener(GameOverListener listener) {
        mListener = listener;
    }

    private float calcBallRotateSpeed(int r) {
        Double x = 360 * getGameSpeed() / 2 / Math.PI / r;
        return x.floatValue();
    }

    private double calcSpeed0() {
        double s = (mCanvasHeight - mCanvasHeight / 6 -
                4 * mGround.getFloorHeight()) / Utils.ENLARGE_RATE;
        double t = 10 / 9.8;
        return (s - Utils.G * t * t) / t;
    }

    public int getLife() {
        return mLife;
    }

    public int getHeight() {
        return mImage.getHeight();
    }

    private void grow() {
        Double newSize = (mLife / (double)Utils.INIT_LIFE) * mSize;
        mImage = scaleBitmapBySize(newSize.intValue());
        mBlockPosition = new Point(mCanvasHeight / 5,
                mCanvasHeight - mGround.getFloorHeight() -
                mImage.getHeight());
        mPosition = new Point(mBlockPosition.x, mBlockPosition.y);
    }

    private void shrink() {
        Double newSize = (mLife / (double)Utils.INIT_LIFE) * mSize;
        mImage = scaleBitmapBySize(newSize.intValue());
        mBlockPosition = new Point(mCanvasHeight / 5,
                mCanvasHeight - mGround.getFloorHeight() -
                mImage.getHeight());
    }

    private Bitmap scaleBitmapBySize(int size) {
        return Bitmap.createScaledBitmap(
                Utils.readBitMap(mContext, R.drawable.ball3),
                size, size, true);
    }

    private void showHurt() {
        mShowHurt = true;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(500);
                    mShowHurt = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public void gotHurt(Hurts hurt) {
        mLife -= Utils.HurtValue[hurt.ordinal()];
        showHurt();
        if(mLife < Utils.MIN_LIFE) {
            dead();
        } else {
            shrink();
        }
    }

    synchronized private void dead() {
        if(!mIsAlive) {
            return;
        }
        mIsAlive = false;
        if(mListener != null) {
            mListener.onGameOver();
        }
    }

    public boolean isAlive() {
        return mIsAlive;
    }

    public void jump() {
        if(mJumping && vt > 0) {
            return;
        }
        if(!mJumping) {
            SPEED0 = calcSpeed0();
            v0 = SPEED0;
            vt = v0;
            mJumpThread.run();
        } else {
            mTouch = true;
            synchronized(mLock) {
                if(Math.abs(vt) >= Utils.SAFE_SPEED) {
                    gotHurt(Hurts.FALL);
                }
                v0 = -vt * Utils.SPEED_REDUCE_RATE;
            }
        }
    }

    public int getDistance() {
        return mDistance;
    }

    @Override
    public void move() {
        mDistance++;
        if(!mJumping) {
            if(mDistance % Utils.GROW_DISTANCE == 0) {
                if(mLife < Utils.MAX_LIFE) {
                    mLife++;
                }
                grow();
            }
        }
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
        if(mShowHurt) {
           if((mDistance / 2) % 2 == 0) {
               canvas.drawBitmap(mImage, matrix, paint);
           }
        } else {
            canvas.drawBitmap(mImage, matrix, paint);
        }
    }

    @Override
    public void init() {
        mIsAlive = true;
        mDistance = 0;
        mLife = Utils.INIT_LIFE;
        mImage = scaleBitmapBySize(mSize);
        mBlockPosition = new Point(mCanvasHeight / 5,
                mCanvasHeight - mGround.getFloorHeight() -
                mImage.getHeight());
        mPosition = new Point(mBlockPosition.x, mBlockPosition.y);
    }

    public int getTop() {
        return mPosition.y;
    }

    public int getLeft() {
        return mPosition.x;
    }

    public int getRight() {
        return mPosition.x + mImage.getWidth();
    }

    public int getBottom() {
        return mPosition.y + mImage.getHeight();
    }
}