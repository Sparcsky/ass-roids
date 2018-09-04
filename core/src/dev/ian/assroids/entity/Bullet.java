package dev.ian.assroids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import dev.ian.assroids.Asset;

/**
 * Created by: Ian Parcon
 * Date created: Sep 03, 2018
 * Time created: 12:04 PM
 */
public class Bullet extends GameObject {

    private Animation<TextureRegion> fireAnimation;
    private float stateTime;
    private float angle;

    public Bullet(float angle, float x, float y) {
        super(x, y);
        this.angle = angle;
        Array<TextureAtlas.AtlasRegion> region = Asset.instance().createRegion("bullet");
        fireAnimation = new Animation<TextureRegion>(0.06f, region, Animation.PlayMode.LOOP);
        sprite = new Sprite();
        sprite.rotate(angle * MathUtils.radDeg);
    }

    public void setDirection(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        x += dx * delta;
        y += dy * delta;
    }

    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        sprite.setRegion(fireAnimation.getKeyFrame(stateTime));
        sprite.setSize(16, 16);
        sprite.setOriginCenter();
        sprite.setRotation(angle * MathUtils.radDeg);
        sprite.setPosition(x, y);

        super.draw(batch);
    }
}
