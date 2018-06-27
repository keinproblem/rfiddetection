package core;

import lombok.Data;

import java.util.List;

/**
 * A basic configuration class which contains information for the customized detection algorithm of the {@link DetectionRunner}.
 * It is used for mapping of AUX ports to specific zones/gates.
 */
@Data
public class TagTrackingParameter {
    private List<Integer> antennaIdsGateA;
    private List<Integer> antennaIdsGateB;
}
