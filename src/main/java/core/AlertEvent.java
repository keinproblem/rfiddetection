package core;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

/**
 * This class represents a Alerting Object of a detected directional movement.
 * It contains basic information for further processing.
 * It is immutable and should only be considered for on-the-fly analysis or persistent storing.
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class AlertEvent implements Serializable {
    private Direction direction;
    private Instant detectionTime;
    private byte[] epc;
    private String epcString;

    /**
     * This enum provides defined directions.
     */
    public enum Direction {
        FORWARD, BACKWARD
    }
}
