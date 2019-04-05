package vn.gtd.data.expedia.continent.handler;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vn.gtd.data.expedia.continent.entity.ContinentEntity;
import vn.gtd.data.expedia.continent.repository.ContinentReadOnlyRepository;
import vn.gtd.event.expedia.ContinentCreatedEvent;

@Component
@ProcessingGroup("continentEvents")
public class ContinentEventHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ContinentReadOnlyRepository repository;

    public ContinentEventHandler(ContinentReadOnlyRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(ContinentCreatedEvent event) {
        ContinentEntity createdContinent = repository.save(new ContinentEntity(
                event.getId(),
                event.getBalance(),
                event.getContinentCreator()));

        logger.info("Account {} is saved", createdContinent);
    }

}
