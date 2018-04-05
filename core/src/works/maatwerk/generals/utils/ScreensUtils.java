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
    private static final String EMPTY = "";
    private static final String REGISTER = "";
    
    private ScreensUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    public static void render(Stage stage, float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    
    public static Stage createStage(String title, final Generals game, final AssetManager assets) {
        Skin skin = assets.get("skin/uiskin.json");
        Stage stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = createTable(skin);
        createTitle(title, skin, table);
        
        final TextField txtUsername = createTextField(EMPTY, skin, false);
        inputFieldInTable("username", skin, table, txtUsername);
        
        final TextField txtPassword = createTextField(EMPTY, skin, true);
        inputFieldInTable("password:", skin, table, txtPassword);
        
        TextButton btnLeft = new TextButton(EMPTY, skin);
        TextButton btnRight = new TextButton(EMPTY, skin);
        
        if(REGISTER.equals(title)) {
            final TextField txtPasswordRepeat = createTextField(EMPTY, skin, true);
            inputFieldInTable("password repeat:", skin, table, txtPasswordRepeat);
            
            createLeftButton(btnLeft, true, game, assets);
            
            btnRight.setText(REGISTER);
            btnRight.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (txtPassword.getText().equals(txtPasswordRepeat.getText())) {
                        new Thread(new AccountRunnable(new Account(txtUsername.getText(), txtPassword.getText()), game, assets, false)).start();
                    }
                }
            });
        } else {
            createLeftButton(btnLeft, false, game, assets);
            
            btnRight.setText("Log in");
            btnRight.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    new Thread(new AccountRunnable(new Account(txtUsername.getText(), txtPassword.getText()), game, assets, true)).start();
                }
            });
        }
        
        table.add(btnLeft).bottom().right().width(100).height(50);
        table.add(btnRight).bottom().right().width(100).height(50);
        table.row();
        stage.addActor(table);
        return stage;
    }
    
    private static Table createTable(Skin skin) {
        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return table;
    }
    
    private static void createTitle(String title, Skin skin, Table table) {
        Label lblTitle = new Label(title, skin);
        lblTitle.setFontScale(2);
        table.add(lblTitle).colspan(2).pad(20);
        table.row().pad(20);
    }
    
    private static TextField createTextField(String text, Skin skin, boolean password) {
        TextField field = new TextField(text, skin);
        if(password) {
            field.setPasswordMode(true);
            field.setPasswordCharacter('*');
        }
        return field;
    }
    
    private static void inputFieldInTable(String label, Skin skin, Table table, TextField field) {
        table.add(new Label(label, skin));
        table.add(field).minWidth(250).pad(10);
        table.row().pad(20);
    }
    
    private static void createLeftButton(TextButton btnLeft, final boolean registerPage, final Generals game, final AssetManager assets) {
        btnLeft.setText(registerPage ? "Back" : "Register");
        btnLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(registerPage ? new LogInScreen(game, assets) : new RegistrationScreen(game, assets));
            }
        });
    }
}
