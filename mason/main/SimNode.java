package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import sim.engine.*;
import sim.field.network.Edge;
import sim.util.*;

public class SimNode implements Steppable {

	
	//To maintain uniqueness of labels
	public HashSet<String> label;
	public SimSkill skills;
	public int sharmaVariable;
	public static int livingNode = 0;
	
	private static final long serialVersionUID = 1L;
	private String nodeName;
	private SimNode spouseObject;
	private SimNode mentorObject = null;
	private ArrayList<SimNode> menteeObjects;
	private int numberOfChildren;
	private int generationFlag;
	private String profession;
	private Integer professionValue;
	private Random r;
	private double randomNormal;
	private int professionAge;
	private int totalMentee;
	
	public void addLabel(String label){
		this.label.add(label);
	}
	
	
	public SimNode(String nodeName,SimSkill skills){
		this.skills = skills;
		setNumberOfChildren(1);
		this.setNodeName(nodeName);
		sharmaVariable = 0;
		profession = "";
		generationFlag = 0;
		setProfessionValue(null);
		setMenteeObjects(new ArrayList<SimNode>());
		this.label = new HashSet<String>();
		r = new Random();
		randomNormal = r.nextGaussian();
		setProfessionAge((int) (randomNormal*1 + 16));
		setTotalMentee((int) (randomNormal*1 + 3));
		spouseObject = null;
	}
	public SimNode(String nodeName){
		this.setNodeName(nodeName);
		setNumberOfChildren(1);
		sharmaVariable = 0;
		generationFlag = 0;
		setProfessionValue(null);
		profession = "";
		this.label = new HashSet<String>();
		setMenteeObjects(new ArrayList<SimNode>());
		this.skills = new SimSkill();
		r = new Random();
		randomNormal = r.nextGaussian();
		setProfessionAge((int) (randomNormal*1 + 16));
		setTotalMentee((int) (randomNormal*1 + 3));
		spouseObject = null;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			SimNode tiger = (SimNode) object;
			if (this.nodeName.equals(tiger.getNodeName())) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		hash = 37 * hash + this.nodeName.hashCode();
		return hash;
	}
	
