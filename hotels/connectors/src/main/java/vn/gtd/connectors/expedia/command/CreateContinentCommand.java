package vn.gtd.connectors.expedia.command;

public class CreateContinentCommand extends AbstractContinentCommand<String> {
    private final String creator;

    public CreateContinentCommand(String s, String creator) {
        super(s);
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "CreateContinentCommand{" +
                "id='" + super.identifier()+ '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }
}
