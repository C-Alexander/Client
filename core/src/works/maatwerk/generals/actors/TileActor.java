package works.maatwerk.generals.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import works.maatwerk.generals.enums.TileStatus;
import works.maatwerk.generals.models.MapManager;
import works.maatwerk.generals.responselisteners.TileListener;
import java.util.Map;

/**
 * Created by teund on 06/03/2018.
 */
public class TileActor extends Actor {
    private final AssetManager assetManager;
    private final Map<String, Sprite> tilestatuses;
    private TileStatus status = TileStatus.NONE;

    public TileActor(Map<String, Sprite> tilestatuses, AssetManager assetManager, MapManager mapManager) {
        this.assetManager = assetManager;
        addListener(new TileListener(this, mapManager));
        this.tilestatuses = tilestatuses;
    }

    public TileStatus getStatus() {
        return status;
    }

    public void setStatus(TileStatus status) {
        this.status = status;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawStatus(batch);
    }

    private void drawStatus(Batch batch) {
        String statusString;
        switch (status) {
            case ATTACK_AVAILABLE:
                statusString = "HealA";
                break;
            case ATTACK_UNAVAILABLE:
                statusString = "AttackU";
                break;
            case HEAL_AVAILABLE:
                statusString = "HealA";
                break;
            case HEAL_UNAVAILABLE:
                statusString = "HealU";
                break;
            case MOVE_AVAILABLE:
                statusString = "MoveA";
                break;
            case MOVE_SELECT:
                statusString = "MoveS";
                break;
            case MOVE_UNAVAILABLE:
                statusString = "MoveU";
                break;
            case SELECT_ALLY:
                statusString = "SelectA";
                break;
            case SELECT_ENEMY:
                statusString = "SelectE";
                break;
            case SELECT_HEAL:
                statusString = "SelectH";
                break;
            default:
                return;
        }
        if (!tilestatuses.containsKey(statusString)) {
            tilestatuses.put(statusString,
                    assetManager.get("tileobjects/tilestatus.atlas", TextureAtlas.class).createSprite(statusString));
        }
        batch.draw(tilestatuses.get(statusString), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
}