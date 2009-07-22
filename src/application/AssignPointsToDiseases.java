
/*
 * The AssignPointsToDiseases class represents the scoring mechanism for the application. The workflow is as follows:
 * 1. takes the diseaseList string containing all matches found between the ontology and the input text
 * 2. using those matches it searches for their codes in the ontology (something in the form of SYMPTOM_120)
 * 3. using the symptom codes, all diseases which indicate each symptom will be stored in a DiseaseHolder array 
 * having the symptoms as part of the linked list of each disease.
 * 4. in the end each position in the DiseaseHolder array will hold a disease/syndrome and a linked list of all 
 * symptoms which indicate that disease/syndrome, thus creating a scoring metric by listing the disease/syndrome and 
 * counting its points by counting the number of symptoms in the linked list.
 * 
 *  @authod Nicolae Dragu, Fouad Elkhoury
 *  Date: July 21, 2009
 */

package application;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLProperty;
import edu.stanford.smi.protegex.owl.model.RDFResource;

public class AssignPointsToDiseases {
        
		//the result variable will contain the displayable information in the form of "disease has x points"
        private String result;
        
        /**
         * This method is used to return the variable containing the results calculated 
         * by the metric
         * @return returns the results calculated by the metric
         */
        public String getResult()
        {
                return result;
        }
        
        /**
         * The assignPoints method is used to calculate the number of points corresponding to each disease/syndrome
         * @param owlModel represents the ontology model which enables access to all information
         * @param diseaseList represents the list of matches found between the ontology and the input text
         */
        public void assignPoints(OWLModel owlModel, String diseaseList)
        {
        DiseaseHolder[] holder=new DiseaseHolder[100];
        int diseaseHolderSize=0;
        
        String symptomCode="";          
        String symptomDescription="";
        
        /*--------------------------------------------------------------------
         *----these OWLProperties enable access contained by OWLIndividuals
         *----hasRootTerm refers to the code of the OWLIndividual (in the form of SYMPTOM_120)
         *----label is a property which indicates the english name of the individual
         *----indicates is a symptom property which displays which diseases/syndromes that symptom indicates
         */
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
                        
                        //retrieving the class containing all the english terms
                        OWLNamedClass englishTermClass=owlModel.getOWLNamedClass("englishTerm");
                        Collection englishTermCollection=englishTermClass.getInstances();
                        Iterator it=englishTermCollection.iterator();
                        
                        StringBuffer englishTerms=new StringBuffer();
                        String term="";
                        
                        boolean done=false;
                        
                        //iterating through all english terms and for all matches the codes are taken
                        while (it.hasNext() && !done)
                        {
                                symptomCode="none";
                                OWLIndividual termIndividual=(OWLIndividual) it.next(); //displays something like englishTerm_55
                                
                                if (symptomDescription.equals((termIndividual.getPropertyValue(hasLabelProp).toString()).toLowerCase()))
                                        {
                                        RDFResource resource=(RDFResource) termIndividual.getPropertyValue(hasRootTermProp);
                                        symptomCode=resource.getBrowserText();
                                        done=true;
                                        }                               
                        }
                        
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
                        
                        System.out.println("\nTerm Code:"+symptomCode);
                        
                        if (symptomCode.indexOf("DISEASE_")>-1)
                        {
                                boolean found=false;
                                        
                                for (int j=0; (j<diseaseHolderSize) && (!found); j++)
                                {
                                        if (symptomCode.equals(holder[j].getDiseaseIndividual().getBrowserText()))
                                        {
                                        found=true;     
                                        }
                                }
                                if (!found)
                                {
                                holder[diseaseHolderSize]=new DiseaseHolder(owlModel.getOWLIndividual(symptomCode));
                                diseaseHolderSize++;
                                }
                        }
                        else
                                if (symptomCode.indexOf("SYNDROME_")>-1)
                                {
                                        boolean found=false;
                                                
                                        for (int j=0; (j<diseaseHolderSize) && (!found); j++)
                                        {
                                                if (symptomCode.equals(holder[j].getDiseaseIndividual().getBrowserText()))
                                                {
                                                found=true;     
                                                }
                                        }
                                        if (!found)
                                        {
                                        holder[diseaseHolderSize]=new DiseaseHolder(owlModel.getOWLIndividual(symptomCode));
                                        diseaseHolderSize++;
                                        }
                                }
                        else
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
                }//end for 
                        
                //creating the final output to be displayed by the application
                        StringBuffer finalResult=new StringBuffer();
                        finalResult.append("\n");
                        for (int j=0; j<diseaseHolderSize; j++)
                        {
                        if (holder[j].getDiseaseIndividual().getPropertyValue(hasLabelProp)!=null)
                                finalResult.append(holder[j].getDiseaseIndividual().getPropertyValue(hasLabelProp).toString());
                        else 
                                finalResult.append(holder[j].getDiseaseIndividual().getBrowserText());
                        
                        
                        if (holder[j].getDiseaseList().getLength()!=0)
                        	finalResult.append(" has "+holder[j].getDiseaseList().getLength()+" point(s)"+"\n");
                        else 
                        	finalResult.append(" is a direct match with the ontology! \n");
                        	
                                for (int k=0; k<holder[j].getDiseaseList().getLength(); k++)
                                {
                                finalResult.append("----"+holder[j].getDiseaseList().getEntry(k+1).getPropertyValue(hasLabelProp).toString()+"\n");     
                                }
                        finalResult.append("\n");
           }
           result=finalResult.toString();
        }
    }
        