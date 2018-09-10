package dev.ian.assroids.entity.bullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by: Ian Parcon
 * Date created: Sep 09, 2018
 * Time created: 3:06 PM
 */
public class LaserBullet extends Bullet {

    public LaserBullet(float angle, float x, float y) {
        super(angle, x, y);
        name = "Laser Bullet";
        color = Color.YELLOW;
        damage = 5;
        width = 10;
        height = 10;
        speed = 500;
        bulletCoil = 0.01f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        sprite.draw(batch);
    }
}
