package works.maatwerk.generals.networking.messages.gamestatus;

import works.maatwerk.generals.networking.messages.Message;

public class JoinGameMessage extends Message {
    String gameId;
    String playerId;

    public JoinGameMessage(String gameId, String playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }
}
