package dev.ian.assroids.entity.bullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by: Ian Parcon
 * Date created: Sep 09, 2018
 * Time created: 1:34 PM
 */
public class LavaBendingBullet extends Bullet {

    public LavaBendingBullet(float angle, float x, float y) {
        super(angle, x, y);
        name = "Lava Bending bullet";
        damage = 50;
        width = 50;
        height = 50;
        color = Color.RED;
        bulletCoil = 1f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        sprite.setOriginCenter();
        sprite.setRotation(angle * MathUtils.radDeg);
        sprite.draw(batch);
    }
}
