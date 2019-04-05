package vn.gtd.connectors.expedia.command;

import vn.gtd.event.expedia.AbstractEvent;

public class WithdrawMoneyCommand extends AbstractContinentCommand<String> {
    private final double amount;

    public WithdrawMoneyCommand(String id, double amount) {
        super(id);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "WithdrawMoneyCommand{" +
                "id='" + super.identifier() + '\'' +
                ", amount=" + amount +
                '}';
    }
}
