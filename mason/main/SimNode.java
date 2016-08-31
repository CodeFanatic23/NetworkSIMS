package main;

import java.util.HashSet;
import java.util.HashMap;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;


public class SimNode implements Steppable {
	
	//To maintain uniqueness of labels
	public HashSet<String> label;
	public SimSkill skills;
	private String nodeName;
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
	}
	
	
    public void step(SimState state) {
    	
        SimNetwork net = (SimNetwork) state;
        Continuous2D yard = net.yard;
        Double2D me = net.yard.getObjectLocation(this);
        MutableDouble2D sumForces = new MutableDouble2D();
        MutableDouble2D forceVector = new MutableDouble2D();
      
       
        sumForces.addIn(new Double2D((yard.width * 0.5 - me.x) * net.forceToSchoolMultiplier, (yard.height * 0.5 - me.y) * net.forceToSchoolMultiplier));
        sumForces.addIn(new Double2D(net.randomMultiplier * (net.random.nextDouble() * 1.0 - 0.5),net.randomMultiplier * (net.random.nextDouble() * 1.0 - 0.5)));
        sumForces.addIn(me);
        net.yard.setObjectLocation(this, new Double2D(sumForces));
    }

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}