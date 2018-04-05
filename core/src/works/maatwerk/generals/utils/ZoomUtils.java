package works.maatwerk.generals.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class ZoomUtils {
    
    public static boolean zoom(String label, float change, OrthographicCamera camera, Vector3 touchPoint) {
        Gdx.app.debug("Input", label + change);
        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        float px = touchPoint.x;
        float py = touchPoint.y;
        camera.zoom += change * camera.zoom * 0.1f;
        camera.update();
        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        camera.position.add(px - touchPoint.x, py - touchPoint.y, 0);
        camera.update();
        return true;
    }
}