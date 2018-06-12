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
public class AlertEvent implements Serializable {
    @Getter private Direction direction;
    @Getter private Instant detectionTime;
    @Getter private byte[] epc;

    private AlertEvent() {
    }

    public enum Direction {
        FORWARD, BACKWARD;
    }
}
