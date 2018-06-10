package connector;

public interface ConnectorStrategy {
    ConnectorEvent sendAlertEvent(final AlertEvent alertEvent);
}
