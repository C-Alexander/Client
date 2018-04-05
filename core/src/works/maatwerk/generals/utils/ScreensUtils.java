package works.maatwerk.generals.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import works.maatwerk.generals.AccountRunnable;
import works.maatwerk.generals.Generals;
import works.maatwerk.generals.LogInScreen;
import works.maatwerk.generals.RegistrationScreen;
import works.maatwerk.generals.models.Account;

/**
 *
 * @author Rick Pijnenburg - REXOTIUM
 * @email m.a.a.pijnenburg@gmail.com
 */
public class ScreensUtils {
    
    private ScreensUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    public static Stage createStage(String label, final Generals game, final AssetManager assets) {
        Skin skin = assets.get("skin/uiskin.json");
        Stage stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        Label lblTitle = new Label("Log in", skin);
        lblTitle.setFontScale(2);
        table.add(lblTitle).colspan(2).pad(20);
        table.row().pad(20);
        
        table.add(new Label("username:", skin));
        final TextField txtUsername = new TextField("", skin);
        table.add(txtUsername).minWidth(250).pad(10);
        table.row().pad(20);
        
        table.add(new Label("password:", skin));
        final TextField txtPassword = new TextField("", skin);
        txtPassword.setPasswordMode(true);
        txtPassword.setPasswordCharacter('*');
        table.add(txtPassword).minWidth(250).pad(10);
        table.row().pad(20);
        
        TextButton btnRegister = new TextButton("Register", skin);
        
        if("Register".equals(label)) {
            table.add(new Label("password repeat:", skin));
            final TextField txtPasswordRepeat = new TextField("", skin);
            txtPasswordRepeat.setPasswordMode(true);
            txtPasswordRepeat.setPasswordCharacter('*');
            table.add(txtPasswordRepeat).minWidth(250).pad(10);
            table.row().pad(20);
            btnRegister.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (txtPassword.getText().equals(txtPasswordRepeat.getText())) {
                        Account account = new Account(txtUsername.getText(), txtPassword.getText());
                        new Thread(new AccountRunnable(account, false)).start();
                        game.setScreen(new LogInScreen(game, assets));
                    }
                }
            });
            TextButton btnBack = new TextButton("Back", skin);
            btnBack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new LogInScreen(game, assets));
                }
            });
            table.add(btnBack).bottom().right().width(100).height(50);
            table.add(btnRegister).bottom().right().width(100).height(50);
        } else {
            btnRegister.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new RegistrationScreen(game, assets));
                }
            });
            TextButton btnLogIn = new TextButton("Log in", skin);
            btnLogIn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    new Thread(new AccountRunnable(new Account(txtUsername.getText(), txtPassword.getText()), game, assets, true)).start();
                }
            });

            table.add(btnRegister).bottom().right().width(100).height(50);
            table.add(btnLogIn).bottom().right().width(100).height(50);
        }
        table.row();
        stage.addActor(table);
        return stage;
    }

    public static void render(Stage stage, float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
}
