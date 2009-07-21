/**
 * The HashTable class is used to parse for ngrams in the articles it receives (from the RSS parser).
 * The ngrams thus found will be stored in a hash table keeping track of the frequency of each ngram.
 * This class also matches between the ngrams and the disease list created by the OutputListToText class.
 * All matches are stored in the matches private variable of the class.
 * 
 * @author Nicolae Dragu, Fouad Elkhoury
 * Date: July 13th, 2009
 * 
 */


package application;

import java.io.*;
import java.util.*;
 
public class HashTable {
        private Hashtable map;
        private String matches;
        
        /**
         * This constructor takes in the article (inputFileName), the disease list, and the nGram (1,2 or 3 for 
         * unigram, bigram or trigram).
         * The methods for collecting the ngrams from articles is called from this constructor, as well as the method to 
         * find matches between these ngram collected and the disease list.
         * @param inputFileName: the content of the input article 
         * @param diseaseList: the list of all diseases, symptoms and syndromes from the Biocaster Ontology
         * @param nGrams: an integer (1, 2, or 3) representing unigrams, bigrams or trigrams.
         * @throws IOException: exception for not finding the input file
         */
        public HashTable(String inputFileName, String diseaseList, int nGrams)
        {
        		try{
        		Scanner doc=new Scanner(new FileInputStream(inputFileName));
                countWordFreq(doc, nGrams);
                matches=match(diseaseList);
        		}
        		catch (Exception e) {
        		System.out.println("Wrong input file path!");
        		}
        }
        
        /**
         * Method used to return the hash table containing all the ngrams and their frequencies.
         * @return Returns the hash table containing all the ngrams extracted from the input article 
         */
        public Hashtable getMap() {
                return map;
        }
        
        /**
         * Method which returns the String representation of all matches found between the ngrams 
         * extracted from the article and the disease list.
         * @return Returns a String containing all matches found between the ngrams from the article and the
         * disease list.
         */
        public String getMatches() {
                return matches;
        }

        
        public String eliminatePunctuation(String word)
        {
        	String delimiters=",./;'[]<>?:\"{}+-;\\";
            
            String lastChar=Character.toString(word.charAt(word.length()-1));
            String firstChar=Character.toString(word.charAt(0));
            
            if (delimiters.indexOf(firstChar)>=0)
            {
             word=word.substring(1,word.length()); 
            }
            
            if (delimiters.indexOf(lastChar)>=0)
            {
             word=word.substring(0, word.length()-1);  
            }
            
        	return word;
        }
        
        
        /**
         * Method which extracts the ngrams from the input article (doc) also counting the frequency of each ngram.
         * @param doc: input article
         * @param nGrams: an integer (1, 2, or 3) representing unigrams, bigrams or trigrams.
         */
        public void countWordFreq(Scanner doc, int nGrams)
        {
                String nGramWord="";
                map=new Hashtable();
                
                //ngram queue used to store ngrams
                LinkedQueue<String> nGramQueue=new LinkedQueue<String>();
                nGramQueue.clear();
                
                String word;
                while (doc.hasNext())
                {
                String line=doc.nextLine();
                StringTokenizer st = new StringTokenizer(line);
                
                while (st.hasMoreTokens()) {
                    word = st.nextToken().toLowerCase();
                    nGramQueue.enqueue(word);
                    nGramWord="";
                    
                    /*
                     * once the ngram queue holds as many words as the nGrams variable an nGramWord is constructed and
                     * stored in the hash table. after that, we dequeue allowing the next word in the article to be 
                     * enqueued to form the next ngram.  
                     */
                    if (nGramQueue.getSize()>=nGrams)
                    {
                        nGramWord=nGramQueue.getValues();
                        nGramQueue.dequeue();
                        
                        nGramWord=eliminatePunctuation(nGramWord);
                        
                        if (map.containsKey(nGramWord)) 
                            {
                                int count = (Integer) map.get(nGramWord.toLowerCase());
                                map.put(nGramWord.toLowerCase(), count + 1);
                            } else {
                                map.put(nGramWord.toLowerCase(), 1);
                                        }
                    }
                }
                }//end first while
            }
         
        /**
         * Method used to find the matches between the ngrams in the hash table and the disease list.
         * @param diseaseList: the list containing all the names of diseases, symptoms and syndromes from the Biocaster 
         * Ontology
         * @return Returns the String representation of all matches found between the ngrams in the hash table
         * and the disease list.
         * @throws IOException
         */
         public String match(String diseaseList) throws IOException
          {
                //each line in the diseaseList is a disease or a symptom or a syndrome
                StringTokenizer st=new StringTokenizer(diseaseList, "\n");
                StringBuffer sb=new StringBuffer();
                
                /*going through the entire disease list and compare between the ngrams
                 * and the names found in the disaseList. All matches are recorded.
                 */
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
        
        /**
         * A convenience method used for testing purposes to display the ngrams in the hash table and 
         * their corresponding frequencies. 
         */
        public void display()
        {
                String word;
        Enumeration<String> e = map.keys();
        while (e.hasMoreElements()) {
            word =e.nextElement();
            System.out.println(word + " " + map.get(word));
        }       
        }
        
}
