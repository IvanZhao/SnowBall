package com.ivan.snowball;

import com.ivan.snowball.model.BackGround;
import com.ivan.snowball.model.Ball;
import com.ivan.snowball.model.Ground;
import com.ivan.snowball.utils.GameOverListener;
import com.ivan.snowball.utils.Utils;
import com.ivan.snowball.view.CanvasView;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,
        GameOverListener {

    private BackGround mBackGround = null;
    private Ground mGround = null;
    private Ball mBall = null;
    private Handler mHandler = new Handler();
    private GameController mGC = null;

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
        mBall.setGameOverListener(this);
        mGC = new GameController(mBackGround, mGround, mBall);
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

    @Override
    public void onGameOver() {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                final Dialog dialog = new Dialog(MainActivity.this,
                        R.style.CustomDialog);
                int score = mBall.getDistance();
                dialog.setContentView(R.layout.custom_dialog_layout);
                Window window = dialog.getWindow();
                window.setWindowAnimations(R.style.dialog_anim_style);
                window.setBackgroundDrawableResource(
                        android.R.color.transparent);
                ImageButton runButton = (ImageButton)window.findViewById(
                        R.id.button_run);
                ImageButton exitButton = (ImageButton)window.findViewById(
                        R.id.button_exit);
                runButton.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mGC.gameInit();
                        mGC.gameStart(mCanvasView);
                        dialog.dismiss();
                    }
                });
                exitButton.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        MainActivity.this.finish();
                    }
                });
                TextView scoreView = (TextView) window.findViewById(
                        R.id.textView_score);
                TextView bestView = (TextView) window.findViewById(
                        R.id.textView_best);
                scoreView.setText(score + "");
                bestView.setText(score + "");
                dialog.show();
            }
        });
    }

}
