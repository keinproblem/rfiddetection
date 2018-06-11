package core;

import com.nordicid.nurapi.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ReaderEventListener implements NurApiListener {
    private final NurTagStorage nurTagStorage;
    private final NurApi nurApi;
    private AtomicLong atomicLong = new AtomicLong();

    public ReaderEventListener(NurTagStorage nurTagStorage, NurApi nurApi) {
        this.nurTagStorage = nurTagStorage;
        this.nurApi = nurApi;
    }


    @Override
    public void logEvent(int i, String s) {
        switch (i) {
            case NurApi.LOG_DATA:
                log.debug(s);
                break;
            case NurApi.LOG_VERBOSE:
                log.trace(s);
                break;
            case NurApi.LOG_USER:
                log.info(s);
                break;
            case NurApi.LOG_ERROR:
                log.error(s);
                break;
            default:
                log.debug(s);
                break;
        }
    }

    @Override
    public void connectedEvent() {
        log.info("Connected to reader.");
    }

    @Override
    public void disconnectedEvent() {
        log.info("Disconnected to reader.");
    }

    @Override
    public void bootEvent(String s) {
        log.info("Booting: " + s);
    }

    @Override
    public void inventoryStreamEvent(NurEventInventory nurEventInventory) {
        log.info("tags added: " + nurEventInventory.tagsAdded);
        /*log.info("stopped: " + nurEventInventory.stopped);
        synchronized (this.nurTagStorage) {
            // Add inventoried tags to our unique tag storage
            for (int n=0; n<this.nurTagStorage.size(); n++) {
                NurTag tag = this.nurTagStorage.get(n);
                log.info(String.format("tag[%d] \t EPC '%s' \t RSSI %d \t antennaID: [%d] \t physicalAntenna: [%s] \t FREQ: %d \t TIME: %d", n, tag.getEpcString(), tag.getRssi(), tag.getAntennaId(), tag.getPhysicalAntenna(),  tag.getFreq(), tag.getTimestamp()));
            }
        }
        atomicLong.incrementAndGet();
        if (nurEventInventory.stopped) {
            try {
                System.out.println("Restarting stream");
                this.nurApi.startInventoryStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        log.info("--------------------------------------------------------------------------------------------------");
    }

    @Override
    public void IOChangeEvent(NurEventIOChange nurEventIOChange) {

    }

    @Override
    public void traceTagEvent(NurEventTraceTag nurEventTraceTag) {
        log.info(String.format("Receiving NurEventTraceTag with  antennaId: %d epcStr: %s rssi: %d", nurEventTraceTag.antennaId, nurEventTraceTag.epcStr, nurEventTraceTag.rssi));
    }

    @Override
    public void triggeredReadEvent(NurEventTriggeredRead nurEventTriggeredRead) {

    }

    @Override
    public void frequencyHopEvent(NurEventFrequencyHop nurEventFrequencyHop) {

    }

    @Override
    public void debugMessageEvent(String s) {
        log.debug(s);
    }

    @Override
    public void inventoryExtendedStreamEvent(NurEventInventory nurEventInventory) {

    }

    @Override
    public void programmingProgressEvent(NurEventProgrammingProgress nurEventProgrammingProgress) {

    }

    @Override
    public void deviceSearchEvent(NurEventDeviceInfo nurEventDeviceInfo) {

    }

    @Override
    public void clientConnectedEvent(NurEventClientInfo nurEventClientInfo) {

    }

    @Override
    public void clientDisconnectedEvent(NurEventClientInfo nurEventClientInfo) {

    }

    @Override
    public void nxpEasAlarmEvent(NurEventNxpAlarm nurEventNxpAlarm) {

    }

    @Override
    public void epcEnumEvent(NurEventEpcEnum nurEventEpcEnum) {

    }

    @Override
    public void autotuneEvent(NurEventAutotune nurEventAutotune) {

    }

    @Override
    public void tagTrackingScanEvent(NurEventTagTrackingData nurEventTagTrackingData) {
        log.info(String.format("Receiving NurEventTagTrackingData with  started: %b antennaMask: %s", nurEventTagTrackingData.started, Integer.toBinaryString(nurEventTagTrackingData.antennaMask)));
    }

    @Override
    public void tagTrackingChangeEvent(NurEventTagTrackingChange nurEventTagTrackingChange) {
        log.info(String.format("Receiving NurEventTagTrackingChange with  stopped: %b readSource: %d changedCount: %d changedEventMask: %d", nurEventTagTrackingChange.stopped, nurEventTagTrackingChange.readSource, nurEventTagTrackingChange.changedCount, nurEventTagTrackingChange.changedEventMask));
    }
}
