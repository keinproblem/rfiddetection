package core;

import com.nordicid.nurapi.NurApi;
import com.nordicid.nurapi.NurApiSerialTransport;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NurApiWrapper implements ApiFacade {
    private final ExecutorService executorService;
    private final CustomTagTracking customTagTracking;
    private NurApi nurApi;
    private boolean isConnected;

    public NurApiWrapper(final TagTrackingParameter tagTrackingParameter) {
        this.isConnected = false;
        this.executorService = Executors.newSingleThreadExecutor();
        this.customTagTracking = new CustomTagTracking(this.nurApi, tagTrackingParameter);

        this.nurApi = new NurApi();
        int nurApiLogLevel = NurApi.LOG_VERBOSE | NurApi.LOG_ERROR | NurApi.LOG_DATA | NurApi.LOG_USER;
        this.nurApi.setLogToStdout(false);
        this.nurApi.setLogLevel(nurApiLogLevel);
        this.nurApi.setListener(new NurApiEventListener());
    }


    @Override
    public void connect(String devPath, int baudRate) {
        try {
            this.nurApi.setTransport(new NurApiSerialTransport(devPath, baudRate));
            this.nurApi.connect();
            this.isConnected = true;
        } catch (Exception e) {
            this.isConnected = false;
            log.error("Caught: ", e);
            this.kill();
        }
    }

    @Override
    public void connect(String devPath) {
        this.connect(devPath, NurApi.DEFAULT_BAUDRATE);
    }

    @Override
    public void initTracking() {
        if (isConnected)
            if (!isTagTrackingRunning())
                this.executorService.submit(this.customTagTracking);
    }

    @Override
    public boolean isTagTrackingRunning() {
        return this.customTagTracking.getIsRunning().get();
    }

    @Override
    public void stopTracking() {
        //TODO; implement
    }

    @Override
    public void registerAlertEventCallbackListener(AlertEventListener alertEventListener) {
        this.customTagTracking.addAlertEventListener(alertEventListener);
    }

    private void kill() {
        try {
            log.info("Disconnecting from reader");
            this.nurApi.disconnect();
            log.debug("Gracefully shutting down");
            this.executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            log.error("Interrupted while graceful shutdown: ", ie);

        } catch (Exception e) {
            log.error("Error while disconnecting: ", e);
        }
        log.debug("Disposing NurApi Instance");
        this.nurApi.dispose();
    }
}
