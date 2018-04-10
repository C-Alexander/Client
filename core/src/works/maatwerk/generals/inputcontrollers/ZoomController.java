package works.maatwerk.generals.inputcontrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import works.maatwerk.generals.utils.ZoomUtils;

public class ZoomController extends InputAdapter {
    private final Vector3 touchPoint = new Vector3();
    private final OrthographicCamera camera;

    public ZoomController(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean scrolled (int change) {
        if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
            return false;
        return ZoomUtils.zoom("Zoom: ", change, camera, touchPoint);
    }
}