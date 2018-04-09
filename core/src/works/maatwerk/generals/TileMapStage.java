package works.maatwerk.generals;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import works.maatwerk.generals.actors.TileActor;
import works.maatwerk.generals.enums.TileStatus;
import works.maatwerk.generals.models.MapManager;
import java.util.*;

public class TileMapStage extends Stage {
    private final MapManager mapManager;
    private AssetManager assetManager;
    private List<List<TileActor>> tileActors;

    public TileMapStage(AssetManager assetManager, MapManager mapManager) {
        this.assetManager = assetManager;
        this.mapManager = mapManager;
        setDebugUnderMouse(true); //TODO: WHY?
    }

    public void setMovementStatuses(boolean[][] tiles) {
        replaceTileStatuses(tiles, TileStatus.MOVE_AVAILABLE, TileStatus.NONE);
    }
    
    public void setAttackStatuses(boolean[][] tiles) {
        replaceTileStatuses(tiles, TileStatus.ATTACK_AVAILABLE, TileStatus.NONE);
    }

    public void createMapActors(TiledMapTileLayer layer) {
        HashMap<String, Sprite> tilestatuses = new HashMap<>();
        tileActors = new ArrayList<>();
        for (int x = 0; x < layer.getWidth(); x++) {
            ArrayList<TileActor> xActorList = new ArrayList<>();
            for (int y = 0; y < layer.getHeight(); y++) {
                TileActor tileActor = new TileActor(tilestatuses, assetManager, mapManager);
                tileActor.setBounds(x * layer.getTileWidth(), y * layer.getTileHeight(), layer.getTileWidth(), layer.getTileHeight());
                addActor(tileActor);
                xActorList.add(y, tileActor);
            }
            tileActors.add(x, xActorList);
        }
    }

    public void clearAllStatuses() {
        for (List<TileActor> actorlist : tileActors) {
            for (TileActor tileActor : actorlist) {
                tileActor.setStatus(TileStatus.NONE);
            }
        }
    }

    public List<List<TileActor>> getTileActors() {
        return tileActors;
    }

    public void setSelectedTile(int x, int y) {
        tileActors.get(x).get(y).setStatus(TileStatus.MOVE_SELECT);
    }
    
    private void replaceTileStatuses(boolean[][] tiles, TileStatus trueStatus, TileStatus falseStatus) {
        for (int x = 0; x < tiles.length; x++) {
            int length = tiles[x].length;
            for (int y = 0; y < length; y++) {
                TileActor actor = tileActors.get(x).get(y);
                if (tiles[x][y])
                    actor.setStatus(trueStatus);
                else
                    actor.setStatus(falseStatus);
            }
        }
    }
}