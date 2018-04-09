package works.maatwerk.generals.networking.runnables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import works.maatwerk.generals.AccountManager;
import works.maatwerk.generals.enums.LoginStatus;
import works.maatwerk.generals.utils.Settings;
import works.maatwerk.generals.utils.logger.Tag;

import java.io.StringWriter;

public class GetAccountFromSessionRunnable implements Runnable {

    private final AccountManager accountManager;
    private final int userId;
    private final String sessionId;

    public GetAccountFromSessionRunnable(AccountManager accountManager, int userId, String sessionId) {
        this.accountManager = accountManager;
        this.userId = userId;
        this.sessionId = sessionId;
    }

    @Override
    public void run() {
        Net.HttpRequest request = getHttpRequest();
        Json content = getJson();

        request.setContent(content.getWriter().getWriter().toString());

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                handleResponse(httpResponse);
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error(Tag.ACCOUNT, "Problem verifying the session", t);
            }

            @Override
            public void cancelled() {
                Gdx.app.error("Account", "Unexpected Cancellation");
            }
        });
    }

    private void handleResponse(Net.HttpResponse response) {
        if (response.getStatus().getStatusCode() != HttpStatus.SC_OK) {
            Gdx.app.error(Tag.ACCOUNT, "Strange HTTP Response: " + response.getResultAsString());
            accountManager.setLoginStatus(LoginStatus.LOGGED_OUT);
            return;
        }

        accountManager.setSession(accountManager.getSessionFromResponse(response));

        if (accountManager.hasLegalSession()) accountManager.setLoginStatus(LoginStatus.LOGGED_IN);
        else accountManager.setLoginStatus(LoginStatus.LOGGED_OUT);

    }

    private Net.HttpRequest getHttpRequest() {
        Net.HttpRequest request = new Net.HttpRequest();
        request.setUrl(Settings.getRestUrl() + "/users/getBySession");
        request.setMethod(Net.HttpMethods.POST);
        request.setHeader("Content-Type", "application/json");

        return request;
    }

    private Json getJson() {
        Json json = new Json(JsonWriter.OutputType.json);
        //write to a string
        json.setWriter(new StringWriter());
        //start creating the object
        json.writeObjectStart();
        json.writeValue("sessionId", sessionId);
        json.writeValue("userId", userId);
        json.writeObjectEnd();

        return json;
    }
}
