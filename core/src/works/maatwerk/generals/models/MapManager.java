package works.maatwerk.generals.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import works.maatwerk.generals.TileMapStage;
import works.maatwerk.generals.utils.PathFinder;
import works.maatwerk.generals.utils.logger.*;
import works.maatwerk.generals.music.MusicManager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("WeakerAccess")
public class MapManager extends Stage {
    private final TileMapStage tileMapStage;
    private final InputMultiplexer multiplexer;
    private final MusicManager musicManager;
    private final AssetManager assetManager;
    private final ArrayList<Character> characterMap;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Character characterSelected;
    private Character[][] characterLayer;
    private Vector2 mapDimensions;
    
    private TextureRegion grid;
    private boolean[][] movementRange;
    private boolean[][] attackRange;

    /**
     * @param assetManager
     * @param inputMultiplexer
     * @param musicManager
     */
    public MapManager(AssetManager assetManager, InputMultiplexer inputMultiplexer, MusicManager musicManager) {
        this.assetManager = assetManager;
        this.multiplexer = inputMultiplexer;
        tileMapStage = new TileMapStage(assetManager, this);
        this.musicManager = musicManager;
        characterMap = new ArrayList<>();
        this.grid = new TextureRegion();
    }

    /**
     * @param mapName
     */
    public void initializeMap(String mapName) {
        map = assetManager.get("speel_map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        createMapActors(layer);
        characterLayer = new Character[layer.getWidth()][layer.getHeight()];
        mapDimensions = new Vector2(layer.getWidth(), layer.getHeight());
        this.characterLayer = new Character[layer.getWidth()][layer.getHeight()];
        this.initializeGrid();
        startMusic();
    }

    private void initializeGrid() {
        Gdx.app.debug("Grid", "Initializing Grid");
        Texture gridTexture = assetManager.get("GridGrayDotted.png");
        gridTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        MapProperties prop = map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        grid = new TextureRegion(gridTexture, mapWidth * tilePixelWidth, mapHeight * tilePixelHeight);
    }

    private void createMapActors(TiledMapTileLayer layer) {
        multiplexer.addProcessor(tileMapStage);
        tileMapStage.createMapActors(layer);

    }

    /**
     *
     */
    private void startMusic() {
        Gdx.app.debug(Tag.MUSIC, "Starting Background Music");

        musicManager.stopAllMusic();
        if (map.getProperties().containsKey("BGM"))
            musicManager.playMusic(map.getProperties().get("BGM").toString());
        else
            musicManager.playRandomMusic();
    }

    /**
     * @param location
     */
    public void leftClickTile(Vector2 location) {
        Character character = characterLayer[(int) location.x][(int) location.y];
        if (character != null) {
            if(attackRange != null && attackRange[(int) location.x][(int) location.y]){
                getCharacterSelected().attack(character);
                setCharacterSelected(null);
                attackRange = null;
                tileMapStage.clearAllStatuses();
                return;
            }
            setCharacterSelected(character);
            tileMapStage.setSelectedTile((int) location.x, (int) location.y);
            movementRange = PathFinder.getPossibleMoves(getMovementMap(), character, (int) location.x, (int) location.y);
            tileMapStage.setMovementStatuses(movementRange);
            attackRange = null;
            //TODO: UpdateUI
        } else if (this.getCharacterSelected() != null) {
            tileMapStage.clearAllStatuses();
            if(movementRange != null && movementRange[(int) location.x][(int) location.y]) {
                moveCharacter(getCharacterSelected(), location);
                movementRange = null;
                attackRange = PathFinder.getAttackRange(getRangeMap(), getCharacterSelected(), (int) location.x, (int) location.y);
                tileMapStage.setAttackStatuses(attackRange);
                return;
            }
            setCharacterSelected(null);
            attackRange = null;
        }
    }
    
    private int[][] getRangeMap() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int horizontalTiles = layer.getWidth();
        int verticalTiles = layer.getHeight();
        int[][] rangeMap = new int[horizontalTiles][verticalTiles];
        for (int x = 0; x < horizontalTiles; x++) {
            for (int y = 0; y < verticalTiles; y++) {
                rangeMap[x][y] = getRangeCost(x, y);
            }
        }
        return rangeMap;
    }

    private int[][] getMovementMap() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int horizontalTiles = layer.getWidth();
        int verticalTiles = layer.getHeight();
        int[][] movementMap = new int[horizontalTiles][verticalTiles];
        for (int x = 0; x < horizontalTiles; x++) {
            for (int y = 0; y < verticalTiles; y++) {
                movementMap[x][y] = getMovementCost(x, y);
            }
        }
        return movementMap;
    }
    
    private int getRangeCost(int x, int y) {
        return getCost(x, y, "noAttack", "rangeCost");
    }
    
    private int getMovementCost(int x, int y) {
        return getCost(x, y, "passable", "movementCost");
    }
    
    private int getCost(int x, int y, String key, String property) {
        boolean passable = true;
        int cost = 1;
        TiledMapTileLayer layer;
        for (MapLayer mapLayer : map.getLayers()) {
            layer = (TiledMapTileLayer) mapLayer;
            if (layer.getCell(x, y) == null)
                continue;
            if (layer.getCell(x, y).getTile().getProperties().containsKey(key))
                passable = layer.getCell(x, y).getTile().getProperties().get(key, true, Boolean.class);
            cost += layer.getCell(x, y).getTile().getProperties().get(property, 0, Integer.class);
        }
        return passable ? cost : -1;
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
        batch.draw(grid, 0, 0);
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
        if (location.x > mapDimensions.x || location.y > mapDimensions.y || location.x < 0 || location.y < 0) {
            Logger.getLogger(MapManager.class.getName()).log(Level.FINE, "Out of boundaries");
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
        return layer.getCell((int) location.x, (int) location.y);
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

    public void update() {
        ArrayList<Character> remove = new ArrayList<Character>();
        for (Character c : characterMap) {
            if (!c.isAlive()) {
                characterLayer[(int) c.getLocation().x][(int) c.getLocation().y] = null;
                remove.add(c);
            }
        }
        for (Character c : remove) {
            characterMap.remove(c);
        }
    }

    public Vector2 getTileSize() {
        return new Vector2(
            ((TiledMapTileLayer)map.getLayers().get(0)).getTileWidth(),
            ((TiledMapTileLayer)map.getLayers().get(0)).getTileHeight()
        );
    }

    public TileMapStage getTileMapStage() {
        return tileMapStage;
    }
}