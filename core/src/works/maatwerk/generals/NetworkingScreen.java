package works.maatwerk.generals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;

class NetworkingScreen extends ScreenAdapter {

    private final Game game;
    private final AssetManager assetManager;

    NetworkingScreen(Game game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        super.show();

        Gdx.app.debug("Network", "Starting NetworkingTests");
        new Thread(new NetworkTestsRunnable()).start();
        Gdx.app.log("Screens", "Starting PlayingScreen");
        game.setScreen(new RegistrationScreen(assetManager));
    }
}
