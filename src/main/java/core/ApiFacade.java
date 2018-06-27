package core;

/**
 * This interface acts as a high-level facade for Hardware API implementations
 */
public interface ApiFacade {

    /**
     * Perform serial connection to hardware.
     *
     * @param devPath  OS File-Descriptor of the RFID Reader Hardware
     * @param baudRate baudrate for serial communication
     */
    void connect(final String devPath, final int baudRate);

    /**
     * Simplification of {@link ApiFacade#connect(String, int)} using default baudrate.
     * @param devPath OS File-Descriptor of the RFID Reader Hardware
     */
    void connect(final String devPath);

    /**
     * Initializes continuous Tag Tracking procedure
     */
    void initTracking();

    /**
     * Check whether Tag Tracking is running or not.
     * @return true if Tag Tracking is running
     */
    boolean isTagTrackingRunning();

    /**
     * Stop the currently running Tag Tracking procedure, if active.
     */
    void stopTracking();

    /**
     * Register a new {@link AlertEventBus} for {@link AlertEvent} delivery.
     *
     * @param alertEventBus the new {@link AlertEventBus} to be registered
     */
    void registerAlertEventBus(final AlertEventBus alertEventBus);
}
