package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.managers.MemoryManager;

import java.util.ArrayList;

public class GameSession {

    public GameState state;
    long nextTrashSpawnTime, nextShootingTrashSpawnTime, nextBoxSpawnTime, nextGoingHorizontalTrashSpawnTime;
    long sessionStartTime;
    long pauseStartTime;
    private int score;
    int destructedTrashNumber, destructedShootingTrashNumber, destructedBoxTrashNumber, destructedHorizontalTrashNumber, difficulty;

    public GameSession() {
    }

    public void startGame() {
        difficulty = MemoryManager.loadDifficulty();
        state = GameState.PLAYING;
        score = 0;
        destructedTrashNumber = 0;
        destructedShootingTrashNumber = 0;
        sessionStartTime = TimeUtils.millis();
        nextTrashSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown() / (difficulty * 0.5));
        nextShootingTrashSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown() * 2 / (difficulty * 0.2));
        nextBoxSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown() * 4 / (difficulty * 0.7));
        nextGoingHorizontalTrashSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown() * 3 / (difficulty * 0.5));
    }

    public void pauseGame() {
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }

    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }

    public void endGame() {
        updateScore();
        state = GameState.ENDED;
        ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
        if (recordsTable == null) {
            recordsTable = new ArrayList<>();
        }
        int foundIdx = 0;
        for (; foundIdx < recordsTable.size(); foundIdx++) {
            if (recordsTable.get(foundIdx) < getScore()) break;
        }
        recordsTable.add(foundIdx, getScore());
        MemoryManager.saveTableOfRecords(recordsTable);
    }

    public void destructionRegistration() {
        destructedTrashNumber += 1;
    }

    public void destructionRegistrationForShootingTrash() {
        destructedShootingTrashNumber += 1;
    }

    public void destructionRegistrationForBox() {
        destructedBoxTrashNumber += 1;
    }

    public void destructionRegistrationForHorizontalTrash() {
        destructedHorizontalTrashNumber += 1;
    }

    public void updateScore() {
        score = (int) (TimeUtils.millis() - sessionStartTime) / 100 + destructedTrashNumber * 100
                + destructedShootingTrashNumber * 150 + destructedBoxTrashNumber * 50 + destructedHorizontalTrashNumber * 25;
    }

    public int getScore() {
        return score;
    }

    // SpawnForTrash

    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime <= TimeUtils.millis()) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                    * getTrashPeriodCoolDown() / (difficulty * 0.5));
            return true;
        }
        return false;
    }

    public boolean shouldSpawnShootingTrash() {
        if (nextShootingTrashSpawnTime <= TimeUtils.millis()) {
            nextShootingTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                    * getTrashPeriodCoolDown() * 2 / (difficulty * 0.2));
            return true;
        }
        return false;
    }



    public boolean shouldSpawnHorizontalTrash() {
        if (nextGoingHorizontalTrashSpawnTime <= TimeUtils.millis()) {
            nextGoingHorizontalTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                    * getTrashPeriodCoolDown() * 1.5 / (difficulty * 0.5));
            return true;
        }
        return false;
    }

    // SpawnForBox (bonus)

    public boolean shouldSpawnBox() {
        if (nextBoxSpawnTime <= TimeUtils.millis()) {
            nextBoxSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                    * getTrashPeriodCoolDown() * 3 / (difficulty * 0.2));
            return true;
        }
        return false;
    }

    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
    }
}
