
/*
 *The Analyzer class is responsible for handling requests from the user, meaning, taking in the ontology path,
 *the input text path, and the type of ngrams to consider while computing results. Note, an ngram represents
 *n consecutive words extracted out of the input text. The class also load the ontology once while enabling the
 *user to input as many documents as he wants without having to reload the ontology.
 *
 * @author Nicolae Dragu, Fouad Elkhoury
 * Date: July 21, 2009 
 */

package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;

public class Analyzer {

	private String finalResult;
	private OWLModel owlModel;
	private String ontologyTerms="";
	
	/**
	 * The getResult method returns the final output of the application
	 * @return returns the final output of the application
	 */
	public String getResult()                        
	{
		return finalResult;
	}
	
	/**
	 * The getOntologyList method is used to retrieve the names of all diseases, syndromes and symptoms
	 * from the ontology using the OutputListToText class.
	 * @throws IOException
	 */
	public void getOntologyList() throws IOException  
	{
	OutputListToText allDiseasesList=new OutputListToText();
	allDiseasesList.computeList(owlModel);
	ontologyTerms=allDiseasesList.getDiseaseList();
	}
	
	/**
	 * The Analyzer constructor takes the ontology path to load the ontology
	 * @param ontologyPath represents the ontology path which is used to load the ontology
	 * @throws OntologyLoadException thrown in the event of the ontology not being able to load
	 */
	public Analyzer(String ontologyPath) throws OntologyLoadException
	{
		owlModel = ProtegeOWL.createJenaOWLModel();
	    owlModel = ProtegeOWL.createJenaOWLModelFromURI(ontologyPath);	
	}
	
	/**
	 * The compute method is respnsible for computing results to be displayed by the application.
	 * Based on the ngrams given by the user, the appropriate HashTable constructor is used and then
	 * based on the matches obtained between the list retrieved from the ontology and the ngrams from
	 * the input text, the AssignPointsToDiseases class is called to assign points based on the metric
	 * defined.
	 * @param inputFilePath represents the path of the input text file
	 * @param number represents an integer, 1,2 or 4 or any number that can be obtained by a summation of 
	 * any combination of 1, 2 and 4. The idea is that if user wants unigrams and bigrams he sums 1 and 2 
	 * and he inputs 3, or if he wants unigrams, bigrams and trigrams he sums 1, 2 and 4 and he inputs 7.
	 * @throws OntologyLoadException represents an exception thrown in case the ontology cannot be loaded
	 * or if the path specified by the user is invalid.
	 * @throws IOException thrown in case the path to the input text file is invalid
	 */
	public void compute(String inputFilePath, int number) throws OntologyLoadException, IOException
	{
		String matches="";	
		
		HashTable ht;
		switch (number)
        {
        case 1: ht=new HashTable(inputFilePath, ontologyTerms, 1);
                        matches=matches+ht.getMatches();
                        break;
        case 2: ht=new HashTable(inputFilePath, ontologyTerms, 2);
                        matches=matches+ht.getMatches();
                        break;
        case 3: ht=new HashTable(inputFilePath, ontologyTerms, 1);
                        matches=matches+ht.getMatches();
                        ht=new HashTable(inputFilePath, ontologyTerms, 2);
                        matches=matches+ht.getMatches();
                        break;
        case 4: ht=new HashTable(inputFilePath, ontologyTerms, 3);
                        matches=matches+ht.getMatches();
                        break;
        case 5: ht=new HashTable(inputFilePath, ontologyTerms, 1);
                        matches=matches+ht.getMatches();
                        ht=new HashTable(inputFilePath, ontologyTerms, 3);
                        matches=matches+ht.getMatches();
                        break;
        case 6: ht=new HashTable(inputFilePath, ontologyTerms, 2);
                        matches=matches+ht.getMatches();
                        ht=new HashTable(inputFilePath, ontologyTerms, 3);
                        matches=matches+ht.getMatches();
                        break;
        case 7: ht=new HashTable(inputFilePath, ontologyTerms, 1);
                        matches=matches+ht.getMatches();
                        ht=new HashTable(inputFilePath, ontologyTerms, 2);
                        matches=matches+ht.getMatches();
                        ht=new HashTable(inputFilePath, ontologyTerms, 3);
                        matches=matches+ht.getMatches();
                        break;
                
        default: System.out.println("Wrong ngram input!");
        }

		
		System.out.println("These ngrams matched with the ontology:\n"+matches);
		
		AssignPointsToDiseases ap=new AssignPointsToDiseases();
		ap.assignPoints(owlModel, matches);
		
		finalResult=ap.getResult();
	}
	
	public static void main(String[] args) throws IOException, OntologyLoadException
	{
	InputStreamReader converter = new InputStreamReader(System.in);
    BufferedReader in = new BufferedReader(converter);
	
    System.out.println("Do you want to specify an ontology path? y/n (no means the default is used)");
    String answer=in.readLine();
    
    Analyzer a;
    if (answer.equals("y"))
    {
    System.out.println("Warning! If the ontology path structure contains spaces in the names of the folders the ontology will not load!");
    System.out.println("Input ontology path: ");
    String ontologyPath=in.readLine();
    
    	if (ontologyPath.indexOf("http")<0) //if it's not an www url
    	{
    		if (ontologyPath.charAt(1)==':')  //second char in the path is : meaning the path must be a win path like c:/bla
    		{
    			ontologyPath="file:///"+ontologyPath;
    		}
    		else 
    			ontologyPath="file:"+ontologyPath; //probably unix
    	}
    a=new Analyzer(ontologyPath);	
    }
    else 
    {
	a=new Analyzer("http://www.cs.trincoll.edu/~ndragu/ontology/biocaster.owl");
    }
	a.getOntologyList();
	
	String exitWord="";
	while (!(exitWord.toLowerCase().equals("y")))
		{
			System.out.println("Input file path: ");
			String inputFilePath=in.readLine();
			
			System.out.println("1. Unigrams");
			System.out.println("2. Bigrams");
			System.out.println("4. Trigrams");
			System.out.println("Input 1 or 2 or 4, or the sum of any combination of the above: ");
			String nGrams=in.readLine();
			System.out.println(inputFilePath);
			a.compute(inputFilePath, Integer.parseInt(nGrams));
			System.out.println("-----------------------------------------------");
			System.out.print("Final Output: "+a.getResult());
			System.out.println("-----------------------------------------------");
			
			System.out.println("Exit? y/n");
			exitWord=in.readLine();
		}
	}//end else
}
