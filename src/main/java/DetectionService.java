import connector.ConnectorStrategy;

import java.util.EventListener;

public class DetectionService {
    private final ConnectorStrategy connectorStrategy;
    private final ApiDelegate apiDelegate;
    private final Configurator configurator;

    public DetectionService(ConnectorStrategy connectorStrategy, ApiDelegate apiDelegate, Configurator configurator) {
        this.connectorStrategy = connectorStrategy;
        this.apiDelegate = apiDelegate;
        this.configurator = configurator;
    }

    public void performAutoTune() {

    }

    public void initTagTracking() {
        this.apiDelegate.initTracking(new TagTrackingParameter());
    }

    public void connect() {
        this.apiDelegate.connect("COM3");
    }

    public boolean isTrackingRunning() {

        return this.apiDelegate.isTagTrackingRunning();
    }

    public void stopTagTracking() {

    }

    public void registerEventListener(final EventListener eventListener) {
        this.apiDelegate.registerCallbackListener();
    }
}
