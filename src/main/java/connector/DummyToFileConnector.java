package connector;

import core.AlertEvent;
import io.reactivex.functions.Consumer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

/**
 * An example implementation of a {@link ConnectorStrategy}
 * This Strategy persists consumed {@link AlertEvent}s into a File.
 */
@Slf4j
public class DummyToFileConnector implements ConnectorStrategy {
    private final Path filePath;

    /**
     * Constructs the implementation.
     *
     * @param filePath the Path of  the File to be used for persisting.
     */
    public DummyToFileConnector(final Path filePath) {
        this.filePath = filePath;
        log.debug("DummyToFileConnector created!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Consumer<AlertEvent> provideAlertEventConsumer() {
        return new Consumer<AlertEvent>() {
            @Override
            public void accept(final AlertEvent alertEvent) {
                processAlertEvent(alertEvent);
            }
        };
    }

    private void processAlertEvent(final AlertEvent alertEvent){
        try {
            Files.write(filePath, (alertEvent.toString() + '\n').getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            final ConnectorEvent connectorEvent = new ConnectorEvent(ConnectorEvent.StatusCode.ERROR, Optional.of(e), alertEvent);
            log.error(connectorEvent.toString(), e);
        }
        final ConnectorEvent connectorEvent = new ConnectorEvent(ConnectorEvent.StatusCode.SUCCESS, Optional.empty(), alertEvent);
        log.info(connectorEvent.toString());

    }
}
