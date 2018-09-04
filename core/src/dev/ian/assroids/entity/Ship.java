package dev.ian.assroids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Ian Parcon
 * Date created: Sep 02, 2018
 * Time created: 8:51 PM
 */
public class Ship extends GameObject {

    private List<Bullet> bullets;
    private float fireDelay;
    private float angle;

    public Ship(Sprite sprite, float x, float y) {
        super(sprite, x, y);

        bullets = new ArrayList<Bullet>();
    }

    public void update(float delta) {
        handleEvents();
        angle = MathUtils.atan2(dy, dx);
        dx += MathUtils.cos(angle) * delta;
        dy += MathUtils.sin(angle) * delta;

        x += dx * delta;
        y += dy * delta;

        super.update(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        for (Bullet bullet : bullets) {
            bullet.update(Gdx.graphics.getDeltaTime());
            bullet.draw(batch);
        }
        sprite.setPosition(x, y);
        sprite.setRotation(angle * MathUtils.radDeg);
        super.draw(batch);
    }

    private void handleEvents() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) dy += 5;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) dy -= 5;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) dx += 5;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) dx -= 5;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) fire();
    }

    private void fire() {
        fireDelay += Gdx.graphics.getDeltaTime();
        if (fireDelay >= .05f) {
            Bullet bullet = new Bullet(angle, x + (getWidth() / 2), y + (getHeight() / 2));
            bullets.add(bullet);
            fireDelay = 0;
        }
    }
}
