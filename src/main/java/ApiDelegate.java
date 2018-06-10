
public interface ApiDelegate {
    void initTracking(final TagTrackingParameter tagTrackingParameter);

    void stopTracking();

    void registerCallbackListener();
}
