package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * Single GPIO configuration.
 * <br>
 *
 * @sa NUR_GPIO_CONFIG, NurApiSetGPIOConfig(), NurApiGetGPIOConfig(), NUR_NOTIFICATION_IOCHANGE, NUR_NOTIFICATION_TRIGGERREAD, NUR_GPIO_ACTION, NUR_GPIO_EDGE<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:246</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_GPIO_ENTRY extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_GPIO_ENTRY() {
        super();
    }

    public NUR_GPIO_ENTRY(Pointer pointer) {
        super(pointer);
    }

    /**
     * < TRUE if configuration is available. When writing new configuration set to FALSE if no need to configure this GPIO.
     */
    @Field(0)
    public int available() {
        return this.io.getIntField(this, 0);
    }

    /**
     * < TRUE if configuration is available. When writing new configuration set to FALSE if no need to configure this GPIO.
     */
    @Field(0)
    public NUR_GPIO_ENTRY available(int available) {
        this.io.setIntField(this, 0, available);
        return this;
    }

    /**
     * < TRUE GPIO is enabled.
     */
    @Field(1)
    public int enabled() {
        return this.io.getIntField(this, 1);
    }

    /**
     * < TRUE GPIO is enabled.
     */
    @Field(1)
    public NUR_GPIO_ENTRY enabled(int enabled) {
        this.io.setIntField(this, 1, enabled);
        return this;
    }

    /**
     * < Type of GPIO. see enum NUR_GPIO_TYPE.
     */
    @Field(2)
    public int type() {
        return this.io.getIntField(this, 2);
    }

    /**
     * < Type of GPIO. see enum NUR_GPIO_TYPE.
     */
    @Field(2)
    public NUR_GPIO_ENTRY type(int type) {
        this.io.setIntField(this, 2, type);
        return this;
    }

    /**
     * < Trigger edge, falling, rising or both. see enum NUR_GPIO_EDGE.
     */
    @Field(3)
    public int edge() {
        return this.io.getIntField(this, 3);
    }

    /**
     * < Trigger edge, falling, rising or both. see enum NUR_GPIO_EDGE.
     */
    @Field(3)
    public NUR_GPIO_ENTRY edge(int edge) {
        this.io.setIntField(this, 3, edge);
        return this;
    }

    /**
     * < Trigger action. see enum NUR_GPIO_ACTION.
     */
    @Field(4)
    public int action() {
        return this.io.getIntField(this, 4);
    }

    /**
     * < Trigger action. see enum NUR_GPIO_ACTION.
     */
    @Field(4)
    public NUR_GPIO_ENTRY action(int action) {
        this.io.setIntField(this, 4, action);
        return this;
    }
}
