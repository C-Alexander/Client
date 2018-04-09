package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import works.maatwerk.generals.utils.files.Paths;
import works.maatwerk.generals.utils.logger.Tag;

@SuppressWarnings("SpellCheckingInspection")
class LoadingScreen extends ScreenAdapter {
    private final Generals game;
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private Texture logo;
    private Texture fullBar;
    private Texture emptyBar;
    private Vector2 logoPos;
    private Vector2 ebPos;
    private Vector2 fbPos;
    private Boolean waitingForLogin = false;

    LoadingScreen(Generals game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;

        batch = new SpriteBatch();

        initializeCamera();

        loadLoadingBar();
        initializeLoadingBar();
    }

    private void initializeCamera() {
        Gdx.app.debug("Camera", "Initializing");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
    }

    private void loadLoadingBar() {
        Gdx.app.debug("Loading", "Loading Loadingbar");
        this.assetManager.load("loader/logo.jpg", Texture.class);
        this.assetManager.load("loader/main_bar.png", Texture.class);
        this.assetManager.load("loader/bar_full.png", Texture.class);

        this.assetManager.finishLoading();
    }

    private void initializeLoadingBar() {
        Gdx.app.debug("Loading", "Initializing Loadingbar");

        logo = this.assetManager.get("loader/logo.jpg");
        emptyBar = this.assetManager.get("loader/main_bar.png");
        fullBar = this.assetManager.get("loader/bar_full.png");

        logoPos = new Vector2((Gdx.graphics.getWidth() - logo.getWidth()) / 2, (Gdx.graphics.getHeight() / 2) - 100);
        ebPos = new Vector2((Gdx.graphics.getWidth() - emptyBar.getWidth()) / 2, logoPos.y - emptyBar.getHeight() + 40);
        fbPos = new Vector2((ebPos.x + (emptyBar.getWidth() / 2)) - (fullBar.getWidth() / 2), (ebPos.y + (emptyBar.getHeight() / 2)) - (fullBar.getHeight() / 2));
    }

    @Override
    public void show() {
        super.show();

        loadAssets();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Gdx.app.debug("Camera", "Resizing screen");

        viewport.update(width, height);
        camera.update();
    }

    private void loadAssets() {
        Gdx.app.debug("Loading", "Loading assets");

        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        assetManager.load("speel_map2.tmx", TiledMap.class);

        assetManager.load("GridGrayDotted.png", Texture.class);
        assetManager.load("characters/hValkyrie.png", Texture.class);
        assetManager.load("characters/gArcane.png", Texture.class);
        assetManager.load("characters/gAxe.png", Texture.class);
        assetManager.load("characters/gBow.png", Texture.class);
        assetManager.load("characters/gCorrupt.png", Texture.class);
        assetManager.load("characters/gDivine.png", Texture.class);
        assetManager.load("characters/gHealer.png", Texture.class);
        assetManager.load("characters/gSpear.png", Texture.class);
        assetManager.load("characters/gSword.png", Texture.class);
        assetManager.load("characters/mArcane.png", Texture.class);
        assetManager.load("characters/mAxe.png", Texture.class);
        assetManager.load("characters/mBow.png", Texture.class);
        assetManager.load("characters/mCorrupt.png", Texture.class);
        assetManager.load("characters/mDivine.png", Texture.class);
        assetManager.load("characters/mHealer.png", Texture.class);
        assetManager.load("characters/mSpear.png", Texture.class);
        assetManager.load("characters/mSword.png", Texture.class);
        assetManager.load("hud/uiBG.png", Texture.class);
        assetManager.load("skin/uiskin.json", Skin.class);
        assetManager.load("skin/uiskin.atlas", TextureAtlas.class);
        assetManager.load("skin/uiskin.json", Skin.class);
        assetManager.load("skin/uiskin.atlas", TextureAtlas.class);

        loadTileStatuses();
        loadMusic();

    }

    private void loadTileStatuses() {
        assetManager.load("tileobjects/tilestatus.atlas", TextureAtlas.class);
    }

    private void loadMusic() {
        for (FileHandle fileHandle : Gdx.files.internal(Paths.MUSIC_FOLDER).list(".mp3")) {
            assetManager.load(fileHandle.path(), Music.class);
        }
        for (FileHandle fileHandle : Gdx.files.internal(Paths.MUSIC_FOLDER).list(".wav")) {
            assetManager.load(fileHandle.path(), Music.class);
        }
        for (FileHandle fileHandle : Gdx.files.internal(Paths.MUSIC_FOLDER).list(".ogg")) {
            assetManager.load(fileHandle.path(), Music.class);
        }
        // todo:: figure out a way to make this prettier
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        assetManager.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(logo, logoPos.x, logoPos.y);
        batch.draw(emptyBar, ebPos.x, ebPos.y);
        batch.draw(fullBar, fbPos.x, fbPos.y, fullBar.getWidth() * assetManager.getProgress(), fullBar.getHeight());

        batch.end();

        if (assetManager.getProgress() == 1.0f) {
            finishLoading();
        }
    }

    private void finishLoading() {
        if (waitingForLogin) {
            switch (game.getAccountManager().getLoginStatus()) {
                case LOGGED_OUT:
                    game.setScreen(new LogInScreen(game, assetManager));
                    break;
                case LOGGED_IN:
                    game.setScreen(new PlayingScreen(game, assetManager));
                    break;
                default:
                    Gdx.app.log(Tag.ACCOUNT, "Searching for session...");
                    break;
            }
            return;
        }
        game.getMusicManager().initializeMusic();

        Gdx.app.log("Screens", "Starting NetworkingScreen");

        game.getAccountManager().init();

        waitingForLogin = true;
    }
}