package connector;

import connector.presentation.Presentation;
import core.AlertEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class PresentationConnector implements ConnectorStrategy {

    private final Presentation presentation;

    public PresentationConnector() {
        log.debug("Constructing PresentationConnector");
        presentation = new Presentation();
    }

    @Override
    public ConnectorEvent sendAlertEvent(AlertEvent alertEvent) {
	boolean isIn;
        switch (alertEvent.getDirection()){
            case FORWARD:
                isIn = true;
                break;
            case BACKWARD:
                isIn = false;
                break;
            default:
                return new ConnectorEvent(ConnectorEvent.StatusCode.ERROR, Optional.empty());
        }
        presentation.updateInformation(isIn, alertEvent.getEpcString() , alertEvent.getDetectionTime().toString());

        return new ConnectorEvent(ConnectorEvent.StatusCode.SUCCESS, Optional.empty());
    }
}
