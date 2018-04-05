package works.maatwerk.generals.networking.messages;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Packet {
    private MessageType type;
    private Message data;
    
    public Packet() {
    }
    
    public Packet(MessageType type, Message data) {
        this.type = type;
        this.data = data;
    }
}