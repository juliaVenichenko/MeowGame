package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.GameResources;

public class AudioManager {
    public Music menuMusic;
    public Music gameMusic;
    public Sound meowSound;
    public Sound gameOverSound;
    public Sound victorySound;
    public Sound clickSound;

    public AudioManager(){
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.MENU_MUSIC));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.GAME_MUSIC));

        meowSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.MEOW_SOUND));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.GAME_OVER_SOUND));
        victorySound = Gdx.audio.newSound(Gdx.files.internal(GameResources.VICTORY_SOUND));
        clickSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.CLICK_SOUND));

        menuMusic.setVolume(0.5f);
        menuMusic.setLooping(true);

        gameMusic.setVolume(0.5f);
        gameMusic.setLooping(true);


    }
}
