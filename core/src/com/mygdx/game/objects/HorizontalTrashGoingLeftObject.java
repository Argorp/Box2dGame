package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.HORIZONTAL_TRASH_BIT;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class HorizontalTrashGoingLeftObject extends GameObject {
    public int hp;

    public HorizontalTrashGoingLeftObject(int width, int height, String texturePath, World world) {
        super(
                texturePath,
                GameSettings.SCREEN_WIDTH,
                height / 2 + (new Random()).nextInt((GameSettings.SCREEN_HEIGHT / 2)),
                width, height, HORIZONTAL_TRASH_BIT,
                world
        );
        hp = 1;
        body.setLinearVelocity(new Vector2((float) (-GameSettings.TRASH_VELOCITY / 1.5), 0));
    }

    @Override
    public void hit() {
        hp--;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean isInFrame() {
        return getX() + width / 2 > 0;
    }
}
