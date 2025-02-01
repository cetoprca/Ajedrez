package src.com.cesar.ajedrez;

import java.util.ArrayList;
import java.util.List;

public class Logica{
    //Movimiento basico vertical y horizontal sin limite
    public static List<int[]> legalMoveVERyHOR(int[] Opos, int[] Dpos, Pieza[][] tablero2d){
        List<int[]> legalMoves = new ArrayList<>();

        int repeticiones = 0;
        int constante = 1;

        //Comprobar si la posicion de destino está hacia arriba o hacia abajo
        //Si se va hacia arriba constante se vuelve -1 para que al iterar se desplaze al reves
        if (Opos[0] < Dpos[0]){
            repeticiones = tablero2d.length-Opos[0];
        } else if (Opos[0] > Dpos[0]) {
            repeticiones = Opos[0]+1;
            constante = -1;
        }

        //Itera x veces dependiendo de la distancia a los bordes
        for (int i = 1; i < repeticiones; i++) {
            //Si la casilla no contiene pieza (es null) se añade la posicion como legal
            if (tablero2d[Opos[0]+(i*constante)][Opos[1]] == null){
                int[] Cpos = {Opos[0]+(i*constante), Opos[1]};
                legalMoves.add(Cpos);
                continue;
            }
            //Si la casilla contiene pieza acaba el bucle, si es del bando contrario añade la posicion como legal
            if (tablero2d[Opos[0]+(i*constante)][Opos[1]].isBando() != tablero2d[Opos[0]][Opos[1]].isBando()){
                int[] Cpos = {Opos[0]+(i*constante), Opos[1]};
                legalMoves.add(Cpos);
            }
            break;
        }

        repeticiones = 0;
        constante = 1;

        //Comprobar si la posicion de destino está hacia la derecha o hacia la izquierda
        //Si se va hacia la izquierda constante se vuelve -1 para que al iterar se desplaze al reves
        if (Opos[1] < Dpos[1]){
            repeticiones = tablero2d.length-Opos[1];
        } else if (Opos[1] > Dpos[1]) {
            repeticiones = Opos[1]+1;
            constante = -1;
        }

        //Itera x veces dependiendo de la distancia a los bordes
        for (int i = 1; i < repeticiones; i++) {
            //Si la casilla no contiene pieza (es null) se añade la posicion como legal
            if (tablero2d[Opos[0]][Opos[1]+(i*constante)] == null){
                int[] Cpos = {Opos[0], Opos[1]+(i*constante)};
                legalMoves.add(Cpos);
                continue;
            }
            //Si la casilla contiene pieza del bando contrario añade la posicion como legal y termina el bucle
            if (tablero2d[Opos[0]][Opos[1]+(i*constante)].isBando() != tablero2d[Opos[0]][Opos[1]].isBando()){
                int[] Cpos = {Opos[0], Opos[1]+(i*constante)};
                legalMoves.add(Cpos);
            }
            break;
        }

        return legalMoves;
    }

