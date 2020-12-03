import java.util.ArrayList;
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class FCFS extends Print
{
    private int [] wt;
    private int [] tt;
    private int [] arrivalTime;
    private int idleTime = 0;
    private int burstCount = 0;
    private int systemCount = 0;

    public FCFS() 
    {
    }

    public void process(ArrayList<Process> p) 
    {
        Scheduler job = new Scheduler();

        //Sorts process based on Arrival Time
        ArrayList <Process> sortP = sortProcess(p);

        getBurst(sortP);

        tt = new int[sortP.size()];
        arrivalTime = new int[sortP.size()];

        int jobsInProcess = 0;

        //Gets the 1st position, if it isnt 0, then there is idle time
        burstCount = sortP.get(0).getArrivalTime();

        //Checks before iterating to see if there is any idle time. 
        if(burstCount != 0) 
        {
            for(int i = 0; i < burstCount; i++) {System.out.println("<system time    " + i + "> no process is running");}
            systemCount = burstCount;
            idleTime = burstCount;
        }

        int arrayCount = 0;
        int totalBLength = 0;

        while(totalBLength != totalBurst(sortP))
        {
            Process tempProcess = sortP.get(arrayCount);

            //Compare Burst Length to System Count to see if there is a difference of time, if there is, that means some idle time occurs. 
            if(burstCount != systemCount) 
            {
                idleTime = idleTime + (systemCount - burstCount);
                burstCount = systemCount;
            }

            arrivalTime[arrayCount] = systemCount;

            //If the process(es) arrival time is the current system time, add the process(es) to the job ready queue. 
            for(int i = 0; i < sortP.size(); i++) 
            {
                if(sortP.get(i).getArrivalTime() == systemCount) 
                {
                    job.add(sortP.get(i));
                    jobsInProcess++;
                }
            }

            int currentBurstLength = tempProcess.getBurstLength(); 
            int currentProcess = tempProcess.getProcessID();

            for(int i = 0; i < currentBurstLength; i++)
            {    
                //If a FUTURE process has a arrival time at the current system count, add it to the job and check the next one. 
                if(jobsInProcess < sortP.size()) 
                {
                    if(sortP.get(jobsInProcess).getArrivalTime() == systemCount) 
                    {
                        job.add(sortP.get(jobsInProcess));
                        jobsInProcess++;
                    }
                }
                System.out.println("<system time    " + systemCount + ">" + " process " + currentProcess + " is running");
                systemCount++;
                totalBLength++;
            }

            System.out.println("<system time    " + systemCount + ">" + " process " + currentProcess + " is finished...");
            job.remove(tempProcess);    
            
            tt[arrayCount] = systemCount;

            burstCount = burstCount + sortP.get(arrayCount).getBurstLength();

            arrayCount++;
        }

        System.out.println("<system time    " + systemCount + ">" + " All processes finished...");

        double t = turnaroundTime(sortP, tt);
        double r = responseTime(sortP, arrivalTime);
        double w = waitTime(sortP, super.getWaitTime());
        double a = averageCPU(systemCount, idleTime);

        print(t, r, w, a);
    } 

    public static int totalBurst(ArrayList <Process> p) 
    {
        int total = 0;
        for(int i = 0; i < p. size(); i++) {total = total + p.get(i).getBurstLength();}

        return total;
    }

}