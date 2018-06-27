import core.ApiFacade;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This high-level class defines the primary layer of the system logic.
 * Currently only Tag Tracking initialization is provided on this layer.
 * A {@link RfidDetectionService} is supplied with an already set-up {@link ApiFacade} implementation.
 * The service is currently designed to run forever, hence only initialization is provided.
 */
@Slf4j
public class RfidDetectionService {
    private final ApiFacade apiFacade;

    public RfidDetectionService(final ApiFacade apiFacade) {
        this.apiFacade = apiFacade;
    }

    public void init(final String devPath) {
        log.info("Initializing System");
        this.apiFacade.connect(devPath);
        log.debug("Init Tracking");
        this.initTagTracking();
    }

    /*
     * TODO; Implement automated antenna tuning - will be necessary for deployment. should be parametrized in cli args - or some kind of high-level remote command protocol.
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
}