    public  void step(SimState state) {
    	
    	
        SimNetwork net = (SimNetwork) state;
        Double2D me = net.yard.getObjectLocation(this);
        MutableDouble2D sumForces = new MutableDouble2D();
        sumForces.addIn(me);
        net.yard.setObjectLocation(this, new Double2D(sumForces));
//        1) Born		0
//        2)Toddler	1
//        3)Adolescent	8
//        4)Teenager	13
//        5)Adult		21------>Marriage	25
//        		|------->Children	27
//
//        6)Middle age	35
//        7)Old age	50
//        8)Death		75
        if(getProfessionValue() != null && generationFlag != 0)
        {
        	//increment value with time and saturate it till it reaches value of mentor
        	setProfessionValue(getProfessionValue() + 1);
        	
        	if(mentorObject != null && getProfessionValue() >= mentorObject.getProfessionValue())
        	{
        		Bag e = SimNetwork.buddies.getEdges(this, mentorObject, null);
        		int i;
        		for( i = 0; i < e.size(); i++) 
        		{
        			if(((Edge)(e.get(i))).getInfo().equals("student-mentor"))
        			{
        				break;
        			}
				}
        		if(i < e.size())
        		{
        			SimNetwork.buddies.removeEdge((Edge)e.get(i));
        			System.out.println(nodeName + " no longer mentee of " + mentorObject.nodeName);
        			mentorObject = null;
        		}
        	}
        }
        if(generationFlag == 0)
        {
        	//increase professional value but no mentor
        	if(getProfessionValue() == null)
        	{
        		setProfessionValue(new Integer(0));
        	}
        	else
        	{
        		setProfessionValue(getProfessionValue() + 1);
        	}
        }
        //sharmaVariabe is age of the node
        if(sharmaVariable == getProfessionAge() )
        {
        	//select profession
        	this.profession = SimNetwork.EquiLikelyProfession();
        	System.out.println(this.nodeName + " becomes " +this.profession);
        	if(this.generationFlag != 0)
        	{
        		//not generation zero assign a mentor
        	 	Bag out = SimNetwork.buddies.getAllNodes();
            	for(int buddy = 0; buddy < out.size();buddy++)
            	{
            		SimNode e = (SimNode)out.get(buddy);
            		
	            	if(e.getProfession().equals(this.profession) && (!(this.nodeName.equals(e.nodeName))) && (e.getProfessionValue()!=null))
		            {
	            		
		            	if(e.getTotalMentee() > 0)
		            	{
		            		//creating mentor
			            	SimNetwork.buddies.addEdge(this,e,"100");
				        	SimNetwork.addEdgeLabel(this, e,"student-mentor");
				        	e.setTotalMentee(e.getTotalMentee() - 1);
				        	setProfessionValue(new Integer(0));
				        	System.out.println(e.nodeName + " mentor of " + nodeName);
				        	mentorObject = e;
				        	e.getMenteeObjects().add(this);
				        	break;
		            	}
		            }
            		
            	}
            	if(mentorObject == null)
            	{
            		//no mentor found
            		System.out.println("no mentor found for " + nodeName);
            	}
        		
        	}
        	
        	
        }
        
        if(sharmaVariable == 0){
        	//born
        	this.skills.changeSkillVal("eat", (float)0);
        }
        else if(sharmaVariable == 1){
        	//todler
        	System.out.println(this.getNodeName() + " entered todler stage");
        	this.skills.changeSkillVal("eat", (float)20);
        }
        else if(sharmaVariable == 8){
        		//adolescent
        	System.out.println(this.getNodeName() + " entered adolescent stage");
        	this.skills.changeSkillVal("eat", (float)70);
        }
        else if(sharmaVariable == 13){
        	//teenager
        	System.out.println(this.getNodeName() + " entered teenager stage");
        	this.skills.changeSkillVal("eat", (float)75);
        }
        else if(sharmaVariable == 21){
        	//adult
        	System.out.println(this.getNodeName() + " entered adult stage");
        	this.skills.changeSkillVal("eat", (float)80);
        }
        else if(sharmaVariable == 25)
        {
        	
        	for(String line : SimNetwork.relation)
        	{
        		String[] n = line.split(" ");
    	    	if(this.getNodeName().equals(n[0]) || this.getNodeName().equals(n[1]))
    	    	{
	    	        	Bag out = SimNetwork.buddies.getEdges(SimNetwork.returnNode(n[0]), null);
	    	        	int flag = 0;
	    	        	for(int buddy = 0; buddy < out.size();buddy++)
	    	        	{
	    	        		//checking if already married
	    	        		if(((String) (((Edge) out.get(buddy)).getInfo())).equals("marriage"))
	    	        		{
	    	        			System.out.println(this.getNodeName() + " " + this.getSpouseObject().getNodeName() + " already married");
	    	        			flag = 1;
	    	        			break;
	    	        		}
	    	        	}
	    	        	if(flag == 0)
	    	        	{
	    	        		if(this.getNodeName().equals(n[0]))
	    	        		{
	    	        			//marring the node v0 v1
	    	        			SimNode n1 = SimNetwork.returnNode(n[1]);
	    	        			if(n1 != null  ){
		    	        			System.out.println(this.getNodeName() + " " + n1.getNodeName() + " happily married");
		    	        			
		    	        			SimNetwork.buddies.addEdge(this,n1,n[2]);
		    	        			SimNetwork.addEdgeLabel(this, n1, n[3]);
		    	        			this.spouseObject = n1;
		    	        			n1.setSpouseObject(this);
	    	        			}
	    	        			else
	    	        			{
	    	        				System.out.println("spouse already dead or not born");
	    	        			}
	    	        			
	    	        		}
	    	        		else
	    	        		{
	    	        			//marring the node v1 v0
	    	        			SimNode n0 = SimNetwork.returnNode(n[0]);
	    	        			if(n0 != null)
	    	        			{
	    	        				System.out.println(this.getNodeName() + " " + n0.getNodeName() + " happily married");
		    	        			SimNetwork.buddies.addEdge(this,n0,n[2]);
		    	        			SimNetwork.addEdgeLabel(this, n0, n[3]);
		    	        			this.spouseObject = n0;
		    	        			n0.setSpouseObject(this);
	    	        			}
	    	        			else
	    	        			{
	    	        				//spouse not born or dead
	    	        				System.out.println("spouse already dead or not born");
	    	        			}
	    	        		}
	    	        		
	    	        	}
	    	    		
    	    	}
        	}
        }
       
        else if(sharmaVariable == 27)
        {
        	//creating child
				if(this.spouseObject != null)
				{
					//if spouse present than create child named lexicographically
		        	if(this.getNumberOfChildren() <= this.spouseObject.getNumberOfChildren())
		        	{
		        		if(this.nodeName.compareToIgnoreCase(this.getSpouseObject().getNodeName())<0)
		        			SimNetwork.createChildNode(this, this.getSpouseObject());
		        		else
		        			SimNetwork.createChildNode( this.getSpouseObject(),this);
		        		this.setNumberOfChildren(this.getNumberOfChildren() - 1);
		        		System.out.println(this.getNodeName() + " " + this.getSpouseObject().getNodeName() + " gave birth");
		        	}
		        	
        	}
        }
        else if(sharmaVariable == 35){
        	//middle age
        	System.out.println(this.getNodeName() + " entered middle age");
        	this.skills.changeSkillVal("eat", (float)60);
        }
        else if(sharmaVariable == 50){
        	//old age
        	System.out.println(this.getNodeName() + " entered old age");
        	this.skills.changeSkillVal("eat", (float)50);
        }
        else if(sharmaVariable == 75)
        { 
        	//dead
        	this.skills.changeSkillVal("eat", (float)0);
        	SimNode n = (SimNode) SimNetwork.buddies.removeNode(this);
        	
//        	Bag out = SimNetwork.buddies.getEdges(n, null);
//        	for(int buddy = 0; buddy < out.size();buddy++)
//        	{
//        		Edge e = (Edge)out.get(buddy);
//        		SimNetwork.buddies.removeEdge(e);
//        	}
        	
        	//SimNetwork.buddies.removeNode(this);
        	//System.out.println("Steps-->"+state.schedule.getSteps());
        	
        	System.out.println(n.getNodeName() + " died");
        	SimNode.livingNode--;
        	SimNetwork.stopper.get(this).stop();
        	//removing node from set containing all the nodes  
        	SimNetwork.nodes.remove(this);
        	//stopping scheduler to call step method of dead state
        	SimNetwork.stopper.remove(this);
        	//remove link to mentee object
        	if(getMenteeObjects() !=null)
        	{
        		for(SimNode i : getMenteeObjects())
        		{
        			i.mentorObject = null;
        		}
        		
        	}
        	if(mentorObject != null)
        	{
        		for(int i = 0; i < mentorObject.getMenteeObjects().size() ; i++)
        		{
        			if((mentorObject.getMenteeObjects().get(i).equals(this)))
        			{
        				mentorObject.getMenteeObjects().remove(i);
        			}
        		}
        	}
        	if(this.getSpouseObject()!=null)
        	{
        		this.getSpouseObject().setSpouseObject(null);
        	}
        	if(SimNode.livingNode == 0)
        	{
        		//finishing simulation
        		state.finish();
        	}
        	return;
        }
       
        //increase age 
        System.out.println("age - " + this.nodeName + " " +  sharmaVariable);
        sharmaVariable++;
        
        
    }

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public SimNode getSpouseObject() {
		return spouseObject;
	}


