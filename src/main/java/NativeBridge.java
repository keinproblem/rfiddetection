import nurapi.NUR_TAGTRACKING_CONFIG;
import nurapi.NUR_TT_TAG;
import nurapi.NurApiLibrary;
import org.bridj.BridJ;
import org.bridj.Pointer;

/**
 * Delegate for native implementations
 * <p>
 * to be replaced by java NUR SDK as soon as released by vendor
 */
public class NativeBridge implements ApiDelegate {

    private final Pointer nativeNurApiInstancePointer;

    public NativeBridge() {
        this.nativeNurApiInstancePointer = NurApiLibrary.NurApiCreate();
        if (nativeNurApiInstancePointer.equals(Pointer.NULL)) {
            throw new RuntimeException("");
        }
    }

    public static NurApiLibrary.NUR_ERRORCODES nurErrorCodeFromInt(final int errCode) {
        final long ll = System.nanoTime();
        for (NurApiLibrary.NUR_ERRORCODES errs : NurApiLibrary.NUR_ERRORCODES.values()) {
            if (errs.value == errCode) {
                System.out.println(System.nanoTime() - ll);
                return errs;
            }
        }
        //TODO; handle default case better
        System.out.println(System.nanoTime() - ll);
        return NurApiLibrary.NUR_ERRORCODES.NUR_ERROR_GENERAL;
    }

    private static String fromPointer(final Pointer<?> pointer) {
        return pointer.getCString();
    }

    private static Pointer<Byte> fromPointer(final String string) {
        return Pointer.pointerToCString(string);
    }

    @Override
    public void connect(String devPath) {
        this.connect(devPath, NurApiLibrary.NUR_DEFAULT_BAUDRATE);
    }

    @Override
    public void initTracking(final TagTrackingParameter tagTrackingParameter) {

        NUR_TAGTRACKING_CONFIG nur_tagtracking_config = new NUR_TAGTRACKING_CONFIG();
        nur_tagtracking_config.inAntennaMask((int) (NurApiLibrary.NUR_ANTENNAMASK.NUR_ANTENNAMASK_1.value | NurApiLibrary.NUR_ANTENNAMASK.NUR_ANTENNAMASK_2.value));
        nur_tagtracking_config.outAntennaMask((int) (NurApiLibrary.NUR_ANTENNAMASK.NUR_ANTENNAMASK_3.value | NurApiLibrary.NUR_ANTENNAMASK.NUR_ANTENNAMASK_4.value));
        nur_tagtracking_config.events((int) (NurApiLibrary.NUR_TAGTRACKING_EVENTS.NUR_TTEV_INOUT.value() | NurApiLibrary.NUR_TAGTRACKING_EVENTS.NUR_TTEV_VISIBILITY.value() | NurApiLibrary.NUR_TAGTRACKING_EVENTS.NUR_TTEV_ANTENNA.value()));
        nur_tagtracking_config.scanUntilNewTagsCount(0);
        nur_tagtracking_config.selectAddress((int) NurApiLibrary.NUR_BANK.NUR_BANK_EPC.value);
        nur_tagtracking_config.flags();
        int visibilityTimeoutInSec = 200;
        nur_tagtracking_config.visibilityTimeout(visibilityTimeoutInSec);

        int startTagTrackingResult = NurApiLibrary.NurApiStartTagTracking(nativeNurApiInstancePointer, Pointer.getPointer(nur_tagtracking_config), (int) BridJ.sizeOf(NUR_TAGTRACKING_CONFIG.class));
        System.out.println(nurErrorCodeFromInt(startTagTrackingResult).name());
    }

    @Override
    public boolean isTagTrackingRunning() {
        return NurApiLibrary.NurApiIsTagTrackingRunning(nativeNurApiInstancePointer) == 1;
    }

    @Override
    public void stopTracking() {
        int startTagTrackingResult = NurApiLibrary.NurApiStopTagTracking(nativeNurApiInstancePointer);
        System.out.println(nurErrorCodeFromInt(startTagTrackingResult).name());
    }

    @Override
    public void connect(String devPath, int baudRate) {
        NurApiLibrary.NurApiConnectSerialPortEx(nativeNurApiInstancePointer, fromPointer(devPath), baudRate);
    }

    @Override
    public void registerCallbackListener() {

        NurApiLibrary.NurApiSetNotificationCallback(nativeNurApiInstancePointer, Pointer.getPointer(new NurApiLibrary.NotificationCallback() {

            @Override
            public void apply(Pointer hApi, int timestamp, int type, Pointer data, int dataLen) {

                //System.out.println(new String(data.getBytes(dataLen), Charset.forName("US-ASCII")));
                //System.out.println(data.getCString());
                //System.out.println(data.getWideCString());
                //System.out.println(data.getString(Pointer.StringType.C, Charset.forName("UTF-8")));
                //System.out.println(Native.toString(data.getBytes(dataLen)));
                //System.out.println(Native.toString(data.getChars(dataLen)));
                System.out.println(fromPointer(data));
                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_LOG.value == type) {
                    //System.out.println(data.toString());
                    //System.out.println(data.getString(Pointer.StringType.C));
                }

                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_PERIODIC_INVENTORY.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_TRDISCONNECTED.value == type) {

                }

                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_MODULEBOOT.value == type) {

                }

                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_TRCONNECTED.value == type) {

                }

                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_TRACETAG.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_IOCHANGE.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_TRIGGERREAD.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_HOPEVENT.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_INVENTORYSTREAM.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_INVENTORYEX.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_DEVSEARCH.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_CLIENTCONNECTED.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_CLIENTDISCONNECTED.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_EASALARM.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_EPCENUM.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_EXTIN.value == type) {
                }


                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_GENERAL.value == type) {
                }

                if (NurApiLibrary.NUR_NOTIFICATION.NUR_NOTIFICATION_TT_CHANGED.value == type) {
                    Pointer<Integer> integerPointer = Pointer.allocateInt();
                    NUR_TT_TAG[] nurTtTags;
                    NurApiLibrary.NurApiTagTrackingGetTags(nativeNurApiInstancePointer, 0, null, integerPointer, (int) BridJ.sizeOf(NUR_TT_TAG.class));
                    int cnt = integerPointer.getInt();
                    nurTtTags = new NUR_TT_TAG[cnt];
                    NurApiLibrary.NurApiTagTrackingGetTags(nativeNurApiInstancePointer, 0, Pointer.pointerToArray(nurTtTags), integerPointer, (int) BridJ.sizeOf(NUR_TT_TAG.class));
                    for (NUR_TT_TAG nurTtTag : nurTtTags) {
                        //use callback proxy
                    }
                }

            }
        }));

    }
}
