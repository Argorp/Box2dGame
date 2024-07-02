package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.SHOOTING_TRASH_BIT;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

import java.util.Random;

public class ShootingTrashObject extends GameObject {

    private static final int padding = 10;

    Random random;

    int sign;

    public int hp;

    public ShootingTrashObject(int width, int height, String texturePath, World world) {
        super(
                texturePath,
                width / 2 + padding + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * padding - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height, SHOOTING_TRASH_BIT,
                world
        );
        random = new Random();
        hp = 2;
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        sign = random.nextInt() > 0.5 ? -1 : 1;
    }

    @Override
    public void hit() {
        hp--;
    }

    public boolean isOnRange() {
        if (getY() + height < (GameSettings.SCREEN_HEIGHT) * 3 / 4) {
            body.setLinearVelocity(new Vector2((float) (sign * GameSettings.TRASH_VELOCITY * 1.3), 0));
            setY((GameSettings.SCREEN_HEIGHT) * 3 / 4);
            return true;
        }
        return false;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean isInFrame() {
        return (getX() + width / 2 > 0) && (getX() + width / 2 < GameSettings.SCREEN_WIDTH);
    }

}
