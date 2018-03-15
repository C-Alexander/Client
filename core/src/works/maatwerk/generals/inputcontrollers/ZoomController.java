package works.maatwerk.generals.inputcontrollers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class ZoomController extends InputAdapter {
    private final Vector3 touchPoint = new Vector3();

    private final OrthographicCamera camera;

    public ZoomController(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean scrolled (int change) {
//        if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) return false;
//        Gdx.app.debug("Input", "Zoom: " + change);
//
//        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//
//        float px = touchPoint.x;
//        float py = touchPoint.y;
//
//        camera.zoom += change * camera.zoom * 0.1f;
//        camera.update();
//
//        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//        camera.position.add(px - touchPoint.x, py - touchPoint.y, 0);
//        camera.update();
        return true;
    }
}
