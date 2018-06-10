package connector;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class AlertEvent implements Serializable {
    private Direction direction;
    private Instant detectionTime;
    ;
    private String EPC;

    private AlertEvent() {
    }

    public enum Direction {
        FORWARD, BACKWARD;
    }
}
