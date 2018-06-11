package core;

import lombok.Data;

import java.util.List;

@Data
public class TagTrackingParameter {
    private List<Integer> antennaIdsGateA;// = new int[16];
    private List<Integer> antennaIdsGateB;// = new int[16];
}
