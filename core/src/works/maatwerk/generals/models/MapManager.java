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
import com.sun.media.jfxmedia.logging.Logger;
import works.maatwerk.generals.TileMapStage;
import works.maatwerk.generals.inputcontrollers.MusicController;

import java.util.ArrayList;
import java.util.Map;

public class MapManager {
    private TiledMap map;
    private final AssetManager assetManager;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;
    private  Character characterSelected;
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
        characterMap = new ArrayList<>();

    }


    /**
     * @param mapName
     */
    public void initializeMap(String mapName){
        map = assetManager.get("speel_map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        characterLayer = new Character[layer.getWidth()][layer.getHeight()];
        this.mapDimensions = new Vector2(layer.getWidth(),layer.getHeight());
        this.characterLayer = new Character[layer.getWidth()][layer.getHeight()];
        MapLayer layerTest = map.getLayers().get(1);
        createMapActors(layerTest);




    }

    private void createMapActors(MapLayer layer) {

        multiplexer.addProcessor(tileMapStage);
        tileMapStage.createMapActors((TiledMapTileLayer) layer);

    }

    private void startMusic() {
        Gdx.app.debug("Music", "Starting Background Music");
        @SuppressWarnings("SpellCheckingInspection") Music bgm = assetManager.get("data/music/megalovania.mp3");
        multiplexer.addProcessor(new MusicController(bgm));
        bgm.play();
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
        for (Character c : this.characterMap) {
            c.draw(batch,delta);
        }

    }


    /**
     * @param character
     * adds a character to the game
     */
    public void addCharacter(Character character){
        this.characterMap.add(character);
        this.characterLayer[(int)character.getLocation().x][(int)character.getLocation().y] = character;
    }

    /**
     * @param character
     * removes character from the game
     */
    public void removeCharacter(Character character){
       this.characterMap.remove(character);
        this.characterLayer[(int)character.getLocation().x][(int)character.getLocation().y]  = null;
    }

    /**
     * @param character
     * @param location
     */
    public void moveCharacter(Character character , Vector2 location){
        if(location.x > this.mapDimensions.x || location.y > this.mapDimensions.y || location.x < 0 || location.y <0){
            Logger.logMsg(1,"Out of boundaries");
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
    public Character getCharacterById(int id){
       return this.characterMap.get(id);
    }


    /**
     * @param location
     * @return cell from location
     */
    public TiledMapTileLayer.Cell getCell(Vector2 location){

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        TiledMapTileLayer.Cell cell =layer.getCell((int)location.x,(int)location.y);

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
}
