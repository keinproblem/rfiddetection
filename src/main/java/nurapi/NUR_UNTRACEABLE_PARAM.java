package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * The Gen2 version 2 "Untraceable" command parameters.
 * <br>
 *
 * @sa NurApiGen2v2Untraceable, NurApiGen2v2Untraceable32, NurApiGen2v2UntraceableByEPC
 * <br>
 * @sa NUR_UTRACE_RANGE_NORMAL, NUR_UTRACE_RANGE_REDUCE<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:535</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_UNTRACEABLE_PARAM extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_UNTRACEABLE_PARAM() {
        super();
    }

    public NUR_UNTRACEABLE_PARAM(Pointer pointer) {
        super(pointer);
    }

    /**
     * < U-bit value; TRUE for '1', FALSE for '0'.
     */
    @Field(0)
    public int setU() {
        return this.io.getIntField(this, 0);
    }

    /**
     * < U-bit value; TRUE for '1', FALSE for '0'.
     */
    @Field(0)
    public NUR_UNTRACEABLE_PARAM setU(int setU) {
        this.io.setIntField(this, 0, setU);
        return this;
    }

    /**
     * < RX attenuation for response reception; TRUE causes "write-like" operation thus reduces operation's range.
     */
    @Field(1)
    public int rxAttn() {
        return this.io.getIntField(this, 1);
    }

    /**
     * < RX attenuation for response reception; TRUE causes "write-like" operation thus reduces operation's range.
     */
    @Field(1)
    public NUR_UNTRACEABLE_PARAM rxAttn(int rxAttn) {
        this.io.setIntField(this, 1, rxAttn);
        return this;
    }

    /**
     * < If TRUE then the EPC is hidden according to other rules.
     */
    @Field(2)
    public int hideEPC() {
        return this.io.getIntField(this, 2);
    }

    /**
     * < If TRUE then the EPC is hidden according to other rules.
     */
    @Field(2)
    public NUR_UNTRACEABLE_PARAM hideEPC(int hideEPC) {
        this.io.setIntField(this, 2, hideEPC);
        return this;
    }

    /**
     * < New length of the EPC in 16-bit words. Allowed range is 0..31.
     */
    @Field(3)
    public int epcWordLen() {
        return this.io.getIntField(this, 3);
    }

    /**
     * < New length of the EPC in 16-bit words. Allowed range is 0..31.
     */
    @Field(3)
    public NUR_UNTRACEABLE_PARAM epcWordLen(int epcWordLen) {
        this.io.setIntField(this, 3, epcWordLen);
        return this;
    }

    /**
     * < TID hiding policy: 0 = none, 1 = hide some, 2 = hide all.
     */
    @Field(4)
    public int tidPolicy() {
        return this.io.getIntField(this, 4);
    }

    /**
     * < TID hiding policy: 0 = none, 1 = hide some, 2 = hide all.
     */
    @Field(4)
    public NUR_UNTRACEABLE_PARAM tidPolicy(int tidPolicy) {
        this.io.setIntField(this, 4, tidPolicy);
        return this;
    }

    /**
     * < User memory hiding policy: TRUE = hide, FALSE = don't hide.
     */
    @Field(5)
    public int hideUser() {
        return this.io.getIntField(this, 5);
    }

    /**
     * < User memory hiding policy: TRUE = hide, FALSE = don't hide.
     */
    @Field(5)
    public NUR_UNTRACEABLE_PARAM hideUser(int hideUser) {
        this.io.setIntField(this, 5, hideUser);
        return this;
    }

    /**
     * < Only "normal" and "reduced" are supported at the moment.
     */
    @Field(6)
    public int rangePolicy() {
        return this.io.getIntField(this, 6);
    }

    /**
     * < Only "normal" and "reduced" are supported at the moment.
     */
    @Field(6)
    public NUR_UNTRACEABLE_PARAM rangePolicy(int rangePolicy) {
        this.io.setIntField(this, 6, rangePolicy);
        return this;
    }
}
