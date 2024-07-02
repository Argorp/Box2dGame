package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.components.MovingBack;
import com.mygdx.game.components.TextView;
import com.mygdx.game.managers.MemoryManager;

public class SetDifficultyScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBack back;
    ButtonView button_1, button_2, button_3;
    TextView title;
    ImageView middle_back;

    public SetDifficultyScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        back = new MovingBack(GameResources.BACK_IMG_PATH);
        title = new TextView(myGdxGame.largeWhiteFont, 120, 1030, "Choose Difficulty !");
        middle_back = new ImageView(70, 450, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        button_1 = new ButtonView(
                90, 690, 210, 210,  GameResources.BUTTON_DIFFICULTY_1
        );
        button_2 = new ButtonView(
                390, 690, 210, 210,  GameResources.BUTTON_DIFFICULTY_2
        );
        button_3 = new ButtonView(
                240, 460, 210, 210,  GameResources.BUTTON_DIFFICULTY_3
        );
    }

    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        back.draw(myGdxGame.batch);
        middle_back.draw(myGdxGame.batch);
        title.draw(myGdxGame.batch);
        button_1.draw(myGdxGame.batch);
        button_2.draw(myGdxGame.batch);
        button_3.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (button_1.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveDifficultySettings(1);
            }
            if (button_2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveDifficultySettings(2);
            }
            if (button_3.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveDifficultySettings(3);
            }
            myGdxGame.setScreen(myGdxGame.gameScreen);
        }
    }


}
