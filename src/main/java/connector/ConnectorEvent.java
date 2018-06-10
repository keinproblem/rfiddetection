package connector;

import java.util.Optional;

public class ConnectorEvent {
    private final StatusCode statusCode;
    private final Optional<Throwable> throwable;

    public ConnectorEvent(final StatusCode statusCode, final Optional<Throwable> throwable) {
        this.statusCode = statusCode;
        this.throwable = throwable;
    }

    enum StatusCode {
        SUCCESS, ERROR;
    }
}
