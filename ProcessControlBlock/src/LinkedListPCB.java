import java.util.*;

public class LinkedListPCB
{
    private int parent; 
    private LinkedList<Integer> children;

    public LinkedListPCB() 
    {
        parent = 0; 
        children = new LinkedList<Integer>(); 
    }

    public LinkedListPCB(int p, LinkedList <Integer> c) 
    {
        parent = p; 
        children = c;
    }

    public int getParent() {return parent;}
    public LinkedList<Integer> getChildren() {return children;}

    public void setParent(int p) {parent = p;}
    public void setChildren(LinkedList <Integer> c) {children = c;}
    
    public HashSet<Integer> arrayOfChildren() 
    {
        HashSet <Integer> array = new HashSet<Integer>();
        
        int i = 0;
        while(children.get(i) != null) 
        {
            array.add(children.get(i));
        }

        return array;
    }

} 