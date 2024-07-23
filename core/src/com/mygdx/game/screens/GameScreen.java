package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.FontBuilder;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.camera.OrthographicCameraWithLeftRightState;
import com.mygdx.game.components.GameUserInterface;
import com.mygdx.game.components.TextView;
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

public class GameScreen implements Screen {

    private  final MyGdxGame myGdxGame;
    private OrthographicCameraWithLeftRightState camera;

    private Texture background;
    private Texture tmpTexture;
    private CoreTower coreTower;
    private Array<Resource> resourceArray;
    private GameUserInterface gameUserInterface;
    private Worker worker;
    private Enemy enemy;
    private Vector3 touchPoint;
    private BitmapFont font;
    private Array<SlotTower> slotTowerArray;
    private Array<DefensiveTower> defensiveTowerArray;
    private Shop shop;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }

    @Override
    public void show() {


        camera = new OrthographicCameraWithLeftRightState();
        camera.setToOrtho(false, GameSettings.SCR_WIDTH, GameSettings.SCR_HEIGHT);

        background = new Texture(GameResources.BACKGROUND_GAME_IMG_PATH);
        tmpTexture = new Texture("tmp.png");
        gameUserInterface = new GameUserInterface(camera);

        coreTower = new CoreTower(170, 226);

        Resource resourceGold, resourceWood, resourceOre;
        resourceGold = new Resource(new Texture(GameResources.GOLD_TOWER), 50 - 25, GameSettings.SCR_HEIGHT / 2f - 165 / 2f, 170, 119, ResourceType.GOLD);
        resourceOre = new Resource(new Texture(GameResources.ORE_TOWER), 50, GameSettings.SCR_HEIGHT - 150 - 35, 146, 119, ResourceType.ORE);
        resourceWood = new Resource(new Texture(GameResources.WOOD_TOWER), 50 + 5, 0 + 15, 150, 125, ResourceType.WOOD);
        resourceArray = Array.with(resourceGold, resourceWood, resourceOre);

        worker = new Worker(coreTower);
        touchPoint = new Vector3();

        enemy = new Enemy(10, coreTower.getHitBox(), GameSettings.WORLD_WIDTH - 100, GameSettings.SCR_HEIGHT / 2f);

        font = new BitmapFont();

        shop = new Shop(GameSettings.SCR_WIDTH + 24, 24);
        shop.addItem(new Item(new Texture(GameResources.SHOP_TOWER), new Price(10, 20, 15)));

        slotTowerArray = new Array<>(6);
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150 + 100 + 50, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150, 480 / 2f - 50 - 100));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150 + 100 + 50, 480 / 2f - 50 - 100));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f + 50));
        slotTowerArray.add(new SlotTower(GameSettings.SCR_WIDTH + 150 + 100 + 50 + 100 + 50, 480 / 2f - 50 - 100));

        defensiveTowerArray = new Array<>();
        User.getInstance().incGold(50);
        User.getInstance().incOre(50);
        User.getInstance().incWood(50);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);

        camera.update();
        myGdxGame.batch.setProjectionMatrix(camera.combined);

        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                if (!shop.isActive()) {
                    if (worker.contains(touchPoint.x, touchPoint.y)) {
                        worker.clicked();
                    } else {
                        if (worker.getCurrentState() == Worker.StateWorker.CLICKED) {
                            boolean startWorking = false;
                            for (Resource resource : resourceArray) {
                                if (resource.getHitBox().contains(touchPoint.x, touchPoint.y)) {
                                    worker.setWorkingPlace(resource);
                                    worker.setCurrentState(Worker.StateWorker.GO_TO);
                                    worker.setDestination(resource.getWorkBox());
                                    startWorking = true;
                                }
                            }

                            if (!startWorking) worker.setCurrentState(Worker.StateWorker.SLEEP);
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
                }else{

                    for (SlotTower slotTower : slotTowerArray) {
                        if (slotTower.getHitBox().contains(touchPoint.x, touchPoint.y) && slotTower.isFree() && User.getInstance().canBuy(shop.getCurChoice().getPrice())){
                            defensiveTowerArray.add(new DefensiveTower(slotTower.getX(), slotTower.getY(), 70, 125, new Texture(GameResources.TOWER_IMG_PATH)));
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
        }

        myGdxGame.batch.begin();

        myGdxGame.batch.draw(background, 0, 0, GameSettings.WORLD_WIDTH, GameSettings.WORLD_HEIGHT);

        if (camera.isLeftState()){
            coreTower.draw(myGdxGame.batch);
            for (Resource resource : resourceArray) {
                resource.draw(myGdxGame.batch);
            }
            font.draw(myGdxGame.batch, User.getInstance().fullInfo(), coreTower.getX(), coreTower.getY());

        }else {
            shop.draw(myGdxGame.batch);
            for (SlotTower slotTower : slotTowerArray) {
                slotTower.draw(myGdxGame.batch);
            }
            for (DefensiveTower defensiveTower : defensiveTowerArray) {
                defensiveTower.draw(myGdxGame.batch);
            }
        }

        if (worker.isAlive()){
            worker.sleepSametime();
            worker.nextXY();
            if (camera.isLeftState()){
                worker.draw(myGdxGame.batch);
            }
            worker.setTimeInState(deltaTime);
        }

        if (enemy.isAlive()){
            enemy.nextXY();
            if (enemy.getX() > GameSettings.SCR_WIDTH && !camera.isLeftState() || enemy.getX() < GameSettings.SCR_WIDTH && camera.isLeftState()){
                enemy.draw(myGdxGame.batch);
            }
            enemy.setTimeInState(deltaTime);
        }

        myGdxGame.batch.end();

        gameUserInterface.drawUI();
    }


    @Override
    public void dispose() {
        background.dispose();
        gameUserInterface.dispose();
        tmpTexture.dispose();

        for (Resource resource : resourceArray) {
            resource.dispose();
        }
        for (SlotTower slotTower : slotTowerArray) {
            slotTower.dispose();
        }
        for (DefensiveTower defensiveTower : defensiveTowerArray) {
            defensiveTower.dispose();
        }

        coreTower.dispose();
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
