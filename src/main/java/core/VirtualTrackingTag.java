package core;

import lombok.Data;

import java.time.Instant;

@Data
public class VirtualTrackingTag {
    private byte[] epc;
    private String epcString;
    private Instant lastSeen;
    private boolean passedGateA;
    private boolean passedGateB;

    private VirtualTrackingTag() {
    }



    public VirtualTrackingTag(final String epcString, final byte[] epc, final Instant lastSeen) {
	this.epc = epc;
	this.lastSeen = lastSeen;
	this.epcString = epcString;
    }



}
