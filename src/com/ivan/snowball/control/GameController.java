package com.ivan.snowball.control;

import com.ivan.snowball.model.BackGround;
import com.ivan.snowball.model.Ball;
import com.ivan.snowball.model.Ground;
import com.ivan.snowball.view.CanvasView;

public class GameController {

    private BackGround mBackGround;
    private Ground mGround;
    private Ball mBall;

    public GameController(BackGround bg, Ground g, Ball b) {
        mBackGround = bg;
        mGround = g;
        mBall = b;
    }

    public void gameInit() {
        mBall.init();
        mGround.init();
        mBackGround.init();
    }

    public void gameStart(CanvasView view) {
        view.startGame();
    }

    public void gameStop(CanvasView view) {
        view.stopGame();
    }

    public void gamePause(CanvasView view) {
        view.pauseGame();
    }

    public void gameResume(CanvasView view) {
        view.resumeGame();
    }
}
