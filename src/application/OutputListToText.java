package application;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLProperty;

public class OutputListToText {
	
	private String diseaseList;
	
	public String getDiseaseList() {
		return diseaseList;
	}

	public void computeList(OWLModel owlModel) throws IOException
	{
		try
		{
		PrintWriter outputFile;
	    outputFile=new PrintWriter("all_diseases_symptoms_syndromes.txt");
			
		StringBuffer sb=new StringBuffer();
    	        //getting access to the diseases, symptoms and syndromes classes
        OWLNamedClass allDiseasesClass = owlModel.getOWLNamedClass("DISEASE");
        OWLNamedClass allSymptomsClass = owlModel.getOWLNamedClass("SYMPTOM");
        OWLNamedClass allSyndromesClass = owlModel.getOWLNamedClass("SYNDROME");
        
		// defining a label property-used for getting the name of an individual
		OWLProperty hasLabelProp= owlModel.getOWLProperty("label");
		//---------------------------------------------------------------------
		
		//--------------------get Diseases-------------------------------------
		
		Collection diseaseInstanceCollection=allDiseasesClass.getInstances();
		Iterator it=diseaseInstanceCollection.iterator();
		
		while (it.hasNext())
		{
			OWLIndividual diseaseInstance=(OWLIndividual) it.next();
			if (diseaseInstance.getPropertyValue(hasLabelProp)!=null)
			sb.append(diseaseInstance.getPropertyValue(hasLabelProp).toString()+"\n");
		}
		
		
		Collection symptomInstanceCollection=allSymptomsClass.getInstances();
		it=symptomInstanceCollection.iterator();
		
		while (it.hasNext())
		{
			OWLIndividual symptomInstance=(OWLIndividual) it.next();
			if (symptomInstance.getPropertyValue(hasLabelProp)!=null)
			sb.append(symptomInstance.getPropertyValue(hasLabelProp).toString()+"\n");
		}
		
		
		Collection syndromeInstanceCollection=allSyndromesClass.getInstances();
		it=syndromeInstanceCollection.iterator();
		
		while (it.hasNext())
		{
			OWLIndividual syndromeInstance=(OWLIndividual) it.next();
			if (syndromeInstance.getPropertyValue(hasLabelProp)!=null)
			sb.append(syndromeInstance.getPropertyValue(hasLabelProp).toString()+"\n");
		}
		
		diseaseList=sb.toString();
		outputFile.append(diseaseList);
		outputFile.close();
	}
	catch (Exception e)
	{}

   }
//	
//    public static void main(String[] args) throws IOException
//    {
//    	OutputListToText test=new OutputListToText();
//    	test.computeList("file:/home/ndragu/Desktop/biocaster.owl");
//    	System.out.println(test.getDiseaseList());
//    	
//    }
}
