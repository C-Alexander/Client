package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.StringWriter;

/**
 * Created by Bob on 28-3-2018.
 */
public class FindMatchRunnable implements Runnable {
    //    private Session session;
    public FindMatchRunnable() {
    }

    /**
     * Testing the http functions of libgdx
     */
    private void RestAPI() {
        Gdx.app.debug("Network", "Testing REST API");

        //request to use for future networking
        Net.HttpRequest request = new Net.HttpRequest();

        Net.HttpResponseListener response = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.debug("Testing", httpResponse.getResultAsString());
                // Open the game
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        };

        //post request
        RESTPost(request, response);
    }


    private void RESTPost(Net.HttpRequest request, Net.HttpResponseListener response) {

        Gdx.app.debug("Network", "Lobby REST GET");

        request.setMethod(Net.HttpMethods.GET);
        request.setUrl("http://localhost:9000/lobby");
        request.setHeader("Content-Type", "application/json"); //needed so the server knows what to expect ;)

        Json json = getJson();
        //put the object as a string in the request body
        //ok so this is ugly. First getWriter gets a JSonwriter -- we dont want that. Second gets the native java stringwriter.
        request.setContent(json.getWriter().getWriter().toString());

        //send!
        Gdx.net.sendHttpRequest(request, response);

    }

    private Json getJson() {
        Gdx.app.debug("JSON", "Writing JSON objects from scratch");

        //creating a json body to post
        Json json = new Json(JsonWriter.OutputType.json);
        //write to a string
        json.setWriter(new StringWriter());
        //start creating the object
        json.writeObjectStart();
        json.writeValue("SessionId", 101); //replace 0 with session id
        json.writeObjectEnd();
        return json;
    }

    @Override
    public void run() {
        RestAPI();
    }

}
