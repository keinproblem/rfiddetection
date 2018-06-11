package core;

public interface ApiFacade {

    void connect(final String devPath, final int baudRate);

    void connect(final String devPath);

    void initTracking();

    boolean isTagTrackingRunning();

    void stopTracking();

    void registerAlertEventCallbackListener(final AlertEventListener alertEventListener);
}
