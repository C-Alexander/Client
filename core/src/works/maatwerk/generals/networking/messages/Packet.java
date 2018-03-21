package works.maatwerk.generals.networking.messages;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Packet {
    public MessageType type;
    @SuppressWarnings("WeakerAccess")
    public Message data;

    public Packet(MessageType type, Message data) {
        this.type = type;
        this.data = data;
    }

    public Packet() {

    }
}
