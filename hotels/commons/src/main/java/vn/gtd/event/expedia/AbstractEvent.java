package vn.gtd.event.expedia;

public class AbstractEvent {
    private final String id;

    public AbstractEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
