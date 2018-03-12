package works.maatwerk.generals.inputcontrollers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;

public class PinchZoomDetector extends GestureDetector {

    public PinchZoomDetector(GestureListener listener) {
        super(listener);
    }
}
