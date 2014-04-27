package com.ivan.snowball.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.ivan.snowball.model.Ball;
import com.ivan.snowball.model.ElementInterface;
import com.ivan.snowball.model.Ground;
import com.ivan.snowball.model.Obstacle;
import com.ivan.snowball.model.ObstacleCallBackListener;
import com.ivan.snowball.model.Tree;

public class ObstacleController implements ElementInterface,
        ObstacleCallBackListener {

    private List<Obstacle> mObstacleSet = null;
    private Context mContext = null;
    private int mCanvasH;
    private int mCanvasW;
    private Ball mBall = null;
    private Ground mGround;

    private Thread mCreateObstacleThread = new Thread() {

        @Override
        public void run() {
            while(mBall.isAlive()) {
                mObstacleSet.add(new Tree(mContext, mCanvasH, mCanvasW,
                        mGround, ObstacleController.this));
                try {
                    this.sleep(6000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public ObstacleController(Context c, Ball ball,
            Ground ground, int canvasH, int canvasW) {
        mObstacleSet = new ArrayList<Obstacle>();
        mBall = ball;
        mGround = ground;
        mContext = c;
        mCanvasH = canvasH;
        mCanvasW = canvasW;
        init();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Iterator it = mObstacleSet.iterator();
        while(it.hasNext()) {
            ((Obstacle) it.next()).draw(canvas, paint);
        }
    }

    @Override
    public void move() {
        Iterator it = mObstacleSet.iterator();
        while(it.hasNext()) {
            ((Obstacle) it.next()).move();
        }
    }

    @Override
    public void init() {
        mObstacleSet.clear();
        mCreateObstacleThread.start();
    }

    @Override
    public void onMoveOut(Obstacle item) {
        mObstacleSet.remove(item);
    }

    @Override
    public void moveAndDraw(Canvas canvas, Paint paint) {
        draw(canvas, paint);
        move();
    }
}
