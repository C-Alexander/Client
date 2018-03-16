package works.maatwerk.generals;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import works.maatwerk.generals.Generals;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CommonWebSockets.initiate();
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Generals(), config);
    }
}
