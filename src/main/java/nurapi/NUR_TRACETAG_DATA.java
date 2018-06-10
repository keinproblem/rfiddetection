package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * Data sent with notification NUR_NOTIFICATION_TRACETAG.
 * <br>
 *
 * @sa NurApiTraceTag32(), NUR_NOTIFICATION_TRACETAG, enum NUR_NOTIFICATION<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:87</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_TRACETAG_DATA extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_TRACETAG_DATA() {
        super();
    }

    public NUR_TRACETAG_DATA(Pointer pointer) {
        super(pointer);
    }

    /**
     * < Tag RSSI, -127 if tag could not be found.
     */
    @Field(0)
    public int rssi() {
        return this.io.getIntField(this, 0);
    }

    /**
     * < Tag RSSI, -127 if tag could not be found.
     */
    @Field(0)
    public NUR_TRACETAG_DATA rssi(int rssi) {
        this.io.setIntField(this, 0, rssi);
        return this;
    }

    /**
     * < Tag RSSI scaled to 0% - 100%.
     */
    @Field(1)
    public int scaledRssi() {
        return this.io.getIntField(this, 1);
    }

    /**
     * < Tag RSSI scaled to 0% - 100%.
     */
    @Field(1)
    public NUR_TRACETAG_DATA scaledRssi(int scaledRssi) {
        this.io.setIntField(this, 1, scaledRssi);
        return this;
    }

    /**
     * < Antenna ID where tag was found.
     */
    @Field(2)
    public int antennaId() {
        return this.io.getIntField(this, 2);
    }

    /**
     * < Antenna ID where tag was found.
     */
    @Field(2)
    public NUR_TRACETAG_DATA antennaId(int antennaId) {
        this.io.setIntField(this, 2, antennaId);
        return this;
    }

    /**
     * < Number of bytes stored in epc field.
     */
    @Field(3)
    public int epcLen() {
        return this.io.getIntField(this, 3);
    }

    /**
     * < Number of bytes stored in epc field.
     */
    @Field(3)
    public NUR_TRACETAG_DATA epcLen(int epcLen) {
        this.io.setIntField(this, 3, epcLen);
        return this;
    }

    /**
     * < Traced tag EPC data.<br>
     * C type : BYTE[(62)]
     */
    @Array({62})
    @Field(4)
    public Pointer<Byte> epc() {
        return this.io.getPointerField(this, 4);
    }
}
