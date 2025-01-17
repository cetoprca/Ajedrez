package src.com.cesar.ajedrez;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static Tablero tablero = new Tablero();
    public static boolean jugadorActivo = true;
    public static void main(String[] args) {
//        tablero.reset();

        tablero.add(1,0, new Pieza(PiezaID.Peon, true));
        tablero.show();

        while (true){
            menu();
        }
    }

    public static void menu(){
        Scanner scanner = new Scanner(System.in);

        List<String> input = List.of(scanner.nextLine().toLowerCase().split(" "));

        switch (input.getFirst()){
            case "move" -> {
                if (input.size() >= 5){
                    int inicioFila = Integer.parseInt(input.get(1));
                    int inicioCol = Integer.parseInt(input.get(2));
                    int finFila = Integer.parseInt(input.get(3));
                    int finCol = Integer.parseInt(input.get(4));

                    if (tablero.getTablero2d()[inicioFila][inicioCol] != null){
                        if(tablero.getTablero2d()[inicioFila][inicioCol].isBando() == jugadorActivo){
                            tablero.move(inicioFila, inicioCol, finFila, finCol);
                        }else {
                            System.out.println("Le toca al otro bando!");
                        }
                    }else {
                        System.out.println("No hay ninguna pieza en esas coordenadas!");
                    }
                }else {
                    System.out.println("Faltan datos!");
                }
            }
            case "showmoves" -> {
                if (input.size() >= 5){
                    int inicioFila = Integer.parseInt(input.get(1));
                    int inicioCol = Integer.parseInt(input.get(2));
                    int finFila = Integer.parseInt(input.get(3));
                    int finCol = Integer.parseInt(input.get(4));

                    tablero.showlegalmoves(inicioFila, inicioCol, finFila, finCol);
                }else {
                    System.out.println("Faltan datos!");
                }
            }
            case "changeplayer" -> jugadorActivo = !jugadorActivo;
            case "reset" -> tablero.reset();
        }
    }

}
