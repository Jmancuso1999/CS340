import java.util.ArrayList;

public class SRTF extends Print
{
    private int [] wt;
    private int [] tt;
    private int [] arrivalTime;
    private int idleTime = 0;
    private int burstCount = 0;
    private int systemCount = 0;

    public SRTF() 
    {        
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
            
            Process smallestRemainingBurst = job.get(0);

            if(job.size() != 0)
            {

                //Set smallestRemainingBurst = job.get(0), LOOP (for loop) through the ENTIRE JOB list, check for smallest and if I find a smaller burst Length change smallestRemainingBurst

                for(int i = 0; i < job.size(); i++) 
                {
                    if(job.get(i).getBurstLength() < smallestRemainingBurst.getBurstLength()) smallestRemainingBurst = job.get(i);
                }

                int jobPosition = sortP.indexOf(smallestRemainingBurst); 
                int ID = smallestRemainingBurst.getProcessID(); 

                //Now do a conditional statement saying if smallestRemainingBurst.getBurstLength() == 0 --> print that the job is done, else print the position subtract length and put it back into job

                if(sortP.get(jobPosition).getBurstLength() != 0)
                {
                    if(arrivalTime[smallestRemainingBurst.getProcessID() - 1] == -1) arrivalTime[smallestRemainingBurst.getProcessID() - 1] = systemCount;
                    job.remove(smallestRemainingBurst);
                    System.out.println("<system time    " + systemCount + ">" + " process " + ID + " is running");
                    sortP.get(jobPosition).setBurstLength(sortP.get(jobPosition).getBurstLength() - 1);
                    job.add(sortP.get(jobPosition));
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
                    System.out.println("<system time    " + systemCount + ">" + " process " + ID + " is finished...");
                    tt[smallestRemainingBurst.getProcessID() - 1] = systemCount;
                    job.remove(smallestRemainingBurst);
                    completed++;     
                }
            }

            else 
            {
                idleTime++;
                systemCount++;
            }
        }

        System.out.println("<system time    " + systemCount + ">" + " All processes finished...");

        double t = turnaroundTime(sortP, tt);
        double r = responseTime(sortP, arrivalTime);
        double w = waitTime(sortP, super.getWaitTime());
        double a = averageCPU(systemCount, idleTime);

        print(t, r, w, a);
    }   
}