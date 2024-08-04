package com.mygdx.game;

import com.badlogic.gdx.utils.TimeUtils;

public class GameSession {
    protected long pauseTime;
    protected long lastSpawnTime;

    public State state;
    long sessionStartTime;
    long pauseStartTime;

    public GameSession() {
    }

    public void startGame() {
        state = State.PLAYING;
        sessionStartTime = TimeUtils.millis();
    }

    public void pauseGame() {
        state = State.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }

    public void resumeGame() {
        state = State.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }

    public void endGame() {
        state = State.ENDED;
    }
}