	public void setSpouseObject(SimNode spouseObject) {
		this.spouseObject = spouseObject;
	}


	public String getProfession() {
		return profession;
	}


	public void setProfession(String profession) {
		this.profession = profession;
	}


	public int getGenerationFlag() {
		return generationFlag;
	}


	public void setGenerationFlag(int generationFlag) {
		this.generationFlag = generationFlag;
	}


	public SimNode getMentorObject() {
		return mentorObject;
	}


	public void setMentorObject(SimNode mentorObject) {
		this.mentorObject = mentorObject;
	}


	public ArrayList<SimNode> getMenteeObjects() {
		return menteeObjects;
	}


	public void setMenteeObjects(ArrayList<SimNode> menteeObjects) {
		this.menteeObjects = menteeObjects;
	}


	public int getNumberOfChildren() {
		return numberOfChildren;
	}


	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}


	public Integer getProfessionValue() {
		return professionValue;
	}


	public void setProfessionValue(Integer professionValue) {
		this.professionValue = professionValue;
	}


	public int getProfessionAge() {
		return professionAge;
	}


	public void setProfessionAge(int professionAge) {
		this.professionAge = professionAge;
	}


	public int getTotalMentee() {
		return totalMentee;
	}


	public void setTotalMentee(int totalMentee) {
		this.totalMentee = totalMentee;
	}

}