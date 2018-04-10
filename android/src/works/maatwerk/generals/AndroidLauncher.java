package works.maatwerk.generals;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import works.maatwerk.generals.utils.Settings;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CommonWebSockets.initiate();

        Settings.setRestUrl(Settings.REST_PROD_URL);
        Settings.setWsUrl(Settings.WS_PROD_URL);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Generals(), config);
    }
}
