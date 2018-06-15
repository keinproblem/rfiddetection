package core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class AlertEvent implements Serializable {
    private Direction direction;
    private Instant detectionTime;
    private byte[] epc;
    private String epcString;

    private AlertEvent() {
    }

    public enum Direction {
        FORWARD, BACKWARD;
    }
}
