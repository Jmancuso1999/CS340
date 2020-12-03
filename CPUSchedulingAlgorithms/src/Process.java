public class Process 
{
    
    private int processID;
    private int arrivalTime;
    private int burstLength;

    public Process() 
    {

    }

    public Process(int p, int t, int l) 
    {
        processID = p;
        arrivalTime = t;
        burstLength = l;
    }

    public int getProcessID() {return processID;}
    public int getArrivalTime() {return arrivalTime;}
    public int getBurstLength() {return burstLength;}

    public void setProcessID(int p) {processID = p;}
    public void setArrivalTime(int t){arrivalTime = t;}
    public void setBurstLength(int l) {burstLength = l;}

}
