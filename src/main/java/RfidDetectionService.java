import core.AlertEventListener;
import core.ApiFacade;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Slf4j
public class RfidDetectionService {
    private final ApiFacade apiFacade;

    public RfidDetectionService(final ApiFacade apiFacade) {
        this.apiFacade = apiFacade;
    }

    public void init(final String devPath) {
        log.debug("Connecting");
        this.apiFacade.connect(devPath);
        log.debug("Init Tracking");
        this.initTagTracking();
    }

    /*
     * TODO; Implement automated antenna tuning
     */
    public void autoTuneReader() {
        throw new NotImplementedException();
    }

    /**
     * Starts Tag Tracking procedure
     */
    private void initTagTracking() {
        this.apiFacade.initTracking();
    }


    /**
     * Add an {@link AlertEventListener} which will receive all {@link core.AlertEvent}s
     *
     * @param alertEventListener
     */
    public void addExternalAlertEventListener(final AlertEventListener alertEventListener) {
        this.apiFacade.registerAlertEventCallbackListener(alertEventListener);
    }
}
