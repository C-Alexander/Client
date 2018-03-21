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
import works.maatwerk.generals.models.Account;

/**
 * Created by teund on 21/03/2018.
 */
public class RegistrationScreen extends ScreenAdapter {

    private final Game game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private TextField txtUsername, txtPassword, txtPasswordRepeat;
    private Stage stage;
    private OrthographicCamera camera;


    public RegistrationScreen(final Game game, final AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = assetManager.get("skin/uiskin.json");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label lblTitle = new Label("Register", skin);
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

        table.add(new Label("password repeat:", skin));
        txtPasswordRepeat = new TextField("", skin);
        txtPasswordRepeat.setPasswordMode(true);
        txtPasswordRepeat.setPasswordCharacter('*');
        table.add(txtPasswordRepeat).minWidth(250).pad(10);
        table.row().pad(20);

        TextButton btnRegister = new TextButton("Register", skin);
        btnRegister.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (txtPassword.equals(txtPassword)) {
                    new Account(txtUsername.getText(), txtPassword.getText());
                    game.setScreen(new PickingScreen(game, assetManager));
                }
            }
        });
        TextButton btnBack = new TextButton("Back", skin);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PickingScreen(game, assetManager));
            }
        });

        table.add(btnBack).bottom().right().width(100).height(50);
        table.add(btnRegister).bottom().right().width(100).height(50);
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
