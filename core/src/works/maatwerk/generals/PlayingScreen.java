package works.maatwerk.generals;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import works.maatwerk.generals.enums.RankName;
import works.maatwerk.generals.inputcontrollers.MusicController;
import works.maatwerk.generals.inputcontrollers.PinchZoomController;
import works.maatwerk.generals.inputcontrollers.PinchZoomDetector;
import works.maatwerk.generals.inputcontrollers.ZoomController;
import works.maatwerk.generals.models.Character;
import works.maatwerk.generals.models.*;
import works.maatwerk.generals.networking.NetworkManager;
import works.maatwerk.generals.utils.BackgroundColor;
import works.maatwerk.generals.utils.StringUtils;

@SuppressWarnings("unused")
class PlayingScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final InputMultiplexer multiplexer;
    private final AssetManager assetManager;
    private final Generals game;
    private MapManager map;
    private Stage stage;
    private Table table;

    PlayingScreen(Generals game, AssetManager assetManager) {
        this.assetManager = assetManager;
        this.stage = new Stage();
        this.game = game;

        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), 80);

        batch = new SpriteBatch();
        multiplexer = new InputMultiplexer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        map = new MapManager(this.assetManager, multiplexer, this.game.getMusicManager());

        addUI(table);
    }

    //TODO: Jesus fucking christ Teun wat is dit
    private void loadOwnTeam(){
        Character character1 = new Character(new Race("Test", new Stats()), new Rank(RankName.GENERAL), WeaponClass.AXE, assetManager, new Vector2(13,3));
        character1.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character1);
        Character character2 = new Character(new Race("Test", new Stats()), new Rank(RankName.GRUNT), WeaponClass.AXE, assetManager, new Vector2(12, 2));
        character2.setWeapon(new Weapon("Axe", 1, new Stats(), false, null));
        map.addCharacter(character2);
        Character character3 = new Character(new Race("Test", new Stats()),new Rank(RankName.GRUNT), WeaponClass.AXE, assetManager, new Vector2(14,2));
        character1.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character3);
        Character character4 = new Character(new Race("Test", new Stats()),new Rank(RankName.GENERAL), WeaponClass.SWORD, assetManager, new Vector2(15,5));
        character1.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character4);
        Character character5 = new Character(new Race("Test", new Stats()),new Rank(RankName.GRUNT), WeaponClass.SWORD, assetManager, new Vector2(14,4));
        character1.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character5);
        Character character6 = new Character(new Race("Test", new Stats()), new Rank(RankName.GRUNT), WeaponClass.SWORD, assetManager, new Vector2(16,4));
        character1.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character6);
        Character character7 = new Character(new Race("Test", new Stats()), new Rank(RankName.HERO), WeaponClass.VALKYRIE, assetManager, new Vector2(15, 3));
        character2.setWeapon(new Weapon("Axe", 1, new Stats(), false, null));
        map.addCharacter(character7);
        Character character8 = new Character(new Race("Test", new Stats()),new Rank(RankName.GENERAL), WeaponClass.BOW, assetManager, new Vector2(17,3));
        character1.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character8);
        Character character9 = new Character(new Race("Test", new Stats()),new Rank(RankName.GRUNT), WeaponClass.BOW, assetManager, new Vector2(16,2));
        character1.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character9);
        Character character10 = new Character(new Race("Test", new Stats()), new Rank(RankName.GRUNT), WeaponClass.BOW, assetManager, new Vector2(18,2));
        character1.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character10);
    }

    //TODO: Jesus fucking christ Teun wat is dit
    private void loadEnemyTeam(){
        Character character11 = new Character(new Race("Test", new Stats()), new Rank(RankName.GENERAL), WeaponClass.ARCANE, assetManager, new Vector2(13,27));
        character11.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character11);
        Character character12 = new Character(new Race("Test", new Stats()), new Rank(RankName.GRUNT), WeaponClass.ARCANE, assetManager, new Vector2(12, 28));
        character12.setWeapon(new Weapon("Axe", 1, new Stats(), false, null));
        map.addCharacter(character12);
        Character character13 = new Character(new Race("Test", new Stats()),new Rank(RankName.GRUNT), WeaponClass.ARCANE, assetManager, new Vector2(14,28));
        character13.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character13);
        Character character14 = new Character(new Race("Test", new Stats()),new Rank(RankName.GENERAL), WeaponClass.SPEAR, assetManager, new Vector2(15,25));
        character14.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character14);
        Character character15 = new Character(new Race("Test", new Stats()),new Rank(RankName.GRUNT), WeaponClass.SPEAR, assetManager, new Vector2(14,26));
        character15.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character15);
        Character character16 = new Character(new Race("Test", new Stats()), new Rank(RankName.GRUNT), WeaponClass.SPEAR, assetManager, new Vector2(16,26));
        character16.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character16);
        Character character17 = new Character(new Race("Test", new Stats()), new Rank(RankName.HERO), WeaponClass.VALKYRIE, assetManager, new Vector2(15, 27));
        character17.setWeapon(new Weapon("Axe", 1, new Stats(), false, null));
        map.addCharacter(character17);
        Character character18 = new Character(new Race("Test", new Stats()),new Rank(RankName.GENERAL), WeaponClass.CORRUPT, assetManager, new Vector2(17,27));
        character18.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character18);
        Character character19 = new Character(new Race("Test", new Stats()),new Rank(RankName.GRUNT), WeaponClass.CORRUPT, assetManager, new Vector2(16,28));
        character19.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character19);
        Character character20 = new Character(new Race("Test", new Stats()), new Rank(RankName.GRUNT), WeaponClass.CORRUPT, assetManager, new Vector2(18,28));
        character20.setWeapon(new Weapon("Axe",1,new Stats(),false,null));
        map.addCharacter(character20);
    }

    private void initializeVolumeControls() {
        multiplexer.addProcessor(new MusicController(game.getMusicManager()));
    }

    private void initializeNetworking() {
        NetworkManager networkManager = new NetworkManager(game);
        networkManager.connect();
    }

    private void initializeInputMultiplexer() {
        Gdx.app.debug("Input", "Initializing Input Multiplexer");

        multiplexer.addProcessor(new ZoomController(camera));
        multiplexer.addProcessor(new PinchZoomDetector(new PinchZoomController(camera)));


        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * Start code for a end game button. Doesn't do anything now. Need to update this when the game can be ended.
     **/
    /*private void initializeEndGame(){
        Skin skin = assetManager.get("skin/uiskin.json");
        TextButton btnRegister = new TextButton("Register", skin);

        btnRegister.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ()) {

                }
            }
        });
    }*/
    
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

    //todo:: Move the UI stuff to another class.
    private void updateCharacterLabel(Table table, Character character){
        table.clearChildren();
        if (character != null){
            Skin skin = assetManager.get("skin/uiskin.json");

            Label lblName = new Label("Name:", skin);
            Label lblNameValue = new Label(character.getName(), skin);

            Label lblRace = new Label("Race:", skin);
            Label lblRaceValue = new Label(character.getRace().getName(), skin);

            Label lblRank = new Label("Rank:", skin);
            Label lblRankValue = new Label(StringUtils.ucFirst(character.getRank().getRankName().toString()), skin);

            Label lblStats = new Label(character.getGameStats().toString(), skin);

            table.top().left();
            float cellsize = table.add(new Image(character.getTexture())).padRight(30).getActorWidth();

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
        }
        stage.addActor(table);
    }
    
    @Override
    public void show() {
        initializeCamera();
        initializeInputMultiplexer();
        initializeNetworking();
        map.initializeMap("");
        loadOwnTeam();
        loadEnemyTeam();
        Gdx.input.vibrate(5000);
        initializeVolumeControls();
        initializeCameraInputController();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        Gdx.app.debug("Camera", "Resizing screen");

        map.getViewport().update(width, height);
        map.getTileMapStage().getViewport().update(width, height);

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

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
