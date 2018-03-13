package works.maatwerk.generals.inputcontrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;

public class PinchZoomController extends GestureDetector.GestureAdapter {

    private final OrthographicCamera camera;
    private final Vector3 touchPoint = new Vector3();
    private float previousChange = 0;
    private float previousDistance = 0;

    public PinchZoomController(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        float change;
        if (previousDistance == 0 && previousChange == 0) {
            change = initialDistance - distance;
        } else {
            change = previousDistance - distance;
        }
        previousDistance = distance;
        change = change * 0.01f;

        if (change == previousChange) return false;

        previousChange = change;

        Gdx.app.debug("Input", "Original pinch distance: " + initialDistance + " New pinch distance: " + distance);
        Gdx.app.debug("Input", "PinchZoom: " + change);
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

    @Override
    public void pinchStop() {
        super.pinchStop();

        previousChange = 0;
        previousDistance = 0;
    }
}
