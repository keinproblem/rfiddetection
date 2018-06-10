package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * Extended inventory function filter parameters. Represents G2 Select command, see UHF G2 standard section 6.3.2.11.1.1
 * <br>
 *
 * @sa NurApiInventoryEx(), NurApiStartInventoryEx()<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:417</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_INVEX_FILTER extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_INVEX_FILTER() {
        super();
    }

    public NUR_INVEX_FILTER(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public int truncate() {
        return this.io.getIntField(this, 0);
    }

    @Field(0)
    public NUR_INVEX_FILTER truncate(int truncate) {
        this.io.setIntField(this, 0, truncate);
        return this;
    }

    @Field(1)
    public byte target() {
        return this.io.getByteField(this, 1);
    }

    @Field(1)
    public NUR_INVEX_FILTER target(byte target) {
        this.io.setByteField(this, 1, target);
        return this;
    }

    @Field(2)
    public byte action() {
        return this.io.getByteField(this, 2);
    }

    @Field(2)
    public NUR_INVEX_FILTER action(byte action) {
        this.io.setByteField(this, 2, action);
        return this;
    }

    @Field(3)
    public byte bank() {
        return this.io.getByteField(this, 3);
    }

    @Field(3)
    public NUR_INVEX_FILTER bank(byte bank) {
        this.io.setByteField(this, 3, bank);
        return this;
    }

    @Field(4)
    public int address() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public NUR_INVEX_FILTER address(int address) {
        this.io.setIntField(this, 4, address);
        return this;
    }

    @Field(5)
    public int maskBitLength() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public NUR_INVEX_FILTER maskBitLength(int maskBitLength) {
        this.io.setIntField(this, 5, maskBitLength);
        return this;
    }

    /**
     * C type : BYTE[(62)]
     */
    @Array({62})
    @Field(6)
    public Pointer<Byte> maskData() {
        return this.io.getPointerField(this, 6);
    }
}
