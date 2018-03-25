package works.maatwerk.generals;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import works.maatwerk.generals.inputcontrollers.MusicController;
import works.maatwerk.generals.inputcontrollers.PinchZoomController;
import works.maatwerk.generals.inputcontrollers.PinchZoomDetector;
import works.maatwerk.generals.inputcontrollers.ZoomController;
import works.maatwerk.generals.models.*;
import works.maatwerk.generals.models.Character;
import works.maatwerk.generals.networking.NetworkManager;

@SuppressWarnings("unused")
class PlayingScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final InputMultiplexer multiplexer;
    private final ParticleEffect pEffect;
    private final AssetManager assetManager;

    private Animation anim;
    private float stateTime = 0f;
    public World world;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;
    private Texture SwordCharacter;
    private Texture AxeCharacter;
    private Texture SpearCharacter;
    private MapManager map;
    private Stage stage;


    @SuppressWarnings("FieldCanBeLocal")
    private NetworkManager networkManager;


    PlayingScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.stage = new Stage();

        batch = new SpriteBatch();
        multiplexer = new InputMultiplexer();
        pEffect = new ParticleEffect();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new MapManager(this.assetManager,multiplexer);

        addUI();
    }

    @Override
    public void show() {
        initializeCamera();
        initializeInputMultiplexer();
        initializeCharacterAnimations();
        initializeParticleEffects();
        initializeNetworking();
        map.initializeMap("");
        Character character1 = new Character( new Race("Test",new Stats(3,1,1,1,1)),assetManager,ClassEnum.AXE,new Vector2(1,1));
        character1.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character1);
        Character character2 = new Character( new Race("Test",new Stats()),assetManager,ClassEnum.AXE,new Vector2(2,2));
        character2.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character2);

        Gdx.input.vibrate(5000);


        initializeCameraInputController();
    }




    private void initializeNetworking() {
        networkManager = new NetworkManager();
        networkManager.connect();
    }



    private void startMusic() {
        Gdx.app.debug("Music", "Starting Background Music");

        @SuppressWarnings("SpellCheckingInspection") Music bgm = assetManager.get("data/music/megalovania.mp3");
      //  multiplexer.addProcessor(new MusicController(bgm));
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

      //  TextureAtlas atlas = assetManager.get("SwordCharacter.atlas");
     //   anim = new Animation<TextureRegion>(0.1f, atlas.getRegions());
    }

    private void initializeInputMultiplexer() {
        Gdx.app.debug("Input", "Initializing Input Multiplexer");

        multiplexer.addProcessor(new ZoomController(camera));
        multiplexer.addProcessor(new PinchZoomDetector(new PinchZoomController(camera)));


        Gdx.input.setInputProcessor(multiplexer);
    }
    private void initializeCharacters(){
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
        if (Gdx.app.getType() == Application.ApplicationType.Android) cameraInputController.translateUnits = 1500;
        else cameraInputController.translateUnits = 1000;
        cameraInputController.scrollFactor = 0;
        cameraInputController.forwardButton = -1000;
        cameraInputController.rotateButton = -1000;
        cameraInputController.rotateAngle = 0;
        cameraInputController.translateButton = Buttons.RIGHT;
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
        map.update();
        super.render(delta);
        batch.setProjectionMatrix(camera.combined);



        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        camera.update();

        map.render(delta,camera,batch);

        batch.end();

        if (pEffect.isComplete()) pEffect.reset();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    private void addUI(){
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Texture texture = assetManager.get("hud/uiBG.png", Texture.class);
        Image image = new Image(texture);

        table.bottom();
        table.add(image);

        stage.addActor(table);
    }
}
