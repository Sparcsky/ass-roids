package dev.ian.assroids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by: Ian Parcon
 * Date created: Sep 03, 2018
 * Time created: 11:40 AM
 */
public class Asteroid extends GameObject {

    private float rotation;

    public Asteroid(Sprite sprite) {
        super(sprite);
        rotation = MathUtils.random(-0.5f, 0.5f);
        x = MathUtils.random(Gdx.graphics.getWidth());
        y = MathUtils.random(Gdx.graphics.getHeight());
        dx = MathUtils.random(-20, 20);
        dy = MathUtils.random(-20, 20);

        setSize(64, 64);
        originCenter();
    }

    @Override
    public void update(float delta) {
        float angle = MathUtils.atan2(dy, dx);
        dx += MathUtils.cos(angle) * delta;
        dy += MathUtils.sin(angle) * delta;

        x += dx * delta;
        y += dy * delta;
        sprite.setPosition(x, y);
        sprite.rotate(rotation);

        super.update(delta);
    }
}
