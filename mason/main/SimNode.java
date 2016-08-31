package main;
import java.util.HashSet;
import sim.engine.*;
import sim.util.*;

public class SimNode implements Steppable {

	private static final long serialVersionUID = 1L;
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
        Double2D me = net.yard.getObjectLocation(this);
        MutableDouble2D sumForces = new MutableDouble2D();
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