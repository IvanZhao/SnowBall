package com.ivan.snowball.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.ivan.snowball.model.Ball;
import com.ivan.snowball.model.Ground;
import com.ivan.snowball.model.Obstacle;
import com.ivan.snowball.model.Tree;
import com.ivan.snowball.model.inf.ElementInterface;
import com.ivan.snowball.model.inf.GameProcessController;
import com.ivan.snowball.model.inf.ObstacleCallBackListener;

public class ObstacleController implements ElementInterface,
        ObstacleCallBackListener {

    private List<Obstacle> mObstacleSet = null;
    private Context mContext = null;
    private int mCanvasH;
    private int mCanvasW;
    private Ball mBall = null;
    private Ground mGround;
    private static Object mLock = new Object();

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

    public void addObstacle() {
        mObstacleSet.add(new Tree(mContext, mCanvasH, mCanvasW, mGround, mBall,
                ObstacleController.this));
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        synchronized(mLock) {
            for(int i = 0; i < mObstacleSet.size(); i++) {
                mObstacleSet.get(i).draw(canvas, paint);
            }
        }
    }

    @Override
    public void move() {
        synchronized(mLock) {
            for(int i = 0; i < mObstacleSet.size(); i++) {
                mObstacleSet.get(i).move();
            }
        }
    }

    @Override
    public void init() {
        synchronized(mLock) {
            mObstacleSet.clear();
        }
    }

    @Override
    public void onMoveOut(Obstacle item) {
        synchronized(mLock) {
            mObstacleSet.remove(item);
        }
    }

    @Override
    public void moveAndDraw(Canvas canvas, Paint paint) {
        synchronized(mLock) {
            draw(canvas, paint);
            move();
            if(mBall.getDistance() % 300 == 0) {
                addObstacle();
            }
        }
    }
}
