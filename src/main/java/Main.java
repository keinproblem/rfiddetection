import com.nordicid.nurapi.NurApi;
import connector.ConnectorStrategy;
import connector.DummyToFileConnector;
import connector.PresentationConnector;
import core.AlertEventBus;
import core.ApiFacade;
import core.NurApiWrapper;
import core.TagTrackingParameter;
import lombok.NonNull;
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

    /**
     * Application entry-point
     * Performs CLI parsing and starts the {@link RfidDetectionService}
     *
     * @param args CLI Arguments as String[]
     */
    public static void main(String[] args) {
        final Options options = getDefaultOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            /*
                Parsing went wrong - so we print the error-message & the help screen for troubleshooting
             */
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("sampling-message-client", options);
            System.exit(1);
            //unreachable return here is just to satisfy the compiler - since cmd might not be initialized, but used afterwards
            return;
        }

        /*
            If another log-level is desired we will set it right now
         */
        if (cmd.hasOption("log-level")) {
            final String logLevelString = cmd.getOptionValue("log-level");
            try {
                Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.valueOf(logLevelString));
            } catch (final IllegalArgumentException illegalArgumentException) {
                log.warn(String.format("Invalid log-level %s specified!", logLevelString));
                log.warn(String.format("Using default log-level: %s", LogManager.getRootLogger().getLevel().name()));
            }
        }

        log.info("Starting RFID Detection System");

        /*
            create an example ConnectorStrategy to consume and process AlertEvents
            if the CLI demo argument was specified we will use a super fancy GUI to display AlertEvents visually
            otherwise we use a simple file writer
         */
        final ConnectorStrategy connectorStrategy = cmd.hasOption("demo") ? new PresentationConnector() : new DummyToFileConnector(Paths.get("testfile.txt"));

        final AlertEventBus alertEventBus = new AlertEventBus();
        //make the ConnectorStrategy subscribe the AlertEventBus
        alertEventBus.subscribe(connectorStrategy.provideAlertEventConsumer());


        /*
            Prepare Input and Output gate mapping.
            IN => OUT = Forward
         */
        final TagTrackingParameter tagTrackingParameter = new TagTrackingParameter();
        final List<Integer> inAntennas = new LinkedList<>();
        inAntennas.add(0);  //AUX0
        inAntennas.add(1);  //AUX1
        final List<Integer> outAntennas = new LinkedList<>();
        outAntennas.add(2); //AUX2
        outAntennas.add(3); //AUX3
        tagTrackingParameter.setAntennaIdsGateA(inAntennas);
        tagTrackingParameter.setAntennaIdsGateB(outAntennas);

        //get the current log-level of the root logger and calculate an appropriate log-level for reader events
        final int logLevel = nurApiLogLevelFromLog4j2LogLevel(LogManager.getRootLogger().getLevel());
        final ApiFacade apiFacade = new NurApiWrapper(tagTrackingParameter, logLevel, alertEventBus);
        // no need for the additional register, since we solved this with the constructor
        // apiFacade.registerAlertEventBus(alertEventBus);

        //create RfidDetectionService with the prepared ApiFacade
        final RfidDetectionService rfidDetectionService = new RfidDetectionService(apiFacade);


        log.info("Starting Service");
        final String devPath = cmd.getOptionValue("devpath");
        log.debug("Using device: " + devPath);
        rfidDetectionService.init(devPath);
    }

    private static Options getDefaultOptions() {
        final Options options = new Options();
        options.addOption(Option.builder("dev").longOpt("device-path").required(true).hasArg(true).build());
        options.addOption(Option.builder("demo").longOpt("demo").required(false).hasArg(false).build());
        options.addOption(Option.builder("l").longOpt("log-level").required(false).hasArg(true).build());
        options.addOption(Option.builder("b").longOpt("enable-beep").required(false).hasArg(false).build());
        return options;
    }

    /**
     * Map log4j2 {@link Level} to a NurAPI log-levevl value.
     * ERROR and FATAL result in the same NurAPI log-level.
     * <p>
     * Default is NurApi.LOG_USER - Level.INFO
     *
     * @param level log4j2 log-level
     * @return a respective value for the NurAPI
     */
    private static int nurApiLogLevelFromLog4j2LogLevel(@NonNull final Level level) {
        if (level.equals(Level.ALL) || level.equals(Level.DEBUG) || level.equals(Level.TRACE)) {
            return NurApi.LOG_VERBOSE | NurApi.LOG_ERROR | NurApi.LOG_DATA | NurApi.LOG_USER;
        } else if (level.equals(Level.ERROR) || level.equals(Level.FATAL)) {
            return NurApi.LOG_ERROR;
        } else if (level.equals(Level.INFO)) {
            return NurApi.LOG_USER;
        } else if (level.equals(Level.OFF)) {
            return 0;
        }
        return NurApi.LOG_USER;
    }
}
