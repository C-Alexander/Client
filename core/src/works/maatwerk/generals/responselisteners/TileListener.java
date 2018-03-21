package works.maatwerk.generals.responselisteners;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import works.maatwerk.generals.actors.ClickableMapActor;
import works.maatwerk.generals.models.MapManager;

/**
 * Created by teund on 06/03/2018.
 */
public class TileListener extends ClickListener{

    private final ClickableMapActor clickableMapActor;
    int tileSize;
    MapManager map;
    public TileListener(ClickableMapActor clickableMapActor, int tileSize, MapManager map) {
        this.clickableMapActor = clickableMapActor;
        this.tileSize = tileSize;
        this.map = map;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
       // System.out.println("Touch up at x:" + clickableMapActor.getX() + " y:" + clickableMapActor.getY());
       // System.out.println(x);
       // System.out.println(y);
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        Vector2 location = new Vector2(clickableMapActor.getX()/this.tileSize,clickableMapActor.getY()/this.tileSize);
        this.map.leftClickTile(location);

    }
}
