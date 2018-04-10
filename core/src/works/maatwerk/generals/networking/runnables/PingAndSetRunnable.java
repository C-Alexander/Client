package works.maatwerk.generals.networking.runnables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import works.maatwerk.generals.utils.Settings;
import works.maatwerk.generals.utils.logger.Tag;

public class PingAndSetRunnable implements Runnable {
    @Override
    public void run() {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(Settings.REST_LOCAL_URL).build();
        Gdx.net.sendHttpRequest(httpRequest, new MyHttpResponseListener());
    }

    private class MyHttpResponseListener implements Net.HttpResponseListener {
        @Override
        public void handleHttpResponse(Net.HttpResponse httpResponse) {
        }

        @Override
        public void failed(Throwable t) {
            Settings.setRestUrl(Settings.REST_DEV_URL);
            Settings.setWsUrl(Settings.WS_DEV_URL);
        }

        @Override
        public void cancelled() {
            Gdx.app.error(Tag.NETWORKING, "Server ping... Cancelled?!");

        }
    }
}
