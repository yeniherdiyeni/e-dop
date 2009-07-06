package application;

import java.io.IOException;

import javax.swing.JCheckBox;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;

public class Analyzer {

	private String finalResult;
	
	public String getResult()
	{
		return finalResult;
	}
	
	public Analyzer()
	{
	}
	
	public Analyzer(String ontologyPath, String inputFilePath, JCheckBox titles[]) 
	{
		try {	
		
		OWLModel owlModel = ProtegeOWL.createJenaOWLModel();
	    owlModel = ProtegeOWL.createJenaOWLModelFromURI(ontologyPath);
			
		OutputListToText allDiseasesList=new OutputListToText();
		allDiseasesList.computeList(owlModel);
		String ontologyTerms=allDiseasesList.getDiseaseList();
		
		String matches="";
		//pass titles[].isSelected to the HashTable constructor
		for (int i=0; i<titles.length; i++)
		{
			if (titles[i].isSelected())
			{
				HashTable ht=new HashTable(inputFilePath, ontologyTerms,i+1);
				matches=matches+ht.getMatches();
				System.out.println("----------------");
				ht.display();
				System.out.println("----------------");
			}
		}
		System.out.println("This is matches: "+matches);
		
		AssignPointsToDiseases ap=new AssignPointsToDiseases();
		ap.assignPoints(owlModel, matches);
		
		finalResult=ap.getResult();
		}
		catch (Exception e) 
		{}
	}
	
	public static void main(String[] args) throws IOException
	{
	//Analyzer a=new Analyzer("file:/home/ndragu/Desktop/biocaster.owl","/home/ndragu/Desktop/inputText");
	//System.out.println("Final Output: "+a.getResult());
	}
}
