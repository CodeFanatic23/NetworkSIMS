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
	
	public static int sharmaVariable = 0;
	
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
	
    public void step(SimState state) {
    	
        SimNetwork net = (SimNetwork) state;
        Double2D me = net.yard.getObjectLocation(this);
        MutableDouble2D sumForces = new MutableDouble2D();
        sumForces.addIn(me);
        net.yard.setObjectLocation(this, new Double2D(sumForces));
        if(sharmaVariable == 0){
        	this.skills.changeSkillVal("eat", (float)0);
        }
        else if(sharmaVariable == 50){
        	this.skills.changeSkillVal("eat", (float)50);
        }
        else if(sharmaVariable == 100){
        	this.skills.changeSkillVal("eat", (float)100);
        }
        else if(sharmaVariable == 150){
        	this.skills.changeSkillVal("eat", (float)50);
        }
        else if(sharmaVariable == 200){
        	state.kill();
        	System.out.println("Steps-->"+state.schedule.getSteps());
       
        }
        
        sharmaVariable++;
        System.out.println(sharmaVariable);
    

    }

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

}