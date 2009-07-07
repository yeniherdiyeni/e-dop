package application;

public class LinkedQueue<T> 
{
	private Node firstNode;
	private Node lastNode;
	private int size;
	
	public LinkedQueue()
	{
		firstNode=null;
		lastNode=null;
		size=0;
	}

	public void enqueue(T newEntry)
	{
		Node newNode=new Node(newEntry, null);
		
		if (isEmpty())
			firstNode=newNode;
		else 
			lastNode.setNextNode(newNode);
		
		size++;
		lastNode=newNode;
	} //end enqueue
	
	public int getSize()
	{
		return size;
	}
	
	public T getFront()
	{
		T front=null;
		if (!isEmpty())
			front=firstNode.getData();
		
		return front;
	}
	
	public T dequeue()
	{
		T front=null;
		size--;
		
		if (!isEmpty())
		{
			front =firstNode.getData();
			firstNode=firstNode.getNextNode();
			
			if (firstNode==null)
				lastNode=null;
		}
		
		return front;
	}
	
	public String getValues()
	{
		Node front=firstNode;
		String result="";
		
		while (front!=null)
		{
			result=result+" "+front.getData().toString();
			front=front.next;
		}
		result=result.substring(1,result.length());
		
		return result;
	}
	
	
	public void display()
	{
		Node front=firstNode;
		
		while (front!=null)
		{
			System.out.println(front.getData());
			front=front.next;
		}
			
	}
	
	public boolean isEmpty()
	{
		return (firstNode==null) && (lastNode==null);
	}
	
	public void clear()
	{
		firstNode=null;
		lastNode=null;
	}
	
//---------------node class---------------------------
	private class Node 
	{
		private T data;
		private Node next;
	
	
    private Node(T dataPortion)
    {
    	data=dataPortion;
    	next=null;
    }
    
    private Node(T dataPortion, Node nextNode)
    {
    	data=dataPortion;
    	next=nextNode;
    }
    
    private T getData()
    {
    	return data;
    }
    
    private void setData(T newData)
    {
    	data=newData;
    }
    
    private Node getNextNode()
    {
    return next;	
    }
    
    private void setNextNode(Node nextNode)
    {
    	next=nextNode;
    }
    
  }//end node class
	
//	public static void main(String[] args)
//	{
//	LinkedQueue<String> q=new LinkedQueue<String>();
//	q.clear();
//	
//	q.enqueue("I");
//	System.out.println(q.getSize());
//	q.enqueue("am");
//	System.out.println(q.getSize());
//	q.enqueue("having");
//	System.out.println(q.getSize());
//	q.enqueue("problems");
//	System.out.println(q.getSize());
//	q.enqueue("with");
//	System.out.println(q.getSize());
//	q.enqueue("this");
//	System.out.println(q.getSize());
//	q.enqueue("text");
//	
//	
//	q.display();
//	
//	System.out.println(q.dequeue());
//	System.out.println(q.getSize());
//	System.out.println(q.dequeue());
//	System.out.println(q.getSize());
//	System.out.println(q.dequeue());
//	System.out.println(q.getSize());
//	System.out.println(q.dequeue());
//	System.out.println(q.getSize());
//	}
}
