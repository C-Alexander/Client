package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import works.maatwerk.generals.responselisteners.AllGamesResponseListener;
import works.maatwerk.generals.responselisteners.TestSocketListener;
import works.maatwerk.generals.utils.logger.Tag;
import java.io.StringWriter;
import works.maatwerk.generals.models.Stats;

@SuppressWarnings("SpellCheckingInspection")
class NetworkTestsRunnable implements Runnable {

    private void testWebSockets() {
        Gdx.app.debug(Tag.NETWORKING, "Beginning websocket test");
        WebSocket socket = WebSockets.newSocket("ws://52.28.233.213:9000/game");
        socket.setSerializeAsString(true);
        socket.addListener(new TestSocketListener());
        socket.connect();

        Gdx.app.debug("JSON", "Serializing object to json");
        Json json = new Json(JsonWriter.OutputType.json);
        Stats stats = new Stats(1, 2, 3, 4, 5);
        String packet = json.toJson(stats);

        Gdx.app.debug(Tag.NETWORKING, "Sending packet to websocket: " + packet);
        socket.send(packet);
    }

    /**
     * Testing the http functions of libgdx
     */
    private void testRestAPI() {
        Gdx.app.debug(Tag.NETWORKING, "Testing REST API");

        //request to use for future networking
        Net.HttpRequest request = new Net.HttpRequest();
        testRESTGet(request);


        //post request
        testRESTPost(request);
    }

    private void testRESTGet(Net.HttpRequest request) {
        Gdx.app.debug(Tag.NETWORKING, "Testing REST GET");

        //get request
        request.setMethod(Net.HttpMethods.GET);
        request.setUrl("http://52.28.233.213:9000/games");
        Gdx.net.sendHttpRequest(request, new AllGamesResponseListener());
    }

    private void testRESTPost(Net.HttpRequest request) {
        Gdx.app.debug(Tag.NETWORKING, "Testing REST POST");

        request.setMethod(Net.HttpMethods.POST);
        request.setUrl("http://52.28.233.213:9000/games");
        request.setHeader("Content-Type", "application/json"); //needed so the server knows what to expect ;)

        Json json = getTestingJson();
        //put the object as a string in the request body
        //ok so this is ugly. First getWriter gets a JSonwriter -- we dont want that. Second gets the native java stringwriter.
        request.setContent(json.getWriter().getWriter().toString());

        //send!
        Gdx.net.sendHttpRequest(request, null);
    }

    private Json getTestingJson() {
        Gdx.app.debug("JSON", "Writing JSON objects from scratch");

        //creating a json body to post
        Json json = new Json(JsonWriter.OutputType.json);
        //write to a string
        json.setWriter(new StringWriter());
        //start creating the object
        json.writeObjectStart();
        json.writeValue("name", "Game 3");
        json.writeValue("host", "Job's Server (May or May Not be running)");
        json.writeObjectEnd();
        return json;
    }

    @Override
    public void run() {
        testRestAPI();
        testWebSockets();
    }
}