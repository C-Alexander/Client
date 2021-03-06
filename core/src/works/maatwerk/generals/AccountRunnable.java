package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import net.dermetfan.utils.Pair;
import works.maatwerk.generals.models.Account;
import works.maatwerk.generals.models.Session;
import works.maatwerk.generals.utils.Settings;
import works.maatwerk.generals.utils.logger.Tag;

import java.io.StringWriter;

/**
 * Created by teund on 26/03/2018.
 */
@SuppressWarnings("SpellCheckingInspection")
public class AccountRunnable implements Runnable {
    private static final String URL_LOGIN = Settings.getRestUrl() + "/login";
    private static final String URL_REGISTER = Settings.getRestUrl() + "/register";
    private Generals game = new Generals();
    private AssetManager assetManager = new AssetManager();
    private boolean isLoggingIn;
    private Account account;
    
    public AccountRunnable(Account account, boolean isLoggingIn){
        this.account = account;
        this.isLoggingIn = isLoggingIn;
    }

    public AccountRunnable(Account account, Generals game, AssetManager assetManager, boolean isLoggingIn) {
        this.account = account;
        this.game = game;
        this.assetManager = assetManager;
        this.isLoggingIn = isLoggingIn;
    }

    /**
     * Testing the http functions of libgdx
     */
    private void restAPI() {
        if (isLoggingIn){
            restPostLogin(getHttpRequest(URL_LOGIN, Net.HttpMethods.POST, new Pair<>("Content-Type", "application/json")));
        }else{
            restPostRegister(getHttpRequest(URL_REGISTER, Net.HttpMethods.POST, new Pair<>("Content-Type", "application/json")));
        }
    }

    private Net.HttpRequest getHttpRequest(String url, String method, Pair<String, String> contentType){
        Net.HttpRequest request = new Net.HttpRequest();
        request.setUrl(url);
        request.setMethod(method);
        request.setHeader(contentType.getKey(), contentType.getValue());
        return request;
    }

    private void restPostLogin(Net.HttpRequest request) {
        Json json = getJson();
        request.setContent(json.getWriter().getWriter().toString());
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(final Net.HttpResponse httpResponse) {
                setSessionFromResponse(httpResponse);
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
    }

    private void setSessionFromResponse(Net.HttpResponse httpResponse) {
        if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            Session session = game.getAccountManager().getSessionFromResponse(httpResponse);

            if (session == null) {
                Gdx.app.error(Tag.ACCOUNT, "Failed to deserialize");
            } else game.getAccountManager().setSession(session, true);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new PlayingScreen(game, assetManager));
                }
            });
        } else {
            Gdx.app.log(Tag.NETWORKING, "Bad Status Code from Login Attempt: " + httpResponse.getStatus().getStatusCode()
                    + " response: \n" + httpResponse.getResultAsString());
        }
    }

    private void restPostRegister(Net.HttpRequest request) {
        Json json = getJson();
        //put the object as a string in the request body
        //ok so this is ugly. First getWriter gets a JSonwriter -- we dont want that. Second gets the native java stringwriter.
        request.setContent(json.getWriter().getWriter().toString());

        //send!
        Gdx.net.sendHttpRequest(request, null);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new LogInScreen(game, assetManager));
            }
        });
    }

    private Json getJson() {
        Json json = new Json(JsonWriter.OutputType.json);
        json.setWriter(new StringWriter());
        json.writeObjectStart();
        json.writeValue("username", account.getUsername());
        json.writeValue("password", account.getPassword());
        json.writeObjectEnd();
        return json;
    }

    @Override
    public void run() {
        restAPI();
    }
}