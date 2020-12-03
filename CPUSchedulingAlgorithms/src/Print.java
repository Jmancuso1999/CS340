import java.util.ArrayList;
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class Print 
{
    private int [] waitTime; 
    private int [] burst;

    public Print() 
    {

    }

    public void print(double tt, double rt, double wt, double avg) 
    {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setRoundingMode(RoundingMode.CEILING);

        System.out.println("============================================================");
        System.out.println("Average CPU usage: " + df.format(avg) + "%");
        System.out.println("Average waiting time: " + df.format(wt));
        System.out.println("Average response time: " + df.format(rt));
        System.out.println("Average turnaround time: " + df.format(tt));
        System.out.println("============================================================");
    }

    public double averageCPU(int sCount, int iCount) 
    {
        float avg = ((float)sCount/(sCount + iCount));
        return 100.00*avg;
    }


    public double waitTime(ArrayList<Process> p, int [] wt) 
    {
        double wtTotal = 0;

        for(int i = 0; i < p.size(); i++) 
        {
            wtTotal = wtTotal + (wt[i] - burst[i]);
        }

        return wtTotal / p.size();
    }

    public double responseTime(ArrayList<Process> p, int [] start) 
    {
        double rtTotal = 0;

        for(int i = 0; i < p.size(); i++) 
        {
            rtTotal = rtTotal + (start[i] - p.get(i).getArrivalTime());
        }
        
        return rtTotal / p.size();
    }

    public double turnaroundTime(ArrayList<Process> p, int [] turnT) 
    {
        double ttTotal = 0;

        waitTime = new int[p.size()];

        for(int i = 0; i < p.size(); i++) 
        {
            waitTime[i] = turnT[i] - p.get(i).getArrivalTime();
            ttTotal = ttTotal + waitTime[i];
        }
        
        return ttTotal / p.size();

    }

   
    public ArrayList<Process> sortProcess(ArrayList<Process> p) 
    {
        ArrayList<Process> sortP = new ArrayList<Process>();

        while(p.size() != 0) 
        {
            int tempSmallArriveTime = 0;

            for(int i = 0; i < p.size(); i++) 
            {
                if(p.get(tempSmallArriveTime).getArrivalTime() > p.get(i).getArrivalTime()) tempSmallArriveTime = i;
            }

            sortP.add(p.get(tempSmallArriveTime));
            p.remove(tempSmallArriveTime);
        }

        return sortP;
    }

    public int [] getWaitTime() 
    {
        return waitTime;
    }

    public void getBurst(ArrayList <Process> p) 
    {
        burst = new int[p.size()];

        for(int i = 0; i < p.size(); i++) burst[i] = p.get(i).getBurstLength();
    }
}
