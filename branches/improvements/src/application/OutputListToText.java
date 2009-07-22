
/*
 *The OutputListToText class is designed to take all the names of all diseases, symptoms 
 *and syndromes (and their synonyms) from the ontology and return a String containing these names each on a 
 *separate line. Note: each line represents the name of the disease/symptom/syndrome followed by the synonyms 
 *(where available)
 *
 *@author Nicolae Dragu, Fouad Elkhoury
 * Date: July 21, 2009
 */


package application;

import java.util.Collection;
import java.util.Iterator;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLProperty;
import edu.stanford.smi.protegex.owl.model.RDFResource;

public class OutputListToText {
	
	private String diseaseList;
	
	/**
	 * This method is used to return a String containing the names of all diseases, symptoms
	 * and syndromes.
	 * @return Returns the String containing the names of all diseases, symptoms and syndromes
	 */
	public String getDiseaseList() {
		return diseaseList;
	}

	/**
	 * The computeList method is used to retrieve the disease class, the symtpom class 
	 * and the syndrome class and then using those classes to compute the english names
	 * of diseases, symptoms and syndromes.
	 * @param owlModel is the ontology model which enables access to all information in the 
	 * ontology
	 */
	public void computeList(OWLModel owlModel) 
	{
		try
		{
		StringBuffer sb=new StringBuffer();
		
		//getting access to the diseases, symptoms and syndromes classes
        OWLNamedClass allDiseasesClass = owlModel.getOWLNamedClass("DISEASE");
        OWLNamedClass allSymptomsClass = owlModel.getOWLNamedClass("SYMPTOM");
        OWLNamedClass allSyndromesClass = owlModel.getOWLNamedClass("SYNDROME");
        
		// defining a label property-used for getting the name of an individual
		OWLProperty hasLabelProp= owlModel.getOWLProperty("label");
		OWLProperty hasSynonymTermProp=owlModel.getOWLProperty("synonymTerm");
		//---------------------------------------------------------------------
		
		//--------------------get Diseases-------------------------------------
		
		Collection diseaseInstanceCollection=allDiseasesClass.getInstances();
		Iterator it=diseaseInstanceCollection.iterator();
		//using the iterator to go through all disease individuals
		while (it.hasNext())
		{
			OWLIndividual diseaseInstance=(OWLIndividual) it.next();
			//the label property tells us whether the disease has a name or not
			if (diseaseInstance.getPropertyValue(hasLabelProp)!=null)
			{
			sb.append(diseaseInstance.getPropertyValue(hasLabelProp).toString());
			
			if (diseaseInstance.getPropertyValue(hasSynonymTermProp)!=null)
			{
            Collection synonymCollection=diseaseInstance.getPropertyValues(hasSynonymTermProp);
            Iterator i=synonymCollection.iterator();
			
				while (i.hasNext())
				{
					RDFResource synonymCode=(RDFResource) i.next();
					if (synonymCode.getBrowserText().indexOf("englishTerm")>=0) //extracting only the english synonym terms
					{
						OWLIndividual synonym=owlModel.getOWLIndividual(synonymCode.getBrowserText());
						sb.append(","+synonym.getPropertyValue(hasLabelProp).toString());
					}
				}
			
			}
			sb.append("\n");
			}//first if
		}
		
		
		Collection symptomInstanceCollection=allSymptomsClass.getInstances();
		it=symptomInstanceCollection.iterator();
		
		//using the iterator to go through all symptom individuals
		while (it.hasNext())
		{
			OWLIndividual symptomInstance=(OWLIndividual) it.next();
			//the label property tells us whether the symptom has a name or not
			if (symptomInstance.getPropertyValue(hasLabelProp)!=null)
			{
			sb.append(symptomInstance.getPropertyValue(hasLabelProp).toString());
			
			//if the term has synonyms then we want to extract them
			if (symptomInstance.getPropertyValue(hasSynonymTermProp)!=null)
			{
            Collection synonymCollection=symptomInstance.getPropertyValues(hasSynonymTermProp);
            Iterator i=synonymCollection.iterator();
			
				while (i.hasNext())
				{
					RDFResource synonymCode=(RDFResource) i.next();
					if (synonymCode.getBrowserText().indexOf("englishTerm")>=0) //extracting only the english synonym terms
					{
						OWLIndividual synonym=owlModel.getOWLIndividual(synonymCode.getBrowserText());
						sb.append(","+synonym.getPropertyValue(hasLabelProp).toString());
					}
				}
			
			}
			sb.append("\n");
			
			}
		}
		
		
		Collection syndromeInstanceCollection=allSyndromesClass.getInstances();
		it=syndromeInstanceCollection.iterator();
		
		/*
		 * using the iterator to go through all syndrome individuals
		 *syndromes do not have synonyms so we're not interested in extracting them
		 */
		while (it.hasNext())
		{
			OWLIndividual syndromeInstance=(OWLIndividual) it.next();
			//the label property tells us whether the syndrome has a name or not
			if (syndromeInstance.getPropertyValue(hasLabelProp)!=null)
			sb.append(syndromeInstance.getPropertyValue(hasLabelProp).toString()+"\n");
		}
		diseaseList=sb.toString();
	}
	catch (Exception e)
	{
	}

   }
}
