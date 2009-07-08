package application;

import java.io.*;
import java.util.*;
 
public class HashTable {
	private Hashtable map;
	private String matches;
	
	public HashTable(String inputFileName, String diseaseList, int nGrams) throws IOException
	{
		Scanner in1=new Scanner(new FileInputStream(inputFileName));
		countWordFreq(in1, nGrams);
		matches=match(diseaseList);
	}
	
	public Hashtable getMap() {
		return map;
	}
	
	public String getMatches() {
		return matches;
	}

	public void countWordFreq(Scanner in, int nGrams) throws IOException {
	        
	        String delimiters=",./;'[]-=<>?:{}+_)(*&^%$#@!~";
	        String nGramWord="";
	        map=new Hashtable();
	        
	        LinkedQueue<String> nGramQueue=new LinkedQueue<String>();
	        nGramQueue.clear();
	        
	        while (in.hasNext())
	        {
	        String line = in.nextLine();
	        
	        String word;
	        StringTokenizer st = new StringTokenizer(line);
	        
	        
	        while (st.hasMoreTokens()) {
	            word = st.nextToken().toLowerCase();
	            nGramQueue.enqueue(word);
	            nGramWord="";
	            
	            if (nGramQueue.getSize()>=nGrams)
	            {
	            	nGramWord=nGramQueue.getValues();
	            	nGramQueue.dequeue();
	            	
	            	if (map.containsKey(nGramWord)) 
		            {
		                int count = (Integer) map.get(nGramWord.toLowerCase());
		                map.put(nGramWord.toLowerCase(), count + 1);
		            } else {
		                map.put(nGramWord.toLowerCase(), 1);
		            		}
	            }
	            
	        }
	       }//first while
	    }
	 
	 public String match(String diseaseList) throws IOException
	  {
	   	StringTokenizer st=new StringTokenizer(diseaseList, "\n");
		StringBuffer sb=new StringBuffer();
	   	
	   	while (st.hasMoreTokens())
	   	{
	   		String word = st.nextToken();
	   		if (map.containsKey(word.toLowerCase()))
	   		{
	   		sb.append(word.toLowerCase()+"\n");	
	   		}
	   	}
	  return sb.toString();
	  } 
	
	public void display()
	{
		String word;
        Enumeration<String> e = map.keys();
        while (e.hasMoreElements()) {
            word =e.nextElement();
            System.out.println(word + " " + map.get(word));
        }	
	}
	
//    public static void main(String[] args) throws IOException {
//        HashTable test = new HashTable("/home/ndragu/Desktop/inputText","/home/ndragu/Desktop/all_diseases_symptoms_syndromes",3);
//        
//    }
 
}




//public String eliminatePunctuation(String nGramWord)
//{
//	String punctuation=",.:;!?";
//	int wordLength=nGramWord.length();
//	boolean acceptable=true;
//	System.out.println(nGramWord);
//	
//	if (nGramWord.indexOf(punctuation)>0 && nGramWord.indexOf(punctuation)<wordLength)
//	{
//	acceptable=false;	
//	System.out.println("found");
//	}
//	else 
//	{
//		System.out.println("not found");
//	}
//	
//	return nGramWord;
//}
