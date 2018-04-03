package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import works.maatwerk.generals.models.Account;
import works.maatwerk.generals.responselisteners.AllGamesResponseListener;
import works.maatwerk.generals.utils.logger.Tag;

import java.io.StringWriter;

/**
 * Created by teund on 26/03/2018.
 */
@SuppressWarnings("SpellCheckingInspection")
class LogInRunnable implements Runnable {
    private final Generals game;
    private final AssetManager assetManager;
private Account account;
    public LogInRunnable(Account account, Generals game, AssetManager assetManager) {
        this.account=account;
        this.game=game;
        this.assetManager=assetManager;
    }

    /**
     * Testing the http functions of libgdx
     */
    private void RestAPI() {
        Gdx.app.debug("Network", "Testing REST API");

        //request to use for future networking
        Net.HttpRequest request = new Net.HttpRequest();

        //post request
        RESTPost(request);
    }



    private void RESTPost(Net.HttpRequest request) {
        Gdx.app.debug("Network", "Register REST POST");

        request.setMethod(Net.HttpMethods.POST);
        request.setUrl("http://dev.maatwerk.works/login");
        request.setHeader("Content-Type", "application/json"); //needed so the server knows what to expect ;)

        Json json = getJson();
        //put the object as a string in the request body
        //ok so this is ugly. First getWriter gets a JSonwriter -- we dont want that. Second gets the native java stringwriter.
        request.setContent(json.getWriter().getWriter().toString());
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode()== 200)
                game.setScreen(new PostGameScreen(game, assetManager,"BoxerShort1",150,20,60,false));
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error(Tag.NETWORKING, t.getMessage(), t);
            }

            @Override
            public void cancelled() {
                Gdx.app.error(Tag.NETWORKING, "Cancelled... why ");
            }
        });

        //send!

    }

    private Json getJson() {
        Json json = new Json(JsonWriter.OutputType.json);

        json.setWriter(new StringWriter());

        json.writeObjectStart();
        json.writeValue("username", account.getUsername());
        json.writeValue("uassword", account.getPassword());
        json.writeObjectEnd();
        return json;
    }

    @Override
    public void run() {
        RestAPI();
    }
}