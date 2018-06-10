
public interface ApiDelegate {

    void connect(final String devPath, final int baudRate);

    void connect(final String devPath);

    void initTracking(final TagTrackingParameter tagTrackingParameter);

    boolean isTagTrackingRunning();

    void stopTracking();

    void registerCallbackListener();
}
