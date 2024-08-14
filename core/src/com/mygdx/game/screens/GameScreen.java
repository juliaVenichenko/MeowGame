package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.OrthographicCameraWithLeftRightState;
import com.mygdx.game.components.GameUserInterface;
import com.mygdx.game.components.ImageView;
import com.mygdx.game.screens.Level1.RestartScreen1;
import com.mygdx.game.shop.Item;
import com.mygdx.game.shop.Price;
import com.mygdx.game.shop.Shop;
import com.mygdx.game.tower.CoreTower;
import com.mygdx.game.tower.DefensiveTower;
import com.mygdx.game.tower.SlotTower;
import com.mygdx.game.tower.resource.Resource;
import com.mygdx.game.tower.resource.ResourceType;
import com.mygdx.game.unit.Enemy;
import com.mygdx.game.unit.Worker;
import com.mygdx.game.user.User;

import java.util.Random;

public class GameScreen implements Screen {

    private  final MyGdxGame myGdxGame;
    private OrthographicCameraWithLeftRightState camera;

    private Texture background;
    private Texture tmpTexture;
    private CoreTower coreTower;
    private ImageView buttonHome;
    private ImageView buttonSounds;
    private Array<Resource> resourceArray;
    private GameUserInterface gameUserInterface;
    private Array<Worker> workers;
    private Array<Worker> activeWorkers;
    private Enemy enemy;
    private Vector3 touchPoint;
    private BitmapFont font;
    private Array<SlotTower> slotTowerArray;
    private Array<DefensiveTower> defensiveTowerArray;
    private Shop shop;
    private float curTime;
    private float prevTime;
    boolean isGameOver = false;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }

    @Override
    public void show() {

        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

        background = new Texture(GameResources.BACKGROUND_GAME_IMG_PATH);

        gameUserInterface = new GameUserInterface(camera);

        tmpTexture = new Texture(Gdx.files.internal("tmp.png"));
        coreTower = new CoreTower(
                170,
                226
        );

        buttonHome = new ImageView(0,425,GameResources.BUTTON_HOME);
        buttonSounds = new ImageView(60,425,GameResources.BUTTON_SOUNDS);

        Resource resourceGold, resourceOre, resourceWood;
        resourceGold = new Resource( new Texture(GameResources.GOLD_TOWER), 50 - 25, 480 / 2f - 165 / 2f, 170, 119, ResourceType.GOLD);
        resourceOre = new Resource(new Texture(GameResources.ORE_TOWER),50, 480 - 150 - 35, 146, 119, ResourceType.ORE);
        resourceWood = new Resource(new Texture(GameResources.WOOD_TOWER), 55, 0 + 15, 150, 125, ResourceType.WOOD);
        resourceArray = Array.with(resourceGold, resourceOre, resourceWood);

        workers = new Array<>();

        activeWorkers = new Array<>();
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));
        workers.add(new Worker(coreTower));

        touchPoint = new Vector3();

        enemy = new Enemy(
                10,
                coreTower.getHitBox(),
                GameSettings.WORLD_WIDTH - 100,
                GameSettings.SCR_HEIGHT / 2f
        );

        font = new BitmapFont();

        shop = new Shop(GameSettings.SCR_WIDTH + 24, 24);
        shop.addItem(
                new Item(
                        new Texture(GameResources.SHOP_TOWER),
                        new Price(10, 20, 15)
                )
        );

        slotTowerArray = new Array<>(6);
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150 + 100 + 50, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150, 480 / 2f - 50 - 100));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150 + 100 + 50, 480 / 2f - 50 - 100));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH+ 150 + 100 + 50 + 100 + 50, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f - 50 - 100));

        defensiveTowerArray = new Array<>();

        curTime = 0f;
        prevTime = 0f;

    }

    public void generateWorker() {
        float dTime = curTime - prevTime;
        if (dTime > (new Random().nextInt(4) + 4f) && activeWorkers.size < workers.size) {
            activeWorkers.add(workers.get(activeWorkers.size));
            prevTime = curTime;
        }
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buttonHome.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
                myGdxGame.audioManager.clickSound.play(0.2f);
                myGdxGame.audioManager.gameMusic.stop();
                myGdxGame.audioManager.menuMusic.play();
            }
        }
    }

    private void handleInputSounds() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buttonSounds.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.audioManager.gameMusic.pause();
            }
        }
    }

    @Override
    public void render(float delta) {
        handleInput();
        handleInputSounds();

        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        myGdxGame.batch.setProjectionMatrix(camera.combined);
        float deltaTime = Gdx.graphics.getDeltaTime();
        curTime += deltaTime;

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (!shop.isActive()) {
                for (Worker worker : activeWorkers) {
                    if (worker.contains(touchPoint.x, touchPoint.y)) {
                        worker.clicked();
                    } else {
                        if (worker.getCurrentState() == Worker.StateWorker.CLICKED) {
                            boolean startWorking = false;

                            for (Resource resource : resourceArray) {

                                if (resource.contains(touchPoint.x, touchPoint.y)) {
                                    worker.setWorkingPlace(resource);
                                    worker.setCurrentState(Worker.StateWorker.GO_TO);
                                    worker.setDestination(resource.getWorkBox());
                                    startWorking = true;
                                }
                            }

                            if (!startWorking) worker.setCurrentState(Worker.StateWorker.SLEEP);
                        }
                    }
                }

                for (Item item : shop.getItems()) {
                    if (item.getHitBox().contains(touchPoint.x, touchPoint.y)) {
                        for (SlotTower slotTower : slotTowerArray) {
                            if (slotTower.isFree()) slotTower.setVisible(true);
                        }
                        shop.setCurChoice(item);
                        shop.setActive(true);
                    }
                }
            } else {
                for (SlotTower slotTower : slotTowerArray) {
                    if (
                            slotTower.getHitBox().contains(touchPoint.x, touchPoint.y)
                                    && slotTower.isFree()
                                    && User.getInstance().canBuy(shop.getCurChoice().getPrice())
                    ) {
                        defensiveTowerArray.add(new DefensiveTower(
                                slotTower.getHitBox().x,
                                slotTower.getHitBox().y,
                                70, 125,
                                new Texture(GameResources.TOWER_IMG_PATH)
                        ));
                        slotTower.setFree(false);
                        slotTower.setVisible(false);
                        User.getInstance().buyItem(shop.getCurChoice());
                    }
                }
                shop.setActive(false);
                for (SlotTower slotTower : slotTowerArray) {
                    if (slotTower.isVisible()) slotTower.setVisible(false);
                }
            }
            //TODO: new
            for (Worker worker : activeWorkers) {
                if ((worker.contains(touchPoint.x, touchPoint.y)) &&
                        worker.getCurrentState() == Worker.StateWorker.GO_TO){
                    myGdxGame.audioManager.meowSound.play(0.9f);
                }
            }

            for (Worker worker : activeWorkers) {
                if ((worker.contains(touchPoint.x, touchPoint.y)) &&
                        worker.getCurrentState() == Worker.StateWorker.GO_FROM){
                    myGdxGame.audioManager.meowSound.play(0.9f);
                }
            }
        }


        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background, 0, 0, GameSettings.SCR_WIDTH * 2, GameSettings.SCR_HEIGHT);
        buttonHome.draw(myGdxGame.batch);
        buttonSounds.draw(myGdxGame.batch);

        if (camera.isLeftState()) {
            coreTower.draw(myGdxGame.batch);
            for (Resource resource : resourceArray) {
                resource.draw(myGdxGame.batch);
            }
            font.draw(myGdxGame.batch, User.getInstance().fullInfo(), GameSettings.SCR_WIDTH - coreTower.getTexture().getWidth() * 2, coreTower.getY()
            );
        } else {
            shop.draw(myGdxGame.batch);
            for (SlotTower slotTower : slotTowerArray) {
                slotTower.draw(myGdxGame.batch);
            }
        }

        for (Worker worker : activeWorkers) {
            if (worker.isAlive()) {
                worker.sleepSametime();
                worker.nextXY();
                if (camera.isLeftState()) {
                    worker.draw(myGdxGame.batch);
                }
                worker.setTimeInState(deltaTime);
            }
        }

        if (enemy.isAlive()) {
            enemy.nextXY();
            if (enemy.getX() > GameSettings.SCR_WIDTH && !camera.isLeftState()
                    || enemy.getX() < GameSettings.SCR_WIDTH && camera.isLeftState()) {
                enemy.draw(myGdxGame.batch);
            }
            enemy.setTimeInState(deltaTime);
        }
        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            defensiveTower.draw(myGdxGame.batch, enemy, curTime);
        }

        if (User.getInstance().hp <= 0 && !isGameOver && !(myGdxGame.getScreen() instanceof RestartScreen1)){
            isGameOver = true;

            (User.getInstance().hp) = 100;
            (User.getInstance().ore) = 0;
            (User.getInstance().gold) = 0;
            (User.getInstance().wood) = 0;

            myGdxGame.setScreen(myGdxGame.restartScreen1);

            isGameOver = false;

            myGdxGame.audioManager.gameMusic.stop();
            myGdxGame.audioManager.gameOverSound.play();
        }

        if (!enemy.isAlive()){
            isGameOver = false;
            myGdxGame.setScreen(myGdxGame.victoryScreen1);
            myGdxGame.audioManager.victorySound.play();
        }


        myGdxGame.batch.end();

        generateWorker();

        gameUserInterface.drawUI();
    }

    @Override
    public void dispose() {

        gameUserInterface.dispose();
        tmpTexture.dispose();

        buttonHome.dispose();
        buttonSounds.dispose();

        myGdxGame.audioManager.meowSound.dispose();
        myGdxGame.audioManager.gameMusic.dispose();
        myGdxGame.audioManager.gameOverSound.dispose();
        myGdxGame.audioManager.victorySound.dispose();
        myGdxGame.audioManager.menuMusic.dispose();
        myGdxGame.audioManager.clickSound.dispose();

        for (Resource resource : resourceArray) {
            resource.dispose();
        }
        coreTower.dispose();

        for (Worker worker : workers) {
            worker.dispose();
        }

        for (Worker worker: activeWorkers){
            worker.dispose();
        }

        enemy.dispose();

        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            defensiveTower.dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}