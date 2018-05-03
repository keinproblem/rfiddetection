import com.nordicid.nurapi.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import java.util.HashMap;
import java.util.Map;

/**
 * This example shows how to read single tag memory bank content with different singulation methods.
 * Set 'targetTagEpc' variable to match your tag EPC
 */
public class Example {
    static final transient XYSeries series = new XYSeries("Random Data");
    static final transient Map<String, XYSeries> antennaSeries = new HashMap<String, XYSeries>();
    final XYSeriesCollection data;

    public Example() {
        ApplicationFrame applicationFrame = new ApplicationFrame("Title");
        data = new XYSeriesCollection();
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Series Demo",
                "X",
                "Y",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        applicationFrame.setContentPane(chartPanel);
        applicationFrame.pack();
        RefineryUtilities.centerFrameOnScreen(applicationFrame);
        applicationFrame.setVisible(true);

    }

    public static void main(String[] args) {
        Example example = new Example();
        example.init();
    }

    public void init() {
        NurApi api = null;

        try {
            // Create and connect new NurApi object
            // To change connection parameters, please modify SamplesCommon.java
            api = SamplesCommon.createAndConnectNurApi();
            //api.setLogLevel(NurApi.LOG_VERBOSE);
            api.setListener(apiListener);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        try {
            NurRespReaderInfo nurRespReaderInfo = api.getReaderInfo();
            System.out.println("Got reader information.");
            System.out.println("# of antennas: " + nurRespReaderInfo.numAntennas);
            AntennaMapping[] antennaMappings = api.getAntennaMapping();
            System.out.println("# of antenna mappings found: " + antennaMappings.length);

            for (int i = 0; i < antennaMappings.length; ++i)
                antennaSeries.put(String.valueOf(antennaMappings[i].antennaId), new XYSeries(String.valueOf(antennaMappings[i].antennaId)));

            antennaSeries.forEach((k, v) -> data.addSeries(v));

            while (true) {
                try {
                    System.out.println("Clearing ID Buffer");
                    api.clearIdBuffer(true);
                    System.out.println("Retrieving Inventory");
                    NurRespInventory resp = api.inventory();
                    System.out.println("Retrieving Inventory Done");
                    System.out.println("inventory numTagsFound: " + resp.numTagsFound);

                    if (resp.numTagsFound > 0) {
                        // Fetch and print tags
                        api.fetchTags();
                        for (int n = 0; n < api.getStorage().size(); n++) {
                            NurTag tag = api.getStorage().get(n);
                            tag.traceTag();
                            if (tag.getEpcString().equals("00000003")) {
                                antennaSeries.get(String.valueOf(tag.getAntennaId())).add(System.nanoTime(), tag.getRssi());
                            }
                            System.out.println(String.format("tag[%d] \t EPC '%s' \t RSSI %d \t FREQ: %d \t TIME: %d", n, tag.getEpcString(), tag.getRssi(), tag.getFreq(), tag.getTimestamp()));
                        }
                    } else {
                        System.out.println("No Tags found.");
                    }


                    // Set this to match your test tag EPC
                    //String targetTagEpc = "300833B2DDD9014000000005";

                    // Convert to bytes
                    //byte []targetEpcData = NurApi.hexStringToByteArray(targetTagEpc);

                    // Read 2words from TID bank, singulated by target EPC
                    //System.out.println("Read TID from tag " + targetTagEpc);
                    //byte []tidData = api.readTagByEpc(targetEpcData, targetEpcData.length, NurApi.BANK_TID, 0, 4);

                    // Read back EPC from target tag, singulated by TID 2words
                    //System.out.println("Read EPC from tag TID " + NurApi.byteArrayToHexString(tidData));
                    //byte[] readEpcData = api.readTag(NurApi.BANK_EPC, 0, 10);
                    //if (!Arrays.equals(readEpcData, new byte[10]))
                    //    System.out.println("Read EPC " + NurApi.byteArrayToHexString(readEpcData));
                    //System.out.println("Tag EPC Data read: " + NurApi.byteArrayToHexString(readEpcData));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Thread.sleep(50);
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
            }
            // Compare original target and read EPC's
            //if (targetTagEpc.equals(NurApi.byteArrayToHexString(readEpcData))) {
            //    System.out.println("OK");
            //}

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("NOTE: Make sure 'targetTagEpc' match your specific tag EPC");
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


    static NurApiListener apiListener = new NurApiListener() {

        public void triggeredReadEvent(NurEventTriggeredRead arg0) {
            System.out.println("triggeredReadEvent: " + arg0);
        }


        public void traceTagEvent(NurEventTraceTag arg0) {

            //System.out.println("traceTagEvent: " + arg0.rssi);
        }


        public void programmingProgressEvent(NurEventProgrammingProgress arg0) {
            System.out.println("programmingProgressEvent: " + arg0);
        }


        public void logEvent(int arg0, String arg1) {
            System.out.println("logEvent: " + arg0);
        }


        public void inventoryStreamEvent(NurEventInventory arg0) {
            System.out.println("inventoryStreamEvent: " + arg0);
        }


        public void inventoryExtendedStreamEvent(NurEventInventory arg0) {
            System.out.println("inventoryExtendedStreamEvent: " + arg0);
        }


        public void frequencyHopEvent(NurEventFrequencyHop arg0) {
            System.out.println("frequencyHopEvent: " + arg0);
        }


        public void disconnectedEvent() {
            System.out.println("disconnectedEvent");
        }


        public void deviceSearchEvent(NurEventDeviceInfo arg0) {
            System.out.println("deviceSearchEvent: " + arg0);
        }


        public void debugMessageEvent(String arg0) {
            System.out.println("debugMessageEvent: " + arg0);
        }


        public void connectedEvent() {
            System.out.println("connectedEvent");
        }


        public void clientDisconnectedEvent(NurEventClientInfo arg0) {
            System.out.println("clientDisconnectedEvent: " + arg0);
        }


        public void clientConnectedEvent(NurEventClientInfo arg0) {
            System.out.println("clientConnectedEvent: " + arg0);
        }


        public void bootEvent(String arg0) {
            System.out.println("bootEvent: " + arg0);
        }


        public void IOChangeEvent(NurEventIOChange arg0) {
            System.out.println("IOChangeEvent: " + arg0);
        }


        public void autotuneEvent(NurEventAutotune arg0) {
            System.out.println("autotuneEvent: " + arg0);
        }


        public void epcEnumEvent(NurEventEpcEnum arg0) {
            System.out.println("epcEnumEvent: " + arg0);
        }


        public void nxpEasAlarmEvent(NurEventNxpAlarm arg0) {
            System.out.println("nxpEasAlarmEvent: " + arg0);
        }


        public void tagTrackingChangeEvent(NurEventTagTrackingChange arg0) {
            //System.out.println("tagTrackingChangeEvent readSource :" + arg0.readSource);

        }


        public void tagTrackingScanEvent(NurEventTagTrackingData arg0) {
            //System.out.println("tagTrackingScanEvent: " + arg0);

        }
    };
}
