package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * Contains module's internal information.
 * <br>
 *
 * @sa NurApiGetSystemInfo<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:486</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_SYSTEMINFO extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_SYSTEMINFO() {
        super();
    }

    public NUR_SYSTEMINFO(Pointer pointer) {
        super(pointer);
    }

    @Field(0)
    public int blAddr() {
        return this.io.getIntField(this, 0);
    }

    @Field(0)
    public NUR_SYSTEMINFO blAddr(int blAddr) {
        this.io.setIntField(this, 0, blAddr);
        return this;
    }

    @Field(1)
    public int appAddr() {
        return this.io.getIntField(this, 1);
    }

    @Field(1)
    public NUR_SYSTEMINFO appAddr(int appAddr) {
        this.io.setIntField(this, 1, appAddr);
        return this;
    }

    @Field(2)
    public int vectorBase() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public NUR_SYSTEMINFO vectorBase(int vectorBase) {
        this.io.setIntField(this, 2, vectorBase);
        return this;
    }

    @Field(3)
    public int appSzWord() {
        return this.io.getIntField(this, 3);
    }

    @Field(3)
    public NUR_SYSTEMINFO appSzWord(int appSzWord) {
        this.io.setIntField(this, 3, appSzWord);
        return this;
    }

    @Field(4)
    public int appCRCWord() {
        return this.io.getIntField(this, 4);
    }

    @Field(4)
    public NUR_SYSTEMINFO appCRCWord(int appCRCWord) {
        this.io.setIntField(this, 4, appCRCWord);
        return this;
    }

    @Field(5)
    public int szFlash() {
        return this.io.getIntField(this, 5);
    }

    @Field(5)
    public NUR_SYSTEMINFO szFlash(int szFlash) {
        this.io.setIntField(this, 5, szFlash);
        return this;
    }

    @Field(6)
    public int szSram() {
        return this.io.getIntField(this, 6);
    }

    @Field(6)
    public NUR_SYSTEMINFO szSram(int szSram) {
        this.io.setIntField(this, 6, szSram);
        return this;
    }

    @Field(7)
    public int stackTop() {
        return this.io.getIntField(this, 7);
    }

    @Field(7)
    public NUR_SYSTEMINFO stackTop(int stackTop) {
        this.io.setIntField(this, 7, stackTop);
        return this;
    }

    @Field(8)
    public int nvSetAddr() {
        return this.io.getIntField(this, 8);
    }

    @Field(8)
    public NUR_SYSTEMINFO nvSetAddr(int nvSetAddr) {
        this.io.setIntField(this, 8, nvSetAddr);
        return this;
    }

    @Field(9)
    public int szNvSettings() {
        return this.io.getIntField(this, 9);
    }

    @Field(9)
    public NUR_SYSTEMINFO szNvSettings(int szNvSettings) {
        this.io.setIntField(this, 9, szNvSettings);
        return this;
    }

    @Field(10)
    public int mainStackUsage() {
        return this.io.getIntField(this, 10);
    }

    @Field(10)
    public NUR_SYSTEMINFO mainStackUsage(int mainStackUsage) {
        this.io.setIntField(this, 10, mainStackUsage);
        return this;
    }

    @Field(11)
    public int szUsedSram() {
        return this.io.getIntField(this, 11);
    }

    @Field(11)
    public NUR_SYSTEMINFO szUsedSram(int szUsedSram) {
        this.io.setIntField(this, 11, szUsedSram);
        return this;
    }

    @Field(12)
    public int szTagBuffer() {
        return this.io.getIntField(this, 12);
    }

    @Field(12)
    public NUR_SYSTEMINFO szTagBuffer(int szTagBuffer) {
        this.io.setIntField(this, 12, szTagBuffer);
        return this;
    }
}
