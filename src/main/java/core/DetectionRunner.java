package core;

import com.nordicid.nurapi.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class DetectionRunner implements Runnable {
    private final AtomicBoolean isRunning;
    private final ConcurrentHashMap<String, VirtualTrackingTag> virtualTrackingTagConcurrentHashMap;
    private final NurApi nurApi;
    private List<Integer> antennaIdsGateA;
    private List<Integer> antennaIdsGateB;

    private @NonNull
    @Getter
    @Setter
    AlertEventBus alertEventBus;

    @Getter
    @Setter
    private boolean isBeepActive;

    public DetectionRunner(final NurApi nurApi, final TagTrackingParameter tagTrackingParameter, final AlertEventBus alertEventBus) {
        this.antennaIdsGateA = tagTrackingParameter.getAntennaIdsGateA();
        this.antennaIdsGateB = tagTrackingParameter.getAntennaIdsGateB();
        this.nurApi = nurApi;
        this.virtualTrackingTagConcurrentHashMap = new ConcurrentHashMap<>();
        this.alertEventBus = alertEventBus;
        this.isRunning = new AtomicBoolean(false);
        this.isBeepActive = false;
    }


    public AtomicBoolean getIsRunning() {
        return isRunning;
    }

    public void publishAlertEvent(final AlertEvent alertEvent) {
        this.alertEventBus.publish(alertEvent);
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
                if (nurApiException.error == NurApiErrors.TR_NOT_CONNECTED) {
                    //transport is not connected - keep continuing. ApiFacade implementation may establish connection
                    continue;
                }
                if (nurApiException.error == NurApiErrors.TR_TIMEOUT) {
                    //something with the transport is wrong - keep continuing
                    continue;
                }
                if (nurApiException.error == NurApiErrors.NO_TAG) {
                    //no tags detected
                    continue;
                }
            } catch (final Exception exception) {
                //TODO handle separate exception
            }

            for (int n = 0; n < apiStorage.size(); ++n) {
                NurTag tag = apiStorage.get(n);
                int antennaId = tag.getAntennaId();
                byte[] epc = tag.getEpc();
                String epcString = tag.getEpcString();
                if (virtualTrackingTagConcurrentHashMap.containsKey(tag.getEpcString())) {
                    final VirtualTrackingTag tagTrackingEvent = virtualTrackingTagConcurrentHashMap.get(epcString);
                    this.processAndUpdateVirtualTrackingTag(tagTrackingEvent, antennaId);
                } else {
                    final VirtualTrackingTag virtualTrackingTag = new VirtualTrackingTag(epcString, epc);
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
        final Gate gate = fromAntennaId(antennaId);
        switch (gate) {
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
                log.debug(String.format("Received VirtualTrackingTag at not configured antennaId: %d", antennaId));
            }
            break;
            default:
                log.warn(String.format("Received invalid Gate: %s"), gate.name());
        }
    }

    private void performStandardBeep() {
        try {
            this.nurApi.beep();
        } catch (final NurApiException nurApiException) {
            log.warn("Failed performing hardware beep: ", nurApiException);
        } catch (final Exception exception) {
            log.warn("Failed performing hardware beep: ", exception);
        }
    }

    private enum Gate {
        A, B, None
    }

}
