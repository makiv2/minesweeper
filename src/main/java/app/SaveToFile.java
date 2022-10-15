package app;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveToFile implements SaveGameProgress {
    @Override
    public void saveGame(Board obj) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
        Date date = new Date();
        String strDate = formatter.format(date);

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            byte [] data = bos.toByteArray();
            FileOutputStream out = new FileOutputStream(strDate);
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
