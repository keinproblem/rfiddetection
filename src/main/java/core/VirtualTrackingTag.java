package core;

import lombok.Data;

import java.time.Instant;

@Data
public class VirtualTrackingTag {
    private byte[] epc;
    private Instant lastSeen;
    private boolean passedGateA;
    private boolean passedGateB;

    private VirtualTrackingTag() {
    }

    ;

    public VirtualTrackingTag(final byte[] epc, final Instant lastSeen) {
    }

    ;

}
