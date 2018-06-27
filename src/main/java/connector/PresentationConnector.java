package connector;

import connector.presentation.Presentation;
import core.AlertEvent;
import io.reactivex.functions.Consumer;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class PresentationConnector implements ConnectorStrategy {

    private final Presentation presentation;

    public PresentationConnector() {
        log.debug("Constructing PresentationConnector");
        presentation = new Presentation();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Consumer<AlertEvent> provideAlertEventConsumer() {
        return new Consumer<AlertEvent>() {
            @Override
            public void accept(final AlertEvent alertEvent) {
                processAlertEvent(alertEvent);
            }
        };
    }

    private void processAlertEvent(final AlertEvent alertEvent) {
        ConnectorEvent connectorEvent;
        switch (alertEvent.getDirection()){
            case FORWARD:
                presentation.updateInformation(true, alertEvent.getEpcString(), alertEvent.getDetectionTime().toString());
                connectorEvent = new ConnectorEvent(ConnectorEvent.StatusCode.SUCCESS, Optional.empty(), alertEvent);
                break;
            case BACKWARD:
                presentation.updateInformation(false, alertEvent.getEpcString(), alertEvent.getDetectionTime().toString());
                connectorEvent = new ConnectorEvent(ConnectorEvent.StatusCode.SUCCESS, Optional.empty(), alertEvent);
                break;
            default:
                //create error ConnectorEvent and do somethind with it
                connectorEvent = new ConnectorEvent(ConnectorEvent.StatusCode.ERROR, Optional.empty(), alertEvent);
        }

        log.info(connectorEvent.toString());
    }
}
