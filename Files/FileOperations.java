package Files;

import java.io.*;
import java.util.ArrayList;

public class FileOperations
{
    public static <T> void writeToFile(T object, String file) throws IOException
    {
        File file1 = new File(file);
        ObjectOutputStream oos;
        if (file1.exists())
        {
            oos = new MyObjectOutputStream(new FileOutputStream(file1,true));
        }
        else
        {
            oos = new ObjectOutputStream(new FileOutputStream(file1));
        }
        oos.writeObject(object);
        oos.close();
    }

    public static <T> ArrayList<T> readAllObject(String file) throws IOException
    {
        File file1 = new File(file);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file1));
        ArrayList<T> list = new ArrayList<>();
        try
        {
            T object = (T) ois.readObject();
            while (true)
            {
                list.add(object);
                object = (T) ois.readObject();
            }
        }
        catch (ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
        }
        catch (ClassCastException cce)
        {
            cce.printStackTrace();
        }
        catch (EOFException eofe)
        {
        }
        ois.close();
        return new ArrayList<>(list);
    }
}