package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * Extended inventory function parameters
 * <br>
 *
 * @sa NurApiInventoryEx(), NurApiStartInventoryEx()<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:388</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_INVEX_PARAMS extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_INVEX_PARAMS() {
        super();
    }

    public NUR_INVEX_PARAMS(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public int Q() {
        return this.io.getIntField(this, 0);
    }

    @Field(0)
    public NUR_INVEX_PARAMS Q(int Q) {
        this.io.setIntField(this, 0, Q);
        return this;
    }

    @Field(1)
    public int session() {
        return this.io.getIntField(this, 1);
    }

    @Field(1)
    public NUR_INVEX_PARAMS session(int session) {
        this.io.setIntField(this, 1, session);
        return this;
    }

    @Field(2)
    public int rounds() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public NUR_INVEX_PARAMS rounds(int rounds) {
        this.io.setIntField(this, 2, rounds);
        return this;
    }

    @Field(3)
    public int transitTime() {
        return this.io.getIntField(this, 3);
    }

    @Field(3)
    public NUR_INVEX_PARAMS transitTime(int transitTime) {
        this.io.setIntField(this, 3, transitTime);
        return this;
    }

    @Field(4)
    public int inventoryTarget() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public NUR_INVEX_PARAMS inventoryTarget(int inventoryTarget) {
        this.io.setIntField(this, 4, inventoryTarget);
        return this;
    }

    @Field(5)
    public int inventorySelState() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public NUR_INVEX_PARAMS inventorySelState(int inventorySelState) {
        this.io.setIntField(this, 5, inventorySelState);
        return this;
    }
}
