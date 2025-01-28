package src.com.cesar.ajedrez;

import java.io.*;

public class Serializer {
    public static void serialize(Object objeto) {
        try {
            FileOutputStream fos = new FileOutputStream("tablero.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(objeto);
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object deserialize() {
        Object newobj = null;
        try {
            FileInputStream fis = new FileInputStream("tablero.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            newobj = ois.readObject();
            ois.close();
            fis.close();
        }catch (IOException | ClassNotFoundException _){
            throw new RuntimeException("Error deserializando");
        }

        return newobj;
    }
}
