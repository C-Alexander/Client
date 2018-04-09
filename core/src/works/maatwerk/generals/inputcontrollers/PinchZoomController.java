package works.maatwerk.generals.inputcontrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import works.maatwerk.generals.utils.ZoomUtils;

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
        if (change == previousChange) {
            return false;
        }
        previousChange = change;
        Gdx.app.debug("Input", "Original pinch distance: " + initialDistance + " New pinch distance: " + distance);
        return ZoomUtils.zoom("PinchZoom: ", change, camera, touchPoint);
    }

    @Override
    public void pinchStop() {
        super.pinchStop();
        previousChange = 0;
        previousDistance = 0;
    }
}