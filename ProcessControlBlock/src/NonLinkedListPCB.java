
public class NonLinkedListPCB 
{
    private int parent, first_child, younger_sibling, older_sibling;

    public NonLinkedListPCB() 
    {
        parent = -1;
        younger_sibling = -1;
        older_sibling = -1;
    }

    public NonLinkedListPCB(int p, int fc, int ys, int os)
    {
        parent = p;
        first_child = fc;
        younger_sibling = ys;
        older_sibling = os;
    }
    
    public int getParent() {return parent; }
    public int getFirstChild() {return first_child;}
    public int getYoungerSibling() {return younger_sibling;}
    public int getOlderSibling() {return older_sibling;}

    public void setParent(int p) {parent = p;}
    public void setFirstChild(int fc) {first_child = fc;}
    public void setYoungerSibling(int ys) {younger_sibling = ys;}
    public void setOlderSibling(int os) {older_sibling = os;}

}