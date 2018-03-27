package works.maatwerk.generals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by teund on 21/03/2018.
 */
public class LogInScreen extends ScreenAdapter{
    private final Generals game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private TextField txtUsername, txtPassword, txtPasswordRepeat;
    private Stage stage;
    private OrthographicCamera camera;


    public LogInScreen(final Generals game, final AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = assetManager.get("skin/uiskin.json");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label lblTitle = new Label("Log in", skin);
        lblTitle.setFontScale(2);
        table.add(lblTitle).colspan(2).pad(20);
        table.row().pad(20);

        table.add(new Label("username:", skin));
        txtUsername = new TextField("", skin);
        table.add(txtUsername).minWidth(250).pad(10);
        table.row().pad(20);

        table.add(new Label("password:", skin));
        txtPassword = new TextField("", skin);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        table.add(txtPassword).minWidth(250).pad(10);
        table.row().pad(20);

        TextButton btnRegister = new TextButton("Register", skin);
        btnRegister.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RegistrationScreen(game, assetManager));
            }
        });
        TextButton btnLogIn = new TextButton("Log in", skin);
        btnLogIn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PostGameScreen(game, assetManager,"BoxerShort1",150,20,60,false));
            }
        });

        table.add(btnRegister).bottom().right().width(100).height(50);
        table.add(btnLogIn).bottom().right().width(100).height(50);
        table.row();
        stage.addActor(table);
    }

    @Override
    public void show() {
        super.show();
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
