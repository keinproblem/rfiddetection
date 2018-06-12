package connector;

import core.AlertEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Slf4j
public class DummyToFileConnector implements ConnectorStrategy {
    final Path filePath;

    public DummyToFileConnector(Path filePath) {
        log.debug("Constructing DummyToFileConnector");
        this.filePath = filePath;
    }

    @Override
    public ConnectorEvent sendAlertEvent(final AlertEvent alertEvent) {
        try {
            Files.write(filePath, (alertEvent.toString() + '\n').getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return new ConnectorEvent(ConnectorEvent.StatusCode.ERROR, Optional.of(e));
        }
        return new ConnectorEvent(ConnectorEvent.StatusCode.SUCCESS, Optional.empty());
    }
}
