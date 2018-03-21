package works.maatwerk.generals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by teund on 21/03/2018.
 */
public class PickingScreen extends ScreenAdapter {
    private final Game game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final Stage stage;

    public PickingScreen(final Game game, final AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = assetManager.get("skin/uiskin.json");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBounds(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextButton btnRegister = new TextButton("Register", skin);
        btnRegister.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("Screens", "Starting RegisterScreen");
                game.setScreen(new RegistrationScreen(game, assetManager));
            }
        });
        TextButton btnLogIn = new TextButton("Log in", skin);
        btnLogIn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("Screens", "Starting PlayingScreen");
                game.setScreen(new PlayingScreen(assetManager));
            }
        });

        table.add(btnLogIn).bottom().right().width(200).height(100);
        table.add(btnRegister).bottom().right().width(200).height(100);
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
