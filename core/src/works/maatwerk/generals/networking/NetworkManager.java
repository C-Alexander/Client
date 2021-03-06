package works.maatwerk.generals.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;
import works.maatwerk.generals.Generals;
import works.maatwerk.generals.networking.listeners.GameStatusWebSocketListener;
import works.maatwerk.generals.networking.messages.Packet;
import works.maatwerk.generals.utils.Settings;
import works.maatwerk.generals.utils.logger.Tag;

public class NetworkManager {
    private final Json json;
    private WebSocket webSocket;
    private Generals game;

    public NetworkManager(Generals game) {
        this.game = game;
        json = new Json(JsonWriter.OutputType.json);
    }

    public void connect() {
        Gdx.app.debug(Tag.NETWORKING, "Connecting by Websocket");

        webSocket = WebSockets.newSocket(Settings.getWsUrl() + "/game");
        webSocket.setSerializeAsString(true);
        webSocket.addListener(new GameStatusWebSocketListener(game));
        webSocket.setSendGracefully(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                webSocket.connect();
            }
        }).start();
    }

    public boolean isConnected() {
        return webSocket.isOpen();
    }

    public void sendPacket(Packet packet) {
        String packetText = json.toJson(packet);
        Gdx.app.debug(Tag.NETWORKING, "Sending packet: " + packetText);
        webSocket.send(packetText);
    }
}