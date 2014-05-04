package com.ivan.snowball.model.inf;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface ElementInterface {

    public abstract void draw(Canvas canvas, Paint paint);

    public abstract void move();

    public abstract void init();

    public void moveAndDraw(Canvas canvas, Paint paint);
}
