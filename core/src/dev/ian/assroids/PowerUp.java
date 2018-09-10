package dev.ian.assroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import dev.ian.assroids.asset.Asset;
import dev.ian.assroids.entity.Asteroid;
import dev.ian.assroids.entity.Entity;
import dev.ian.assroids.entity.Ship;

/**
 * Created by: Ian Parcon
 * Date created: Sep 09, 2018
 * Time created: 4:59 PM
 */
public class PowerUp extends Asteroid implements Entity {
    private BulletType bulletType;

    public PowerUp(BulletType bulletType) {
        this.bulletType = bulletType;
        sprite = Asset.instance().createSprite("star");
        x = MathUtils.random(Gdx.graphics.getWidth());
        y = MathUtils.random(Gdx.graphics.getHeight());
        dx = MathUtils.random(-40, 20);
        dy = MathUtils.random(-40, 20);
        setSize(32, 32);
        originCenter();
    }

    @Override
    public void collide(Ship ship) {

    }

    public BulletType getBulletType() {
        return bulletType;
    }
}
