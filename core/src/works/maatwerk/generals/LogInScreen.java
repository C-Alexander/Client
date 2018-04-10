package works.maatwerk.generals;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import works.maatwerk.generals.utils.ScreensUtils;

/**
 * Created by teund on 21/03/2018.
 */
public class LogInScreen extends ScreenAdapter {
    private final Stage stage;

    public LogInScreen(final Generals game, final AssetManager assetManager) {
        this.stage = ScreensUtils.createStage("Log in", game, assetManager);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreensUtils.render(stage, delta);
    }
}