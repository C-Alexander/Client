package works.maatwerk.generals.responselisteners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import works.maatwerk.generals.utils.logger.Tag;

public class AllGamesResponseListener implements Net.HttpResponseListener {

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        Gdx.app.log(Tag.NETWORKING, httpResponse.toString());

        JsonValue json = new JsonReader().parse(httpResponse.getResultAsString());

        Gdx.app.log("JSON", "Name: " + json.child().getString("name"));

        Gdx.app.log("JSON", "Second Host: " + json.child().next().getString("host"));
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.error(Tag.NETWORKING, t.getMessage(), t);
    }

    @Override
    public void cancelled() {
        Gdx.app.error(Tag.NETWORKING, "Cancelled... why ");
    }
}