/***
Coder: Roslan S, UiTM Pahang, roslancs@uitm.edu.my
Year: 2012
***/

public class ArrayList {
    
    private int size = 0;
    public static int INITIAL_CAPACITY = 20;
    private Object[] data = (Object[]) (new Object[INITIAL_CAPACITY]);

    public ArrayList(){}   
    
    public ArrayList(int newCapacity) {
        this.INITIAL_CAPACITY = newCapacity;
    }

    public ArrayList(Object[] element)
    {
        for(int i=0; i<element.length; i++)
            this.add(element[i]);        
    }
    
    public boolean isEmpty(){
        return this.size == 0;
    }
    
    public int size(){ return this.size; }
    
    private void ensureCapacity()
    {
        if(this.size >= this.data.length)
        {
            Object[] newData = (Object[]) (new Object[this.size * 2 + 1]);
            System.arraycopy(this.data, 0, newData, 0, this.size);
            this.data=newData;
        }
    }
        
    public void add(Object element) { 
        add(size, element); 
    }   
    
    public void add(int index, Object element) {
        this.ensureCapacity();
        
        for (int i=this.size-1; i>=index; i--)        
            this.data[i+1] = this.data[i];   
           
        this.data[index] = element;  
        this.size++;
        
    }
    
    public Object get(int index) {
        return this.data[index];
    }

    public void clear() {
        this.data = (Object[]) (new Object[this.INITIAL_CAPACITY]);
        this.size=0;     
    }

    public boolean contains(Object element) {
        for (int i=0; i<size; i++)
            if(element.equals(data[i])) 
                return true;
        return false;
    }

    public int indexOf(Object element) {
        for(int i=0; i<this.size; i++)
            if(element.equals(this.data[i]))
                    return i;
        return -1;
    }
    
    public int lastIndexOf(Object element) {
        for(int i=this.size-1; i>= 0; i--)
            if(element.equals(this.data[i]))
                return i;
        return -1;      
    }   

    public Object remove(int index) {
        Object element = data[index];
        
        for(int j=index; j<this.size-1; j++)
            this.data[j]=this.data[j+1];
        
        this.data[this.size-1] = null;
        this.size--;     
        return element;
    }
    
    public boolean remove(Object element) {
        if(indexOf(element) >= 0){
            remove(indexOf(element));
            return true;
        }
        else {
            return false;
        }
    }

    public Object set(int index, Object element) {
        Object old = this.data[index];
        this.data[index] = element;        
        return old;
    }
    
    public String toString()
    {
        this.trimToSize();
        StringBuilder result = new StringBuilder("[");
        
        for(int i =0; i<size(); i++)
        {
            result.append(data[i]);
            if(i<this.size-1)
                result.append(", ");
        }
        return result.toString() + "]";
    }

    public void trimToSize() {
        if(this.size != this.data.length) {
            Object[] newData = (Object[]) (new Object[this.size]);
            System.arraycopy(data, 0, newData, 0, this.size);
            this.data = newData;     
        }
    }    	
}
