package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;

class NetworkingScreen extends ScreenAdapter {

    private final Generals game;
    private final AssetManager assetManager;

    NetworkingScreen(Generals game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        super.show();

        Gdx.app.debug("Network", "Starting NetworkingTests");
        new Thread(new NetworkTestsRunnable()).start();
        Gdx.app.log("Screens", "Starting PlayingScreen");
        game.setScreen(new PlayingScreen(game, assetManager));
        game.setScreen(new PostGameScreen(game, assetManager,"BoxerShort1",150,20,60,false));
    }
}