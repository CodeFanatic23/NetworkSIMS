package main;
//import java.util.HashSet;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;

public class SimNetwork extends SimState {

    private static final long serialVersionUID = 1L;
    public Continuous2D yard = new Continuous2D(1.0, 100, 100);
    double randomMultiplier = 0.1;
    double forceToSchoolMultiplier = 0.01;
    
    //Use this hashset to create nodes which should be unique
   // private HashSet<SimNode> nodes = new HashSet<SimNode>();
    
   
    public Network buddies = new Network(false);
    public SimNetwork(long seed) {
        super(seed);
    }
    public void start() {
        super.start();
        // clear the yard
        yard.clear();
        // clear the buddies
        buddies.clear();

        
        //TODO: Read from some file and then make node, remove hardcoding
        
        
        SimNode v1 = new SimNode("v1",new SimSkill("eat",(float)100.0));
        SimNode v2 = new SimNode("v2",new SimSkill("eat",(float)100.0));
        SimNode v3 = new SimNode("v3",new SimSkill("eat",(float)100.0));
        /**
         * if(node_is_in_hashset)then skip else add new node*/

        //add labels and edge
        
        v1.addLabel("l1");
        v2.addLabel("l1");
        v1.addLabel("l2");
        v2.addLabel("l2");
        v3.addLabel("l3");
        v2.addLabel("l3");
                
      
                                   
        yard.setObjectLocation(v1,
                new Double2D(yard.getWidth() * 0.5 + 10.0 - 0.5,yard.getHeight() * 0.5 + 10.0 - 0.5));
        buddies.addNode(v1);
        schedule.scheduleRepeating(v1);
        
        yard.setObjectLocation(v2,
                new Double2D(yard.getWidth() * 0.5 + 20.0 - 0.5,yard.getHeight() * 0.5 + 10.0 - 0.5));
        buddies.addNode(v2);
        schedule.scheduleRepeating(v2);
        
        yard.setObjectLocation(v3,
                new Double2D(yard.getWidth() * 0.5 + 20.0 - 0.5,yard.getHeight() * 0.5 + 20.0 - 0.5));
        buddies.addNode(v3);
        schedule.scheduleRepeating(v3);
        
        buddies.addEdge(v1,v2,50);
        buddies.addEdge(v1,v2,50);        
        buddies.addEdge(v2,v3,50);

    }
    public static void main(String[] args) {
        doLoop(SimNetwork.class, args);
        System.exit(0);
    }
}