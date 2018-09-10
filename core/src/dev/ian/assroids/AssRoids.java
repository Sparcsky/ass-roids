package dev.ian.assroids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.ian.assroids.asset.Asset;
import dev.ian.assroids.entity.Asteroid;
import dev.ian.assroids.entity.GameObject;
import dev.ian.assroids.entity.Ship;
import dev.ian.assroids.entity.bullet.Bullet;
import dev.ian.assroids.factory.ExplosionFactory;

public class AssRoids extends Game {

    private Ship ship;
    private Asset asset;
    private BitmapFont font;
    private SpriteBatch batch;
    private GameObject background;
    private List<Asteroid> asteroids;
    private float width;
    private float height;
    private float timer;
    private ExplosionManager explosionManager;
    private Player player;

    private PowerUpGenerator powerGenerator;
    private boolean gameOver;

    @Override
    public void create() {
        loadAsset();
        powerGenerator = new PowerUpGenerator();
        explosionManager = new ExplosionManager();
        font = Asset.instance().get(Asset.PIXEL_FONT);
        batch = new SpriteBatch();

        initShip();
        initAsteroid();
        initBackground();

        player = new Player(ship);
    }

    private void loadAsset() {
        asset = Asset.instance();
        asset.load();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }

    private void initBackground() {
        background = new GameObject(asset.createSprite("background"), 0, 0);
        background.setSize(width, height);
    }

    private void initAsteroid() {
        asteroids = new ArrayList<Asteroid>();
        for (int i = 0; i < 10; i++) {
            Asteroid asteroid = new Asteroid();
            asteroids.add(asteroid);
        }
    }

    private void initShip() {
        ship = new Ship(asset.createSprite("hero-ship"), width / 2, height / 2);
        ship.changeBullet(new PowerUp(BulletType.VENOM));
        ship.setSize(50, 50);
        ship.originCenter();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        timer += delta;

        if (player.isHasLife()) {
            player.handleEvent();
            player.update(delta);
            ship.update(delta);
        } else {
            gameOver = true;
        }
        powerGenerator.generatePower(delta);
        clearScreen();
        batch.begin();
        background.draw(batch);
        ship.draw(batch);
        Iterator<Asteroid> asteroidIter = asteroids.iterator();
        while (asteroidIter.hasNext()) {
            Asteroid asteroid = asteroidIter.next();
            asteroid.update(delta);
            asteroid.draw(batch);

            ship.accept(asteroid);
            checkBulletCollision(asteroid);
            if (!asteroid.hasLife()) {
                ship.increaseKill();
                explosionManager.add(ExplosionFactory.create(asteroid));
                asteroidIter.remove();
            }
            if (ship.isCollide(asteroid)) {
                player.decreaseHealth(5);
                explosionManager.add(ExplosionFactory.create(ship));
            }
        }
        if (timer >= 2.0f) {
            timer = 0;
            asteroids.add(new Asteroid());
        }
        generatePower();
        drawSprite();
        batch.end();
    }

    private void generatePower() {
        Iterator<PowerUp> powerUpIter = powerGenerator.getPowerUps().iterator();
        while (powerUpIter.hasNext()) {
            PowerUp powerUp = powerUpIter.next();
            if (ship.isCollide(powerUp)) {
                ship.changeBullet(powerUp);
                powerUpIter.remove();
            }
        }
    }

    private void drawSprite() {
        powerGenerator.draw(batch);
        explosionManager.draw(batch);
        player.draw(batch);
        drawGui();
    }

    private void drawGui() {
        font.draw(batch, "Player X: " + (int) ship.getX(), 15, height - 15);
        font.draw(batch, "Player Y: " + (int) ship.getY(), 15, height - 30);
        font.draw(batch, "Velocity X: " + (int) ship.getDx(), 15, height - 45);
        font.draw(batch, "Velocity Y: " + (int) ship.getDy(), 15, height - 60);
        font.draw(batch, "Ship angle: " + (int) ship.getAngle(), 15, height - 75);
        font.draw(batch, "Bullet: : " + ship.getBulletType().name(), 15, height - 90);
        font.draw(batch, "Kill: " + ship.getKill(), 200, height - 15);
        font.draw(batch, "Asteroid count: " + asteroids.size(), 200, height - 30);
        font.draw(batch, "Ian Parcs", ship.getX(), ship.getY());

        if(gameOver)
        font.draw(batch, "GAME OVER!", (width / 2) -50, height / 2);

    }

    private void checkBulletCollision(Asteroid asteroid) {
        Iterator<Bullet> bulletIter = ship.getBullets().iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.accept(asteroid);
            asteroid.accept(bullet);
            if (bullet.isCollide(asteroid)) {
                font.draw(batch, "damage: " + ship.getDamage() + "", asteroid.getX(), asteroid.getY());
                bulletIter.remove();
                explosionManager.add(ExplosionFactory.create(bullet));
            }
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        batch.dispose();
        asset.dispose();
    }
}
