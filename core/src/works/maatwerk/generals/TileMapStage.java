package works.maatwerk.generals;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import works.maatwerk.generals.actors.ClickableMapActor;
import works.maatwerk.generals.enums.TileStatus;

import java.util.HashMap;
import java.util.Random;

class TileMapStage extends Stage {
    private final Random rand;
    private AssetManager assetManager;

    public TileMapStage(AssetManager assetManager) {
        this.assetManager = assetManager;
        setDebugUnderMouse(true);
        rand = new Random();
    }

    @Override
    public void act() {
        super.act();
    }

    void createMapActors(TiledMapTileLayer layer) {
        HashMap<String, Sprite> tilestatuses = new HashMap<String, Sprite>();
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                ClickableMapActor clickableMapActor = new ClickableMapActor(tilestatuses, assetManager);
                clickableMapActor.setBounds(x * layer.getTileWidth(), y * layer.getTileHeight(), layer.getTileWidth(), layer.getTileHeight());
                addActor(clickableMapActor);
                clickableMapActor.setStatus(TileStatus.values()[rand.nextInt(TileStatus.values().length)]);
            }
        }
    }
}