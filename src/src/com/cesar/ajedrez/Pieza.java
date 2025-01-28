package src.com.cesar.ajedrez;

public class Pieza {
    private final PiezaID id;
    private final boolean bando;
    private boolean primerMove;

    public Pieza(PiezaID id, boolean bando, boolean primerMove) {
        this.id = id;
        this.bando = bando;
        this.primerMove = primerMove;
    }

    public Pieza(PiezaID id, boolean bando) {
        this.id = id;
        this.bando = bando;
        this.primerMove = true;
    }

    @Override
    public String toString() {
        String lado = bando ? "B" : "N";
        String tipo;
        switch (id.id()){
            case "torre" -> tipo = "T";
            case "alfil" -> tipo = "A";
            case "caballo" -> tipo = "C";
            case "reina" -> tipo = "Q";
            case "rey" -> tipo = "K";
            case "peon" -> tipo = "P";
            case "peonpassant" -> tipo = "E";
            default -> tipo = "";
        }
        return tipo + lado;
    }

    public PiezaID getId() {
        return id;
    }

    public boolean isBando() {
        return bando;
    }

    public boolean isPrimerMove() {
        return primerMove;
    }

    public void setPrimerMove(boolean primerMove) {
        this.primerMove = primerMove;
    }
}


