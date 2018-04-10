package works.maatwerk.generals.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import works.maatwerk.generals.Generals;
import works.maatwerk.generals.utils.Settings;

public class DesktopLauncher {
    public static void main(String[] args) {
		CommonWebSockets.initiate();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 800;
		config.width = 1200;
		config.samples = 16;
		config.allowSoftwareMode = true;
		config.vSyncEnabled = true;

        config.preferencesDirectory = ".maatwerk/";

        if (args.length > 0 && args[0].equals("production")) {
            Settings.setRestUrl(Settings.REST_PROD_URL);
            Settings.setWsUrl(Settings.WS_PROD_URL);
        } else {
            Settings.setRestUrl(Settings.REST_LOCAL_URL);
            Settings.setWsUrl(Settings.WS_LOCAL_URL);
        }


		new LwjglApplication(new Generals(), config);
	}
}
