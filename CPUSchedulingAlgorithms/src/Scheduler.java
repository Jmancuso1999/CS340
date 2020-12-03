import java.util.ArrayList;

public class Scheduler 
{

    private ArrayList<Process> allProcesses;

    public Scheduler() 
    {
        allProcesses = new ArrayList<Process>();
    }

    public Scheduler(ArrayList <Process> p) 
    {
        allProcesses = p;
    }

    public Process get(int p) {return allProcesses.get(p);}

    public void add(Process p) 
    {
        allProcesses.add(p);
    }

    public void remove(Process p) 
    {
        //Goes to the index of the 1st time the specific id, time and burst length occurs and removes that index position
        allProcesses.remove(p);
    }

    public int size() {return allProcesses.size();}

    public int getSmallestArrival() 
    {
        int index = 0;
        for(int i = 0; i < allProcesses.size(); i++) 
        {
            if(allProcesses.get(index).getBurstLength() > allProcesses.get(i).getBurstLength()) index = i;
        }

        return index; 
    }
}
