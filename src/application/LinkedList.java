package application;

import edu.stanford.smi.protegex.owl.model.OWLIndividual;

class LinkedList 
{

    private Node firstNode;
    private int length;

    public LinkedList() 
    {
      clear();
    } // end default constructor

    public final void clear() 
    {
      firstNode = null;
      length = 0;
    } // end clear

    public boolean add(OWLIndividual newEntry) 
    {
      Node newNode = new Node(newEntry);
      if (isEmpty())
        firstNode = newNode;
      else 
      {
        Node lastNode = getNodeAt(length);
        lastNode.next = newNode;
      }
      length++;
      return true;
    } // end add

    public OWLIndividual remove(int givenPosition) 
    {
      OWLIndividual result = null; // return value

      if (!isEmpty() && (givenPosition >= 1) && (givenPosition <= length)) 
      {
        assert !isEmpty();
        if (givenPosition == 1) 
        {
          result = firstNode.data;
          firstNode = firstNode.next;
        }
        else 
        {
          Node nodeBefore = getNodeAt(givenPosition-1);
          Node nodeToRemove = nodeBefore.next;
          Node nodeAfter = nodeToRemove.next;
          nodeBefore.next = nodeAfter; // disconnect node to be removed
          result = nodeToRemove.data; // save entry to be removed
        } // end if
        length--;
      } // end if
      return result;
    } // end remove

    public boolean contains(OWLIndividual anEntry) 
    {
      boolean found = false;
      Node currentNode = firstNode;

      while (!found && (currentNode != null)) 
      {
        if (anEntry.equals(currentNode.data))
          found = true;
        else
          currentNode = currentNode.next;
      } // end while
      return found;
    } // end contains

    public OWLIndividual getEntry(int givenPosition) 
    {
      OWLIndividual result = null; // result to return

      if ((givenPosition >= 1) && (givenPosition <= length))
      {
          assert !isEmpty();
          result = getNodeAt(givenPosition).data;
      }
      return result;
    } // end getEntry

        
    public int getLength() 
    {
      return length;
    }

    public boolean isEmpty() 
    {
      boolean result;
      if(length == 0)
      {
        assert firstNode == null;
        result = true;
      }
      else
      {
        assert firstNode != null;
        result = false;
      } // end if
    
      return result;

    }

    public void display() 
    {
      Node currentNode = firstNode;

      while (currentNode != null) 
      {
        System.out.println("-----"+currentNode.data.getBrowserText());
        currentNode = currentNode.next;
      }

    }

    public Node getNodeAt(int givenPosition) 
    {
      Node currentNode = firstNode;

      // traverse the list to locate the desired node
      for (int counter = 1; counter < givenPosition; counter++)
        currentNode = currentNode.next;

      return currentNode;
    } // end getNodeAt

    private class Node  
    {
      private OWLIndividual data; // data portion
      private Node next; // link to next node

      private Node(OWLIndividual dataPortion)  
      {
        data = dataPortion;
        next = null;
      } // end constructor

      private Node(OWLIndividual dataPortion, Node nextNode) 
      {
        data = dataPortion;
        next = nextNode;
      } // end constructor

    } // end Node      
}
