import com.nordicid.nurapi.*;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This Test shows how to run continuous inventory in asynchronous stream.
 * - Inventory is used to read multiple tag's EPC codes in reader field of view
 */
public class Test {

    static NurApiListener apiListener = new NurApiListener() {

        @Override
        public void triggeredReadEvent(NurEventTriggeredRead arg0) {
        }

        @Override
        public void traceTagEvent(NurEventTraceTag arg0) {
        }

        @Override
        public void programmingProgressEvent(NurEventProgrammingProgress arg0) {
        }

        @Override
        public void logEvent(int arg0, String arg1) {
        }

        // This event is fired when ever reader completes inventory round and tags are read
        // NOTE: Depending on reader settings, tag amount and environment this event maybe fired very frequently
        @Override
        public void inventoryStreamEvent(NurEventInventory arg0) {
            /*eventCount++;

            NurTagStorage apiStorage = api.getStorage();

            //api.fetchTags(true);
            // When accessing api owned storage from event, we need to lock it
            synchronized (apiStorage) {
                // Add inventoried tags to our unique tag storage
                for (int n = 0; n < apiStorage.size(); n++) {
                    NurTag tag = apiStorage.get(n);
                        int antennaId = tag.getAntennaId();
                        Instant now = Instant.now();
                        byte[] epc = tag.getEpc();
                        synchronized (tagTrackingEventMap) {
                            final TagTrackingEvent tagTrackingEvent = tagTrackingEventMap.get(tag.getEpcString());
                            if (tagTrackingEvent != null) {
                                System.out.println("got tag");
                                System.out.println(antennaId);
                                if (antennaId == NurApi.ANTENNAID_1 || antennaId == NurApi.ANTENNAID_2) {
                                    if (tagTrackingEvent.passedB ) {
                                        System.out.println("############ DIRECTION 1");
                                    }
                                    System.out.println("AREA: 1");
                                    tagTrackingEvent.passedA = true;
                                    tagTrackingEvent.passedB = false;
                                } else {//if (antennaId == NurApi.ANTENNAID_3 || antennaId == NurApi.ANTENNAID_4) {
                                    if (tagTrackingEvent.passedA) {
                                        System.out.println("************* DIRECTION ");
                                    }
                                    System.out.println("AREA: 2");
                                    tagTrackingEvent.passedB = true;
                                    tagTrackingEvent.passedA = false;
                                }

                            } else {
                                System.out.println("putting new tag");
                                System.out.println(tagTrackingEventMap.size());
                                tagTrackingEventMap.put(tag.getEpcString(), new TagTrackingEvent(epc, antennaId, now));
                            }
                        }
                }
                apiStorage.clear();
            }

            // If stream stopped, restart
            if (arg0.stopped) {
                try {
                    System.out.println("Restarting stream");
                    api.startInventoryStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/
        }

        @Override
        public void inventoryExtendedStreamEvent(NurEventInventory arg0) {
        }

        @Override
        public void frequencyHopEvent(NurEventFrequencyHop arg0) {
        }

        @Override
        public void disconnectedEvent() {
        }

        @Override
        public void deviceSearchEvent(NurEventDeviceInfo arg0) {
        }

        @Override
        public void debugMessageEvent(String arg0) {
        }

        @Override
        public void connectedEvent() {
        }

        @Override
        public void clientDisconnectedEvent(NurEventClientInfo arg0) {
        }

        @Override
        public void clientConnectedEvent(NurEventClientInfo arg0) {
        }

        @Override
        public void bootEvent(String arg0) {
        }

        @Override
        public void IOChangeEvent(NurEventIOChange arg0) {
        }

        @Override
        public void autotuneEvent(NurEventAutotune arg0) {
        }

        @Override
        public void epcEnumEvent(NurEventEpcEnum arg0) {
        }

        @Override
        public void nxpEasAlarmEvent(NurEventNxpAlarm arg0) {
        }

        @Override
        public void tagTrackingChangeEvent(NurEventTagTrackingChange arg0) {
        }

        @Override
        public void tagTrackingScanEvent(NurEventTagTrackingData arg0) {
        }
    };
    private static ConcurrentHashMap<String, TagTrackingEvent> tagTrackingEventMap = new ConcurrentHashMap<>();

    // We store unique read tags in this storage
    static NurTagStorage uniqueTags = new NurTagStorage();

    // API access
    static NurApi api = null;

    // stream event count
    static int eventCount = 0;

    public static void main(String[] args) {

        try {
            // Create and connect new NurApi object
            // To change connection parameters, please modify SamplesCommon.java
            api = SamplesCommon.createAndConnectNurApi();
            api.setListener(apiListener);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        try {
            // Clear tag storage
            api.clearIdBuffer(true);


            // Start inventory stream, see inventoryStreamEvent() below
            //api.startInventoryStream();

            // Let it run for 30 sec
            NurTagStorage apiStorage = api.getStorage();
            while (true) {
                //System.out.println(String.format("Total %d stream events", eventCount));
                Thread.sleep(150);
                //System.out.println(String.format("Inventoried total %d unique tags", uniqueTags.size()));

                NurRespInventory nurRespInventory = api.inventory();
                if (nurRespInventory.numTagsFound > 0) {
                    try {
                        api.fetchTags(true);
                    } catch (Exception e) {

                    }
                }
                // When accessing api owned storage from event, we need to lock it
                synchronized (apiStorage) {
                    // Add inventoried tags to our unique tag storage
                    for (int n = 0; n < apiStorage.size(); n++) {
                        NurTag tag = apiStorage.get(n);
                        int antennaId = tag.getAntennaId();
                        Instant now = Instant.now();
                        byte[] epc = tag.getEpc();
                        synchronized (tagTrackingEventMap) {
                            final TagTrackingEvent tagTrackingEvent = tagTrackingEventMap.get(tag.getEpcString());
                            if (tagTrackingEvent != null) {
                                //System.out.println("got tag");
                                //System.out.println(antennaId);
                                if (antennaId == NurApi.ANTENNAID_1 || antennaId == NurApi.ANTENNAID_2) {
                                    if (tagTrackingEvent.passedB) {
                                        //System.out.println("############ DIRECTION 1");
                                        System.out.println(eventCount++);

                                    }
                                    //System.out.println("AREA: 1");
                                    tagTrackingEvent.passedA = true;
                                    tagTrackingEvent.passedB = false;
                                } else {//if (antennaId == NurApi.ANTENNAID_3 || antennaId == NurApi.ANTENNAID_4) {
                                    if (tagTrackingEvent.passedA) {
                                        //System.out.println("************* DIRECTION 2");
                                        System.out.println(eventCount--);

                                    }
                                    //System.out.println("AREA: 2");
                                    tagTrackingEvent.passedB = true;
                                    tagTrackingEvent.passedA = false;
                                }

                            } else {
                                //TODO; set initial area detected in
                                tagTrackingEventMap.put(tag.getEpcString(), new TagTrackingEvent(epc, antennaId, now));
                            }
                        }
                    }
                    apiStorage.clear();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("See you again!.");
        try {
            // Disconnect the connection
            api.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Dispose the NurApi
        api.dispose();
    }

    @Data
    static class TagTrackingEvent {
        private final Duration timeOut = Duration.ofMillis(700);
        private int antennaId;
        private Instant lastSeen;
        private byte[] epc;
        private boolean passedA;
        private boolean passedB;

        public TagTrackingEvent(byte[] epc, int antennaId, Instant lastSeen) {
            this.antennaId = antennaId;
            this.lastSeen = lastSeen;
            this.epc = epc;
        }

        public void update(final int antennaId, final Instant lastSeen) {

        }

    }
}