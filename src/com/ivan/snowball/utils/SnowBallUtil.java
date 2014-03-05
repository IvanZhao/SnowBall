package com.ivan.snowball.utils;

public class SnowBallUtil {
    public static enum Gears{STOP, NORMAL, HIGH};
    public static final int[] GameSpeed = {0, 3, 24};
    public static final float G = -9.8f;
    public static enum Hurts{DRY_LAND, WATER, FALL, SMALL_TREE, BIG_TREE,
        VILLAGE};
    public static final int[] HurtValue = {1, 2, 10, 20, 50, 80};
    public static final int MAX_LIFE = 200;
    public static final int MIN_LIFE = 20;
}
