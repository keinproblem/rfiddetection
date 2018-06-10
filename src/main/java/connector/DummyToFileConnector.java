package connector;

public class DummyToFileConnector implements ConnectorStrategy {
    @Override
    public ConnectorEvent sendAlertEvent(ConnectorEvent connectorEvent) {
        return null;
    }
}
