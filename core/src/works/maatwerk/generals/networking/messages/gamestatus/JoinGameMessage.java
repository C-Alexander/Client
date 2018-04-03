package works.maatwerk.generals.networking.messages.gamestatus;

import works.maatwerk.generals.networking.messages.Message;

@SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
public class JoinGameMessage extends Message {
    String gameId;
    String playerId;

    public JoinGameMessage(String gameId, String playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }
}