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
		config.allowSoftwareMode = true;
		config.vSyncEnabled = true;

		new LwjglApplication(new Generals(), config);
	}
}
