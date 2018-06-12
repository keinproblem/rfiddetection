import connector.AlertEventForwarder;
import connector.ConnectorStrategy;
import connector.DummyToFileConnector;
import connector.PresentationConnector;
import core.ApiFacade;
import core.NurApiWrapper;
import core.TagTrackingParameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("d").longOpt("devpath").required(true).hasArg(true).build());
        options.addOption(Option.builder("demo").longOpt("demo").required(false).hasArg(false).build());

        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("sampling-message-client", options);
            System.exit(1);
        }


        log.info("Starting RFID Detection");

        final ConnectorStrategy connectorStrategy = cmd.hasOption("demo") ? new PresentationConnector() : new DummyToFileConnector(Paths.get("testfile.txt"));

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
        log.debug("Device Path: " + (cmd == null ? "COM3" : cmd.getOptionValue("devpath")));
        rfidDetectionService.init(cmd == null ? "COM3" : cmd.getOptionValue("devpath"));
    }
}
