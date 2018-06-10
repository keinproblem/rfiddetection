package connector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class DummyToFileConnector implements ConnectorStrategy {
    final Path filePath;

    public DummyToFileConnector(Path filePath) {
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
