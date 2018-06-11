package connector;

import core.AlertEvent;

public interface ConnectorStrategy {
    ConnectorEvent sendAlertEvent(final AlertEvent alertEvent);
}
