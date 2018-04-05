package works.maatwerk.generals;

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

@SuppressWarnings("WeakerAccess")
public class PostGameScreen extends ScreenAdapter {
    private final Stage stage;

    public PostGameScreen(final Generals game, final AssetManager assetManager, String opponentName, Integer expGained, Integer turnsPlayed, Integer movesPlayed, boolean isVictory) {
        this.stage = new Stage();


        Gdx.input.setInputProcessor(stage);
        Skin skin = assetManager.get("skin/uiskin.json");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        Label lblTitle;
        if(isVictory){
            lblTitle = new Label("Victory!", skin);
        }else {
            lblTitle = new Label("Defeat!", skin);
        }
        lblTitle.setFontScale(2);
        table.add(lblTitle).colspan(2).pad(20);
        table.row().pad(20);

        table.add(new Label("Defeated by:", skin));
        Label lblDefeatedBy = new Label(opponentName, skin);
        table.add(lblDefeatedBy).minWidth(250).pad(10);
        table.row().pad(20);

        table.add(new Label("Exp gained:", skin));
        Label lblExp = new Label(expGained.toString(), skin);
        table.add(lblExp).minWidth(250).pad(10);
        table.row().pad(20);

        table.add(new Label("Turns:", skin));
        Label lblTurns = new Label(turnsPlayed.toString() , skin);
        table.add(lblTurns).minWidth(250).pad(10);
        table.row().pad(20);

        table.add(new Label("Moves:", skin));
        Label lblMoves = new Label(movesPlayed.toString() , skin);
        table.add(lblMoves).minWidth(250).pad(10);
        table.row().pad(20);

        TextButton btnContinue = new TextButton("Continue", skin);

        btnContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayingScreen(game, assetManager));
            }

        });

        table.add(btnContinue).bottom().right().width(100).height(50);
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