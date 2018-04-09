package works.maatwerk.generals;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import works.maatwerk.generals.music.MusicManager;

public class Generals extends Game {
    private MusicManager musicManager;
    private AssetManager assetManager;
    private AccountManager accountManager;

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }
    
    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        assetManager = new AssetManager();
        musicManager = new MusicManager(assetManager);
        accountManager = new AccountManager();
        Gdx.app.log("Screens", "Starting LoadingScreen");
        this.setScreen(new LoadingScreen(this, assetManager));
    }

    @Override
    public void dispose() {
        super.dispose();
        musicManager.dispose();
        assetManager.dispose();
        accountManager.dispose();
    }
}