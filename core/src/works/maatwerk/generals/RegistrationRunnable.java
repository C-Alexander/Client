package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import works.maatwerk.generals.models.Account;
import java.io.StringWriter;

/**
 * Created by teund on 26/03/2018.
 */
@SuppressWarnings("SpellCheckingInspection")
class RegistrationRunnable implements Runnable {
    private Account account;
    
    public RegistrationRunnable(Account account) {
        this.account=account;
    }

    /**
     * Testing the http functions of libgdx
     */
    private void restAPI() {
        Gdx.app.debug("Network", "Testing REST API");

        //request to use for future networking
        Net.HttpRequest request = new Net.HttpRequest();

        //post request
        restPost(request);
    }

    private void restPost(Net.HttpRequest request) {
        Gdx.app.debug("Network", "Register REST POST");

        request.setMethod(Net.HttpMethods.POST);
        request.setUrl("http://dev.maatwerk.works/register");
        request.setHeader("Content-Type", "application/json"); //needed so the server knows what to expect ;)

        Json json = getJson();
        //put the object as a string in the request body
        //ok so this is ugly. First getWriter gets a JSonwriter -- we dont want that. Second gets the native java stringwriter.
        request.setContent(json.getWriter().getWriter().toString());

        //send!
        Gdx.net.sendHttpRequest(request, null);
    }

    private Json getJson() {
        Gdx.app.debug("JSON", "Writing JSON objects from scratch");

        //creating a json body to post
        Json json = new Json(JsonWriter.OutputType.json);
        //write to a string
        json.setWriter(new StringWriter());
        //start creating the object
        json.writeObjectStart();
        json.writeValue("username", account.getUsername());
        json.writeValue("uassword", account.getPassword());
        json.writeObjectEnd();
        return json;
    }

    @Override
    public void run() {
        restAPI();
    }
}