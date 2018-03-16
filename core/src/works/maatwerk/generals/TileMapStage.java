package works.maatwerk.generals;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sun.org.apache.xpath.internal.operations.Bool;
import works.maatwerk.generals.actors.ClickableMapActor;
import works.maatwerk.generals.enums.TileStatus;
import works.maatwerk.generals.models.Tile;

import java.util.*;

class TileMapStage extends Stage {
    private final Random rand;
    private AssetManager assetManager;
    List<List<ClickableMapActor>> tileActors;

    public TileMapStage(AssetManager assetManager) {
        this.assetManager = assetManager;
        setDebugUnderMouse(true);
        rand = new Random();
    }

    @Override
    public void act() {
        super.act();
    }

    public void setMovementStatuses(List<List<Boolean>> tiles) {
        HashSet<TileStatus> statusesToReplace = new HashSet<TileStatus>();

        statusesToReplace.add(TileStatus.MOVE_UNAVAILABLE);
        statusesToReplace.add(TileStatus.MOVE_AVAILABLE);
        statusesToReplace.add(TileStatus.NONE);

        replaceTileStatusesFromList(tiles, TileStatus.MOVE_AVAILABLE, TileStatus.MOVE_UNAVAILABLE, statusesToReplace);
    }

    public void replaceTileStatusesFromList(List<List<Boolean>> tiles, TileStatus trueStatus, TileStatus falseStatus, HashSet<TileStatus> statusesToReplace) {
        for (int x = 0; x < tiles.size(); x++) {
            List<Boolean> xRow = tiles.get(x);
            for (int y = 0; y < xRow.size(); y++) {
                ClickableMapActor actor = tileActors.get(x).get(y);

                if (statusesToReplace.contains(actor.getStatus())) {
                    if (xRow.get(y))
                        actor.setStatus(TileStatus.MOVE_AVAILABLE);
                    else
                        actor.setStatus(TileStatus.MOVE_UNAVAILABLE);
                }
            }
        }
    }

//    public void replaceTileStatusesFromList(List<List<TileStatus>> tiles, HashSet<TileStatus> statusesToReplace) {
//
//    }
//
//    public void replaceTileStatusesFromList(List<List<Boolean>> tiles, TileStatus trueStatus, TileStatus falseStatus, TileStatus replacedStatus, Boolean replaceNONE) {
//
//    }

    public void setTileStatusesFromList(List<List<Boolean>> tiles, TileStatus trueStatus, TileStatus falseStatus) {
        for (int x = 0; x < tiles.size(); x++) {
            List<Boolean> xRow = tiles.get(x);
            List<ClickableMapActor> xActorRow = tileActors.get(x);

            for (int y = 0; y < xRow.size(); y++) {
                ClickableMapActor clickableMapActor = xActorRow.get(y);

                if (xRow.get(y)) clickableMapActor.setStatus(trueStatus);
                else clickableMapActor.setStatus(falseStatus);
            }

        }
    }

    public void setTileStatusesFromList(List<List<TileStatus>> tiles) {
        for (int x = 0; x < tiles.size(); x++) {
            List<TileStatus> xStatusRow = tiles.get(x);
            List<ClickableMapActor> xActorRow = tileActors.get(x);

            for (int y = 0; y < xStatusRow.size(); y++) {
                xActorRow.get(y).setStatus(xStatusRow.get(y));
            }
        }
    }

    void createMapActors(TiledMapTileLayer layer) {
        HashMap<String, Sprite> tilestatuses = new HashMap<String, Sprite>();
        tileActors = new ArrayList<List<ClickableMapActor>>();
        for (int x = 0; x < layer.getWidth(); x++) {
            List xActorList = new ArrayList<ClickableMapActor>();

            for (int y = 0; y < layer.getHeight(); y++) {
                ClickableMapActor clickableMapActor = new ClickableMapActor(tilestatuses, assetManager);
                clickableMapActor.setBounds(x * layer.getTileWidth(), y * layer.getTileHeight(), layer.getTileWidth(), layer.getTileHeight());
                addActor(clickableMapActor);

                xActorList.add(y, clickableMapActor);
            }

            tileActors.add(x, xActorList);
        }
    }

    public List<List<ClickableMapActor>> getTileActors() {
        return tileActors;
    }
}