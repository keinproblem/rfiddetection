import com.nordicid.nurapi.NurApi;
import connector.AlertEventForwarder;
import connector.ConnectorStrategy;
import connector.DummyToFileConnector;
import connector.PresentationConnector;
import core.ApiFacade;
import core.NurApiWrapper;
import core.TagTrackingParameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option.builder("dev").longOpt("device-path").required(true).hasArg(true).build());
        options.addOption(Option.builder("demo").longOpt("demo").required(false).hasArg(false).build());
        options.addOption(Option.builder("l").longOpt("log-level").required(false).hasArg(true).build());
        options.addOption(Option.builder("b").longOpt("enable-beep").required(false).hasArg(false).build());

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
        if (cmd.hasOption("log-level")) {
            final String logLevelString = cmd.getOptionValue("log-level");
            try {
                Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.valueOf(logLevelString));
            } catch (final IllegalArgumentException illegalArgumentException) {
                log.warn(String.format("Invalid log-level %s specified!", logLevelString));
                log.warn(String.format("Using default log-level: %s", LogManager.getRootLogger().getLevel().name()));
            }
        }

        log.info("Starting RFID Detection");

        final ConnectorStrategy connectorStrategy = cmd.hasOption("demo") ? new PresentationConnector() : new DummyToFileConnector(Paths.get("testfile.txt"));

        final AlertEventForwarder alertEventForwarder = new AlertEventForwarder(connectorStrategy);

        final List<Integer> inAntennas = new LinkedList<>();
        inAntennas.add(0);  //AUX0
        inAntennas.add(1);  //AUX1
        final List<Integer> outAntennas = new LinkedList<>();
        outAntennas.add(2); //AUX2
        outAntennas.add(3); //AUX3

        final TagTrackingParameter tagTrackingParameter = new TagTrackingParameter();
        tagTrackingParameter.setAntennaIdsGateA(inAntennas);
        tagTrackingParameter.setAntennaIdsGateB(outAntennas);

        //TODO; calculate nurapi loglevel from log4j2 api loglevel
        int logLevel = NurApi.LOG_VERBOSE | NurApi.LOG_ERROR | NurApi.LOG_DATA | NurApi.LOG_USER;
        final ApiFacade apiFacade = new NurApiWrapper(tagTrackingParameter, logLevel);
        final RfidDetectionService rfidDetectionService = new RfidDetectionService(apiFacade);
        rfidDetectionService.addExternalAlertEventListener(alertEventForwarder);
        log.info("Starting Service");
        log.debug("Using device: " + (cmd == null ? "COM3" : cmd.getOptionValue("devpath")));
        rfidDetectionService.init(cmd == null ? "COM3" : cmd.getOptionValue("devpath"));
    }
}
