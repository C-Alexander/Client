package works.maatwerk.generals.networking.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Timer;
import com.github.czyzby.websocket.WebSocket;
import works.maatwerk.generals.networking.messages.MessageType;
import works.maatwerk.generals.networking.messages.Packet;
import works.maatwerk.generals.utils.logger.Tag;

@SuppressWarnings({"WeakerAccess", "unused"})
public class KeepAlive extends Timer.Task {
    private final WebSocket webSocket;
    private final String keepAlivePacket;

    @Override
    public void run() {
        Gdx.app.debug(Tag.NETWORKING, "Sending KeepAlive to WebSocket");
        webSocket.send(keepAlivePacket);
    }

    public KeepAlive(WebSocket webSocket) {
        this.webSocket = webSocket;
        Packet packet = new Packet(MessageType.KEEPALIVE, null);
        Json json = new Json(JsonWriter.OutputType.json);
        this.keepAlivePacket = json.toJson(packet);
    }
}