import core.AlertEventListener;
import core.ApiFacade;

public class RfidDetectionService {
    private final ApiFacade apiFacade;

    public RfidDetectionService(ApiFacade apiFacade) {
        this.apiFacade = apiFacade;
    }

    public void init(final String devPath) {
        this.apiFacade.connect(devPath);
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
