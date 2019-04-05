package vn.gtd.data.expedia.continent.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.gtd.data.expedia.continent.entity.ContinentEntity;

import java.util.concurrent.CompletableFuture;

@RequestMapping(value = "/continents")
public interface ContinentRestApi {
    @GetMapping
    CompletableFuture<Page<ContinentEntity>> findAll(Pageable pageable);

    @GetMapping(path = "{id}")
    CompletableFuture<ContinentEntity> findById(@PathVariable("id") String id);
}
