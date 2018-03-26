package works.maatwerk.generals;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import works.maatwerk.generals.inputcontrollers.PinchZoomController;
import works.maatwerk.generals.inputcontrollers.PinchZoomDetector;
import works.maatwerk.generals.inputcontrollers.ZoomController;
import works.maatwerk.generals.models.*;
import works.maatwerk.generals.models.Character;
import works.maatwerk.generals.networking.NetworkManager;
import works.maatwerk.generals.utils.BackgroundColor;
import works.maatwerk.generals.utils.StringUtils;

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
    private Table table;


    @SuppressWarnings("FieldCanBeLocal")
    private NetworkManager networkManager;


    PlayingScreen(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.stage = new Stage();
        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), 80);

        batch = new SpriteBatch();
        multiplexer = new InputMultiplexer();
        pEffect = new ParticleEffect();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new MapManager(this.assetManager,multiplexer);

        addUI(table);
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
        updateCharacterLabel(table, map.getCharacterSelected());

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

    private void addUI(Table table){
        Skin skin = assetManager.get("skin/uiskin.json");

        Gdx.input.setInputProcessor(stage);

        Label label = new Label("Character X", skin);
        label.setFontScale(1);

        BackgroundColor backgroundColor = new BackgroundColor("skin/white.png");
        backgroundColor.setColor(0, 129, 128, 255);
        table.setBackground(backgroundColor);

        updateCharacterLabel(table, map.getCharacterSelected());
    }

    private void updateCharacterLabel(Table table, Character character){
        table.clearChildren();
        if (character != null){
            Skin skin = assetManager.get("skin/uiskin.json");

            Image image = new Image(character.getTexture());

            Label lblName = new Label("Name:", skin);
            Label lblNameValue = new Label(character.getName(), skin);

            Label lblRace = new Label("Race:", skin);
            Label lblRaceValue = new Label(character.getRace().getName(), skin);

            Label lblRank = new Label("Rank:", skin);
            Label lblRankValue = new Label(StringUtils.UcFirst(character.getRank().getRankName().toString()), skin);

            Label lblStats = new Label(character.getBaseStats().toUsefulString(), skin);


            table.top().left();
            float cellsize = table.add(image).padRight(30).getActorWidth();

            //Name
            table.add(lblName).left().padRight(10);
            table.add(lblNameValue).left();
            table.add(lblStats).left().padLeft(60);

            table.row().padTop(-10);
            table.add().padRight(30).setActorWidth(cellsize);
            table.add(lblRace).left();
            table.add(lblRaceValue).left();

            table.row().padTop(-7);
            table.add().padRight(30).setActorWidth(cellsize);
            table.add(lblRank).left();
            table.add(lblRankValue).left();




        }else{
            //TODO: Draw empty HUD
        }

        stage.addActor(table);
    }
}
