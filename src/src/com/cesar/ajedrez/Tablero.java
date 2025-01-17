package src.com.cesar.ajedrez;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Tablero {
    protected static int size;
    protected static Pieza[][] tablero2d;

    public Tablero(int size) {
        this.size = size;
        tablero2d = new Pieza[size][size];
    }

    public Tablero() {
        this.size = 8;
        tablero2d = new Pieza[size][size];
    }

    public Pieza[][] getTablero2d() {
        return tablero2d;
    }

    public void reset(){
        for (int i = 0; i < tablero2d.length; i++) {
            for (int j = 0; j < tablero2d[i].length; j++) {
                switch (i){
                    case 0 -> {
                        switch (j){
                            case 0,7 -> tablero2d[i][j] = new Pieza(PiezaID.Torre, false);
                            case 1,6 -> tablero2d[i][j] = new Pieza(PiezaID.Caballo, false);
                            case 2,5 -> tablero2d[i][j] = new Pieza(PiezaID.Alfil, false);
                            case 3 -> tablero2d[i][j] = new Pieza(PiezaID.Rey, false);
                            case 4 -> tablero2d[i][j] = new Pieza(PiezaID.Reina, false);
                        }
                    }
                    case 1 -> tablero2d[i][j] = new Pieza(PiezaID.Peon, false);

                    case 7 -> {
                        switch (j){
                            case 0,7 -> tablero2d[i][j] = new Pieza(PiezaID.Torre, true);
                            case 1,6 -> tablero2d[i][j] = new Pieza(PiezaID.Caballo, true);
                            case 2,5 -> tablero2d[i][j] = new Pieza(PiezaID.Alfil, true);
                            case 3 -> tablero2d[i][j] = new Pieza(PiezaID.Reina, true);
                            case 4 -> tablero2d[i][j] = new Pieza(PiezaID.Rey, true);
                        }
                    }
                    case 6 -> tablero2d[i][j] = new Pieza(PiezaID.Peon, true);
                }
            }
        }
        show();
    }

    public void move(int Ofila, int Ocolumna, int Dfila, int Dcolumna){
        int[] coordOrigen = {Ofila, Ocolumna};
        int[] coordDestino = {Dfila, Dcolumna};

        Pieza piezaOrigen = null;
        try {
            piezaOrigen = tablero2d[coordOrigen[0]][coordOrigen[1]];
        }catch (NullPointerException _){
            System.out.println("No se ha encontrado ninguna pieza en la casilla seleccionada");
        }

        List<int[]> posLegal = new ArrayList<>();

        if (piezaOrigen != null){
            switch (piezaOrigen.getId().id()){
                case "torre" -> {
                    List<int[]> legalMovesVERyHOR = Logica.legalMoveVERyHOR(coordOrigen, coordDestino);

                    posLegal.addAll(legalMovesVERyHOR);
                }
                case "alfil" -> {
                    List<int[]> legalMovesDIAGONAL = Logica.legalMoveDIAGONAL(coordOrigen, coordDestino);

                    posLegal.addAll(legalMovesDIAGONAL);
                }
                case "reina" -> {
                    List<int[]> legalMovesVERyHOR = Logica.legalMoveVERyHOR(coordOrigen, coordDestino);
                    List<int[]> legalMovesDIAGONAL = Logica.legalMoveDIAGONAL(coordOrigen, coordDestino);

                    posLegal.addAll(legalMovesDIAGONAL);
                    posLegal.addAll(legalMovesVERyHOR);
                }
                case "rey" -> {
                    List<int[]> legalMovesRey = Logica.legalMoveRey(coordOrigen, coordDestino);

                    posLegal.addAll(legalMovesRey);
                }
                case "peon" -> {
                    List<int[]> posLegalPeon = Logica.legalMovePeon(coordOrigen);

                    posLegal.addAll(posLegalPeon);
                }
                case "caballo" -> {
                    List<int[]> posLegalCaballo = Logica.legalMoveCaballo(coordOrigen);

                    posLegal.addAll(posLegalCaballo);
                }
            }
        }

        boolean moveLegal = false;
        for (int[] ints : posLegal){
            if (ints[0] == coordDestino[0] && ints[1] == coordDestino[1]) {
                moveLegal = true;
                break;
            }
        }


        if (moveLegal){
            tablero2d[coordDestino[0]][coordDestino[1]] = piezaOrigen;
            tablero2d[coordOrigen[0]][coordOrigen[1]] = null;

            for (int i = 0; i < tablero2d.length; i++) {
                for (int j = 0; j < tablero2d[i].length; j++) {
                    if (tablero2d[i][j] != null){
                        if (tablero2d[i][j].getId() == PiezaID.PeonPassant){
                            tablero2d[i][j] = null;
                        }
                    }
                }
            }

            if (piezaOrigen.getId() == PiezaID.Peon && piezaOrigen.isPrimerMove()){
                if ((Math.max(coordOrigen[0],coordDestino[0]) - Math.min(coordOrigen[0],coordDestino[0])) == 2){
                    if (piezaOrigen.isBando()){
                        tablero2d[coordOrigen[0]-1][coordOrigen[1]] = new Pieza(PiezaID.PeonPassant, piezaOrigen.isBando());
                    }else {
                        tablero2d[coordOrigen[0]+1][coordOrigen[1]] = new Pieza(PiezaID.PeonPassant, piezaOrigen.isBando());
                    }
                }
            }
            if (piezaOrigen.getId() == PiezaID.Peon && (coordDestino[0] == 0 || coordDestino[0] == 7)){
                promocion(coordDestino[0], coordDestino[1], piezaOrigen.isBando());
            }

            tablero2d[coordDestino[0]][coordDestino[1]].setPrimerMove(false);
            Main.jugadorActivo = !Main.jugadorActivo;
        }

        show();
    }

    public void promocion(int fila, int columna, boolean bando){
        Scanner scanner = new Scanner(System.in);

        System.out.println("A qué pieza va a ser promociado este peón?");
        System.out.println("1 = Reina\n2 = Torre\n3 = Caballo\n4 = Alfil\nCualquier otro valor será considerado Reina");

        switch (scanner.nextLine()){
            case "2" -> tablero2d[fila][columna] = new Pieza(PiezaID.Torre, bando);
            case "3" -> tablero2d[fila][columna] = new Pieza(PiezaID.Caballo, bando);
            case "4" -> tablero2d[fila][columna] = new Pieza(PiezaID.Alfil, bando);
            default -> tablero2d[fila][columna] = new Pieza(PiezaID.Reina, bando);
        }
    }

    public void remove(int fila, int columna){
        tablero2d[fila][columna] = null;
    }

    public void add(int fila, int columna, Pieza pieza){
        tablero2d[fila][columna] = pieza;
    }

    public void show(){
        for (int i = 0; i < tablero2d.length; i++) {
            String msg = "";
            for (int j = 0; j < tablero2d.length; j++) {
                if (tablero2d[i][j] == null){
                    msg += "[] ";
                }
                else msg += tablero2d[i][j] + " ";
            }
            System.out.println(msg);
        }
    }

    public void showlegalmoves(int Ofila, int Ocolumna, int Dfila, int Dcolumna){
        int[] coordOrigen = {Ofila, Ocolumna};
        int[] coordDestino = {Dfila, Dcolumna};

        Pieza piezaOrigen = null;
        try {
            piezaOrigen = tablero2d[coordOrigen[0]][coordOrigen[1]];
        }catch (NullPointerException _){
            System.out.println("No se ha encontrado ninguna pieza en la casilla seleccionada");
        }

        List<int[]> posLegal = new ArrayList<>();

        if (piezaOrigen != null){
            switch (piezaOrigen.getId().id()){
                case "torre" -> {
                    List<int[]> legalMovesVERyHOR = Logica.legalMoveVERyHOR(coordOrigen, coordDestino);

                    posLegal.addAll(legalMovesVERyHOR);
                }
                case "alfil" -> {
                    List<int[]> legalMovesDIAGONAL = Logica.legalMoveDIAGONAL(coordOrigen, coordDestino);

                    posLegal.addAll(legalMovesDIAGONAL);
                }
                case "reina" -> {
                    List<int[]> legalMovesVERyHOR = Logica.legalMoveVERyHOR(coordOrigen, coordDestino);
                    List<int[]> legalMovesDIAGONAL = Logica.legalMoveDIAGONAL(coordOrigen, coordDestino);

                    posLegal.addAll(legalMovesDIAGONAL);
                    posLegal.addAll(legalMovesVERyHOR);
                }
                case "rey" -> {
                    List<int[]> legalMovesRey = Logica.legalMoveRey(coordOrigen, coordDestino);

                    posLegal.addAll(legalMovesRey);
                }
                case "peon" -> {
                    List<int[]> posLegalPeon = Logica.legalMovePeon(coordOrigen);

                    posLegal.addAll(posLegalPeon);
                }
                case "caballo" -> {
                    List<int[]> posLegalCaballo = Logica.legalMoveCaballo(coordOrigen);

                    posLegal.addAll(posLegalCaballo);
                }
            }
        }

        for (int[] pos : posLegal){
            System.out.println(Arrays.toString(pos));
        }
    }
}
