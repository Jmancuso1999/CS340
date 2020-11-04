import java.util.LinkedList;
import java.util.HashSet;
import java.util.Iterator;

public class mainOS 
{
    private static LinkedListPCB [] PCB1 = new LinkedListPCB[10000];
    private static NonLinkedListPCB [] PCB2 = new NonLinkedListPCB[10000];

    public mainOS() 
    {
        for(int i = 0; i < 10; i++)
        {
            Create(0); 
            Create(0); 
            Create(2); 
            Create(0); 
            Destroy(0);
            print(PCB1);
            Create(0);
            Create(0);
            Create(2);
            Create(0);
            Create(2);
            Destroy(2);
            print(PCB1);
        }
        
        for(int i = 0; i < 10; i++)
        {
            CreateNon(0); 
            CreateNon(0);  
            CreateNon(2);  
            CreateNon(0); 
            DestroyNon(0);
            printNon(PCB2);
            CreateNon(0);
            CreateNon(0);
            CreateNon(2);
            CreateNon(0);
            CreateNon(2);
            DestroyNon(2);
            printNon(PCB2);
        }    
    } 

    //LinkedListPCB create
    public static void Create(int p) 
    {
        int index = 0;
        int tempLastListIndex = -1;
        LinkedList <Integer> tempLastList = new LinkedList<Integer>();
        LinkedList <Integer> nullList = new LinkedList<Integer>();

        if(PCB1[0] == null) 
        {
            PCB1[0] = new LinkedListPCB(); //initalizes list of children as empty. 
            
        }

        while(PCB1[index] != null) 
        {
            if(PCB1[index].getParent() == p){ 
                tempLastListIndex = index;
            }
            index++;

        }

        PCB1[index] = new LinkedListPCB(p, nullList);

        //if the parent already exists, take the list and add the current index to it, then set it to the current PCB index.
        if(tempLastListIndex != -1) 
        {
            tempLastList = PCB1[tempLastListIndex].getChildren();
            tempLastList.add(index);

            //Create the object at the specified index.
            PCB1[index] = new LinkedListPCB(p, tempLastList);
        }
    } 

    //LinkedListPCB Destroy
    public static void Destroy(int p) 
    {
        int index = 0;
        LinkedList <Integer> nullList = new LinkedList<Integer>();

        HashSet <Integer> descendants = new HashSet<Integer>();

        int parentCount = 0;

        while(PCB1[index] != null) 
        {
            if(PCB1[index].getParent() == p || descendants.contains(PCB1[index].getParent())) 
            {
                if(parentCount == 0) 
                {
                    PCB1[index].setChildren(nullList);
                    parentCount++;
                }
                else 
                {
                    //Similar approach to Breadth First Search
                    Iterator<Integer> it = PCB1[index].getChildren().iterator();
                    while(it.hasNext()) descendants.add(it.next());
                    PCB1[index] = null;
                }
            }

            index++;
        }
    }

    //LinkedListPCB Print
    public static void print(LinkedListPCB [] p)
    {
        int i = 0;
        HashSet <Integer> parentValues = new HashSet<Integer>();

        while(p[i] != null) 
        {
            //Avoids printing out the same parent multiple times -- justs get the list of that specific parent when print is called. 
            if(!parentValues.contains(p[i].getParent()))
            {
                System.out.print("Parent: " + p[i].getParent() + " --> ");
                System.out.println("Children: " + p[i].getChildren());
                parentValues.add(p[i].getParent());
            }
            i++;
        }
        System.out.println();
    }

    public static int currentPCBSize(NonLinkedListPCB [] pcb) 
    {
        int size = 1;
        while(pcb[size] != null) size++;

        return size;
    }

    //NonLinkedListPCB Create
    public static void CreateNon(int p) 
    {
        int tempOlderS = -1;
        int index = 0;
        int firstCParent = -1;

        if(PCB2[0] == null) 
        {
            PCB2[0] = new NonLinkedListPCB();
        }

        int size = currentPCBSize(PCB2) + 1;

        while(PCB2[index] != null) 
        {   
            if(PCB2[index].getParent() == p) 
            {
                if(firstCParent == -1  && PCB2[index].getFirstChild() == -1)  
                {
                    PCB2[index].setFirstChild(size);
                    firstCParent++;
                }
                tempOlderS = index; 
            }
            index++;
        } 

        //Update the younger sibling if found.
        if(tempOlderS != -1) 
        {
            PCB2[tempOlderS].setYoungerSibling(index);

            //Create the new instance  and put into end of PCB
            PCB2[index] = new NonLinkedListPCB(p, -1, -1, tempOlderS);
        }
        else //No younger sibling or older sibling found. 
        {
            PCB2[index] = new NonLinkedListPCB();
            PCB2[index].setParent(p);
        }
    }
    
    //NonLinkedListPCB Destroy
    public static void DestroyNon(int p) 
    {
        HashSet <Integer> descendants = new HashSet<Integer>();
        int parentCount = 0;
        int index = 0;

        while(PCB2[index] != null) 
        {
            if(PCB2[index].getParent() == p || descendants.contains(PCB2[index].getParent())) 
            {
                if(parentCount == 0) 
                {
                    PCB2[index] = new NonLinkedListPCB(p, -1, -1, -1);
                    parentCount++;
                }
                else
                {
                    descendants.add(index);
                    PCB2[index] = null;
                }
            }
            index++;
        }
    }

    //NonLinkedListPCB Print
    public static void printNon(NonLinkedListPCB [] PCB) 
    {
        int i = 0;
        HashSet <Integer> parentValues = new HashSet<Integer>();

        System.out.println("Format: [parent, first child, younger sibling, older sibling]");

        while(PCB[i] != null && i != PCB.length) 
        {
            System.out.println("PCB[" + i + "]" + " = [" + PCB[i].getParent() + ", " + PCB[i].getFirstChild() + ", " + PCB[i].getYoungerSibling() + ", " + PCB[i].getOlderSibling() + "]");
            i++;
        }

        System.out.println();
    }

} 