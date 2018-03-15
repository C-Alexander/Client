package works.maatwerk.generals.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import works.maatwerk.generals.Generals;

public class DesktopLauncher {
	public static void main (String[] arg) {
		CommonWebSockets.initiate();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 800;
		config.width = 1200;
		config.samples = 16;
		config.backgroundFPS = 10;
		config.foregroundFPS = 0;
		config.allowSoftwareMode = true;
		config.vSyncEnabled = false;

		new LwjglApplication(new Generals(), config);
	}
}
