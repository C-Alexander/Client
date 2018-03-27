package works.maatwerk.generals;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import works.maatwerk.generals.music.MusicManager;

class Generals extends Game {

    private MusicManager musicManager;
    private AssetManager assetManager;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        musicManager = new MusicManager(assetManager);

		Gdx.app.log("Screens", "Starting LoadingScreen");
        this.setScreen(new LoadingScreen(this, assetManager));
	}


    public MusicManager getMusicManager() {
        return musicManager;
    }

    @Override
    public void dispose() {
        super.dispose();
        musicManager.dispose();
        assetManager.dispose();
    }
}
