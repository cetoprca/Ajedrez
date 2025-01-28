package src.com.cesar.ajedrez;

import java.io.Serializable;

public record PiezaID(String id) implements Serializable {
    public static PiezaID Torre = new PiezaID("torre");
    public static PiezaID Caballo = new PiezaID("caballo");
    public static PiezaID Alfil = new PiezaID("alfil");
    public static PiezaID Reina = new PiezaID("reina");
    public static PiezaID Rey = new PiezaID("rey");
    public static PiezaID Peon = new PiezaID("peon");
    public static PiezaID PeonPassant = new PiezaID("peonpassant");
}