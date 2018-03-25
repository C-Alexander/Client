//Project: Client
//Author: J Putters
//Creation date: 25-Mar-18

package works.maatwerk.generals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class UiScreen extends ScreenAdapter {
    private Game game;
    private AssetManager assetManager;
    private SpriteBatch spriteBatch;

    private Label label;
    private Stage stage;
    private OrthographicCamera orthographicCamera;

    public UiScreen(Game game, AssetManager assetManager){
        this.game = game;
        this.assetManager = assetManager;
        this.spriteBatch = new SpriteBatch();
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        Skin skin = assetManager.get("hud/bg.jpg");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label lblTitle = new Label("Character: Valkyrie", skin);
        lblTitle.setFontScale(2);
        table.add(lblTitle).colspan(2).pad(20);
        table.row().pad(20);
    }

    @Override
    public void show(){
        super.show();
    }

    @Override
    public void render(float delta){
        super.render(delta);

        stage.act(delta);
        stage.draw();
    }
}
