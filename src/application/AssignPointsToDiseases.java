package application;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLProperty;
import edu.stanford.smi.protegex.owl.model.RDFResource;

public class AssignPointsToDiseases {
	
	private String result;
	
	public String getResult()
	{
		return result;
	}
	
	public void assignPoints(OWLModel owlModel, String diseaseList)
	{
        DiseaseHolder[] holder=new DiseaseHolder[100];
    	int diseaseHolderSize=0;
    	
    	String symptomCode="";    	
    	String symptomDescription="";
    	
    	OWLNamedClass allDiseasesClass = owlModel.getOWLNamedClass("DISEASE");
        
		//--------------------------------------------------------------------
		OWLProperty hasRootTermProp= owlModel.getOWLProperty("hasRootTerm");		
		OWLProperty hasLabelProp= owlModel.getOWLProperty("label");
		OWLProperty indicatesProp= owlModel.getOWLProperty("indicates");
		//---------------------------------------------------------------------
		
		//----------------------------reading symptoms and finding their codes----------------------------
	
		StringBuffer sb=new StringBuffer();
		StringTokenizer st=new StringTokenizer(diseaseList, "\n");
		while (st.hasMoreTokens())
		{
			symptomDescription=st.nextToken();
			
			
			
			OWLNamedClass englishTermClass=owlModel.getOWLNamedClass("englishTerm");
			Collection englishTermCollection=englishTermClass.getInstances();
			Iterator it=englishTermCollection.iterator();
			
			StringBuffer englishTerms=new StringBuffer();
			String term="";
			
			boolean done=false;
			
			while (it.hasNext() && !done)
			{
				symptomCode="none";
				OWLIndividual termIndividual=(OWLIndividual) it.next(); //displays something like englishTerm_55
				
				if (symptomDescription.equals((termIndividual.getPropertyValue(hasLabelProp).toString()).toLowerCase()))
					{
					RDFResource resource=(RDFResource) termIndividual.getPropertyValue(hasRootTermProp);
					symptomCode=resource.getBrowserText();
					System.out.println("match");
					done=true;
					}				
			}
			//after the while loop, term has the code of the symptom given as an argument
			
			sb.append(symptomCode+"\n");
		}
		
		
		//-----------------------------finding diseases and assigning points-------------------------
		
		int i=-1;
		boolean done=false;
		st=new StringTokenizer(sb.toString(),"\n");
		while (st.hasMoreTokens())
		{
			i++;
			symptomCode=st.nextToken();
			System.out.println("Symptom Code:"+symptomCode);
			if (symptomCode.indexOf("SYMPTOM_")>-1)   //you do this because some might not be correct codes
			{
			OWLIndividual symptomIndividual=owlModel.getOWLIndividual(symptomCode);
			Collection indicates=symptomIndividual.getPropertyValues(indicatesProp);
			Iterator it=indicates.iterator();
			
				while (it.hasNext()) //this iterates through the diseases found in the symptom individual
				{
					RDFResource resource=(RDFResource) it.next();
					System.out.print("----code: "+resource.getBrowserText());
			
					OWLIndividual foundMatch=owlModel.getOWLIndividual(resource.getBrowserText());
					System.out.println("----name: "+foundMatch.getPropertyValue(hasLabelProp));					
					
					if (i>0)
					{
						for (int j=0; (j<diseaseHolderSize) && !done; j++)
						{
							if (holder[j].getDiseaseIndividual().equals(foundMatch))
							{
								holder[j].getDiseaseList().add(symptomIndividual);
								done=true;
							}
						}
						
						if (!done)
						{
						holder[diseaseHolderSize]=new DiseaseHolder(foundMatch);
						holder[diseaseHolderSize].getDiseaseList().add(symptomIndividual);
						diseaseHolderSize++;
						done=true;
						}
					done=false;
					}
					else { 
						holder[diseaseHolderSize]=new DiseaseHolder(foundMatch);
						holder[diseaseHolderSize].getDiseaseList().add(symptomIndividual);
						diseaseHolderSize++;
						}
					
				}//end while 
				
			}//end if
			//else System.out.println("This symptom does not have a code!"); // not relevant for final app.
		}//end for 
			
			StringBuffer finalResult=new StringBuffer();
		
			for (int j=0; j<diseaseHolderSize; j++)
			{
			
			if (holder[j].getDiseaseIndividual().getPropertyValue(hasLabelProp)!=null)
				finalResult.append(holder[j].getDiseaseIndividual().getPropertyValue(hasLabelProp).toString());
			else 
				System.out.print(holder[j].getDiseaseIndividual().getBrowserText());
			
			finalResult.append(" has "+holder[j].getDiseaseList().getLength()+" point(s)"+"\n");
			
				for (int k=0; k<holder[j].getDiseaseList().getLength(); k++)
				{
				finalResult.append("----"+holder[j].getDiseaseList().getEntry(k+1).getPropertyValue(hasLabelProp).toString()+"\n");	
				}
			}
			result=finalResult.toString();
	}
	
//    public static void main(String[] args) 
//    {
//    	try{
//    	OWLModel owlModel = ProtegeOWL.createJenaOWLModel();
//	    owlModel = ProtegeOWL.createJenaOWLModelFromURI("file:/home/ndragu/Desktop/biocaster.owl");
//    	AssignPointsToDiseases ap=new AssignPointsToDiseases();
//    	StringBuffer sb=new StringBuffer();
//    	sb.append("bleeding\nfrom\ngums\ndeafness\nswollen\nsalivary\nglands\nbleeding from gums\nswollen salivary glands");
//    	ap.assignPoints(owlModel, sb.toString());
//    	System.out.println("Output: "+ap.getResult());
//    	}
//    	catch (Exception e)
//    	{}
//    }
}
