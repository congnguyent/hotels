package vn.gtd.connectors.expedia.api.impl;

import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.gtd.connectors.expedia.api.ContinentCommandApi;
import vn.gtd.connectors.expedia.api.request.Continent;
import vn.gtd.connectors.expedia.command.continent.ContinentCommandFactory;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class ContinentCommandApiImpl implements ContinentCommandApi {
    private static final Logger logger = LoggerFactory.getLogger(ContinentCommandApiImpl.class);
    private final CommandGateway commandGateway;
    private final ContinentCommandFactory commandFactory = ContinentCommandFactory.INSTANCE;

    public ContinentCommandApiImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> createContinent(@RequestBody Continent user) {
        Assert.hasLength(user.getName(), "Missing continent creator");
        String continentId = UUID.randomUUID().toString();
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() ->
                commandGateway.sendAndWait(commandFactory.newCreateAccountCommand(continentId, user.getName())));
        return completableFuture
                .thenApply(s -> s)
                .exceptionally(e -> {
                    logger.warn("Create Continent {} failed", user);
                    throw new RuntimeException(String.format("Create Continent %s failed", user), e);
                });
    }

    @Override
    public CompletableFuture deposit(@PathVariable String continentId, @RequestBody double amount) {
        try {
            CompletableFuture<ResponseEntity> completableFuture;
            if (amount > 0.0) {
                completableFuture = commandGateway.sendAndWait(commandFactory.newDepositMoneyCommand(continentId, amount));
            } else {
                completableFuture = commandGateway.sendAndWait(commandFactory.newWithdrawMoneyCommand(continentId, -amount));
            }
            return completableFuture.thenApply(s -> ResponseEntity.ok().build());
        } catch (AssertionError | IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        } catch (CommandExecutionException cex) {
            return CompletableFuture.completedFuture(new ResponseEntity(HttpStatus.CONFLICT));
        }
    }

    @Override
    public CompletableFuture delete(String continentId) {
        try {
            return commandGateway.sendAndWait(commandFactory.newCloseAccountCommand(continentId));
        } catch (AssertionError | IllegalArgumentException e) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
        } catch (CommandExecutionException cex) {
            return CompletableFuture.completedFuture(new ResponseEntity(HttpStatus.CONFLICT));
        }
    }
}
