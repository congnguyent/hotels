package vn.gtd.connectors.expedia.api.request;

public class Continent {
    private String name;

    public Continent(String name) {
        this.name = name;
    }

    public Continent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "name='" + name + '\'' +
                '}';
    }
}
