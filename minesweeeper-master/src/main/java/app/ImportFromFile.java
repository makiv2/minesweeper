package app;

import java.io.*;

public class ImportFromFile implements ImportGameProgress {
    @Override
    public Object importGame(byte[] gameFile) {


        ByteArrayInputStream byteStream = new ByteArrayInputStream(gameFile);
        ObjectInputStream objStream;
        try {
            objStream = new ObjectInputStream(byteStream);
            return objStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }
}
