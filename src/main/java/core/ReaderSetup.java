package core;

/*
@Slf4j
public class ReaderSetup {
    private final NurApi nurApi;
    private final NurApiTransport nurApiTransport;
    private final AtomicBoolean atomicBoolean;
    private int antennaNumber;
    private short timeOut;

    //DEFAULT "/dev/NordicIdSampoS1", 115200
    public ReaderSetup(@NonNull final String devicePath, final int baudRate, final int antennaNumber) {
        this.atomicBoolean = new AtomicBoolean(false);

        this.antennaNumber = antennaNumber;
        log.info(String.format("Setting up reader connection to device: %s using baudRate: %d", devicePath, baudRate));
        this.nurApiTransport = new NurApiSerialTransport(devicePath, baudRate);
        this.nurApi = new NurApi(this.nurApiTransport);
        this.nurApi.setLogLevel(NurApi.LOG_VERBOSE);
        this.nurApi.setLogToStdout(false);

        try {
            this.connect();
            this.setUpInOutMode();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Throws NurApi internally created exception to next layer
     *
     * @throws Exception
     */
/*
    public void connect() throws Exception {
        this.nurApi.connect();
        this.atomicBoolean.set(true);
    }

    public void registerReaderEventListener(final NurApiEventListener nurApiEventListener) {
        this.nurApi.setListener(nurApiEventListener);
    }

    private void autoTune() {

    }

    private void setUpInOutMode() throws Exception {
        NurRespReaderInfo nurRespReaderInfo = this.nurApi.getReaderInfo();
        log.info("Got reader information.");
        log.info("# of antennas: " + nurRespReaderInfo.numAntennas);
        AntennaMapping[] antennaMappings = this.nurApi.getAntennaMapping();
        log.info("# of antenna mappings found: " + antennaMappings.length);

        log.info("Setting up INOUT Mode");
        this.nurApi.setSetupTxLevel(10);
        //NurRespInventory nurRespInventory = this.nurApi.inventorySelect(0,7, NurApi.SESSION_S0,true,NurApi.BANK_EPC,0,0,new byte[0]);
        //this.nurApi.startInventoryStream();
        NurTagStorage apiStorage = this.nurApi.getStorage();
        this.registerReaderEventListener(new NurApiEventListener(apiStorage, this.nurApi));

        this.nurApi.traceTagByEpc(new byte[]{0x00});

        while (true) {
            //this.nurApi.getStorage().get()
            /*for (int n = 0; n < apiStorage.size(); n++) {
                NurTag tag = apiStorage.get(n);
                log.info(String.format("tag[%d] \t EPC '%s' \t RSSI %d \t antennaID: [%d] \t physicalAntenna: [%s] \t FREQ: %d \t TIME: %d", n, tag.getEpcString(), tag.getRssi(), tag.getAntennaId(), tag.getPhysicalAntenna(),  tag.getFreq(), tag.getTimestamp()));
            }
            this.nurApi.clearIdBuffer();*/

/*            Thread.sleep(100);
        }

/*        NurTagTrackingConfig nurTagTrackingConfig = new NurTagTrackingConfig();
        switch (this.antennaNumber) {
            case 0:
                //TODO; error
                break;
            case 1:
                //TODO; error
                break;
            case 2:
                log.info("Using 2 antennas");

                nurTagTrackingConfig.inAntennaMask = NurApi.ANTENNAMASK_1;
                nurTagTrackingConfig.outAntennaMask = NurApi.ANTENNAMASK_2;
                break;
            case 3:
                //TODO; think about how to handle this.. ether 2IN 1OUT or 1IN 2 OUT
                //TODO; else error
                break;
            case 4:
                log.info("Using 4 antennas");

                nurTagTrackingConfig.inAntennaMask = NurApi.ANTENNAMASK_1 | NurApi.ANTENNAMASK_2;
                nurTagTrackingConfig.outAntennaMask = NurApi.ANTENNAMASK_3 | NurApi.ANTENNAMASK_4;
                break;
            default:
                //TODO; throw illegal argument exception when setting antennaNumber..
                //TODO; thow illegal state exception here
                break;
        }
        nurTagTrackingConfig.flags = NurApi.NUR_TTFL_FULLROUNDREPORT;
        nurTagTrackingConfig.events = NurApi.NUR_TTEV_INOUT | NurApi.NUR_TTEV_VISIBILITY | NurApi.NUR_TTEV_ANTENNA;
        nurTagTrackingConfig.scanUntilNewTagsCount = 1;
        nurTagTrackingConfig.visibilityTimeout = 2000;
        /*
            Q-value		slots		Q-value		slots
0			automatic	8			256
1			2			9			512
2			4			10			1024
3			8			11			2048
4			16			12			4096
5			32			13			8192
6			64			14			16384
7			128			15			32768
         */
        //NurCmdTagTracking nurCmdTagTracking = new NurCmdTagTracking(nurTagTrackingConfig, 7, 0, 1);
        //NurPacket.write(this.nurApiTransport,nurCmdTagTracking);

/*
    }
}
*/
