package nurapi;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;

/**
 * Contains information about connected client
 * <br>
 *
 * @sa NUR_NOTIFICATION_CLIENTCONNECTED<br>
 * <i>native declaration : nur_sdk-master\native\include\NurAPI.h:467</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("NurApi")
public class NUR_CLIENT_INFO extends StructObject {
    static {
        BridJ.register();
    }

    public NUR_CLIENT_INFO() {
        super();
    }

    public NUR_CLIENT_INFO(Pointer pointer) {
        super(pointer);
    }

    /**
     * C type : HANDLE
     */
    @Field(0)
    public Pointer<?> clientApi() {
        return this.io.getPointerField(this, 0);
    }

    /**
     * C type : HANDLE
     */
    @Field(0)
    public NUR_CLIENT_INFO clientApi(Pointer<?> clientApi) {
        this.io.setPointerField(this, 0, clientApi);
        return this;
    }

    /**
     * C type : TCHAR[32]
     */
    @Array({32})
    @Field(1)
    public Pointer<Byte> ip() {
        return this.io.getPointerField(this, 1);
    }

    @Field(2)
    public int port() {
        return this.io.getIntField(this, 2);
    }

    @Field(2)
    public NUR_CLIENT_INFO port(int port) {
        this.io.setIntField(this, 2, port);
        return this;
    }
}
