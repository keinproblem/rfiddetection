import connector.AlertEventForwarder;
import connector.ConnectorStrategy;
import connector.DummyToFileConnector;
import core.ApiFacade;
import core.NurApiWrapper;
import core.TagTrackingParameter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Starting RFID Detection");

        final ConnectorStrategy connectorStrategy = new DummyToFileConnector(Paths.get("testfile.txt"));
        final AlertEventForwarder alertEventForwarder = new AlertEventForwarder(connectorStrategy);

        final List<Integer> inAntennas = new LinkedList<>();
        inAntennas.add(0);
        inAntennas.add(1);
        final List<Integer> outAntennas = new LinkedList<>();
        outAntennas.add(2);
        outAntennas.add(3);

        final TagTrackingParameter tagTrackingParameter = new TagTrackingParameter();
        tagTrackingParameter.setAntennaIdsGateA(inAntennas);
        tagTrackingParameter.setAntennaIdsGateB(outAntennas);

        final ApiFacade apiFacade = new NurApiWrapper(tagTrackingParameter);
        final RfidDetectionService rfidDetectionService = new RfidDetectionService(apiFacade);
        rfidDetectionService.addExternalAlertEventListener(alertEventForwarder);
        log.info("Starting Service");
        rfidDetectionService.init("COM3");
    }
}
