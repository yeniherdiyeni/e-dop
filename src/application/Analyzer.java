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
	
	public String getResult()                        
	{
		return finalResult;
	}
	
	public void getOntologyList() throws IOException  
	{
	OutputListToText allDiseasesList=new OutputListToText();
	allDiseasesList.computeList(owlModel);
	ontologyTerms=allDiseasesList.getDiseaseList();
	}
	
	public Analyzer(String ontologyPath) throws OntologyLoadException
	{
		owlModel = ProtegeOWL.createJenaOWLModel();
	    owlModel = ProtegeOWL.createJenaOWLModelFromURI(ontologyPath);	
	}
	
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
