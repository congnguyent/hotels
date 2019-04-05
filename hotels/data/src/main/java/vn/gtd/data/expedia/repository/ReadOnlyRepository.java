package vn.gtd.data.expedia.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {
    @Override
    Page<T> findAll(Pageable pageable);

    @Override
    Optional<T> findById(ID id);

    @Override
    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);
}
