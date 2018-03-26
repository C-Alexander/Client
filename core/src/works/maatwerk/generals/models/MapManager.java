package works.maatwerk.generals.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sun.media.jfxmedia.logging.Logger;
import works.maatwerk.generals.TileMapStage;
import works.maatwerk.generals.inputcontrollers.MusicController;

import java.util.ArrayList;
import java.util.Map;

public class MapManager extends Stage {
    private TiledMap map;
    private final AssetManager assetManager;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;
    private Character characterSelected;
    private ArrayList<Character> characterMap;
    private Character[][] characterLayer;
    private static Vector2 mapDimensions;
    private final TileMapStage tileMapStage = new TileMapStage();
    private final InputMultiplexer multiplexer;


    /**
     * @param assetManager
     */
    public MapManager(AssetManager assetManager, InputMultiplexer inputMultiplexer) {
        this.assetManager = assetManager;
        this.multiplexer = inputMultiplexer;
        characterMap = new ArrayList<Character>();


    }


    /**
     * @param mapName
     */
    public void initializeMap(String mapName) {
        map = assetManager.get("speel_map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        createMapActors(layer);
        characterLayer = new Character[layer.getWidth()][layer.getHeight()];
        this.mapDimensions = new Vector2(layer.getWidth(), layer.getHeight());
        this.characterLayer = new Character[layer.getWidth()][layer.getHeight()];
    }

    private void createMapActors(TiledMapTileLayer layer) {

        multiplexer.addProcessor(tileMapStage);
        tileMapStage.createMapActors(layer, this);

    }

    /**
     *
     */
    private void startMusic() {
        Gdx.app.debug("Music", "Starting Background Music");
        @SuppressWarnings("SpellCheckingInspection") Music bgm = assetManager.get("data/music/megalovania.mp3");
        multiplexer.addProcessor(new MusicController(bgm));
        bgm.play();
    }

    /**
     * @param location
     */
    public void leftClickTile(Vector2 location) {


        Character character = characterLayer[(int) location.x][(int) location.y];


        if (character != null && this.getCharacterSelected() == null) {
            this.setCharacterSelected(character);
            //TODO: UpdateUI
        } else {
            if (character == null && this.getCharacterSelected() !=null) {
                moveCharacter(this.getCharacterSelected(), location);
                this.setCharacterSelected(null);

            }
            else {
                if(this.getCharacterSelected() !=null){
                this.getCharacterSelected().attack(character);
                this.setCharacterSelected(null);
                }
            }
        }
    }


    /**
     * @param delta
     * @param camera
     * @param batch
     */
    public void render(final float delta, OrthographicCamera camera, final SpriteBatch batch) {
        tileMapStage.getViewport().setCamera(camera);
        renderer.setView(camera);
        renderer.render();
        tileMapStage.draw();
        batch.end();
        batch.begin();
        for (Character c : this.characterMap) {
            c.draw(batch, delta);
        }
    }


    /**
     * @param character adds a character to the game
     */
    public void addCharacter(Character character) {
        this.characterMap.add(character);
        this.characterLayer[(int) character.getLocation().x][(int) character.getLocation().y] = character;
    }

    /**
     * @param character removes character from the game
     */
    public void removeCharacter(Character character) {
        if (character.getLocation() == null) {
            System.out.println("wtf");
        }
        this.characterLayer[(int) character.getLocation().x][(int) character.getLocation().y] = null;
        this.characterMap.remove(character);
    }

    /**
     * @param character
     * @param location
     */
    public void moveCharacter(Character character, Vector2 location) {
        if (location.x > this.mapDimensions.x || location.y > this.mapDimensions.y || location.x < 0 || location.y < 0) {
            Logger.logMsg(1, "Out of boundaries");
            return;
        }
        removeCharacter(character);
        character.setLocation(location);
        addCharacter(character);
    }

    /**
     * @param id
     * @return
     */
    public Character getCharacterById(int id) {
        return this.characterMap.get(id);
    }


    /**
     * @param location
     * @return cell from location
     */
    public TiledMapTileLayer.Cell getCell(Vector2 location) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        TiledMapTileLayer.Cell cell = layer.getCell((int) location.x, (int) location.y);

        return cell;
    }

    /**
     * @return
     */
    public Character getCharacterSelected() {
        return characterSelected;
    }

    /**
     * @param characterSelected
     */
    public void setCharacterSelected(Character characterSelected) {
        this.characterSelected = characterSelected;
    }

    public void update(){
        ArrayList<Character> remove = new ArrayList<Character>();
        for (Character c: characterMap){
            if(!c.isAlive()){
                characterLayer[(int)c.getLocation().x][(int)c.getLocation().y] = null;
                remove.add(c);
            }
        }
        for (Character c:remove){
            characterMap.remove(c);

        }

    }
}
