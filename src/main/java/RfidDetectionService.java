import core.AlertEventListener;
import core.ApiFacade;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RfidDetectionService {
    private final ApiFacade apiFacade;

    public RfidDetectionService(ApiFacade apiFacade) {
        this.apiFacade = apiFacade;
    }

    public void init(final String devPath) {
     	log.debug("Connecting");   
	this.apiFacade.connect(devPath);
	log.debug("Init Tracking");
        this.initTagTracking();
    }

    public void autoTuneReader() {

    }


    private void initTagTracking() {
        this.apiFacade.initTracking();
    }

    public void addExternalAlertEventListener(final AlertEventListener alertEventListener) {
        this.apiFacade.registerAlertEventCallbackListener(alertEventListener);
    }
}
