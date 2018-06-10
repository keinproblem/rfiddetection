package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * Data sent with notification NUR_NOTIFICATION_TRIGGERREAD.
 * <br>
 *
 * @sa NUR_NOTIFICATION_TRIGGERREAD, enum NUR_NOTIFICATION<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:109</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_TRIGGERREAD_DATA extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_TRIGGERREAD_DATA() {
        super();
    }

    public NUR_TRIGGERREAD_DATA(Pointer pointer) {
        super(pointer);
    }

    /**
     * < TRUE if notification source is sensor, otherwise FALSE and source is GPIO.
     */
    @Field(0)
    public int sensor() {
        return this.io.getIntField(this, 0);
    }

    /**
     * < TRUE if notification source is sensor, otherwise FALSE and source is GPIO.
     */
    @Field(0)
    public NUR_TRIGGERREAD_DATA sensor(int sensor) {
        this.io.setIntField(this, 0, sensor);
        return this;
    }

    /**
     * < Sensor/GPIO source number.
     */
    @Field(1)
    public int source() {
        return this.io.getIntField(this, 1);
    }

    /**
     * < Sensor/GPIO source number.
     */
    @Field(1)
    public NUR_TRIGGERREAD_DATA source(int source) {
        this.io.setIntField(this, 1, source);
        return this;
    }

    /**
     * < ID of antenna where tag was read.
     */
    @Field(2)
    public int antennaID() {
        return this.io.getIntField(this, 2);
    }

    /**
     * < ID of antenna where tag was read.
     */
    @Field(2)
    public NUR_TRIGGERREAD_DATA antennaID(int antennaID) {
        this.io.setIntField(this, 2, antennaID);
        return this;
    }

    /**
     * < Tag RSSI, -127 if tag could not be found.
     */
    @Field(3)
    public int rssi() {
        return this.io.getIntField(this, 3);
    }

    /**
     * < Tag RSSI, -127 if tag could not be found.
     */
    @Field(3)
    public NUR_TRIGGERREAD_DATA rssi(int rssi) {
        this.io.setIntField(this, 3, rssi);
        return this;
    }

    /**
     * < Tag RSSI scaled to 0% - 100%.
     */
    @Field(4)
    public int scaledRssi() {
        return this.io.getIntField(this, 4);
    }

    /**
     * < Tag RSSI scaled to 0% - 100%.
     */
    @Field(4)
    public NUR_TRIGGERREAD_DATA scaledRssi(int scaledRssi) {
        this.io.setIntField(this, 4, scaledRssi);
        return this;
    }

    /**
     * < Number of bytes stored in epc field, zero if tag could not be found.
     */
    @Field(5)
    public int epcLen() {
        return this.io.getIntField(this, 5);
    }

    /**
     * < Number of bytes stored in epc field, zero if tag could not be found.
     */
    @Field(5)
    public NUR_TRIGGERREAD_DATA epcLen(int epcLen) {
        this.io.setIntField(this, 5, epcLen);
        return this;
    }

    /**
     * < Tag EPC data.<br>
     * C type : BYTE[(62)]
     */
    @Array({62})
    @Field(6)
    public Pointer<Byte> epc() {
        return this.io.getPointerField(this, 6);
    }
}
