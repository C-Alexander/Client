package works.maatwerk.generals.networking.messages.gamestatus;

import works.maatwerk.generals.networking.messages.Message;

@SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
public class JoinGameMessage extends Message {
    private String gameId;
    private String sessionId;
    private int userId;

    public JoinGameMessage(String gameId, String sessionId, int userId) {
        this.gameId = gameId;
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public JoinGameMessage() {
    }

    ;
}