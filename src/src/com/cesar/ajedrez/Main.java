package src.com.cesar.ajedrez;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static src.com.cesar.ajedrez.Tablero.*;

public class Main {
    public static Tablero tablero = new Tablero();
    static boolean exit = false;

    public static void main(String[] args) {
        inicio();
        tablero.reset();
        tablero.show();

        while (!exit){
            menu();
        }
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);

        List<String> input = List.of(scanner.nextLine().toLowerCase().split(" "));

        switch (input.getFirst()){
            case "move" -> {
                if (!tablero.fin){
                    if (input.size() >= 5){
                        try {
                            int inicioFila = Integer.parseInt(input.get(1));
                            int inicioCol = Integer.parseInt(input.get(2));
                            int finFila = Integer.parseInt(input.get(3));
                            int finCol = Integer.parseInt(input.get(4));

                            if (tablero.getTablero2d()[inicioFila][inicioCol] != null){
                                if(tablero.getTablero2d()[inicioFila][inicioCol].isBando() == tablero.jugadorActivo){
                                    tablero.move(inicioFila, inicioCol, finFila, finCol, false);
                                }else {
                                    System.out.println("Le toca al otro bando!");
                                }
                            }else {
                                System.out.println("No hay ninguna pieza en esas coordenadas!");
                            }
                        }catch (InputMismatchException _){
                            System.out.println("Datos incorrectos!");
                        }
                    }else {
                        System.out.println("Faltan datos!");
                    }
                }else System.out.println("La partida ya ha acabado, ejecuten \"reset\" en caso de querer seguir jugando");
            }
            case "showmoves" -> {
                if (!tablero.fin){
                    if (input.size() >= 5){
                        try {
                            int inicioFila = Integer.parseInt(input.get(1));
                            int inicioCol = Integer.parseInt(input.get(2));
                            int finFila = Integer.parseInt(input.get(3));
                            int finCol = Integer.parseInt(input.get(4));

                            if (tablero.getTablero2d()[inicioFila][inicioCol] != null){
                                tablero.move(inicioFila, inicioCol, finFila, finCol, true);
                            }else {
                                System.out.println("No hay ninguna pieza en esas coordenadas!");
                            }
                        }catch (InputMismatchException _){
                            System.out.println("Datos incorrectos!");
                        }
                    }else {
                        System.out.println("Faltan datos!");
                    }
                }else System.out.println("La partida ya ha acabado, ejecuten \"reset\" en caso de querer seguir jugando");
            }
            case "changeplayer" -> {
                if (!tablero.fin){
                    tablero.jugadorActivo = !tablero.jugadorActivo;
                }else System.out.println("La partida ya ha acabado, ejecuten \"reset\" en caso de querer seguir jugando");
            }
            case "reset" -> {
                System.out.println("Empieza una nueva partida!");
                tablero.reset();
                tablero.show();
                tablero.jugadorActivo = true;
                tablero.fin = false;
            }
            case "refresh" -> tablero.show();
            case "guardar" -> Serializer.serialize(tablero);
            case "cargar" -> {
                    tablero = null;
                    tablero = ((Tablero) Serializer.deserialize());
            }
            case "exit" -> exit = true;
        }
    }

    public static void inicio(){
        System.out.println(
                """
                Por favor lea el README para aprender a usar el programa
                """
        );
    }
}
