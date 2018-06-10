package connector;

import java.util.Optional;

public class ConnectorEvent {
    private StatusCode statusCode;
    private Optional<Throwable> throwable;

    enum StatusCode {
        SUCCESS, ERROR;
    }
}
