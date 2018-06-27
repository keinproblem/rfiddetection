package connector;

import core.AlertEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

/**
 * This class represents the result feedback of an {@link ConnectorStrategy} implementation's operation for a specific {@link AlertEvent}.
 * A {@link ConnectorEvent} is always associated with a {@link AlertEvent} for which it was initialized.
 * Optionally/in case of failure a throwable may be carried within a {@link ConnectorEvent}.
 * Objects of {@link ConnectorEvent} are immutable and may only be used for further processing.
 */
@ToString
@EqualsAndHashCode
@Getter
public class ConnectorEvent {
    private final StatusCode statusCode;
    private final Optional<Throwable> throwable;
    private final AlertEvent alertEvent;

    public ConnectorEvent(final StatusCode statusCode, final Optional<Throwable> throwable, final AlertEvent alertEvent) {
        this.statusCode = statusCode;
        this.throwable = throwable;
        this.alertEvent = alertEvent;
    }

    enum StatusCode {
        SUCCESS, ERROR
    }
}
