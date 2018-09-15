package dev.ian.assroids.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.ian.assroids.AssRoids;
import dev.ian.assroids.Player;
import dev.ian.assroids.asset.Asset;
import dev.ian.assroids.entity.Asteroid;
import dev.ian.assroids.entity.GameObject;
import dev.ian.assroids.entity.PowerUp;
import dev.ian.assroids.entity.Ship;
import dev.ian.assroids.entity.bullet.Bullet;
import dev.ian.assroids.entity.bullet.BulletType;
import dev.ian.assroids.factory.ExplosionFactory;
import dev.ian.assroids.manager.ExplosionManager;
import dev.ian.assroids.manager.PowerUpGenerator;

/**
 * Created by: Ian Parcon
 * Date created: Sep 15, 2018
 * Time created: 9:18 AM
 */
public class PlayScreen extends BaseScreen {

    private Ship ship;
    private BitmapFont font;
    private GameObject background;
    private List<Asteroid> asteroids;
    private ExplosionManager explosionManager;
    private Sound destroySound;
    private Sound hitExplodeSound;
    private Sound pickUpSound;
    private Player player;
    private Music bgMusic;

    private PowerUpGenerator powerGenerator;

    private boolean gameOver;
    private float spawnTimer;
    private float spawnTimeDelay = 10;
    private int level;

    public PlayScreen(AssRoids game) {
        super(game);
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
        ship.changeBullet(new PowerUp(BulletType.NORMAL));
        ship.setSize(50, 50);
        ship.originCenter();
    }

    @Override
    public void show() {
        powerGenerator = new PowerUpGenerator();
        explosionManager = new ExplosionManager();
        initSounds();
        initShip();
        initAsteroid();
        initBackground();

        player = new Player(ship);
    }

    private void initSounds() {
        font = asset.get(Asset.PIXEL_FONT_SMALL);
        destroySound = asset.get(Asset.EXPLOSION_B);
        hitExplodeSound = asset.get(Asset.EXPLOSION_A);
        pickUpSound = asset.get(Asset.POWER_PICK_UP);
        bgMusic = asset.get(Asset.SCI_FI_MUSIC);
        bgMusic.play();
        bgMusic.setLooping(true);
    }

    @Override
    public void update(float delta) {
        spawnTimer += delta;
        if (player.isHasLife()) {
            player.handleEvent();
            player.update(delta);
            ship.update(delta);
        } else {
            gameOver = true;
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        powerGenerator.generatePower(delta);
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
            if (!asteroid.isAlive()) {
                ship.increaseKill();
                explosionManager.add(ExplosionFactory.create(asteroid));
                destroySound.setVolume(destroySound.play(), 0.1f);
                asteroidIter.remove();
            }
            if (ship.isCollide(asteroid)) {
                player.decreaseHealth(5);
                explosionManager.add(ExplosionFactory.create(ship));
            }
        }
        if (spawnTimer >= spawnTimeDelay) {
            spawnTimer = 0;
            asteroids.add(new Asteroid());
        }
        if (asteroids.isEmpty()) {
            spawnTimeDelay--;
            level++;
            initAsteroid();
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
                pickUpSound.play();
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
        font.draw(batch, "LEVEL: " + level, 400, height - 15);
        font.draw(batch, "Kill: " + ship.getKill(), 200, height - 15);
        font.draw(batch, "Asteroid count: " + asteroids.size(), 200, height - 30);
        font.draw(batch, "Ian Parcs", ship.getX(), ship.getY());

        if (gameOver)
            font.draw(batch, "GAME OVER!", (width / 2) - 50, height / 2);
    }

    private void checkBulletCollision(Asteroid asteroid) {
        Iterator<Bullet> bulletIter = ship.getBullets().iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = bulletIter.next();
            bullet.accept(asteroid);
            asteroid.accept(bullet);
            if (bullet.isCollide(asteroid)) {
                hitExplodeSound.setVolume(hitExplodeSound.play(), 0.15f);
                explosionManager.add(ExplosionFactory.create(bullet));
                bulletIter.remove();
            }
        }
    }
}
