package concurrency.blockingqueue;

/**
 *
 * @author Alexander Bravo
 */
public class Message {
    private final int id;

    public Message(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
