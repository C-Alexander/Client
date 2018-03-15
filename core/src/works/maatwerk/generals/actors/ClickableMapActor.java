package works.maatwerk.generals.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import works.maatwerk.generals.enums.TileStatus;
import works.maatwerk.generals.responselisteners.TileListener;

import java.util.HashMap;

/**
 * Created by teund on 06/03/2018.
 */
public class ClickableMapActor extends Actor {
    private AssetManager assetManager;
    private HashMap<String, Sprite> tilestatuses;
    private TileStatus status = TileStatus.NONE;

    public ClickableMapActor(HashMap<String, Sprite> tilestatuses, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.addListener(new TileListener(this));
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

        String statusString = null;
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
//
//        if (true) {
//            Sprite spr = assetManager.get("tileobjects/tilestatus.atlas", TextureAtlas.class).createSprite("AttackA");
//            batch.setColor(255, 255, 255, 1);
//            batch.draw(spr, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
//            return;
//        }
        if (!tilestatuses.containsKey(statusString)) {
            tilestatuses.put(statusString,
                    assetManager.get("tileobjects/tilestatus.atlas", TextureAtlas.class).createSprite(statusString));
        }
        batch.draw(tilestatuses.get(statusString), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }


}
