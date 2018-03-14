package works.maatwerk.generals.responselisteners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import works.maatwerk.generals.actors.ClickableMapActor;

/**
 * Created by teund on 06/03/2018.
 */
public class TileListener extends ClickListener{

    private final ClickableMapActor clickableMapActor;

    public TileListener(ClickableMapActor clickableMapActor) {
        this.clickableMapActor = clickableMapActor;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        System.out.println("Touch up at x:" + clickableMapActor.getX() + " y:" + clickableMapActor.getY());
        System.out.println(x);
        System.out.println(y);
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        System.out.println("clicked");

    }
}
