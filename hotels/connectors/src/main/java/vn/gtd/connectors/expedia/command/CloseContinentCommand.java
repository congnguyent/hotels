package vn.gtd.connectors.expedia.command;

public class CloseContinentCommand extends AbstractContinentCommand<String> {
    public CloseContinentCommand(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return "CloseContinentCommand{" +
                "id='" + super.identifier() + '\'' +
                '}';
    }
}
