package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import sim.engine.*;
import sim.field.network.Edge;
import sim.util.*;

public class SimNode implements Steppable {

	private static final long serialVersionUID = 1L;
	//To maintain uniqueness of labels
	public HashSet<String> label;
	public SimSkill skills;
	private String nodeName;
	private SimNode spouseObject = null;
	private int numberOfChildren = 1;
	public int sharmaVariable = 0;
	public static int livingNode = 0;
	
	public void addLabel(String label){
		this.label.add(label);
	}
	
	
	public SimNode(String nodeName,SimSkill skills){
		this.skills = skills;
		this.setNodeName(nodeName);
		this.label = new HashSet<String>();
	}
	public SimNode(String nodeName){
		this.setNodeName(nodeName);
		this.label = new HashSet<String>();
		this.skills = new SimSkill();
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
	    	        		if(((String) (((Edge) out.get(buddy)).getInfo())).equals("marriage"))
	    	        		{
	    	        			System.out.println(this.getNodeName() + this.getSpouseObject().getNodeName() + " already married");
	    	        			flag = 1;
	    	        			break;
	    	        		}
	    	        	}
	    	        	if(flag == 0)
	    	        	{
	    	        		if(this.getNodeName().equals(n[0]))
	    	        		{
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
	    	        				System.out.println("spouse already dead or not born");
	    	        			}
	    	        		}
	    	        		
	    	        	}
	    	    		
    	    	}
        	}
        }
       
        else if(sharmaVariable == 27)
        {
        	//synchronized (this) {
				if(this.spouseObject != null)
				{
		        	if(this.numberOfChildren <= this.spouseObject.numberOfChildren)
		        	{
		        		if(this.nodeName.compareToIgnoreCase(this.getSpouseObject().getNodeName())<0)
		        			SimNetwork.createChildNode(this, this.getSpouseObject());
		        		else
		        			SimNetwork.createChildNode( this.getSpouseObject(),this);
		        		this.numberOfChildren--;
		        		System.out.println(this.getNodeName() + " " + this.getSpouseObject().getNodeName() + " gave birth");
		        	}
		        	
				//}
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
        	System.out.println(n.getNodeName() + " died");
        	//SimNetwork.buddies.removeNode(this);
        	//System.out.println("Steps-->"+state.schedule.getSteps()); 
        	SimNode.livingNode--;
        	SimNetwork.stopper.get(this).stop();
        	SimNetwork.nodes.remove(this);
        	SimNetwork.stopper.remove(this);
        	if(this.getSpouseObject()!=null)
        	{
        		this.getSpouseObject().setSpouseObject(null);
        	}
        	if(SimNode.livingNode == 0)
        	{
        		state.finish();
        	}
        }
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

}