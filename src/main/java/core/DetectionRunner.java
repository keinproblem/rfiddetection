package core;

import com.nordicid.nurapi.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class DetectionRunner implements Runnable {
    private final AtomicBoolean isRunning;
    private final ConcurrentHashMap<String, VirtualTrackingTag> virtualTrackingTagConcurrentHashMap;
    private final ConcurrentLinkedQueue<AlertEventListener> alertEventListeners;
    private final NurApi nurApi;
    private List<Integer> antennaIdsGateA;
    private List<Integer> antennaIdsGateB;

    @Getter
    @Setter
    private boolean isBeepActive;

    public DetectionRunner(final NurApi nurApi, final TagTrackingParameter tagTrackingParameter) {
        this.antennaIdsGateA = tagTrackingParameter.getAntennaIdsGateA();
        this.antennaIdsGateB = tagTrackingParameter.getAntennaIdsGateB();
        this.nurApi = nurApi;
        this.virtualTrackingTagConcurrentHashMap = new ConcurrentHashMap<>();
        this.alertEventListeners = new ConcurrentLinkedQueue<>();
        this.isRunning = new AtomicBoolean(false);
        this.isBeepActive = false;
    }


    public AtomicBoolean getIsRunning() {
        return isRunning;
    }

    public void addAlertEventListener(@NonNull final AlertEventListener alertEventListener) {
        this.alertEventListeners.add(alertEventListener);
    }

    public void removeAlertEventListener(@NonNull final AlertEventListener alertEventListener) {
        this.alertEventListeners.remove(alertEventListener);
    }

    public void publishAlertEvent(final AlertEvent alertEvent) {
        this.alertEventListeners.forEach(alertEventListener -> alertEventListener.onAlert(alertEvent));
    }

    private Gate fromAntennaId(final int antennaId) {
        if (this.antennaIdsGateA.contains(antennaId)) {
            return Gate.A;
        } else if (this.antennaIdsGateB.contains(antennaId)) {
            return Gate.B;
        } else {
            return Gate.None;
        }
    }


    @Override
    public void run() {
        final NurTagStorage apiStorage = this.nurApi.getStorage();
        this.isRunning.set(true);
        while (isRunning.get()) {
        /*
            Avoid logging here
         */

            try {
                final NurRespInventory nurRespInventory = nurApi.inventory();
                if (nurRespInventory.numTagsFound > 0) {
                    nurApi.fetchTags(true);
                } else {
                    //No Tags found
                    //place holder
                }
            } catch (final NurApiException nurApiException) {

            } catch (final Exception exception) {
                //TODO; no tags found
            }

            for (int n = 0; n < apiStorage.size(); ++n) {
                NurTag tag = apiStorage.get(n);
                int antennaId = tag.getAntennaId();
                Instant now = Instant.now();
                byte[] epc = tag.getEpc();
                String epcString = tag.getEpcString();
                if (virtualTrackingTagConcurrentHashMap.containsKey(tag.getEpcString())) {
                    final VirtualTrackingTag tagTrackingEvent = virtualTrackingTagConcurrentHashMap.get(epcString);
                    this.processAndUpdateVirtualTrackingTag(tagTrackingEvent, antennaId);
                } else {
                    final VirtualTrackingTag virtualTrackingTag = new VirtualTrackingTag(epcString, epc, now);
                    virtualTrackingTagConcurrentHashMap.put(tag.getEpcString(), virtualTrackingTag);
                }
            }

            apiStorage.clear();

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processAndUpdateVirtualTrackingTag(final VirtualTrackingTag virtualTrackingTag, final int antennaId) {
        switch (fromAntennaId(antennaId)) {
            case A: {
                if (virtualTrackingTag.isPassedGateB()) {
                    if (isBeepActive) {
                        this.performStandardBeep();
                    }
                    final AlertEvent alertEvent = new AlertEvent(AlertEvent.Direction.FORWARD, Instant.now(), virtualTrackingTag.getEpc(), virtualTrackingTag.getEpcString());
                    log.info(alertEvent.toString());
                    this.publishAlertEvent(alertEvent);
                }
                virtualTrackingTag.setPassedGateA(true);
                virtualTrackingTag.setPassedGateB(false);
            }
            break;
            case B: {
                if (virtualTrackingTag.isPassedGateA()) {
                    if (isBeepActive) {
                        this.performStandardBeep();
                    }
                    final AlertEvent alertEvent = new AlertEvent(AlertEvent.Direction.BACKWARD, Instant.now(), virtualTrackingTag.getEpc(), virtualTrackingTag.getEpcString());
                    log.info(alertEvent.toString());
                    this.publishAlertEvent(alertEvent);
                }
                virtualTrackingTag.setPassedGateB(true);
                virtualTrackingTag.setPassedGateA(false);
            }
            break;
            case None: {
                //TODO; handle this
                log.debug("Received ");
            }
            break;
            default:

        }
    }

    private void performStandardBeep() {
        try {
            this.nurApi.beep();
        } catch (final NurApiException nurApiException) {
            log.warn("Failed beeping: ", nurApiException);
        } catch (final Exception exception) {
            log.warn("Failed beeping: ", exception);
        }
    }

    private enum Gate {
        A, B, None
    }

}
