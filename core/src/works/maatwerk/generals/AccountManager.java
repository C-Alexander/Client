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
import works.maatwerk.generals.networking.runnables.GetAccountFromSessionRunnable;
import works.maatwerk.generals.utils.logger.Tag;

public class AccountManager implements Disposable {
    private static final String PLAYER_IDENTIFIER = "player";
    private static final String USERID_IDENTIFIER = "userId";
    private static final String SESSIONID_IDENTIFIER = "sessionId";
    private Session session;
    private LoginStatus loginStatus = LoginStatus.LOGGED_OUT;

    public void init() {
        if (!getPreferences().contains(USERID_IDENTIFIER)) return;
        if (!getPreferences().contains(SESSIONID_IDENTIFIER)) return;

        int userId = getPreferences().getInteger(USERID_IDENTIFIER);
        String sessionId = getPreferences().getString(SESSIONID_IDENTIFIER);

        Gdx.app.log(Tag.ACCOUNT, "Checking for existing session...");
        setLoginStatus(LoginStatus.CHECKING_SESSION);
        new Thread(new GetAccountFromSessionRunnable(this, userId, sessionId)).start();
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

        Session newSession = new Session();
        newSession.setId(body.getString("id"));

        Account player = new Account();
        player.setId(body.get(PLAYER_IDENTIFIER).getInt("id"));
        player.setUsername(body.get(PLAYER_IDENTIFIER).getString("username"));
        player.setPassword(body.get(PLAYER_IDENTIFIER).getString("password"));

        newSession.setPlayer(player);
        return newSession;
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
            getPreferences().putString(SESSIONID_IDENTIFIER, session.getId());
            getPreferences().putInteger(USERID_IDENTIFIER, session.getPlayer().getId());
        }
    }
}