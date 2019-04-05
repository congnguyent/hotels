package vn.gtd.data.expedia.continent.rest.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.gtd.data.expedia.continent.entity.ContinentEntity;
import vn.gtd.data.expedia.continent.repository.ContinentReadOnlyRepository;
import vn.gtd.data.expedia.continent.rest.ContinentRestApi;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
public class ContinentRestApiImpl implements ContinentRestApi {
    private final ContinentReadOnlyRepository repository;

    public ContinentRestApiImpl(ContinentReadOnlyRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Page<ContinentEntity>> findAll(Pageable pageable) {
        try {
            return CompletableFuture.supplyAsync(() -> repository.findAll(pageable));
        } catch (AssertionError | IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public CompletableFuture<ContinentEntity> findById(@PathVariable String id) {
        try {
            Assert.hasLength(id, "Missing continent id");
            return CompletableFuture.supplyAsync(() -> {
                Optional<ContinentEntity> entity = repository.findById(id);
                if (entity.isPresent()) {
                    return entity.get();
                } else {
                    throw new AccountEntityNotFoundException(id);
                }
            });
        } catch (AssertionError | IllegalArgumentException e) {
            throw e;
        }
    }

    @ExceptionHandler({
            AssertionError.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handle(HttpServletRequest request, Throwable e) {
        String badRequestContent = String.format("Request api call {} is bad request", request.getRequestURL());
        return ResponseEntity.badRequest().body(badRequestContent);
    }

    @ExceptionHandler(AccountEntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound(){}

    private class AccountEntityNotFoundException extends RuntimeException {
        public AccountEntityNotFoundException(String id) {
            super(String.format("Account %s not found", id));
        }
    }
}
