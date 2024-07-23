package com.mygdx.game.unit;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameSettings;
import com.mygdx.game.tower.CoreTower;
import com.mygdx.game.tower.resource.Resource;
import com.mygdx.game.user.User;
import com.mygdx.game.util.AnimationUtil;
import com.mygdx.game.util.MathUtil;

import java.util.EnumMap;

public class Worker extends Unit{

    private static final float WORKING_TIME = 5f;
    //TODO: экземпляр класса Resource
    private Resource workingPlace;
    //TODO: экземпляр класса CoreTower
    private final CoreTower coreTower;

    public enum StateWorker{
        FLY, FALL, SLEEP, CLICKED, GO_TO, WORK, GO_FROM
    }

    private EnumMap<StateWorker, StateAttribute> stateAttrMap;
    private StateWorker currentState;
    private boolean rightPosition;

    public Worker(CoreTower coreTower){
        this.coreTower = coreTower;
        initStateMap();
        isAlive = true;
        rightPosition = true;

        setCurrentState(StateWorker.FLY);
        x = MathUtil.getRandomNumber(300, (int) (coreTower.getX() - getWidth()));
        y = 480;
        setDestination(x, 0); //к этой точке стремится котик
    }


    @Override
    public void initStateMap() {
        stateAttrMap = new EnumMap<>(StateWorker.class);

        stateAttrMap.put(StateWorker.FLY, new StateAttribute(42f, 75f, AnimationUtil.getAnimationFromAtlas("catflysupersmall.atlas.txt", 2.5f), 0.5f));
        stateAttrMap.put(StateWorker.FALL, new StateAttribute(37.8f, 67.5f, AnimationUtil.getAnimationFromAtlas("catfall.atlas.txt", 5.5f), 2f));
        stateAttrMap.put(StateWorker.SLEEP, new StateAttribute(45f, 39.5f, AnimationUtil.getAnimationFromAtlas("catsleep.atlas.txt", 2f), 0f));
        stateAttrMap.put(StateWorker.CLICKED, new StateAttribute(45f, 39.5f, AnimationUtil.getAnimationFromAtlas("catshine.atlas.txt", 2f), 0f));
        stateAttrMap.put(StateWorker.GO_TO, new StateAttribute(35f, 35f, AnimationUtil.getAnimationFromAtlas("catwalk.atlas.txt", 1f), 0.75f));
        stateAttrMap.put(StateWorker.WORK, new StateAttribute(35f, 35f, AnimationUtil.getAnimationFromAtlas("catwork.atlas.txt", 0.75f), 0f));
        stateAttrMap.put(StateWorker.GO_FROM, new StateAttribute(35f, 35f, AnimationUtil.getAnimationFromAtlas("catcarrysupersmall.atlas.txt", 1f), 0.75f));
    }

    public void setCurrentState(StateWorker currentState){
        this.currentState = currentState;
        timeInState = 0;
    }

    public StateWorker getCurrentState(){
        return currentState;
    }

    public TextureRegion getCurrentFrame(){
        return stateAttrMap.get(currentState).animation.getKeyFrame(timeInState, true);
    }

    public  void setTimeInState(float deltaTime){
        this.timeInState += deltaTime; //высчитываем время
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void setWorkingPlace(Resource workingPlace){
        this.workingPlace = workingPlace;
    }

    @Override
    public float getSpeed() {
        return stateAttrMap.get(currentState).speed;
    }

    public boolean contains(float x, float y){
        return x >= this.x && x <= this.x + getWidth()
                && y >= this.y && y <= this.y + getHeight();
    }

    public void clicked(){
        switch (currentState){
            case FLY:
                if (isAlive){
                    setCurrentState(StateWorker.FALL);

                    float randY;
                    if (y < GameSettings.SCR_HEIGHT / 2f)
                        randY = MathUtil.getRandomNumber((int) getHeight(), (int) y);
                    else
                        randY = MathUtil.getRandomNumber((int) getHeight(), GameSettings.SCR_HEIGHT / 2);

                    setDestination(x, randY);
                }
                break;
            case SLEEP:
                setCurrentState(StateWorker.CLICKED);
                break;
        }
    }

    @Override
    public void nextXY() {
        if (currentState != StateWorker.SLEEP && currentState != StateWorker.CLICKED)
            if (!destination.contains(x, y)) {

                if (destination.y + destination.height / 2f > y) y += deltaY;
                if (destination.y + destination.height / 2f < y) y -= deltaY;

                if (destination.x + destination.width / 2f >= x) {
                    x += deltaX;
                    rightPosition = true;
                }
                if (destination.x + destination.width / 2f < x) {
                    x -= deltaX;
                    rightPosition = false;
                }

            } else {

                switch (currentState) {
                    case FLY:
                        isAlive = false;
                        break;
                    case FALL:
                        setCurrentState(StateWorker.SLEEP);
                        break;
                    case GO_TO:
                        setCurrentState(StateWorker.WORK);
                        break;
                    case WORK:
                        if (timeInState >= WORKING_TIME) {
                            setCurrentState(StateWorker.GO_FROM);
                            setDestination(coreTower.getStorageBox());
                        }
                        break;
                    case GO_FROM:

                        setCurrentState(StateWorker.GO_TO);

                        switch (workingPlace.getType()){
                            case GOLD:
                                User.getInstance().incGold(10);
                                break;
                            case WOOD:
                                User.getInstance().incWood(15);
                                break;
                            case ORE:
                                User.getInstance().incOre(20);
                                break;
                        }
                        setDestination(workingPlace.getWorkBox());
                        break;
                }

            }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getCurrentFrame(), rightPosition ? x + getWidth() : x, y, rightPosition ? -getWidth() : getWidth(), getHeight()); // -getWidth() зеркалит фрейм
    }

    @Override
    public float getWidth() {
        return stateAttrMap.get(currentState).width;
    }

    @Override
    public float getHeight() {
        return stateAttrMap.get(currentState).height;
    }

    public void sleepSametime() {
        if ((getCurrentState() == StateWorker.GO_TO)
                && Math.random() < 0.0005
        ) {
            setCurrentState(StateWorker.SLEEP);
        }
    }
}
