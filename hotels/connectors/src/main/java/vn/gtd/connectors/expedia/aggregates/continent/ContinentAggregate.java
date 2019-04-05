package vn.gtd.connectors.expedia.aggregates.continent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;
import vn.gtd.connectors.expedia.command.CloseContinentCommand;
import vn.gtd.connectors.expedia.command.CreateContinentCommand;
import vn.gtd.connectors.expedia.command.DepositMoneyCommand;
import vn.gtd.connectors.expedia.command.WithdrawMoneyCommand;
import vn.gtd.event.expedia.ContinentCloseEvent;
import vn.gtd.event.expedia.ContinentCreatedEvent;
import vn.gtd.event.expedia.MoneyDepositedEvent;
import vn.gtd.event.expedia.MoneyWithdrawnEvent;

import java.io.Serializable;

@Aggregate
public class ContinentAggregate implements Serializable {
    private static final long serialVersionUID = 1L;

    @AggregateIdentifier
    private String id;

    private double balance;
    private String owner;

    public ContinentAggregate() {
    }

    @CommandHandler
    public ContinentAggregate(CreateContinentCommand command) {
        String id = command.identifier();
        String creator = command.getCreator();

        Assert.hasLength(id, "Missing id");
        Assert.hasLength(creator, "Missing account creator");

        AggregateLifecycle.apply(new ContinentCreatedEvent(id, creator, 0));
    }

    @EventSourcingHandler
    protected void on(ContinentCreatedEvent event) {
        this.id = event.getId();
        this.owner = event.getContinentCreator();
        this.balance = event.getBalance();
    }

    @CommandHandler
    protected void hanlde(CloseContinentCommand command) {
        AggregateLifecycle.apply(new ContinentCloseEvent(id));
    }

    @EventSourcingHandler
    protected void on(ContinentCloseEvent event) {
        AggregateLifecycle.markDeleted();
    }

    @CommandHandler
    protected void handle(DepositMoneyCommand command) {
        Assert.isTrue(command.getAmount() > 0.0, "Deposit must be a positive number.");
        AggregateLifecycle.apply(new MoneyDepositedEvent(id, command.getAmount()));
    }

    @EventSourcingHandler
    protected void on(MoneyDepositedEvent event) {
        this.balance += event.getAmount();
    }

    @CommandHandler
    protected void handle(WithdrawMoneyCommand command) {
        Assert.isTrue(command.getAmount() > 0.0, "Deposit mus be a positive number.");
        if (balance - command.getAmount() < 0) {
            throw new InsufficientBalanceException(String.format("Continent: %s has insufficient balance", id));
        }

        AggregateLifecycle.apply(new MoneyWithdrawnEvent(id, command.getAmount()));
    }

    @EventSourcingHandler
    protected void on(MoneyWithdrawnEvent event) {
        this.balance -= event.getAmount();
    }

    public class InsufficientBalanceException extends RuntimeException {
        public InsufficientBalanceException(String message) {
            super(message);
        }
    }


    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContinentAggregate that = (ContinentAggregate) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", owner='" + owner + '\'' +
                '}';
    }
}
