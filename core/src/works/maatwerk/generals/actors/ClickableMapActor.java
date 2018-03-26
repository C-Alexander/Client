package works.maatwerk.generals.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import works.maatwerk.generals.models.MapManager;
import works.maatwerk.generals.responselisteners.TileListener;

/**
 * Created by teund on 06/03/2018.
 */
public class ClickableMapActor extends Actor {
    public ClickableMapActor(int tileSize,MapManager map) {
        this.addListener(new TileListener(this,tileSize,map));
    }
}
