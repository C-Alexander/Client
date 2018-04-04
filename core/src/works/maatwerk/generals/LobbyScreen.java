package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Bob on 27-3-2018.
 */
public class LobbyScreen extends ScreenAdapter {
    private final Generals game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final Stage stage;
    private OrthographicCamera camera;


    public LobbyScreen(final Generals game, final AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        this.batch = new SpriteBatch();
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        Skin skin = assetManager.get("skin/uiskin.json");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        Label lblLobby = new Label("Generals", skin);
        lblLobby.setFontScale(2);
        table.add(lblLobby).colspan(2).pad(20);
        table.row().pad(20);

        TextButton btnFindMatch = new TextButton("Find match", skin);

        btnFindMatch.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Thread thread = new Thread(new FindMatchRunnable());
                thread.run();

                // Find 2 players and connect them
                game.setScreen(new PlayingScreen(game, assetManager));
            }

        });

        table.add(btnFindMatch).bottom().right().width(100).height(50);
        table.row();
        stage.addActor(table);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
}
