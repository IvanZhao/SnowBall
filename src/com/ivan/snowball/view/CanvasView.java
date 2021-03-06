package com.ivan.snowball.view;

import com.ivan.snowball.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CanvasView extends SurfaceView implements Callback,
        Runnable {

    private MainActivity mActivity = null;

    private Canvas mCanvas = null;
    private SurfaceHolder mSurfaceHolder = null;
    private Thread mThread = null;
    private Paint mPaint = null;
    private boolean mRun = false;
    private boolean mPause = false;
    private Object mLock = new Object();

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mPaint = new Paint();
        setFocusable(true);
    }

    public void setActivity(MainActivity activity) {
        mActivity = activity;
    }

    private void draw() {
        mActivity.getBackgroundObject().moveAndDraw(mCanvas, mPaint);
        mActivity.getGroundObject().moveAndDraw(mCanvas, mPaint);
        mActivity.getObstacleController().moveAndDraw(mCanvas, mPaint);
        mActivity.getBallObject().moveAndDraw(mCanvas, mPaint);
        mActivity.getHPObject().moveAndDraw(mCanvas, mPaint);
    }

    public void startGame() {
        mRun = true;
        mPause = false;
        mThread = new Thread(this);
        mThread.start();
    }

    public void stopGame() {
        mRun = false;
        mPause = false;
    }

    public void pauseGame() {
        mRun = true;
        mPause = true;
    }

    public void resumeGame() {
        mRun = true;
        synchronized(mLock) {
            if(mPause) {
                mLock.notify();
            }
            mPause = false;
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startGame();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread.interrupt();
    }

    @Override
    public void run() {
        try {
            while(mRun && mActivity.getBallObject().isAlive()) {
                if(mPause) {
                    mLock.wait();
                }
                mCanvas = mSurfaceHolder.lockCanvas();
                draw();
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}