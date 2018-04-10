package works.maatwerk.generals.networking.messages;

public class Packet {
    private MessageType type;
    private Message data;
    
    /**
     * Default constructor
     */
    public Packet() {
    }
    
    /**
     * 
     * @param type
     * @param data 
     */
    public Packet(MessageType type, Message data) {
        this.type = type;
        this.data = data;
    }
    
    /**
     * 
     * @return 
     */
    public MessageType getType() {
        return type;
    }
    
    /**
     * 
     * @return 
     */
    public Message getData() {
        return data;
    }
    
    /**
     * 
     * @param type 
     */
    public void setType(MessageType type) {
        this.type = type;
    }
    
    /**
     * 
     * @param data 
     */
    public void setData(Message data) {
        this.data = data;
    }
}