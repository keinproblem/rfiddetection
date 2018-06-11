package connector;


import core.AlertEvent;
import core.AlertEventListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlertEventForwarder implements AlertEventListener {
    private final ConnectorStrategy connectorStrategy;

    public AlertEventForwarder(ConnectorStrategy connectorStrategy) {
        this.connectorStrategy = connectorStrategy;
    }

    @Override
    public void onAlert(AlertEvent alertEvent) {
        ConnectorEvent connectorEvent = this.connectorStrategy.sendAlertEvent(alertEvent);
        log.info(connectorEvent.toString());
    }
}
