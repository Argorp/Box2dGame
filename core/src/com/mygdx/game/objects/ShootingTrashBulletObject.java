package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.BULLET_TRASH_BIT;
import static com.mygdx.game.GameSettings.BULLET_VELOCITY;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class ShootingTrashBulletObject extends GameObject{

    public boolean wasHit;

    public ShootingTrashBulletObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, BULLET_TRASH_BIT, world);
        body.setLinearVelocity(new Vector2((float) 0, (float) -(BULLET_VELOCITY * 2)));
        body.setBullet(true);
        wasHit = false;
    }

    @Override
    public void hit() {
        wasHit = true;
    }

    public boolean hasToBeDestroyed() {
        return (getY() - height / 2 < 0) || wasHit;
    }


}
