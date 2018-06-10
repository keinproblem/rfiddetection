package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * Contains information about the current inventory read configuration.
 * <br>
 *
 * @sa NurApiGetInventoryRead, NUR_IRTYPE<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:163</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_IRINFORMATION extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_IRINFORMATION() {
        super();
    }

    public NUR_IRINFORMATION(Pointer pointer) {
        super(pointer);
    }

    /**
     * < Active / not.
     */
    @Field(0)
    public int active() {
        return this.io.getIntField(this, 0);
    }

    /**
     * < Active / not.
     */
    @Field(0)
    public NUR_IRINFORMATION active(int active) {
        this.io.setIntField(this, 0, active);
        return this;
    }

    /**
     * < Current inventory + read type.
     */
    @Field(1)
    public int type() {
        return this.io.getIntField(this, 1);
    }

    /**
     * < Current inventory + read type.
     */
    @Field(1)
    public NUR_IRINFORMATION type(int type) {
        this.io.setIntField(this, 1, type);
        return this;
    }

    /**
     * < Bank to read from. 0 means invalid.
     */
    @Field(2)
    public int bank() {
        return this.io.getIntField(this, 2);
    }

    /**
     * < Bank to read from. 0 means invalid.
     */
    @Field(2)
    public NUR_IRINFORMATION bank(int bank) {
        this.io.setIntField(this, 2, bank);
        return this;
    }

    /**
     * < Word address to read from.
     */
    @Field(3)
    public int wAddress() {
        return this.io.getIntField(this, 3);
    }

    /**
     * < Word address to read from.
     */
    @Field(3)
    public NUR_IRINFORMATION wAddress(int wAddress) {
        this.io.setIntField(this, 3, wAddress);
        return this;
    }

    /**
     * < Word length. 0 means invalid.
     */
    @Field(4)
    public int wLength() {
        return this.io.getIntField(this, 4);
    }

    /**
     * < Word length. 0 means invalid.
     */
    @Field(4)
    public NUR_IRINFORMATION wLength(int wLength) {
        this.io.setIntField(this, 4, wLength);
        return this;
    }
}