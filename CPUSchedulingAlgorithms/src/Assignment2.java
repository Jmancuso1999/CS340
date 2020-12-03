import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Assignment2 
{
    public static void main(String [] args) 
    {

        ArrayList <Process> data = new ArrayList<Process>();
    
        try
        {
            FileInputStream fstream = new FileInputStream("assignment2.txt");
            Scanner file = new Scanner(fstream);
            
            while(file.hasNext()) 
            {
                int [] arr = new int[3];
                for(int i = 0; i < 3; i++) 
                {
                    arr[i] = file.nextInt();
                }

                Process tempProcess = new Process(arr[0], arr[1], arr[2]);
                data.add(tempProcess);
             }

            file.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        if(args[0].toUpperCase().equals("FCFS")) 
        {
            FCFS FCFSScheduler = new FCFS();
            FCFSScheduler.process(data);
        }

        else if(args[0].toUpperCase().equals("SRTF"))
        {
            SRTF SRTFScheduler = new SRTF();
            SRTFScheduler.process(data);
        }
        else if(args[0].toUpperCase().equals("RR")) 
        {
            RR RRScheduler = new RR(Integer.parseInt(args[1])); 
            RRScheduler.process(data);
        }
    }
}
