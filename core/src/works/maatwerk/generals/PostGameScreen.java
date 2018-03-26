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

public class PostGameScreen extends ScreenAdapter {

    private final Game game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private Stage stage;
    private OrthographicCamera camera;
    private String opponentName;
    private int expGained;
    private int turnsPlayed;
    private int movesPlayed;
    private boolean isVictory;




    public PostGameScreen(final Game game, final AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        opponentName = "Boxershort01";
        isVictory = true;
        Gdx.input.setInputProcessor(stage);
        Skin skin = assetManager.get("skin/uiskin.json");

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label lblTitle = new Label("Victory!", skin);
        lblTitle.setFontScale(2);
        table.add(lblTitle).colspan(2).pad(20);
        table.row().pad(20);

        table.add(new Label("Defeated by:", skin));
        Label lblDefeatedBy = new Label(opponentName, skin);
        table.add(lblDefeatedBy).minWidth(250).pad(10);
        table.row().pad(20);

        table.add(new Label("Exp gained:", skin));
        Label lblExp = new Label("150", skin);
        table.add(lblExp).minWidth(250).pad(10);
        table.row().pad(20);

        table.add(new Label("Turns:", skin));
        Label lblTurns = new Label("13" , skin);
        table.add(lblTurns).minWidth(250).pad(10);
        table.row().pad(20);

        table.add(new Label("Moves:", skin));
        Label lblMoves = new Label("100" , skin);
        table.add(lblMoves).minWidth(250).pad(10);
        table.row().pad(20);

        TextButton btnContinue = new TextButton("Continue", skin);

        btnContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                    game.setScreen(new PlayingScreen(assetManager));
                }

        });

        table.add(btnContinue).bottom().right().width(100).height(50);
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
