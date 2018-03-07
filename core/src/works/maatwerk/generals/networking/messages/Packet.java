package works.maatwerk.generals.networking.messages;

public class Packet {
    public MessageType type;
    public Message data;

    public Packet(MessageType type, Message data) {
        this.type = type;
        this.data = data;
    }

    public Packet() {

    }
}
