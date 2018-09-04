package dev.ian.assroids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import dev.ian.assroids.entity.Asteroid;
import dev.ian.assroids.entity.Bullet;
import dev.ian.assroids.entity.GameObject;
import dev.ian.assroids.entity.Ship;

public class AssRoids extends ApplicationAdapter {

    private SpriteBatch batch;

    private Asset asset;
    private GameObject background;
    private List<Asteroid> asteroids;
    private Ship ship;

    @Override
    public void create() {
        Asset.instance().load();
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        asset = Asset.instance();
        asteroids = new ArrayList<Asteroid>();
        for (int i = 0; i < 10; i++) {
            Asteroid asteroid = new Asteroid(asset.createSprite("asteroid_a"));
            asteroids.add(asteroid);
        }

        batch = new SpriteBatch();

        background = new GameObject(asset.createSprite("background"), 0, 0);
        background.setSize(width, height);

        ship = new Ship(asset.createSprite("ship"), width / 2, height / 2);
        ship.setSize(32, 32);
        ship.originCenter();

    }

    @Override
    public void render() {
        ship.update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        ship.draw(batch);
        for (Asteroid asteroid : asteroids) {
            asteroid.update(Gdx.graphics.getDeltaTime());
            asteroid.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        Asset.instance().dispose();
    }
}