    //Movimiento basico en diagonal sin limite
    public static List<int[]> legalMoveDIAGONAL(int[] Opos, int[] Dpos, Pieza[][] tablero2d){
        List<int[]> legalMoves = new ArrayList<>();

        int repeticiones = 0;
        int constante1 = 1;
        int constante2 = 1;

        int distabajo = (tablero2d.length-1) - Opos[0];
        int distderecha = (tablero2d.length-1)-Opos[1];

        //Se comprueba la direccion del destino y se actualizan las repeticiones segun la distancia
        //En caso de ir hacia arriba constante1 se vuelve -1
        //En caso de ir hacia la izquierda constante2 se vuelve -1
        if (Opos[0] > Dpos[0] && Opos[1] > Dpos[1]){
            repeticiones = Math.max(Opos[0], Opos[1]) - (Math.abs(Opos[0] - Opos[1]))+1;
            constante1 = -1;
            constante2 = -1;
        }
        if (Opos[0] > Dpos[0] && Opos[1] < Dpos[1]){
            repeticiones = Math.max(Opos[0], distderecha) - (Math.abs(Opos[0] - distderecha))+1;
            constante1 = -1;
        }
        if (Opos[0] < Dpos[0] && Opos[1] < Dpos[1]){
            repeticiones = Math.max(distabajo, distderecha) - (Math.abs(distabajo - distderecha))+1;
        }
        if (Opos[0] < Dpos[0] && Opos[1] > Dpos[1]){
            repeticiones = Math.max(distabajo, Opos[1]) - (Math.abs(distabajo - Opos[1]))+1;
            constante2 = -1;
        }

        //Se itera x veces segun la distancia
        for (int i = 1; i < repeticiones; i++) {
            //Si la casilla no contiene pieza (es null) se añade la posicion como legal
            if (tablero2d[Opos[0]+(i*constante1)][Opos[1]+(i*constante2)] == null){
                int[] Cpos = {Opos[0]+(i*constante1), Opos[1]+(i*constante2)};
                legalMoves.add(Cpos);
                continue;
            }
            //Si la casilla contiene pieza del bando contrario añade la posicion como legal y termina el bucle
            if (tablero2d[Opos[0]+(i*constante1)][Opos[1]+(i*constante2)].isBando() != tablero2d[Opos[0]][Opos[1]].isBando()){
                int[] Cpos = {Opos[0]+(i*constante1), Opos[1]+(i*constante2)};
                legalMoves.add(Cpos);
            }
            break;
        }

        return legalMoves;
    }

    //Movimiento basico vertical y horizontal con limite
    public static List<int[]> legalMoveVERyHOR(int[] Opos, int[] Dpos, int limit, Pieza[][] tablero2d){
        List<int[]> legalMoves = new ArrayList<>();

        int constante = 1;

        if (Opos[0] > Dpos[0]) {
            limit++;
            constante = -1;
        }

        for (int i = 1; i < limit+1; i++) {
            if (Opos[0]+(i*constante) < 0 || Opos[0]+(i*constante) > 7){
                continue;
            }

            if (tablero2d[Opos[0]+(i*constante)][Opos[1]] == null){
                int[] Cpos = {Opos[0]+(i*constante), Opos[1]};
                legalMoves.add(Cpos);
                continue;
            }
            if (tablero2d[Opos[0]+(i*constante)][Opos[1]].isBando() != tablero2d[Opos[0]][Opos[1]].isBando()){
                int[] Cpos = {Opos[0]+(i*constante), Opos[1]};
                legalMoves.add(Cpos);
                break;
            }
        }

        constante = 1;

        if (Opos[1] > Dpos[1]) {
            limit++;
            constante = -1;
        }

        for (int i = 1; i < limit+1; i++) {
            if (Opos[1]+(i*constante) < 0 || Opos[1]+(i*constante) > 7){
                continue;
            }

            if (tablero2d[Opos[0]][Opos[1]+(i*constante)] == null){
                int[] Cpos = {Opos[0], Opos[1]+(i*constante)};
                legalMoves.add(Cpos);
                continue;
            }
            if (tablero2d[Opos[0]][Opos[1]+(i*constante)].isBando() != tablero2d[Opos[0]][Opos[1]].isBando()){
                int[] Cpos = {Opos[0], Opos[1]+(i*constante)};
                legalMoves.add(Cpos);
                break;
            }
        }

        return legalMoves;
    }

    //Movimiento basico en diagonal con limite
    public static List<int[]> legalMoveDIAGONAL(int[] Opos, int[] Dpos, int limit, Pieza[][] tablero2d){
        List<int[]> legalMoves = new ArrayList<>();

        int constante1 = 1;
        int constante2 = 1;

        if (Opos[0] > Dpos[0] && Opos[1] > Dpos[1]){
            constante1 = -1;
            constante2 = -1;
        }
        if (Opos[0] > Dpos[0] && Opos[1] < Dpos[1]){
            constante1 = -1;
        }
        if (Opos[0] < Dpos[0] && Opos[1] > Dpos[1]){
            constante2 = -1;
        }

        for (int i = 1; i < limit+1; i++) {
            if (Opos[0]+(i*constante1) > 7 || Opos[1]+(i*constante2) > 7 || Opos[0]+(i*constante1) < 0 || Opos[1]+(i*constante2) < 0){
                continue;
            }

            if (tablero2d[Opos[0]+(i*constante1)][Opos[1]+(i*constante2)] == null){
                int[] Cpos = {Opos[0]+(i*constante1), Opos[1]+(i*constante2)};
                legalMoves.add(Cpos);
                continue;
            }
            if (tablero2d[Opos[0]+(i*constante1)][Opos[1]+(i*constante2)].isBando() != tablero2d[Opos[0]][Opos[1]].isBando()){
                int[] Cpos = {Opos[0]+(i*constante1), Opos[1]+(i*constante2)};
                legalMoves.add(Cpos);
            }
            break;
        }

        return legalMoves;
    }

