package vn.gtd.connectors.expedia.command.continent;

import vn.gtd.connectors.expedia.command.*;

public enum  ContinentCommandFactory {
    INSTANCE;

    public AbstractContinentCommand<String> newCreateAccountCommand(String id, String creator) {
        return new CreateContinentCommand(id, creator);
    }

    public AbstractContinentCommand<String> newCloseAccountCommand(String id) {
        return new CloseContinentCommand(id);
    }

    public AbstractContinentCommand<String> newDepositMoneyCommand(String id, double amount) {
        return new DepositMoneyCommand(id, amount);
    }
    public AbstractContinentCommand<String> newWithdrawMoneyCommand(String id, double amount) {
        return new WithdrawMoneyCommand(id, amount);
    }
}
