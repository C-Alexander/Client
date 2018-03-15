package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import works.maatwerk.generals.inputcontrollers.MusicController;
import works.maatwerk.generals.inputcontrollers.ZoomController;
import works.maatwerk.generals.models.World;

class PlayingScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final InputMultiplexer multiplexer;
    private final ParticleEffect pEffect;
    private final AssetManager assetManager;
    private final TileMapStage tileMapStage;
    private Animation anim;
    private float stateTime = 0f;
    public World world;
    private TiledMap map;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;
    private Texture SwordCharacter;
    private Texture AxeCharacter;
    private Texture SpearCharacter;
    private FPSLogger fpsLogger;


    PlayingScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
        tileMapStage = new TileMapStage(assetManager);
        batch = new SpriteBatch();
        multiplexer = new InputMultiplexer();
        pEffect = new ParticleEffect();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.fpsLogger = new FPSLogger();

    }

    @Override
    public void show() {
        initializeCamera();
        initializeMap();
        initializeInputMultiplexer();
        initializeCharacters();
        initializeCharacterAnimations();
        initializeParticleEffects();
        startMusic();
        initializeCameraInputController();
    }

    private void initializeMap() {
        Gdx.app.debug("Map", "Initializing Map");
        map = assetManager.get("speel_map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        createMapActors(map.getLayers().get(1));
    }

    private void createMapActors(MapLayer layer) {
        multiplexer.addProcessor(tileMapStage);
        tileMapStage.createMapActors((TiledMapTileLayer) layer);
    }

    private void startMusic() {
        Gdx.app.debug("Music", "Starting Background Music");

        Music bgm = assetManager.get("data/music/megalovania.mp3");
        multiplexer.addProcessor(new MusicController(bgm));
        bgm.play();
    }

    private void initializeParticleEffects() {
        Gdx.app.debug("Particles", "Initializing Particle Effects");

      //  pEffect.load(Gdx.files.internal("fire"), Gdx.files.internal(""));
       // pEffect.getEmitters().first().setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
       // pEffect.start();
    }

    private void initializeCharacterAnimations() {
        Gdx.app.debug("Animations", "Initializing Character Animations");

        //TextureAtlas atlas = assetManager.get("SwordCharacter.atlas");
       // anim = new Animation<TextureRegion>(0.1f, atlas.getRegions());
    }

    private void initializeInputMultiplexer() {
        Gdx.app.debug("Input", "Initializing Input Multiplexer");

        multiplexer.addProcessor(new ZoomController(camera));

        Gdx.input.setInputProcessor(multiplexer);
    }

    private void initializeCharacters() {
        Gdx.app.debug("Characters", "Initializing Characters");
        SwordCharacter = assetManager.get("GruntSword.png");
        AxeCharacter = assetManager.get("GruntAxe.png");
        SpearCharacter = assetManager.get("GruntSpear.png");
    }
    /**
     * Initializes an input controller that controls the camera.
     **/
    private void initializeCameraInputController() {
        Gdx.app.debug("Input", "Initializing CameraInputController");

        CameraInputController cameraInputController = new CameraInputController(camera);
        cameraInputController.translateUnits = 900;
        cameraInputController.scrollFactor = 0;
        cameraInputController.forwardButton = -1000;
        cameraInputController.rotateButton = -1000;
        cameraInputController.rotateAngle = 0;
        cameraInputController.translateButton = Buttons.RIGHT;

        multiplexer.addProcessor(cameraInputController);
    }

    private void initializeCamera() {
        Gdx.app.debug("Camera", "Initializing Camera");

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Gdx.app.debug("Camera", "Resizing screen");

        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        fpsLogger.log();
        batch.setProjectionMatrix(camera.combined);
        stateTime += Gdx.graphics.getDeltaTime();

        tileMapStage.getViewport().setCamera(camera);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tileMapStage.act();

        batch.begin();

        camera.update();
        renderer.setView(camera);

        renderer.render();
        tileMapStage.draw();

        batch.end();
        batch.begin();

        batch.draw(SwordCharacter, 416, 64);
        batch.draw(AxeCharacter, 448, 64);
        batch.draw(SwordCharacter, 480, 64);
        batch.draw(SpearCharacter, 448, 96);
        batch.draw(AxeCharacter, 480, 480);
        //batch.draw((TextureRegion)anim.getKeyFrame(stateTime, true), 0, 0);
        //pEffect.draw(batch, delta);

        batch.end();



        if (pEffect.isComplete()) pEffect.reset();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

}
