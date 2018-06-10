package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * The Gen2 version 2 "Authenticate" parameter.
 * <br>
 *
 * @sa NUR_GEN2V2_MAX_AUTHBYTES<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:549</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_AUTHENTICATE_PARAM extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_AUTHENTICATE_PARAM() {
        super();
    }

    public NUR_AUTHENTICATE_PARAM(Pointer pointer) {
        super(pointer);
    }

    /**
     * < Cryptographic Suite Indicator.
     */
    @Field(0)
    public int csi() {
        return this.io.getIntField(this, 0);
    }

    /**
     * < Cryptographic Suite Indicator.
     */
    @Field(0)
    public NUR_AUTHENTICATE_PARAM csi(int csi) {
        this.io.setIntField(this, 0, csi);
        return this;
    }

    /**
     * < Set to 0 if reception length is not known, otherwise this is the bit length of the reception ranging in 1...1008.
     */
    @Field(1)
    public int rxLength() {
        return this.io.getIntField(this, 1);
    }

    /**
     * < Set to 0 if reception length is not known, otherwise this is the bit length of the reception ranging in 1...1008.
     */
    @Field(1)
    public NUR_AUTHENTICATE_PARAM rxLength(int rxLength) {
        this.io.setIntField(this, 1, rxLength);
        return this;
    }

    /**
     * < RX attenuation for response reception; causes "write-like" operation thus reduces operation's range.
     */
    @Field(2)
    public int rxAttn() {
        return this.io.getIntField(this, 2);
    }

    /**
     * < RX attenuation for response reception; causes "write-like" operation thus reduces operation's range.
     */
    @Field(2)
    public NUR_AUTHENTICATE_PARAM rxAttn(int rxAttn) {
        this.io.setIntField(this, 2, rxAttn);
        return this;
    }

    /**
     * < If module needs to internally use the ReadBuffer command then setting this to TRUE causes the tag to be re-selected between operations.
     */
    @Field(3)
    public int reSelect() {
        return this.io.getIntField(this, 3);
    }

    /**
     * < If module needs to internally use the ReadBuffer command then setting this to TRUE causes the tag to be re-selected between operations.
     */
    @Field(3)
    public NUR_AUTHENTICATE_PARAM reSelect(int reSelect) {
        this.io.setIntField(this, 3, reSelect);
        return this;
    }

    /**
     * < Response timeout similar to read or write; unit is milliseconds ranging from 20...50.
     */
    @Field(4)
    public int timeout() {
        return this.io.getIntField(this, 4);
    }

    /**
     * < Response timeout similar to read or write; unit is milliseconds ranging from 20...50.
     */
    @Field(4)
    public NUR_AUTHENTICATE_PARAM timeout(int timeout) {
        this.io.setIntField(this, 4, timeout);
        return this;
    }

    /**
     * < Additional wait time before executing the command in microseconds (0...50000); may be used to energize the tag properly (carrier is on).
     */
    @Field(5)
    public int preTxWait() {
        return this.io.getIntField(this, 5);
    }

    /**
     * < Additional wait time before executing the command in microseconds (0...50000); may be used to energize the tag properly (carrier is on).
     */
    @Field(5)
    public NUR_AUTHENTICATE_PARAM preTxWait(int preTxWait) {
        this.io.setIntField(this, 5, preTxWait);
        return this;
    }

    /**
     * < The CSI message's length in bits.
     */
    @Field(6)
    public int msgBitLength() {
        return this.io.getIntField(this, 6);
    }

    /**
     * < The CSI message's length in bits.
     */
    @Field(6)
    public NUR_AUTHENTICATE_PARAM msgBitLength(int msgBitLength) {
        this.io.setIntField(this, 6, msgBitLength);
        return this;
    }

    /**
     * < Must be taken care by the host application.<br>
     * C type : BYTE[128]
     */
    @Array({128})
    @Field(7)
    public Pointer<Byte> message() {
        return this.io.getPointerField(this, 7);
    }
}