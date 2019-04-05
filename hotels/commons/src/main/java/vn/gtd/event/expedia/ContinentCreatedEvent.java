package vn.gtd.event.expedia;

public class ContinentCreatedEvent extends AbstractEvent{
    private final String continentCreator;
    private final double balance;

    public ContinentCreatedEvent(String id, String continentCreator, double balance) {
        super(id);
        this.continentCreator = continentCreator;
        this.balance = balance;
    }

    public String getContinentCreator() {
        return continentCreator;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "ContinentCreatedEvent{" +
                "id='" + super.getId() + '\'' +
                ", accountCreator='" + continentCreator + '\'' +
                ", balance=" + balance +
                '}';
    }
}
