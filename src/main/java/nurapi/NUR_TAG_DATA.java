package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * Contains single tag inventoried information.
 * <br>
 *
 * @sa NurApiGetTagData(), NurApiGetAllTagData()<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:331</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_TAG_DATA extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_TAG_DATA() {
        super();
    }

    public NUR_TAG_DATA(Pointer pointer) {
        super(pointer);
    }

    /**
     * < Timestamp when tag was found in milliseconds; Only valid when meta data is fetched.
     */
    @Field(0)
    public short timestamp() {
        return this.io.getShortField(this, 0);
    }

    /**
     * < Timestamp when tag was found in milliseconds; Only valid when meta data is fetched.
     */
    @Field(0)
    public NUR_TAG_DATA timestamp(short timestamp) {
        this.io.setShortField(this, 0, timestamp);
        return this;
    }

    /**
     * < Tag RSSI; Only valid when meta data is fetched.
     */
    @Field(1)
    public byte rssi() {
        return this.io.getByteField(this, 1);
    }

    /**
     * < Tag RSSI; Only valid when meta data is fetched.
     */
    @Field(1)
    public NUR_TAG_DATA rssi(byte rssi) {
        this.io.setByteField(this, 1, rssi);
        return this;
    }

    /**
     * < Tag RSSI scaled to 0% - 100%; Only valid when meta data is fetched.
     */
    @Field(2)
    public byte scaledRssi() {
        return this.io.getByteField(this, 2);
    }

    /**
     * < Tag RSSI scaled to 0% - 100%; Only valid when meta data is fetched.
     */
    @Field(2)
    public NUR_TAG_DATA scaledRssi(byte scaledRssi) {
        this.io.setByteField(this, 2, scaledRssi);
        return this;
    }

    /**
     * < Frequency in kHz where tag was found; Only valid when meta data is fetched.
     */
    @Field(3)
    public int freq() {
        return this.io.getIntField(this, 3);
    }

    /**
     * < Frequency in kHz where tag was found; Only valid when meta data is fetched.
     */
    @Field(3)
    public NUR_TAG_DATA freq(int freq) {
        this.io.setIntField(this, 3, freq);
        return this;
    }

    /**
     * < Tag PC word; Only valid when meta data is fetched.
     */
    @Field(4)
    public short pc() {
        return this.io.getShortField(this, 4);
    }

    /**
     * < Tag PC word; Only valid when meta data is fetched.
     */
    @Field(4)
    public NUR_TAG_DATA pc(short pc) {
        this.io.setShortField(this, 4, pc);
        return this;
    }

    /**
     * < Channel index where tag was found; Only valid when meta data is fetched.
     */
    @Field(5)
    public byte channel() {
        return this.io.getByteField(this, 5);
    }

    /**
     * < Channel index where tag was found; Only valid when meta data is fetched.
     */
    @Field(5)
    public NUR_TAG_DATA channel(byte channel) {
        this.io.setByteField(this, 5, channel);
        return this;
    }

    /**
     * < Antenna ID where tag was found; Only valid when meta data is fetched.
     */
    @Field(6)
    public byte antennaId() {
        return this.io.getByteField(this, 6);
    }

    /**
     * < Antenna ID where tag was found; Only valid when meta data is fetched.
     */
    @Field(6)
    public NUR_TAG_DATA antennaId(byte antennaId) {
        this.io.setByteField(this, 6, antennaId);
        return this;
    }

    /**
     * < Number of bytes stored in epc field.
     */
    @Field(7)
    public byte epcLen() {
        return this.io.getByteField(this, 7);
    }

    /**
     * < Number of bytes stored in epc field.
     */
    @Field(7)
    public NUR_TAG_DATA epcLen(byte epcLen) {
        this.io.setByteField(this, 7, epcLen);
        return this;
    }

    /**
     * < Tag EPC data.<br>
     * C type : BYTE[(62)]
     */
    @Array({62})
    @Field(8)
    public Pointer<Byte> epc() {
        return this.io.getPointerField(this, 8);
    }
}