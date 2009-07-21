
/*
 * The LinkedQueue is an implementation of a queue using linked nodes.
 *
 * @author Nicolae Dragu, Fouad Elkhoury
 * Date: July 21, 2009
 */

package application;

public class LinkedQueue<T> 
{
	private Node firstNode;
	private Node lastNode;
	private int size;
	
	//default constructor for the queue
	public LinkedQueue()
	{
		firstNode=null;
		lastNode=null;
		size=0;
	}

	/**
	 * Enqueue method used to insert an item of type T in the queue
	 * @param newEntry represents the data to be stored in the queue
	 */
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
	
	
	/**
	 * Returns the number of elements in the queue.
	 * @return returns the number of elements in the queue
	 */
	public int getSize()
	{
		return size;
	}
	
	/**
	 * The getFront method is used to read the first node in the queue
	 * @return returns the first node in the queue
	 */
	public T getFront()
	{
		T front=null;
		if (!isEmpty())
			front=firstNode.getData();
		
		return front;
	}
	
	/**
	 * The dequeue method is used to eliminate the first-enqueued item in the queue.
	 * @return returns the value of the eliminated item.
	 */
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
	
	/**
	 * The getValues is a method used to return a String containing all the names of the items in the
	 * queue
	 * @return returns a String containing all the names of the items in the queue.
	 */
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
	
	/**
	 * The display method is used to display all information contained in the queue.
	 */
	public void display()
	{
		Node front=firstNode;
		
		while (front!=null)
		{
			System.out.println(front.getData());
			front=front.next;
		}
			
	}
	
	/**
	 * The isEmpty method returns a boolean value regarding whether the queue is empty or not 
	 * @return returns a boolean value regarding whether the queue is empty or not
	 */
	public boolean isEmpty()
	{
		return (firstNode==null) && (lastNode==null);
	}
	
	/**
	 * The clear method is used to set the pointers to the first and last nodes in the queue to null.
	 */
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
	
	/**
	 * Constructor which creates a node based on the information given by the parameter dataPortion
	 * @param dataPortion represents the data which is going to be contained by the node.
	 */
    private Node(T dataPortion)
    {
    	data=dataPortion;
    	next=null;
    }
    
    /**
     * Constructor which creates a node in front of nextNode using the information contained in dataPortion
     * @param dataPortion represents the information which will be contained by the node
     * @param nextNode represents the node in front of which we will create a new one
     */
    private Node(T dataPortion, Node nextNode)
    {
    	data=dataPortion;
    	next=nextNode;
    }
    
    /**
     * The getData method is used to return the data information contained in a certain node.
     * @return returns the information contained by the node.
     */
    private T getData()
    {
    	return data;
    }
    
    /**
     * The setData method is used to change or set the data of the node.
     * @param newData
     */
    private void setData(T newData)
    {
    	data=newData;
    }
    
    /**
     * The getNextNode method is used to return the reference to the next node.
     * @return returns the reference to the next node.
     */
    private Node getNextNode()
    {
    return next;	
    }
    
    /**
     * The setNextNode is used to set a new node after the current one.
     * @param nextNode represents the node that is going to be placed after the current one.
     */
    private void setNextNode(Node nextNode)
    {
    	next=nextNode;
    }
    
  }//end node class
}
