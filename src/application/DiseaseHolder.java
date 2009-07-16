package application;

import edu.stanford.smi.protegex.owl.model.OWLIndividual;

public class DiseaseHolder 
{
	private OWLIndividual diseaseIndividual;
	private LinkedList diseaseList;
	
	public DiseaseHolder()
	{
		diseaseIndividual = null;
		diseaseList = null;
	}
	public DiseaseHolder(OWLIndividual diseaseIndividual)
	{
		this.diseaseIndividual = diseaseIndividual;
		diseaseList = new LinkedList();
	}
	public OWLIndividual getDiseaseIndividual() {
		return diseaseIndividual;
	}
	public void setDiseaseIndividual(OWLIndividual diseaseIndividual) {
		diseaseList=new LinkedList();
		this.diseaseIndividual = diseaseIndividual;
	
	}
	public LinkedList getDiseaseList() {
		return diseaseList;
	}
	
}
