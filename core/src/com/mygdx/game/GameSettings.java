package com.mygdx.game;

public class GameSettings {

    // Device settings

    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;

    // Physics settings

    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;

    public static float TRASH_VELOCITY = 20;
    public static float BOX_VELOCITY = 41;
    public static long STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000; // in [ms] - milliseconds

    public static float SHIP_FORCE_RATIO = 10;

    // Object sizes

    public static final int SHIP_WIDTH = 150;
    public static final int SHIP_HEIGHT = 150;

    public static final int TRASH_WIDTH = 140;
    public static final int TRASH_HEIGHT = 100;

    public static final int SHOOTING_TRASH_WIDTH = 240;
    public static final int SHOOTING_TRASH_HEIGHT = 160;

    public static final int BOX_WIDTH = 100;
    public static final int BOX_HEIGHT = 50;

    // Bullet Settings

    public static final int BULLET_VELOCITY = 220;

    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 45;


    public static final int SHOOTING_COOL_DOWN = 1000;

    // Bits for ContactManager

    public static final short TRASH_BIT = 1;
    public static final short SHIP_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final short SHOOTING_TRASH_BIT = 8;
    public static final short BULLET_TRASH_BIT = 16;
    public static final short BOX_BIT = 32;

    public static final short HORIZONTAL_TRASH_BIT = 64;
}
