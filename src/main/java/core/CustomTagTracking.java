package core;

import com.nordicid.nurapi.NurApi;
import com.nordicid.nurapi.NurRespInventory;
import com.nordicid.nurapi.NurTag;
import com.nordicid.nurapi.NurTagStorage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class CustomTagTracking implements Runnable {
    private final AtomicBoolean isRunning;
    private final ConcurrentHashMap<String, VirtualTrackingTag> virtualTrackingTagConcurrentHashMap;
    private final ConcurrentLinkedQueue<AlertEventListener> alertEventListeners;
    private final NurApi nurApi;
    private List<Integer> antennaIdsGateA;
    private List<Integer> antennaIdsGateB;

    public CustomTagTracking(NurApi nurApi, TagTrackingParameter tagTrackingParameter) {
        this.antennaIdsGateA = tagTrackingParameter.getAntennaIdsGateA();
        this.antennaIdsGateB = tagTrackingParameter.getAntennaIdsGateB();
        this.nurApi = nurApi;
        this.virtualTrackingTagConcurrentHashMap = new ConcurrentHashMap<>();
        this.alertEventListeners = new ConcurrentLinkedQueue<>();
        this.isRunning = new AtomicBoolean(false);
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

    public Gate fromAntennaId(final int antennaId) {
        for (int i : this.antennaIdsGateA) {
            if (i == antennaId)
                return Gate.A;
        }
        for (int i : this.antennaIdsGateB) {
            if (i == antennaId)
                return Gate.B;
        }
        return Gate.None;
    }

    @Override
    public void run() {
	
        final NurTagStorage apiStorage = this.nurApi.getStorage();
	this.isRunning.set(true);
        while (isRunning.get()) {
            try {
                NurRespInventory nurRespInventory = nurApi.inventory();
                if (nurRespInventory.numTagsFound > 0) {
                    try {
                        nurApi.fetchTags(true);
                    } catch (Exception e) {
                        //TODO; no tags found
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int n = 0; n < apiStorage.size(); n++) {
                NurTag tag = apiStorage.get(n);
                int antennaId = tag.getAntennaId();
                Instant now = Instant.now();
                byte[] epc = tag.getEpc();
                if (virtualTrackingTagConcurrentHashMap.containsKey(tag.getEpcString())) {
                    final VirtualTrackingTag tagTrackingEvent = virtualTrackingTagConcurrentHashMap.get(tag.getEpcString());
                    this.processAndUpdateVirtualTrackingTag(tagTrackingEvent, antennaId);
                } else {
                    final VirtualTrackingTag virtualTrackingTag = new VirtualTrackingTag(epc, now);
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
                    //System.out.println("############ DIRECTION 1");
                    try {
                        this.nurApi.beep();
                    } catch (Exception e) {
                        log.warn("Failed beeping: ", e);
                    }
                    AlertEvent alertEvent = new AlertEvent(AlertEvent.Direction.FORWARD, Instant.now(), virtualTrackingTag.getEpc());
                    log.info(alertEvent.toString());
                    this.publishAlertEvent(alertEvent);
                }
                //System.out.println("AREA: 1");
                virtualTrackingTag.setPassedGateA(true);
                virtualTrackingTag.setPassedGateB(false);
            }
            break;
            case B: {
                if (virtualTrackingTag.isPassedGateA()) {
                    try {
                        this.nurApi.beep();
                    } catch (Exception e) {
                        log.warn("Failed beeping: ", e);
                    }
                    AlertEvent alertEvent = new AlertEvent(AlertEvent.Direction.BACKWARD, Instant.now(), virtualTrackingTag.getEpc());
                    log.info(alertEvent.toString());
                    this.publishAlertEvent(alertEvent);
                }
                //System.out.println("AREA: 2");
                virtualTrackingTag.setPassedGateB(true);
                virtualTrackingTag.setPassedGateA(false);
            }
            break;
            case None: {
                //TODO; handle this

            }
            break;
            default:

        }
    }

    public enum Gate {
        A, B, None
    }

}
