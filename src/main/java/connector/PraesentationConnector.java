package connector;

import connector.presentation.Presentation;
import core.AlertEvent;

import java.util.Optional;

public class PraesentationConnector implements ConnectorStrategy {

    Presentation presentation;

    public PraesentationConnector(){
        presentation = new Presentation();
    }

    @Override
    public ConnectorEvent sendAlertEvent(AlertEvent alertEvent) {
        String imgPath;

        switch (alertEvent.getDirection()){
            case FORWARD:
                imgPath = "src/connector/presentation/OUT.png";
                break;
            case BACKWARD:
                imgPath = "src/connector/presentation/IN.png";
                break;
            default:
                return new ConnectorEvent(ConnectorEvent.StatusCode.ERROR, Optional.empty());
        }
        presentation.updateInformation(imgPath, alertEvent.getEpc().toString(), alertEvent.getDetectionTime().toString());

        return new ConnectorEvent(ConnectorEvent.StatusCode.SUCCESS, Optional.empty());
    }
}
