import nurapi.NurApiLibrary;
import org.bridj.BridJ;
import org.bridj.Platform;
import org.bridj.Pointer;

public class Main {
    public static void main(String[] args) {
        new DetectionService();
        System.out.println(System.getProperty("java.library.path"));
        NativeBridge nativeBridge = new NativeBridge();
        nativeBridge.initTracking(new TagTrackingParameter());
        //System.loadLibrary("NURAPI");
        NurApiLibrary nurApiLibrary = new NurApiLibrary();
        Pointer p = NurApiLibrary.NurApiCreate();


        NurApiLibrary.NurApiSetNotificationCallback(p, Pointer.getPointer(new NurApiLibrary.NotificationCallback() {

            @Override
            public void apply(Pointer hApi, int timestamp, int type, Pointer data, int dataLen) {


                byte[] bb = data.getBytes(dataLen);
                System.out.println(new String(bb));

                //System.out.println(data.getString(Pointer.StringType.C, Charset.forName("UTF-8")));


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
                }

            }
        }));

        NurApiLibrary.NurApiSetLogLevel(p, (int) NurApiLibrary.NUR_LOG.NUR_LOG_ALL.value);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        int isRunning = NurApiLibrary.NurApiIsTagTrackingRunning(p);
        int resultStopped = NurApiLibrary.NurApiStopTagTracking(p);
        System.out.println("isRunning: " + isRunning);
        System.out.println("resultStopped: " + resultStopped);

        String devPathStr = "COM3";
        //Pointer s = Pointer.pointerToCString(devPathStr);
        byte[] bys = devPathStr.getBytes();
        Pointer<Byte> s = Pointer.pointerToBytes(devPathStr.getBytes());
        Pointer pp = Pointer.pointerToCString(devPathStr);
        NurApiLibrary.NurApiConnectSerialPortEx(p, pp, NurApiLibrary.NUR_DEFAULT_BAUDRATE);

       /* NurApiLibrary nurApi = (NurApiLibrary) Native.loadLibrary(
                Platform.isWindows() ? "NURAPI": "NurApiRaspi",NurApiLibrary.class);
        System.out.println(System.getProperty("java.library.path"));
        Pointer NurApiInstance = nurApi;
        int cal = nurApi.NurApiSetNotificationCallback(NurApiInstance, new NurApi.NotificationCallback(){

            @Override
            public void callback(Pointer hApi, int timestamp, int type, Pointer data, int dataLen) {
                byte[] bb = data.getByteArray(0,dataLen);
                System.out.println("time: " + timestamp + " type: " + type + " data: " + new String(bb));

            }
        });
        System.out.println(cal);
        nurApi.NurApiSetLogLevel(NurApiInstance, 0x1111);
        String devPathStr = "COM3";
        Pointer devPathMem = new Memory(devPathStr.length() + 1);
        System.out.println(nurApi.NurApiGetLogLevel(NurApiInstance));
        devPathMem.setString(0,devPathStr);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(nurApi.NurApiConnectSerialPortEx(NurApiInstance, devPathMem, 115200 ));*/


    }


    public static void determineLibraryAlias() {
        if (Platform.isWindows()) {
            BridJ.addNativeLibraryAlias("NurApi", "NURAPI");
            if (Platform.isArm()) {

            } else if (Platform.is64Bits()) {

            } else {

            }
        } else {
            if (Platform.isArm()) {
                BridJ.addNativeLibraryAlias("NurApi", "libNurApiRaspi");
            } else if (Platform.is64Bits()) {
                BridJ.addNativeLibraryAlias("NurApi", "libNurApix64");
            } else {
                BridJ.addNativeLibraryAlias("NurApi", "libNurApix86");
            }
        }
    }
}
