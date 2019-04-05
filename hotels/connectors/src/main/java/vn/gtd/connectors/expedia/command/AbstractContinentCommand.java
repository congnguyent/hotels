package vn.gtd.connectors.expedia.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class AbstractContinentCommand<ID> {
    @TargetAggregateIdentifier
    private final ID id;

    public AbstractContinentCommand(ID id) {
        this.id = id;
    }

    public ID identifier() {
        return id;
    }
}
