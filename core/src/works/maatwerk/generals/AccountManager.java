package works.maatwerk.generals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import works.maatwerk.generals.enums.LoginStatus;
import works.maatwerk.generals.models.Account;
import works.maatwerk.generals.models.Session;
import works.maatwerk.generals.networking.runnables.GetSessionRunnable;
import works.maatwerk.generals.utils.logger.Tag;

public class AccountManager implements Disposable {
    Session session;
    LoginStatus loginStatus = LoginStatus.LOGGED_OUT;


    public void init() {
        if (!getPreferences().contains("userId")) return;
        if (!getPreferences().contains("sessionId")) return;

        int userId = getPreferences().getInteger("userId");
        String sessionId = getPreferences().getString("sessionId");

        Gdx.app.log(Tag.ACCOUNT, "Checking for existing session...");
        setLoginStatus(LoginStatus.CHECKING_SESSION);
        new Thread(new GetSessionRunnable(this, userId, sessionId)).start();
    }

    @Override
    public void dispose() {
        getPreferences().flush();
    }

    public Session getSessionFromResponse(Net.HttpResponse httpResponse) {
        String responseText = httpResponse.getResultAsString();
        Gdx.app.log(Tag.ACCOUNT, "Got response: " + responseText);

        JsonReader jsonReader = new JsonReader();

        JsonValue body = jsonReader.parse(responseText);


        Session session = new Session();
        session.setId(body.getString("id"));

        Account player = new Account();
        player.setId(body.get("player").getInt("id"));
        player.setUsername(body.get("player").getString("username"));
        player.setPassword(body.get("player").getString("password"));

        session.setPlayer(player);

        return session;
    }

    public boolean hasLegalSession() {
        return getSession().getPlayer().getUsername() != null && !getSession().getPlayer().getUsername().isEmpty();
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Preferences getPreferences() {
        return Gdx.app.getPreferences(Tag.ACCOUNT);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSession(Session session, Boolean saveInPreferences) {
        setSession(session);

        if (saveInPreferences) {
            getPreferences().putString("sessionId", session.getId());
            getPreferences().putInteger("userId", session.getPlayer().getId());
        }
    }
}
