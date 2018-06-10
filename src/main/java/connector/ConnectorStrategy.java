package connector;

public interface ConnectorStrategy {
    ConnectorEvent sendAlertEvent(final ConnectorEvent connectorEvent);
}
