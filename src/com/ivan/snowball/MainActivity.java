package com.ivan.snowball;

import java.io.InputStream;

import com.ivan.snowball.model.BackGround;
import com.ivan.snowball.model.Ball;
import com.ivan.snowball.model.Ground;
import com.ivan.snowball.view.CanvasView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

    private BackGround mBackGround = null;
    private Ground mGround = null;
    private Ball mBall = null;

    private CanvasView mCanvasView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCanvasView = (CanvasView) this.findViewById(R.id.canvas_view);
        mCanvasView.setActivity(this);
        mCanvasView.setOnClickListener(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mBackGround = new BackGround(scaleBitmapByHeight(R.drawable.background,
                dm.heightPixels), dm.heightPixels, dm.widthPixels);
        mGround = new Ground(scaleBitmapByHeight(R.drawable.land, 0),
                dm.heightPixels, dm.widthPixels);
        mBall = new Ball(scaleBitmapByHeight(R.drawable.ball, 0), mGround,
                dm.heightPixels, dm.widthPixels);
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
            return readBitMap(this, resId);
        } else {
            Bitmap bitmap = readBitMap(this, resId);
            float height = (float)bitmap.getHeight();
            float width = (float)bitmap.getWidth();
            int dstWidth = ((Float)((float)dstHeight / height * width))
                    .intValue();
            return Bitmap.createScaledBitmap(bitmap,
                    dstWidth, dstHeight, true);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onClick(View arg0) {
        mBall.jump();
    }

}
