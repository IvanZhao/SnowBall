package com.ivan.snowball;

import com.ivan.snowball.model.BackGround;
import com.ivan.snowball.model.Ball;
import com.ivan.snowball.model.Ground;
import com.ivan.snowball.utils.Utils;
import com.ivan.snowball.view.CanvasView;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public class MainActivity extends Activity implements OnClickListener {

    private BackGround mBackGround = null;
    private Ground mGround = null;
    private Ball mBall = null;

    private CanvasView mCanvasView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
        mCanvasView = (CanvasView) this.findViewById(R.id.canvas_view);
        mCanvasView.setActivity(this);
        mCanvasView.setOnClickListener(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mBackGround = new BackGround(this,
                scaleBitmapByHeight(R.drawable.background, dm.heightPixels),
                dm.heightPixels, dm.widthPixels);
        mGround = new Ground(this,
                scaleBitmapByHeight(R.drawable.land, 0),
                dm.heightPixels, dm.widthPixels);
        mBall = new Ball(this,
                scaleBitmapByHeight(R.drawable.ball3, Utils.INIT_LIFE),
                mGround, dm.heightPixels, dm.widthPixels);
    }

    public BackGround getBackgroundObject() {
        return mBackGround;
    }

    public Ground getGroundObject() {
        return mGround;
    }

    public Ball getBallObject() {
        return mBall;
    }

    private Bitmap scaleBitmapByHeight(int resId, int dstHeight) {
        if(dstHeight == 0) {
            return Utils.readBitMap(this, resId);
        } else {
            Bitmap bitmap = Utils.readBitMap(this, resId);
            float height = (float)bitmap.getHeight();
            float width = (float)bitmap.getWidth();
            int dstWidth = ((Float)((float)dstHeight / height * width))
                    .intValue();
            return Bitmap.createScaledBitmap(bitmap,
                    dstWidth, dstHeight, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onClick(View arg0) {
        new Thread() {

            @Override
            public void run() {
                if(mBall.isAlive()) {
                    mBall.jump();
                }
            }
        }.start();
    }

}
