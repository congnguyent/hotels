package vn.gtd.event.expedia;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContinentCreatedEvent extends AbstractEvent{
    private final String continentCreator;
    private final double balance;

    @JsonCreator
    public ContinentCreatedEvent(@JsonProperty("id") String id,
                                 @JsonProperty("continentCreator") String continentCreator,
                                 @JsonProperty("balance") double balance) {
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