    //Funciones especificas

    public static List<int[]> legalMoveRey(int[] Opos, int[] Dpos, Pieza[][] tablero2d){
        List<int[]> legalMoves = new ArrayList<>();

        legalMoves.addAll(legalMoveVERyHOR(Opos, Dpos, 1, tablero2d));
        legalMoves.addAll(legalMoveDIAGONAL(Opos, Dpos, 1, tablero2d));

        return legalMoves;
    }

    public static List<int[]> legalMovePeon(int[] pos, Pieza[][] tablero2d){
        List<int[]> legalMoves = new ArrayList<>();

        if (!tablero2d[pos[0]][pos[1]].isBando()){
            //Si es peon negro

            //Avanzar
            try {
                //Si es el primer movimiento, 2 iteraciones hacia abajo, en caso contrario 1
                if (tablero2d[pos[0]][pos[1]].isPrimerMove()){
                    for (int i = 1; i < 3; i++) {
                        if (tablero2d[pos[0]+i][pos[1]] == null){
                            int[] Cpos = {pos[0]+i, pos[1]};
                            legalMoves.add(Cpos);
                        }else break;
                    }
                }else {
                    for (int i = 1; i < 2; i++) {
                        if (tablero2d[pos[0]+i][pos[1]] == null){
                            int[] Cpos = {pos[0]+i, pos[1]};
                            legalMoves.add(Cpos);
                        }else break;
                    }
                }
            }catch (Exception _){

            }


            try {
                //Si la casilla tiene una pieza del otro bando la posicion se registra como valida
                if (tablero2d[pos[0]+1][pos[1]-1] != null){
                    if (tablero2d[pos[0]+1][pos[1]-1].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                        int[] Cpos = {pos[0]+1, pos[1]-1};
                        legalMoves.add(Cpos);
                    }
                }
            }catch (Exception _){

            }

            //Atacar derecha
            try {
                //Si la casilla tiene una pieza del otro bando la posicion se registra como valida
                if (tablero2d[pos[0]+1][pos[1]+1] != null){
                    if (tablero2d[pos[0]+1][pos[1]+1].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                        int[] Cpos = {pos[0]+1, pos[1]+1};
                        legalMoves.add(Cpos);
                    }
                }
            }catch (Exception _){

            }

        }else {
            //Si es peon blanco

            //Avanzar
            try {
                //Si es el primer movimiento, 2 iteraciones hacia arriba, en caso contrario 1
                if (tablero2d[pos[0]][pos[1]].isPrimerMove()){
                    for (int i = 1; i < 3; i++) {
                        if (tablero2d[pos[0]-i][pos[1]] == null){
                            int[] Cpos = {pos[0]-i, pos[1]};
                            legalMoves.add(Cpos);
                        }else break;
                    }
                }else {
                    for (int i = 1; i < 2; i++) {
                        if (tablero2d[pos[0]-i][pos[1]] == null){
                            int[] Cpos = {pos[0]-i, pos[1]};
                            legalMoves.add(Cpos);
                        }else break;
                    }
                }
            }catch (Exception _){

            }

            //Atacar derecha
            try {
                //Si la casilla tiene una pieza del otro bando la posicion se registra como valida
                if (tablero2d[pos[0]-1][pos[1]+1] != null){
                    if (tablero2d[pos[0]-1][pos[1]+1].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                        int[] Cpos = {pos[0]-1, pos[1]+1};
                        legalMoves.add(Cpos);
                    }
                }
            }catch (Exception _){

            }

            //Atacar izquierda
            try {
                //Si la casilla tiene una pieza del otro bando la posicion se registra como valida
                if (tablero2d[pos[0]-1][pos[1]-1] != null){
                    if (tablero2d[pos[0]-1][pos[1]-1].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                        int[] Cpos = {pos[0]-1, pos[1]-1};
                        legalMoves.add(Cpos);
                    }
                }
            }catch (Exception _){

            }
        }

        return legalMoves;
    }

