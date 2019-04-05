package vn.gtd.connectors.expedia.command;

public class DepositMoneyCommand extends AbstractContinentCommand<String> {
    private final double amount;

    public DepositMoneyCommand(String id, double amount) {
        super(id);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "DepositMoneyCommand{" +
                "id='" + super.identifier() + '\'' +
                ", amount=" + amount +
                '}';
    }
}
