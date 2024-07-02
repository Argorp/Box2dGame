package com.mygdx.game.objects;

import static com.mygdx.game.GameSettings.BOX_BIT;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameSettings;
import com.mygdx.game.managers.MemoryManager;

import java.util.Random;

public class BoxObject extends GameObject {

    public int hp;

    private static final int paddingHorizontal = 10;

    private float unfairness;

    public int difficulty;

    public BoxObject(int width, int height, String texturePath, World world) {
        super(
                texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height, BOX_BIT,
                world
        );
        hp = 1;
        body.setLinearVelocity(new Vector2(0, -GameSettings.BOX_VELOCITY));
        difficulty = MemoryManager.loadDifficulty();
        unfairness = 0.2F;
    }

    public void setDifficulty(int difficulty) {
        unfairness *= difficulty;
    }

    public int chooseSurprise() {
        float r1 = new Random().nextInt();
        float r2 = new Random().nextInt();
        if (r1  >= unfairness) {
            return 2;
        } else if (r2 >= unfairness) {
            return 3;
        } else if (r1 * r2 >= unfairness) {
            return 1;
        } else {
            return 0;
        }
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