    public static List<int[]> legalMoveCaballo(int[] pos, Pieza[][] tablero2d){
        List<int[]> legalMoves = new ArrayList<>();

        //Movimientos hard codeados

        try {
            if (tablero2d[pos[0]+2][pos[1]+1] == null){
                int[] Cpos = {pos[0]+2, pos[1]+1};
                legalMoves.add(Cpos);
            }else if (tablero2d[pos[0]+2][pos[1]+1].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                int[] Cpos = {pos[0]+2, pos[1]+1};
                legalMoves.add(Cpos);
            }
        }catch (Exception _){

        }

        try {
            if (tablero2d[pos[0]+2][pos[1]-1] == null){
                int[] Cpos = {pos[0]+2, pos[1]-1};
                legalMoves.add(Cpos);
            }else if (tablero2d[pos[0]+2][pos[1]-1].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                int[] Cpos = {pos[0]+2, pos[1]-1};
                legalMoves.add(Cpos);
            }
        }catch (Exception _){

        }

        try {
            if (tablero2d[pos[0]-2][pos[1]+1] == null){
                int[] Cpos = {pos[0]-2, pos[1]+1};
                legalMoves.add(Cpos);
            }else if (tablero2d[pos[0]-2][pos[1]+1].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                int[] Cpos = {pos[0]-2, pos[1]+1};
                legalMoves.add(Cpos);
            }
        }catch (Exception _){

        }

        try {
            if (tablero2d[pos[0]-2][pos[1]-1] == null){
                int[] Cpos = {pos[0]-2, pos[1]-1};
                legalMoves.add(Cpos);
            }else if (tablero2d[pos[0]-2][pos[1]-1].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                int[] Cpos = {pos[0]-2, pos[1]-1};
                legalMoves.add(Cpos);
            }
        }catch (Exception _){

        }

        try {
            if (tablero2d[pos[0]+1][pos[1]+2] == null){
                int[] Cpos = {pos[0]+1, pos[1]+2};
                legalMoves.add(Cpos);
            }else if (tablero2d[pos[0]+1][pos[1]+2].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                int[] Cpos = {pos[0]+1, pos[1]+2};
                legalMoves.add(Cpos);
            }
        }catch (Exception _){

        }

        try {
            if (tablero2d[pos[0]+1][pos[1]-2] == null){
                int[] Cpos = {pos[0]+1, pos[1]-2};
                legalMoves.add(Cpos);
            }else if (tablero2d[pos[0]+1][pos[1]-2].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                int[] Cpos = {pos[0]+1, pos[1]-2};
                legalMoves.add(Cpos);
            }
        }catch (Exception _){

        }

        try {
            if (tablero2d[pos[0]-1][pos[1]+2] == null){
                int[] Cpos = {pos[0]-1, pos[1]+2};
                legalMoves.add(Cpos);
            }else if (tablero2d[pos[0]-1][pos[1]+2].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                int[] Cpos = {pos[0]-1, pos[1]+2};
                legalMoves.add(Cpos);
            }
        }catch (Exception _){

        }

        try {
            if (tablero2d[pos[0]-1][pos[1]-2] == null){
                int[] Cpos = {pos[0]-1, pos[1]-2};
                legalMoves.add(Cpos);
            }else if (tablero2d[pos[0]-1][pos[1]-2].isBando() != tablero2d[pos[0]][pos[1]].isBando()){
                int[] Cpos = {pos[0]-1, pos[1]-2};
                legalMoves.add(Cpos);
            }
        }catch (Exception _){

        }

        return legalMoves;
    }
}
