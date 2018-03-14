package works.maatwerk.generals;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import works.maatwerk.generals.actors.ClickableMapActor;

public class TileMapStage  extends Stage {
    public TileMapStage() {
    }

    void createMapActors(TiledMapTileLayer layer) {
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                ClickableMapActor clickableMapActor = new ClickableMapActor();
                clickableMapActor.setBounds(x*layer.getTileWidth(),y*layer.getTileHeight(),layer.getTileWidth(),layer.getTileHeight());
                addActor(clickableMapActor);
            }
        }
    }
}