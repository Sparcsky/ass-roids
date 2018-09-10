package dev.ian.assroids.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Array;

/**
 * Created by: Ian Parcon
 * Date created: Sept 3, 2018
 * Time created: 10:55 PM
 */
public class Asset {

    private AssetManager assetManager = new AssetManager();
    private static Asset instance = new Asset();

    private static final String EXPLOSION_PACK = "images/explosion/explosion.pack";
    private static final String ASSROID_PACK = "images/objects/assroids.pack";
    public static final String PIXEL_FONT = "fonts/pixel.ttf";

    private TextureAtlas asteroidAtlas;
    private TextureAtlas explosionAtlas;

    private Asset() {
    }

    public static Asset instance() {
        return instance;
    }

    private void initHandler() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    public void load() {
        loadFont();
        loadSprites();
        assetManager.finishLoading();

        asteroidAtlas = assetManager.get(Asset.ASSROID_PACK);
        explosionAtlas = assetManager.get(Asset.EXPLOSION_PACK);
    }

    private void loadFont() {
        initHandler();
        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = PIXEL_FONT;
        mySmallFont.fontParameters.size = 12;
        mySmallFont.fontParameters.color = Color.WHITE;
        assetManager.load(PIXEL_FONT, BitmapFont.class, mySmallFont);
    }

    private void loadSprites() {
        assetManager.load(EXPLOSION_PACK, TextureAtlas.class);
        assetManager.load(ASSROID_PACK, TextureAtlas.class);
    }

    public <T> T get(String filename) {
        return assetManager.get(filename);
    }

    public Sprite createSprite(String name) {
        return asteroidAtlas.createSprite(name);
    }

    public Array<TextureAtlas.AtlasRegion> createRegion(String name) {
        return asteroidAtlas.findRegions(name);
    }

    public Array<TextureAtlas.AtlasRegion> createExplosion(String name) {
        return explosionAtlas.findRegions(name);
    }

    public void dispose() {
        assetManager.dispose();
    }

}
