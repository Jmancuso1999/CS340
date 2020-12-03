import java.util.ArrayList;
import java.lang.System;

public class RR extends Print
{
    private int timer;
    private int [] wt;
    private int [] tt;
    private int [] arrivalTime;
    private int idleTime = 0;
    private int burstCount = 0;
    private int systemCount = 0;

    public RR(int t) 
    {
        timer = t;
    }

    public void process(ArrayList <Process> p) 
    {
        Scheduler job = new Scheduler();

        //Sorts process based on Arrival Time
        ArrayList <Process> sortP = sortProcess(p);

        getBurst(sortP);

        tt = new int[sortP.size()];
        arrivalTime = new int[sortP.size()];
        for(int i = 0; i < arrivalTime.length; i++) arrivalTime[i] = -1;

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
        int completed = 0;

        // If ALL processes are done AND the scheduler has no remaining jobs.
        while(completed != sortP.size()) 
        {
            //Add's all  the current elements who have the same arrival time to the job.
            for(int j = arrayCount; j < sortP.size(); j++) 
            {
                if(sortP.get(j).getArrivalTime() == systemCount) 
                {
                    job.add(sortP.get(j));
                    arrayCount++;
                }
            } 
            Process currentProcess = job.get(0);

            if(job.size() != 0)
            {
                // Gets the INDEX position of the current job at the Jth position
                int jobPosition = sortP.indexOf(job.get(0)); 
                
                for(int i = 0; i < timer; i++) 
                {

                    if(sortP.get(jobPosition).getBurstLength() != 0)
                    {
                        if(arrivalTime[currentProcess.getProcessID() - 1] == -1) arrivalTime[currentProcess.getProcessID() - 1] = systemCount;
                        System.out.println("<system time    " + systemCount + ">" + " process " + currentProcess.getProcessID() + " is running");
                        sortP.get(jobPosition).setBurstLength(sortP.get(jobPosition).getBurstLength() - 1);
                        systemCount++;
                        
                        for(int r = arrayCount; r < sortP.size(); r++) 
                        {
                            if(sortP.get(r).getArrivalTime() == systemCount) 
                            {
                                job.add(sortP.get(r));
                                arrayCount++;
                            }
                        } 
                    }
                    else 
                    {
                        System.out.println("<system time    " + systemCount + ">" + " process " + currentProcess.getProcessID() + " is finished...");
                        tt[currentProcess.getProcessID() - 1] = systemCount;
                        job.remove(currentProcess);
                        completed++; 
                        break;       
                    }
                }

                if(sortP.get(jobPosition).getBurstLength() != 0) 
                {
                    //Removes the process from QUEUE (FIFO) and puts it in the back, also decrease the burst length by the timer length before putting it back in.
                    Process addToEnd = currentProcess;
                    job.remove(addToEnd);
                    job.add(addToEnd);
                    currentProcess = job.get(0);
                }
            }
            else 
            {
                idleTime++;
                systemCount++;
            }

        } //end of main while loop

        System.out.println("<system time    " + systemCount + ">" + " All processes finished...");

        double t = turnaroundTime(sortP, tt);
        double r = responseTime(sortP, arrivalTime);
        double w = waitTime(sortP, super.getWaitTime());
        double a = averageCPU(systemCount, idleTime);

        print(t, r, w, a);
    }

}
