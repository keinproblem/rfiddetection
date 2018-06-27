package core;

import com.nordicid.nurapi.NurApi;
import com.nordicid.nurapi.NurApiException;
import com.nordicid.nurapi.NurApiSerialTransport;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * {@inheritDoc}
 */
@Slf4j
public class NurApiWrapper implements ApiFacade {
    private final ExecutorService executorService;
    private final DetectionRunner detectionRunner;
    private final NurApi nurApi;
    private boolean isConnected;
    private boolean isShutdown;

    public NurApiWrapper(final TagTrackingParameter tagTrackingParameter, final int nurApiLogLevel, final AlertEventBus alertEventBus) {
        this.isConnected = false;
        this.isShutdown = false;

        this.nurApi = new NurApi();
        this.nurApi.setLogToStdout(false);
        this.nurApi.setLogLevel(nurApiLogLevel);
        this.nurApi.setListener(new CustomNurApiEventListener());

        //Using one thread for the custom detection runner
        this.executorService = Executors.newSingleThreadExecutor();
        this.detectionRunner = new DetectionRunner(this.nurApi, tagTrackingParameter, alertEventBus);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(final String devPath, final int baudRate) {
        try {
            log.info("Connecting to reader.");
            this.nurApi.setTransport(new NurApiSerialTransport(devPath, baudRate));
            this.nurApi.connect();
            this.isConnected = true;
            log.info(String.format("Connected to reader: %s", devPath));
        } catch (NurApiException ex) {
            this.isConnected = false;
            log.error("Caught: ", ex);
            this.kill();
        } catch (Exception e) {
            this.isConnected = false;
            log.error("Caught: ", e);
            this.kill();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(final String devPath) {
        this.connect(devPath, NurApi.DEFAULT_BAUDRATE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initTracking() {
        if (isConnected) {
            if (!isTagTrackingRunning()) {
                log.info("Submitting new TagTracking job.");
                this.executorService.submit(this.detectionRunner);
            } else {
                log.warn("TagTracking is already running!");
            }
        } else {
            log.warn("System not connected to reader!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTagTrackingRunning() {
        return this.detectionRunner.getIsRunning().get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopTracking() {
        //TODO; implement
        throw new NotImplementedException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerAlertEventBus(final AlertEventBus alertEventBus) {
        this.detectionRunner.setAlertEventBus(alertEventBus);
    }

    private void kill() {
        try {
            log.info("Disconnecting from reader");
            this.nurApi.disconnect();
            log.debug("Gracefully shutting down");
            this.executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (NurApiException nurApiException) {
            log.error("NurApiError: ", nurApiException);
        } catch (InterruptedException interruptedException) {
            log.error("Interrupted while graceful shutdown: ", interruptedException);

        } catch (Exception exception) {
            log.error("Error while disconnecting: ", exception);
        }
        log.debug("Disposing NurApi Instance");
        this.nurApi.dispose();
        this.isShutdown = true;
    }
}
