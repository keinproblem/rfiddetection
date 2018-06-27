package core;

import lombok.Data;

/**
 * This class represents the state of a specific RFID Tag.
 * It holds the state for two gates.
 * A VirtualTrackingTag is identified by the unique EPC of the non-virtual RFID Tag it is associated to.
 * This class is used by the {@link DetectionRunner}.
 */
@Data
public class VirtualTrackingTag {
    private byte[] epc;
    private String epcString;
    private boolean passedGateA;
    private boolean passedGateB;

    private VirtualTrackingTag() {
    }


    public VirtualTrackingTag(final String epcString, final byte[] epc) {
	this.epc = epc;
	this.epcString = epcString;
    }



}
