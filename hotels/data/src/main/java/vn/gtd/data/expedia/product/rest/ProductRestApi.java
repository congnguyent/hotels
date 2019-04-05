package vn.gtd.data.expedia.product.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.gtd.data.expedia.product.entity.ProductEntity;

import java.util.concurrent.CompletableFuture;

@RequestMapping(value = "/products")
public interface ProductRestApi {
    @GetMapping
    CompletableFuture<ResponseEntity<Page<ProductEntity>>> findAll(Pageable pageable);

    @GetMapping(path = "{id}")
    CompletableFuture<ResponseEntity<ProductEntity>> findById(@PathVariable("id") String id);
}

