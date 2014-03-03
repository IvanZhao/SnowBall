package com.ivan.snowball.view;

import java.io.InputStream;
import java.util.TimerTask;

import com.ivan.snowball.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CanvasView extends SurfaceView implements Callback, Runnable {

    private Canvas mCanvas = null;
    private Context mContext = null;
    private SurfaceHolder mSurfaceHolder = null;

    private Bitmap mBackgroundImage = null;
    private Bitmap mLandImage = null;
    private Bitmap mBall = null;

    private Thread mThread = null;
    private Paint mPaint = null;
    private int mPaintY0 = 0;
    private int mPaintY1 = 0;
    private int mLandX0 = 0;
    private int mLandX1 = 0;
    private int mLandX2 = 0;
    private int mImageWidth = 0;
    private int mLandWidth = 0;
    private int mScreenHeight = 0;

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
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mContext = this.getContext();
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        mScreenHeight = dm.heightPixels;
        mBackgroundImage = scaleBitmapByHeight(R.drawable.background,
                dm.heightPixels);
        mLandImage = scaleBitmapByHeight(R.drawable.land, 0);
        mPaint = new Paint();
        mPaintY0 = 0;
        mPaintY1 = mBackgroundImage.getWidth();
        mLandX0 = 0;
        mLandX1 = mLandImage.getWidth() - 1;
        mLandX2 = mLandImage.getWidth() * 2 - 2;
        mImageWidth = mBackgroundImage.getWidth();
        mLandWidth = mLandImage.getWidth() - 1;
    }

    private Bitmap scaleBitmapByHeight(int resId, int dstHeight) {
    	if(dstHeight == 0) {
            return readBitMap(mContext, resId);
    	} else {
            Bitmap bitmap = readBitMap(mContext, resId);
            float height = (float)bitmap.getHeight();
            float width = (float)bitmap.getWidth();
            int dstWidth = ((Float)((float)dstHeight / height * width))
                    .intValue();
            return Bitmap.createScaledBitmap(bitmap,
                    dstWidth, dstHeight, true);
    	}
    }

    private void draw() {
        drawBackground();
        drawLand();
    }

    private void drawLand() {
        mCanvas.drawBitmap(mLandImage, mLandX0,
                mScreenHeight - mLandImage.getHeight(), mPaint);
        mCanvas.drawBitmap(mLandImage, mLandX1,
                mScreenHeight - mLandImage.getHeight(), mPaint);
        mCanvas.drawBitmap(mLandImage, mLandX2,
                mScreenHeight - mLandImage.getHeight(), mPaint);
        if(mLandX0 <= -mLandWidth) {
            mLandX0 = mLandWidth * 2;
        }
        if(mLandX1 <= -mLandWidth) {
            mLandX1 = mLandWidth * 2;
        }
        if(mLandX2 <= -mLandWidth) {
            mLandX2 = mLandWidth * 2;
        }
        mLandX0 -= 3;
        mLandX1 -= 3;
        mLandX2 -= 3;
    }

    private void drawBackground() {
        mCanvas.drawBitmap(mBackgroundImage, mPaintY0, 0, mPaint);
        mCanvas.drawBitmap(mBackgroundImage, mPaintY1, 0, mPaint);
        if(mPaintY0 == -mImageWidth) {
            mPaintY0 = mImageWidth;
        } if(mPaintY1 == -mImageWidth) {
            mPaintY1 = mImageWidth;
        } else {
            mPaintY0--;
            mPaintY1--;
        }
    }

    public Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new Thread(this);
        mThread.start();
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
            while(true) {
                mCanvas = mSurfaceHolder.lockCanvas();
                draw();
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
