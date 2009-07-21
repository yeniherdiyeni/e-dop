
/**
 * The DiseaseHolder class is used to create objects containing an OWLIndividual, representing the name 
 * of a disease or a syndrome, and containing a LinkedList of OWLIndividuals for all symptoms that indicate
 * that disease. This is useful because after finding all matching symptoms between the input text and the
 * ontology, an array of such object could be created to hold the disease/syndrome and its symptoms and 
 * easily count the number of points for each disease/syndrome based on the number of nodes in the linked
 * list.
 * 
 *  @author Nicolae Dragu, Fouad Elkhoury
 *  Date: July 21, 2009
 */

package application;

import edu.stanford.smi.protegex.owl.model.OWLIndividual;

public class DiseaseHolder 
{
	private OWLIndividual diseaseIndividual;
	private LinkedList diseaseList;
	
	//default constructor which sets the private variables to null.
	public DiseaseHolder()
	{
		diseaseIndividual = null;
		diseaseList = null;
	}
	
	/**
	 * Constructor which sets the diseaseIndividual to the specified value and automatically creates
	 * a new linked list for that disease individual.
	 * @param diseaseIndividual represents the name of the disease/syndrome
	 */
	public DiseaseHolder(OWLIndividual diseaseIndividual)
	{
		this.diseaseIndividual = diseaseIndividual;
		diseaseList = new LinkedList();
	}
	
	/**
	 * The getDiseaseIndividual method is used to return the disease/syndrome individual contained by the
	 * object 
	 * @return returns the disease/syndrome individual contained by the object
	 */
	public OWLIndividual getDiseaseIndividual() {
		return diseaseIndividual;
	}
	
	/**
	 * The method is used to define a new disease/symptom individual based on the information passed
	 * as a parameter.
	 * @param diseaseIndividual represents the value that is going to form a new disease/syndrome individual
	 * for the object.
	 */
	public void setDiseaseIndividual(OWLIndividual diseaseIndividual) {
		diseaseList=new LinkedList();
		this.diseaseIndividual = diseaseIndividual;
	
	}
	
	/**
	 * This method is used to return the list contained by the class. This list will contain all 
	 * symptoms that indicate the disease/syndrome expressed by the diseaseIndividual from the same 
	 * object
	 * @return returns the linked list containing all the symptoms that indicate the disease/syndrome 
	 * expressed by the diseaseIndividual from the same object
	 */
	public LinkedList getDiseaseList() {
		return diseaseList;
	}
	
}
