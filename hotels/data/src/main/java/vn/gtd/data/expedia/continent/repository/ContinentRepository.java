package vn.gtd.data.expedia.continent.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.gtd.data.expedia.continent.entity.ContinentEntity;

public interface ContinentRepository extends MongoRepository<ContinentEntity, String> {
}
