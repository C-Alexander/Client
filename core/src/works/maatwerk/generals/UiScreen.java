//Project: Client
//Author: J Putters
//Creation date: 25-Mar-18

package works.maatwerk.generals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class UiScreen extends ScreenAdapter {
    private Game game;
    private AssetManager assetManager;
    private SpriteBatch spriteBatch;
    private Stage stage;

    public UiScreen(Game game, AssetManager assetManager){
        this.game = game;
        this.assetManager = assetManager;
        this.spriteBatch = new SpriteBatch();
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Texture texture = assetManager.get("hud/uiBG.png", Texture.class);
        Image image = new Image(texture);

        table.bottom();
        table.add(image);

        stage.addActor(table);
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
