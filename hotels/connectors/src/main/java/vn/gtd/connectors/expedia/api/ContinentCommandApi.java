package vn.gtd.connectors.expedia.api;

import org.springframework.web.bind.annotation.*;
import vn.gtd.connectors.expedia.api.request.Continent;

import java.util.concurrent.CompletableFuture;

@RequestMapping(value = "/api/v1/continents")
public interface ContinentCommandApi {
    @PostMapping
    CompletableFuture<String> createContinent(@RequestBody Continent user);

    @PostMapping(path = "{continentId}/balance")
    CompletableFuture deposit(@PathVariable("continentId") String continentId, @RequestBody double amount);

    @DeleteMapping(path = "{continentId}")
    CompletableFuture delete(@PathVariable("continentId") String continentId);
}
