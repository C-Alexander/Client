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
import com.sun.media.jfxmedia.logging.Logger;
import works.maatwerk.generals.TileMapStage;
import works.maatwerk.generals.utils.PathFinder;
import works.maatwerk.generals.utils.logger.*;
import works.maatwerk.generals.music.MusicManager;
import java.util.ArrayList;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

@SuppressWarnings("WeakerAccess")
public class MapManager extends Stage {
    private final MusicManager musicManager;
    private TiledMap map;
    private final AssetManager assetManager;
    private OrthogonalTiledMapRenderer renderer;
    private Character characterSelected;
    @SuppressWarnings("CanBeFinal")
    private ArrayList<Character> characterMap;
    private Character[][] characterLayer;
    private static Vector2 mapDimensions;
    private final TileMapStage tileMapStage;
    private final InputMultiplexer multiplexer;
    private TextureRegion grid;
    private boolean[][] movementRange;

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
        characterMap = new ArrayList<Character>();
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
            if (this.getCharacterSelected() == null) {
                this.setCharacterSelected(character);

                tileMapStage.setSelectedTile((int) location.x, (int) location.y);
                movementRange = PathFinder.getPossibleMoves(getMovementMap(), character, (int) location.x, (int) location.y);
                tileMapStage.setMovementStatuses(movementRange);
                //TODO: UpdateUI
            } else {
                if (this.getCharacterSelected() != null) {
                    this.getCharacterSelected().attack(character);
                    this.setCharacterSelected(null);
                    tileMapStage.clearAllStatuses();
                }
            }
        } else {
            if (this.getCharacterSelected() != null && this.movementRange[(int) location.x][(int) location.y]) {
                moveCharacter(this.getCharacterSelected(), location);
                this.setCharacterSelected(null);
                tileMapStage.clearAllStatuses();
            }
        }
    }

    private int[][] getMovementMap() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        int horizontalTiles = layer.getWidth();
        int verticalTiles = layer.getHeight();

        int[][] movementMap = new int[horizontalTiles][verticalTiles];

        for (int x = 0; x < horizontalTiles; x++) {
            for (int y = 0; y < verticalTiles; y++) {
                boolean passable = true;
                int cost = 1;

                for (MapLayer mapLayer : map.getLayers()) {
                    layer = (TiledMapTileLayer) mapLayer;

                    if (layer.getCell(x, y) == null) continue;

                    if (layer.getCell(x, y).getTile().getProperties().containsKey("passable"))
                                    passable = layer.getCell(x, y).getTile().getProperties().get("passable", true, Boolean.class);
                                    cost += layer.getCell(x, y).getTile().getProperties().get("movementCost", 0, Integer.class);
                }

                movementMap[x][y] = passable ? cost : -1;
            }
        }
        return movementMap;
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

        @SuppressWarnings("UnnecessaryLocalVariable") TiledMapTileLayer.Cell cell = layer.getCell((int) location.x, (int) location.y);

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