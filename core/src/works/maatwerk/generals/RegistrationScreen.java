package works.maatwerk.generals;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by teund on 21/03/2018.
 */
public class RegistrationScreen extends ScreenAdapter {

    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private OrthographicCamera camera;


    public RegistrationScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        super.show();
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();

        batch.end();
    }
}
