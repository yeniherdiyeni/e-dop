package application;

import java.io.*;
import java.util.*;
 
public class HashTable {
	private Hashtable map;
	private String matches;
	
	//could add filename, unigrams or bigrams etc.
	public HashTable(String inputFileName, String diseaseList) throws IOException
	{
		Scanner in1=new Scanner(new FileInputStream(inputFileName));
		countWordFreq(in1);
		matches=match(diseaseList);
	}
	
	public Hashtable getMap() {
		return map;
	}

	public String getMatches() {
		return matches;
	}

	public void countWordFreq(Scanner in) throws IOException {
	        
	        String delimiters=",./;'[]-=<>?:{}+_)(*&^%$#@!~";
	        map=new Hashtable();
	        
	        while (in.hasNext())
	        {
	        String line = in.nextLine();
	        
	        String word;
	        StringTokenizer st = new StringTokenizer(line);
	        
	        
	        while (st.hasMoreTokens()) {
	            word = st.nextToken().toLowerCase();
	            
	         if (word.length()>3)
	         {
	            if (delimiters.indexOf(word.charAt(word.length()-1))>=0)
	            	word=word.substring(0, word.length()-1);
	            
	            if (delimiters.indexOf(word.charAt(0))>=0)
	            	word=word.substring(1, word.length());
	            
	            if (map.containsKey(word)) 
	            {
	                int count = (Integer) map.get(word);
	                map.put(word, count + 1);
	            } else {
	                map.put(word, 1);
	            		}
	         }//first if
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
	   		sb.append(word+"\n");	
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
//        HashTable test = new HashTable("/home/ndragu/Desktop/test","/home/ndragu/Desktop/all_diseases_symptoms_syndromes");
//        System.out.println("The match list: "+test.getMatches());
//    }
 
}
