package works.maatwerk.generals;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sun.org.apache.xpath.internal.operations.Bool;
import works.maatwerk.generals.actors.TileActor;
import works.maatwerk.generals.actors.TileActor;
import works.maatwerk.generals.enums.TileStatus;
import works.maatwerk.generals.models.MapManager;
import works.maatwerk.generals.models.Tile;

import java.util.*;

public class TileMapStage extends Stage {
    private final MapManager mapManager;
    private AssetManager assetManager;
    private List<List<TileActor>> tileActors;

    public TileMapStage(AssetManager assetManager, MapManager mapManager) {
        this.assetManager = assetManager;
        this.mapManager = mapManager;
        setDebugUnderMouse(true);
    }

    @Override
    public void act() {
        super.act();
    }

    public void setMovementStatuses(List<List<Boolean>> tiles) {
        HashSet<TileStatus> statusesToReplace = getStatusesToReplaceForMovement();

        replaceTileStatuses(tiles, TileStatus.MOVE_AVAILABLE, TileStatus.MOVE_UNAVAILABLE, statusesToReplace);
    }

    public void setMovementStatuses(boolean[][] tiles) {
        HashSet<TileStatus> statusesToReplace = getStatusesToReplaceForMovement();

        replaceTileStatuses(tiles, TileStatus.MOVE_AVAILABLE, TileStatus.NONE, statusesToReplace);
    }

    private HashSet<TileStatus> getStatusesToReplaceForMovement() {
        HashSet<TileStatus> statusesToReplace = new HashSet<TileStatus>();

        statusesToReplace.add(TileStatus.MOVE_UNAVAILABLE);
        statusesToReplace.add(TileStatus.MOVE_AVAILABLE);
        statusesToReplace.add(TileStatus.NONE);
        return statusesToReplace;
    }

    public void replaceTileStatuses(List<List<Boolean>> tiles, TileStatus trueStatus, TileStatus falseStatus, HashSet<TileStatus> statusesToReplace) {
        for (int x = 0; x < tiles.size(); x++) {
            List<Boolean> xRow = tiles.get(x);
            for (int y = 0; y < xRow.size(); y++) {
                TileActor actor = tileActors.get(x).get(y);

                if (statusesToReplace.contains(actor.getStatus())) {
                    if (xRow.get(y))
                        actor.setStatus(trueStatus);
                    else
                        actor.setStatus(falseStatus);
                }
            }
        }
    }

    public void replaceTileStatuses(boolean[][] tiles, TileStatus trueStatus, TileStatus falseStatus, HashSet<TileStatus> statusesToReplace) {
        for (int x = 0; x < tiles.length; x++) {
            boolean[] xRow = tiles[x];
            for (int y = 0; y < xRow.length; y++) {
                TileActor actor = tileActors.get(x).get(y);

                if (statusesToReplace.contains(actor.getStatus())) {
                    if (xRow[y])
                        actor.setStatus(trueStatus);
                    else
                        actor.setStatus(falseStatus);
                }
            }
        }
    }

    public void setTileStatuses(List<List<Boolean>> tiles, TileStatus trueStatus, TileStatus falseStatus) {
        for (int x = 0; x < tiles.size(); x++) {
            List<Boolean> xRow = tiles.get(x);
            List<TileActor> xActorRow = tileActors.get(x);

            for (int y = 0; y < xRow.size(); y++) {
                TileActor TileActor = xActorRow.get(y);

                if (xRow.get(y)) TileActor.setStatus(trueStatus);
                else TileActor.setStatus(falseStatus);
            }

        }
    }

    public void setTileStatuses(List<List<TileStatus>> tiles) {
        for (int x = 0; x < tiles.size(); x++) {
            List<TileStatus> xStatusRow = tiles.get(x);
            List<TileActor> xActorRow = tileActors.get(x);

            for (int y = 0; y < xStatusRow.size(); y++) {
                xActorRow.get(y).setStatus(xStatusRow.get(y));
            }
        }
    }

    public void createMapActors(TiledMapTileLayer layer) {
        HashMap<String, Sprite> tilestatuses = new HashMap<String, Sprite>();
        tileActors = new ArrayList<List<TileActor>>();
        for (int x = 0; x < layer.getWidth(); x++) {
            List xActorList = new ArrayList<TileActor>();

            for (int y = 0; y < layer.getHeight(); y++) {
                TileActor TileActor = new TileActor(tilestatuses, assetManager, mapManager);
                TileActor.setBounds(x * layer.getTileWidth(), y * layer.getTileHeight(), layer.getTileWidth(), layer.getTileHeight());
                addActor(TileActor);

                xActorList.add(y, TileActor);
            }

            tileActors.add(x, xActorList);
        }
    }

    public void clearAllStatuses() {
        for (List<TileActor> tileActors : tileActors) {
            for (TileActor tileActor : tileActors) {
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
}