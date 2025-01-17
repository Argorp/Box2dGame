package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameResources;

public class AudioManager {
    public boolean isSoundOn;
    public boolean isMusicOn;

    public Music backgroundMusic;
    public Sound shootSound;
    public Sound shootSoundForShootingTrash;
    public Sound explosionSound;
    public Sound explosionSoundForShootingTrash;

    public Sound explosionSoundForBox;

    public Sound explosionSoundForHorizontalTrash;

    public AudioManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_MUSIC_PATH));
        shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND_PATH));
        shootSoundForShootingTrash = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_FOR_SHOOTING_TRASH_SOUND_PATH));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SOUND_PATH));
        explosionSoundForShootingTrash = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_SHOOTING_TRASH_SOUND_PATH));
        explosionSoundForBox = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_BOX_SOUND_PATH));
        explosionSoundForHorizontalTrash = Gdx.audio.newSound(Gdx.files.internal(GameResources.DESTROY_HORIZONTAL_TRASH_SOUND_PATH));

        backgroundMusic.setVolume(0.2f);
        backgroundMusic.setLooping(true);

        updateSoundFlag();
        updateMusicFlag();
    }

    public void updateSoundFlag() {
        isSoundOn = MemoryManager.loadIsSoundOn();
    }

    public void updateMusicFlag() {
        isMusicOn = MemoryManager.loadIsMusicOn();

        if (isMusicOn) backgroundMusic.play();
        else backgroundMusic.stop();
    }

}
