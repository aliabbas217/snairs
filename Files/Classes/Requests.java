package Files.Classes;

import Files.FileOperations;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Requests implements Serializable , Comparable<Requests>
{
    private String name;
    private LocalTime requestDate;
    public Requests()
    {}
    public Requests(String name)
    {
        this.name = name;
        requestDate = LocalTime.now();
    }

    public static String getFilePath()
    {
        return "Files/Classes/Requests.ser";
    }

    public static void addRequest(Requests request) throws IOException
    {
        FileOperations.writeToFile(request, getFilePath());
    }

    public static Requests getRequest() throws IOException
    {
        ArrayList<Requests> requests = FileOperations.readAllObject(getFilePath());
        Requests toReturn = requests.get(0);
        requests.remove(0);
        File file = new File(getFilePath());
        file.delete();
        for (int i =0; i < requests.size(); i++)
        {
            FileOperations.writeToFile(requests.get(i),getFilePath());
        }
        return toReturn;
    }


    public int compareTo(Requests requests)
    {
        return this.requestDate.compareTo(requests.requestDate);
    }
}