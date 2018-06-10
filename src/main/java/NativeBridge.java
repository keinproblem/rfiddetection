import nurapi.NUR_TAGTRACKING_CONFIG;
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

    @Override
    public void initTracking(final TagTrackingParameter tagTrackingParameter) {

        NUR_TAGTRACKING_CONFIG nur_tagtracking_config = new NUR_TAGTRACKING_CONFIG();
        nur_tagtracking_config.inAntennaMask((int) (NurApiLibrary.NUR_ANTENNAMASK.NUR_ANTENNAMASK_1.value | NurApiLibrary.NUR_ANTENNAMASK.NUR_ANTENNAMASK_2.value));
        nur_tagtracking_config.outAntennaMask((int) (NurApiLibrary.NUR_ANTENNAMASK.NUR_ANTENNAMASK_3.value | NurApiLibrary.NUR_ANTENNAMASK.NUR_ANTENNAMASK_4.value));
        nur_tagtracking_config.events();
        nur_tagtracking_config.scanUntilNewTagsCount(0);
        nur_tagtracking_config.selectAddress((int) NurApiLibrary.NUR_BANK.NUR_BANK_EPC.value);
        nur_tagtracking_config.flags();
        int visibilityTimeoutInSec = 200;
        nur_tagtracking_config.visibilityTimeout(visibilityTimeoutInSec);

        int startTagTrackingResult = NurApiLibrary.NurApiStartTagTracking(nativeNurApiInstancePointer, Pointer.getPointer(nur_tagtracking_config), (int) BridJ.sizeOf(NUR_TAGTRACKING_CONFIG.class));
        System.out.println(nurErrorCodeFromInt(startTagTrackingResult).name());
    }

    @Override
    public void stopTracking() {
        int startTagTrackingResult = NurApiLibrary.NurApiStopTagTracking(nativeNurApiInstancePointer);
        System.out.println(nurErrorCodeFromInt(startTagTrackingResult).name());
    }

    @Override
    public void registerCallbackListener() {

    }
}
