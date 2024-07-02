package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.TRASH_BIT;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject{

    private static final int paddingHorizontal = 60;

    public int hp;
    public TrashObject(int width, int height, String texturePath, World world) {
        super(
                texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height, TRASH_BIT,
                world
        );
        hp = 1;
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
    }

    @Override
    public void hit() {
        hp--;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

}
