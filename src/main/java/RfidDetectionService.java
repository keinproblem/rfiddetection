import core.ReaderSetup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RfidDetectionService {
    private final ReaderSetup readerSetup;

    public RfidDetectionService() {
        final String devicePath;
        if (System.getProperty("os.name").endsWith("x")) {
            devicePath = "/dev/NordicIdSampoS1";
        } else {
            devicePath = "COM3";
        }
        this.readerSetup = new ReaderSetup(devicePath, 115200, 2);
    }

    public void init() {
        log.info("Initializing RFID Detection Service");

    }
}
