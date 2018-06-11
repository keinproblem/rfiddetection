package core;

import com.nordicid.nurapi.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NurApiEventListener implements NurApiListener {
    @Override
    public void logEvent(int loglevel, String content) {
        switch (loglevel) {
            case NurApi.LOG_DATA:
                log.debug(content);
                break;
            case NurApi.LOG_VERBOSE:
                log.trace(content);
                break;
            case NurApi.LOG_USER:
                log.info(content);
                break;
            case NurApi.LOG_ERROR:
                log.error(content);
                break;
            default:
                log.debug(content);
                break;
        }
    }

    @Override
    public void connectedEvent() {
        log.info("Connected to reader.");
    }

    @Override
    public void disconnectedEvent() {
        log.info("Disconnected from reader.");
    }

    @Override
    public void bootEvent(String s) {
        log.info("Booting: " + s);
    }

    @Override
    public void inventoryStreamEvent(NurEventInventory nurEventInventory) {
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
        log.info(String.format("Performing antenna auto antenna: %d cap1: %d cap2: %d freqKhz: %d reflPower_dBm: %d", nurEventAutotune.antenna, nurEventAutotune.cap1, nurEventAutotune.cap2, nurEventAutotune.freqKhz, nurEventAutotune.reflPower_dBm));
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
