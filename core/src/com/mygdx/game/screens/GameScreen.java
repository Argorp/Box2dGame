
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.components.RecordsListView;
import com.mygdx.game.managers.ContactManager;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSession;
import com.mygdx.game.GameSettings;
import com.mygdx.game.GameState;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ButtonView;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.components.LiveView;
import com.mygdx.game.components.MovingBack;
import com.mygdx.game.components.TextView;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.objects.BoxObject;
import com.mygdx.game.objects.BulletObject;
import com.mygdx.game.objects.HorizontalTrashGoingLeftObject;
import com.mygdx.game.objects.HorizontalTrashGoingRightObject;
import com.mygdx.game.objects.ShipObject;
import com.mygdx.game.objects.ShootingTrashBulletObject;
import com.mygdx.game.objects.ShootingTrashObject;
import com.mygdx.game.objects.TrashObject;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    GameSession gameSession;
    ShipObject shipObject;

    ArrayList<TrashObject> trashArray;

    ArrayList<ShootingTrashObject> shootingTrashArray;
    ArrayList<BulletObject> bulletArray;

    ArrayList<ShootingTrashBulletObject> shootingTrashBulletArray;

    ArrayList<BoxObject> boxObjectArray;

    ArrayList<HorizontalTrashGoingRightObject> horizontalTrashGoingRightArray;

    ArrayList<HorizontalTrashGoingLeftObject> horizontalTrashGoingLeftArray;

    ContactManager contactManager;

    // PLAY state UI
    MovingBack backgroundView;
    ImageView topBlackoutView;
    LiveView liveView;
    TextView scoreTextView;
    ButtonView pauseButton;

    // PAUSED state UI
    ImageView fullBlackoutView;
    TextView pauseTextView;
    ButtonView homeButton;
    ButtonView continueButton;

    // ENDED state UI
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();

        contactManager = new ContactManager(myGdxGame.world);

        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        shootingTrashArray = new ArrayList<>();
        shootingTrashBulletArray = new ArrayList<>();
        boxObjectArray = new ArrayList<>();
        horizontalTrashGoingRightArray = new ArrayList<>();
        horizontalTrashGoingLeftArray = new ArrayList<>();

        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );

        backgroundView = new MovingBack(GameResources.BACK_IMG_PATH);
        topBlackoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        liveView = new LiveView(305, 1215);
        scoreTextView = new TextView(myGdxGame.commonWhiteFont, 50, 1215);
        pauseButton = new ButtonView(
                605, 1200,
                46, 54,
                GameResources.PAUSE_IMG_PATH
        );

        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_IMG_PATH);
        pauseTextView = new TextView(myGdxGame.largeWhiteFont, 282, 842, "Pause");
        homeButton = new ButtonView(
                138, 695,
                200, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Home"
        );
        continueButton = new ButtonView(
                393, 695,
                200, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Continue"
        );

        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "Last records");
        homeButton2 = new ButtonView(
                280, 365,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Home"
        );
    }

    @Override
    public void show() {
        restartGame();
    }

    @Override
    public void render(float delta) {

        handleInput();

        if (gameSession.state == GameState.PLAYING) {
            if (gameSession.shouldSpawnTrash()) {
                TrashObject trashObject = new TrashObject(
                        GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                        GameResources.TRASH_IMG_PATH,
                        myGdxGame.world
                );
                trashArray.add(trashObject);
            }

            if (gameSession.shouldSpawnShootingTrash()) {
                ShootingTrashObject shootingTrashObject = new ShootingTrashObject(
                        GameSettings.SHOOTING_TRASH_WIDTH, GameSettings.SHOOTING_TRASH_HEIGHT,
                        GameResources.SHOOTING_TRASH_IMG_PATH,
                        myGdxGame.world
                );
                shootingTrashArray.add(shootingTrashObject);
            }

            if (gameSession.shouldSpawnBox()) {
                BoxObject boxObject = new BoxObject(
                        GameSettings.BOX_WIDTH, GameSettings.BOX_HEIGHT,
                        GameResources.BOX_IMG_PATH,
                        myGdxGame.world
                );
                boxObjectArray.add(boxObject);
            }

            if (gameSession.shouldSpawnHorizontalTrash()) {
                HorizontalTrashGoingRightObject horizontalTrashGoingRightObject = new HorizontalTrashGoingRightObject(
                        GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                        GameResources.HORIZONTAL_GOING_RIGHT_TRASH_IMG_PATH,
                        myGdxGame.world
                );
                horizontalTrashGoingRightArray.add(horizontalTrashGoingRightObject);

                HorizontalTrashGoingLeftObject horizontalTrashGoingLeftObject = new HorizontalTrashGoingLeftObject(
                        GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                        GameResources.HORIZONTAL_GOING_LEFT_TRASH_IMG_PATH,
                        myGdxGame.world
                );
                horizontalTrashGoingLeftArray.add(horizontalTrashGoingLeftObject);
            }

            if (shipObject.needToShoot()) {
                BulletObject laserBullet = new BulletObject(
                        shipObject.getX(), shipObject.getY() + shipObject.height / 2,
                        GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                        GameResources.BULLET_IMG_PATH,
                        myGdxGame.world
                );
                bulletArray.add(laserBullet);
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play();
            }

            if (!shipObject.isAlive()) {
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }

            updateTrash();
            updateBullets();
            updateBox();
            backgroundView.move();
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());
            liveView.setLeftLives(shipObject.getLiveLeft());

            myGdxGame.stepWorld();
        }

        draw();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    shipObject.move(myGdxGame.touch);
                    break;

                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;

                case ENDED:

                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
            }

        }
    }

    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        for (ShootingTrashObject shootingTrashObject : shootingTrashArray) shootingTrashObject.draw(myGdxGame.batch);
        for (HorizontalTrashGoingRightObject horizontalTrashGoingRightObject : horizontalTrashGoingRightArray) horizontalTrashGoingRightObject.draw(myGdxGame.batch);
        for (HorizontalTrashGoingLeftObject horizontalTrashGoingLeftObject : horizontalTrashGoingLeftArray) horizontalTrashGoingLeftObject.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        for (ShootingTrashBulletObject shoot : shootingTrashBulletArray) shoot.draw(myGdxGame.batch);
        for (BoxObject box : boxObjectArray) box.draw(myGdxGame.batch);
        topBlackoutView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);

        if (gameSession.state == GameState.PAUSED) {
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
        }

        myGdxGame.batch.end();

    }

    private void updateBox() {
        // BoxObject
        Iterator<BoxObject> iterator = boxObjectArray.iterator();
        while(iterator.hasNext()) {
            BoxObject boxObject = iterator.next();
            boolean hasToBeDestroyed = !boxObject.isAlive() || !boxObject.isInFrame();
            if (!boxObject.isAlive()) {
                gameSession.destructionRegistrationForBox();
                shipObject.setBoost(boxObject.chooseSurprise());
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSoundForBox.play(0.2F);
            }
            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(boxObject.body);
                iterator.remove();
            }
        }
    }

    private void updateTrash() {
        // TrashObject
        Iterator<TrashObject> iter = trashArray.iterator();
        while(iter.hasNext()) {
            TrashObject trashObject = iter.next();
            boolean hasToBeDestroyed = !trashObject.isAlive() || !trashObject.isInFrame();
            if (!trashObject.isAlive()) {
                gameSession.destructionRegistration();
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSound.play(0.2f);
            }
            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(trashObject.body);
                iter.remove();
            }
        }
        // ShootingTrash
        Iterator<ShootingTrashObject> iter1 = shootingTrashArray.iterator();
        while(iter1.hasNext()) {
            ShootingTrashObject shootingTrashObject = iter1.next();
            if (shootingTrashObject.isOnRange()) {
                // ShootingTrashBulletObject spawn
                ShootingTrashBulletObject shootingTrashBulletObject = new ShootingTrashBulletObject(
                        shootingTrashObject.getX(), shootingTrashObject.getY() - shootingTrashObject.height / 2,
                        (int) (GameSettings.BULLET_WIDTH * 1.5), (int) (GameSettings.BULLET_HEIGHT * 1.5),
                        GameResources.TRASH_BULLET_IMG_PATH,
                        myGdxGame.world
                );
                shootingTrashBulletArray.add(shootingTrashBulletObject);
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSoundForShootingTrash.play();
            }
            boolean hasToBeDestroyed = !shootingTrashObject.isAlive() || !shootingTrashObject.isInFrame();
            // Deleting shootingTrashObject
            if (!shootingTrashObject.isAlive()) {
                gameSession.destructionRegistrationForShootingTrash();
                if (myGdxGame.audioManager.isSoundOn) {
                    myGdxGame.audioManager.explosionSoundForShootingTrash.play(0.2f);
                }
            }
            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(shootingTrashObject.body);
                iter1.remove();
            }
        }
        // Horizontal_Going_Right_Object
        Iterator<HorizontalTrashGoingRightObject> iterator = horizontalTrashGoingRightArray.iterator();
        while(iterator.hasNext()) {
            HorizontalTrashGoingRightObject horizontalTrashGoingRightObject = iterator.next();
            boolean hasToBeDestroyed = !horizontalTrashGoingRightObject.isAlive() || !horizontalTrashGoingRightObject.isInFrame();
            if (!horizontalTrashGoingRightObject.isAlive()) {
                gameSession.destructionRegistrationForHorizontalTrash();
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSoundForHorizontalTrash.play(0.2f);
            }
            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(horizontalTrashGoingRightObject.body);
                iterator.remove();
            }
        }
        // Horizontal_Going_Left_Object
        Iterator<HorizontalTrashGoingLeftObject> iterator1 = horizontalTrashGoingLeftArray.iterator();
        while(iterator1.hasNext()) {
            HorizontalTrashGoingLeftObject horizontalTrashGoingLeftObject = iterator1.next();
            boolean hasToBeDestroyed = !horizontalTrashGoingLeftObject.isAlive() || !horizontalTrashGoingLeftObject.isInFrame();
            if (!horizontalTrashGoingLeftObject.isAlive()) {
                gameSession.destructionRegistrationForHorizontalTrash();
                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.explosionSoundForHorizontalTrash.play(0.2f);
            }
            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(horizontalTrashGoingLeftObject.body);
                iterator1.remove();
            }
        }
    }

    private void updateBullets() {
        // For Ship
        Iterator<BulletObject> iter = bulletArray.iterator();
        while(iter.hasNext()) {
            BulletObject bulletObject_now = iter.next();
            if (bulletObject_now.hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletObject_now.body);
                iter.remove();
            }
        }
        // For Shooting Enemy
        Iterator<ShootingTrashBulletObject> iter1 = shootingTrashBulletArray.iterator();
        while(iter.hasNext()) {
            ShootingTrashBulletObject shootingTrashBulletObject = iter1.next();
            if (shootingTrashBulletObject.hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(shootingTrashBulletObject.body);
                iter.remove();
            }
        }
    }

    private void restartGame() {
        Iterator<TrashObject> iter = trashArray.iterator();
        while(iter.hasNext()) {
            myGdxGame.world.destroyBody(iter.next().body);
            iter.remove();
        }
        Iterator<ShootingTrashObject> iter1 = shootingTrashArray.iterator();
        while(iter1.hasNext()) {
            myGdxGame.world.destroyBody(iter1.next().body);
            iter1.remove();
        }
        Iterator<HorizontalTrashGoingRightObject> iterator = horizontalTrashGoingRightArray.iterator();
        while(iterator.hasNext()) {
            myGdxGame.world.destroyBody(iterator.next().body);
            iterator.remove();
        }
        Iterator<HorizontalTrashGoingLeftObject> iterator1 = horizontalTrashGoingLeftArray.iterator();
        while(iterator1.hasNext()) {
            myGdxGame.world.destroyBody(iterator1.next().body);
            iterator1.remove();
        }
        if (shipObject != null) {
            myGdxGame.world.destroyBody(shipObject.body);
        }
        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );
        bulletArray.clear();
        shootingTrashBulletArray.clear();
        gameSession.startGame();
    }
}
