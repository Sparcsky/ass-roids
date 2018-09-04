package dev.ian.assroids.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by: Ian Parcon
 * Date created: Sep 03, 2018
 * Time created: 10:52 AM
 */
public class GameObject implements Entity {

    float x, y;
    float dx, dy;
    Sprite sprite;

    public GameObject(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }

    public GameObject(Sprite sprite) {
        this.sprite = sprite;
    }

    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    public void originCenter() {
        sprite.setOriginCenter();
    }

    @Override
    public void update(float delta) {
        wrap();
    }

    private void wrap() {
        if (y > Gdx.graphics.getHeight()) y = -getHeight();
        if (y + getHeight() < 0) y = Gdx.graphics.getHeight();
        if (x + getWidth() < 0) x = Gdx.graphics.getWidth();
        if (x > Gdx.graphics.getWidth()) x = -getWidth();
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setDirection(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
