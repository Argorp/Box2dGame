package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SHIP_BIT;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class ShipObject extends GameObject {

    public long lastShotTime;

    public int livesLeft;

    public float shot_cool_down;


    public ShipObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, SHIP_BIT, world);
        livesLeft = 3;
        body.setLinearDamping(10);
        shot_cool_down = GameSettings.SHOOTING_COOL_DOWN;
    }

    private void putInFrame() {
        if (getY() > (GameSettings.SCREEN_HEIGHT / 2f - height / 2f)) {
            setY(GameSettings.SCREEN_HEIGHT / 2 - height / 2);
        }
        if (getY() <= (height / 2f)) {
            setY(height / 2);
        }
        if (getX() < (-width / 2f)) {
            setX(GameSettings.SCREEN_WIDTH);
        }
        if (getX() > (GameSettings.SCREEN_WIDTH + width / 2f)) {
            setX(0);
        }
    }

    @Override
    public void hit() {
        livesLeft--;
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public void move(Vector3 vector3) {
        body.applyForceToCenter(new Vector2(
                        (vector3.x - getX()) * GameSettings.SHIP_FORCE_RATIO,
                        (vector3.y - getY()) * GameSettings.SHIP_FORCE_RATIO),
                true
        );
    }
    public int getLiveLeft() {
        return livesLeft;
    }

    public void increaseLive() {
        livesLeft = min(livesLeft + 1, 5);
    }

    public void decreaseLive() {
        livesLeft = max(livesLeft - 1, 2);
    }

    public void setBoost(int par) {
        /*
        0 - улучшение скорости стрельбы,
        1 - уменьшение скорости стрельбы,
        2 - увеличение хп корабля,
        3 - уменьшение хп корабля
         */
        if (par == 2)
            increaseLive();
        if (par == 3)
            decreaseLive();
        else if (par == 0) {
            shot_cool_down = max(shot_cool_down / 1.5F, 250.0F);
        }
        else {
            shot_cool_down = min(shot_cool_down * 1.1F, 1300.0F);
        }
    }

    public boolean needToShoot() {
        if (TimeUtils.millis() - lastShotTime >= shot_cool_down) {
            lastShotTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        super.draw(batch);
    }
}
