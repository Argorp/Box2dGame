package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.BULLET_BIT;
import static com.mygdx.game.GameSettings.BULLET_VELOCITY;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;

public class BulletObject extends GameObject{

    public boolean wasHit;

    public BulletObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, BULLET_BIT, world);
        body.setLinearVelocity(new Vector2(0, BULLET_VELOCITY));
        body.setBullet(true);
        wasHit = false;
    }

    @Override
    public void hit() {
        wasHit = true;
    }

    public boolean hasToBeDestroyed() {
        return (getY() - height / 2 > GameSettings.SCREEN_HEIGHT) || wasHit;
    }
}
