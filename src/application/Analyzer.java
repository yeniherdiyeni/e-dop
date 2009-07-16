package application;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;

public class Analyzer {
	
	public OWLModel owlModel;
	private String finalResult;
	
	public String getResult()
	{
		return finalResult;
	}
	
	public Analyzer()
	{
		try{
		owlModel = ProtegeOWL.createJenaOWLModel();
		owlModel = ProtegeOWL.createJenaOWLModelFromURI("http://www.cs.trincoll.edu/~ndragu/ontology/biocaster.owl");
		}
		catch (Exception e)
		{}
	}
	
	public Analyzer(String ontologyPath)
	{
		try{
		owlModel = ProtegeOWL.createJenaOWLModel();
		owlModel = ProtegeOWL.createJenaOWLModelFromURI(ontologyPath);
		}
		catch (Exception e)
		{}
	}
	
	public void compute(String inputFilePath, String ngrams) throws IOException
	{
		
		OutputListToText allDiseasesList=new OutputListToText();
		allDiseasesList.computeList(owlModel);
		String ontologyTerms=allDiseasesList.getDiseaseList();
		
		String matches="";
		
		int number=Integer.parseInt(ngrams);
		
		RSSParser p=new RSSParser("<description>","</description>");
		p.parse(inputFilePath);
		HashTable ht;
		
		for (Iterator<String> document = p.getList().iterator(); document.hasNext();)
		{
			String doc=document.next();
			
			switch (number)
			{
			case 1: ht=new HashTable(doc, ontologyTerms, 1);
					matches=matches+ht.getMatches();
					break;
			case 2:	ht=new HashTable(doc, ontologyTerms, 2);
					matches=matches+ht.getMatches();
					break;
			case 3: ht=new HashTable(doc, ontologyTerms, 1);
					matches=matches+ht.getMatches();
					ht=new HashTable(doc, ontologyTerms, 2);
					matches=matches+ht.getMatches();
					break;
			case 4: ht=new HashTable(doc, ontologyTerms, 3);
					matches=matches+ht.getMatches();
					break;
			case 5: ht=new HashTable(doc, ontologyTerms, 1);
					matches=matches+ht.getMatches();
					ht=new HashTable(doc, ontologyTerms, 3);
					matches=matches+ht.getMatches();
					break;
			case 6: ht=new HashTable(doc, ontologyTerms, 2);
					matches=matches+ht.getMatches();
					ht=new HashTable(doc, ontologyTerms, 3);
					matches=matches+ht.getMatches();
					break;
			case 7: ht=new HashTable(doc, ontologyTerms, 1);
					matches=matches+ht.getMatches();
					ht=new HashTable(doc, ontologyTerms, 2);
					matches=matches+ht.getMatches();
					ht=new HashTable(doc, ontologyTerms, 3);
					matches=matches+ht.getMatches();
					break;
				
			default: System.out.println("Wrong input!");
			}
			
		}
		System.out.println("matches: "+matches);
		
		AssignPointsToDiseases ap=new AssignPointsToDiseases();
		ap.assignPoints(owlModel, matches);
		
		finalResult=ap.getResult();
	}
	
	public static void main(String[] args) throws IOException
	{
	try{
		
	File dir = new File("/home/ndragu/Desktop");
	    
	String[] children = dir.list();
	if (children == null) {
	        // Either dir does not exist or is not a directory
	} else {
	     for (int i=0; i<children.length; i++) {
	            // Get filename of file or directory
	            String filename = children[i];
	            System.out.println(filename);
	        }
	    }
	    
	
		
	System.out.println("args[0]=input file path; args[1]=ngrams -1,2,4");
	Analyzer a=new Analyzer();	
	a.compute(args[0], args[1]);
	System.out.println("Final Output: "+a.getResult());
	}
	catch (Exception e)
	{}
	}
	
}


