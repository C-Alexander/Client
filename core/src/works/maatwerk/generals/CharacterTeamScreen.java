package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import works.maatwerk.generals.utils.ScreensUtils;

/**
 * Created by samdirkx1 on 10/04/2018.
 */
public class CharacterTeamScreen extends ScreenAdapter {
    private final Stage stage;

    public CharacterTeamScreen(final Generals game, final AssetManager assetManager) {
        this.stage = new Stage();
        Skin skin = assetManager.get("skin/uiskin.json");
        Gdx.input.setInputProcessor(stage);

        Label lblTitle = new Label("CharacterTeam customization", skin);
        lblTitle.setPosition(0,0);
        lblTitle.setFontScale(2);
        stage.getActors().add(lblTitle);

        TextButton btnAdd = new TextButton("buttonAdd", skin);
        btnAdd.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //implement code
            }
        });
        stage.getActors().add(btnAdd);

        TextButton btnRemove = new TextButton("buttonRemove", skin);
        btnAdd.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //implement code
               //exampleTemplate:: new Thread(new AccountRunnable(new Account(txtUsername.getText(), txtPassword.getText()), game, assets, false)).start();

            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreensUtils.render(stage, delta);
    }
}