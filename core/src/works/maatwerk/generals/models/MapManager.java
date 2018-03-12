package works.maatwerk.generals.models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

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




    /**
     * @param assetManager
     */
    public MapManager(AssetManager assetManager) {
        this.assetManager = assetManager;

    }


    /**
     * @param mapName
     */
    public void loadMap(String mapName){
        map = assetManager.get("speel_map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        characterLayer = new Character[layer.getWidth()][layer.getHeight()];
    }


    /**
     * @param delta
     * @param camera
     * @param batch
     */
    public void render(final float delta, OrthographicCamera camera, final SpriteBatch batch) {

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

    public void moveCharacter(Character character , Vector2 location){


        removeCharacter(character);
        character.setLocation(location);
        addCharacter(character);


    }

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

    public Character getCharacterSelected() {
        return characterSelected;
    }

    public void setCharacterSelected(Character characterSelected) {
        this.characterSelected = characterSelected;
    }
}
