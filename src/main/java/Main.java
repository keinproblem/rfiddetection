import connector.AlertEventForwarder;
import connector.ConnectorStrategy;
import connector.PraesentationConnector;
import core.ApiFacade;
import core.NurApiWrapper;
import core.TagTrackingParameter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        /*Options options = new Options();
        options.addOption(Option.builder().argName("d").longOpt("devpath").required().build());

        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        try {
            CommandLine cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("sampling-message-client", options);
            System.exit(1);
        }*/


        log.info("Starting RFID Detection");

        //final ConnectorStrategy connectorStrategy = new DummyToFileConnector(Paths.get("testfile.txt"));
        final ConnectorStrategy connectorStrategy = new PraesentationConnector();
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
