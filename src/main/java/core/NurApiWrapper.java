package core;

import com.nordicid.nurapi.NurApi;
import com.nordicid.nurapi.NurApiException;
import com.nordicid.nurapi.NurApiSerialTransport;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NurApiWrapper implements ApiFacade {
    private final ExecutorService executorService;
    private final DetectionRunner detectionRunner;
    private final NurApi nurApi;
    private boolean isConnected;

    public NurApiWrapper(final TagTrackingParameter tagTrackingParameter, final int nurApiLogLevel) {
        this.isConnected = false;

        this.nurApi = new NurApi();
        //int nurApiLogLevel = NurApi.LOG_ERROR | NurApi.LOG_USER;//NurApi.LOG_VERBOSE | NurApi.LOG_ERROR | NurApi.LOG_DATA | NurApi.LOG_USER;
        this.nurApi.setLogToStdout(false);
        this.nurApi.setLogLevel(nurApiLogLevel);
        this.nurApi.setListener(new NurApiEventListener());

        //Using one thread for the custom detection runner
        this.executorService = Executors.newSingleThreadExecutor();
        this.detectionRunner = new DetectionRunner(this.nurApi, tagTrackingParameter);
    }


    @Override
    public void connect(final String devPath, final int baudRate) {
        try {
            this.nurApi.setTransport(new NurApiSerialTransport(devPath, baudRate));
            this.nurApi.connect();
            this.isConnected = true;
        } catch (NurApiException ex) {

        } catch (Exception e) {
            this.isConnected = false;
            log.error("Caught: ", e);
            this.kill();
        }
    }

    @Override
    public void connect(final String devPath) {
        this.connect(devPath, NurApi.DEFAULT_BAUDRATE);
    }

    @Override
    public void initTracking() {
        if (isConnected){
            if (!isTagTrackingRunning()){
                this.executorService.submit(this.detectionRunner);
	    }
	}
    }

    @Override
    public boolean isTagTrackingRunning() {
        return this.detectionRunner.getIsRunning().get();
    }

    @Override
    public void stopTracking() {
        //TODO; implement
    }

    @Override
    public void registerAlertEventCallbackListener(AlertEventListener alertEventListener) {
        this.detectionRunner.addAlertEventListener(alertEventListener);
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
