package vn.gtd.data.expedia.continent.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "continents")
public class ContinentEntity {
    @Id
    private String id;
    private double balance;
    private String owner;

    public ContinentEntity() {
    }

    public ContinentEntity(String id, double balance, String owner) {
        this.id = id;
        this.balance = balance;
        this.owner = owner;
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

        ContinentEntity that = (ContinentEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ContinentEntity{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", owner='" + owner + '\'' +
                '}';
    }
}
