package works.maatwerk.generals.networking.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Timer;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketAdapter;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import works.maatwerk.generals.networking.messages.gamestatus.JoinGameMessage;
import works.maatwerk.generals.networking.messages.MessageType;
import works.maatwerk.generals.networking.messages.Packet;
import works.maatwerk.generals.networking.tasks.KeepAlive;
import works.maatwerk.generals.utils.logger.Tag;
import java.util.Random;

public class GameStatusWebSocketListener extends WebSocketAdapter {
    private final Json json;
    @SuppressWarnings({"FieldCanBeLocal", "CanBeFinal"})
    private boolean endGame = false;
    private Timer.Task keepAliveTask;

    public GameStatusWebSocketListener() {
        json = new Json(JsonWriter.OutputType.json);
        initializeClassTags();
    }

    @Override
    public boolean onOpen(WebSocket webSocket) {
        Gdx.app.log(Tag.NETWORKING, "Websocket connection opened");
        joinGame(webSocket);
        startKeepAlive(webSocket);
        return false;
    }

    private void startKeepAlive(WebSocket webSocket) {
        keepAliveTask = Timer.schedule(new KeepAlive(webSocket), 5, 15);
    }

    @Override
    public boolean onClose(WebSocket webSocket, WebSocketCloseCode code, String reason) {
        Gdx.app.log(Tag.NETWORKING, "Websocket connection closed. Reason: " + reason);
        stopKeepAlive();
        if (!endGame) {
            webSocket.connect();
        }
        return false;
    }

    private void stopKeepAlive() {
        keepAliveTask.cancel();
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) {
        Gdx.app.debug(Tag.NETWORKING, "Got Packet: " + packet);
        return false;
    }

    @Override
    public boolean onError(WebSocket webSocket, Throwable error) {
        Gdx.app.error(Tag.NETWORKING, "Error in the websocket connection", error);
        return false;
    }

    private void joinGame(WebSocket webSocket) {
        if (!webSocket.isOpen()) Gdx.app.error(Tag.NETWORKING, "Tried to join game before websocket was open");
        Gdx.app.debug("JSON", "Creating an object for a JoinGame request");

        //write to a string
        Random rand = new Random();
        JoinGameMessage joinGameMessage = new JoinGameMessage("gameid#1", "player" + rand.nextInt());
        Packet packet = new Packet(MessageType.JOIN_GAME, joinGameMessage);
        Gdx.app.debug(Tag.NETWORKING, "Sending a JoinGame request");

        sendPacket(packet, webSocket);
    }

    private void sendPacket(Packet packet, WebSocket webSocket) {
        String packetText = json.toJson(packet);
        Gdx.app.debug(Tag.NETWORKING, "Sending packet from listener: " + packetText);

        webSocket.send(packetText);
    }

    private void initializeClassTags() {
        json.addClassTag("JoinGameMessage", JoinGameMessage.class);
        json.setTypeName("type");
    }
}