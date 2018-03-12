package works.maatwerk.generals;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import works.maatwerk.generals.inputcontrollers.MusicController;
import works.maatwerk.generals.inputcontrollers.PinchZoomController;
import works.maatwerk.generals.inputcontrollers.PinchZoomDetector;
import works.maatwerk.generals.inputcontrollers.ZoomController;
import works.maatwerk.generals.networking.NetworkManager;

import static com.badlogic.gdx.Input.Peripheral.Vibrator;

class PlayingScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final InputMultiplexer multiplexer;
    private final ParticleEffect pEffect;
    private final AssetManager assetManager;
    private Animation anim;
    private float stateTime = 0f;

    private TiledMap map;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;
    private NetworkManager networkManager;


    PlayingScreen(AssetManager assetManager) {
        this.assetManager = assetManager;

        batch = new SpriteBatch();
        multiplexer = new InputMultiplexer();
        pEffect = new ParticleEffect();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        initializeCamera();
        initializeInputMultiplexer();
        initializeCameraInputController();
        initializeCharacterAnimations();
        initializeParticleEffects();
        initializeNetworking();

        startMusic();
        loadMap();

        Gdx.input.vibrate(5000);
    }

    private void initializeNetworking() {
        networkManager = new NetworkManager();
        networkManager.connect();
    }

    private void loadMap(){
        map = assetManager.get("speel_map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
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

        //TextureAtlas atlas = assetManager.get("character.atlas");
       // anim = new Animation<TextureRegion>(0.1f, atlas.getRegions());
    }

    private void initializeInputMultiplexer() {
        Gdx.app.debug("Input", "Initializing Input Multiplexer");

        multiplexer.addProcessor(new ZoomController(camera));
        multiplexer.addProcessor(new PinchZoomDetector(new PinchZoomController(camera)));


        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * Initializes an input controller that controls the camera.
     **/
    private void initializeCameraInputController() {
        Gdx.app.debug("Input", "Initializing CameraInputController");

        CameraInputController cameraInputController = new CameraInputController(camera);
        if (Gdx.app.getType() == Application.ApplicationType.Android) cameraInputController.translateUnits = 1500;
        else cameraInputController.translateUnits = 1000;
        cameraInputController.scrollFactor = 0;
        cameraInputController.forwardButton = -1000;
        cameraInputController.rotateButton = -1000;
        cameraInputController.rotateAngle = 0;
        cameraInputController.translateButton = Buttons.LEFT;
        cameraInputController.pinchZoomFactor = 0;

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
        batch.setProjectionMatrix(camera.combined);
        stateTime += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        camera.update();
        renderer.setView(camera);
        renderer.render();

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
