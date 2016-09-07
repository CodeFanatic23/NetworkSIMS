package main;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;

public class SimNetwork extends SimState {

    private static final long serialVersionUID = 1L;
    public Continuous2D yard = new Continuous2D(1.0, 100, 100);
    
    private HashSet<SimNode> nodes = new HashSet<SimNode>();
    private ArrayList<String> data = new ArrayList<String>();
   
    public Network buddies = new Network(false);

    public SimNetwork(long seed) {
    	super(seed);
    	try {
			takeInputFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
        
    private  void takeInputFromFile() throws IOException
    {

    	try (BufferedReader br = new BufferedReader(new FileReader("C://Programming/Java/Mason/mason/main/input.txt"))) {
    	    String line;
    	    while ((line = br.readLine()) != null) {
    	       // process the line.
    	    	System.out.println(line);
    	    	data.add(line);
    	    	
    	    }
    	}
    }
    
    
    private  void takeInput(Schedule schedule) throws IOException
    {

    	    for(String line : data) {
    	       // process the line.
    	    	System.out.println(line);
    	    	String[] n = line.split(" ");
    	    	SimNode v1 = new SimNode(n[0]);
    	    	SimNode v2 = new SimNode(n[1]);
    	    	System.out.println(v1.equals(v2));
    	    	if(nodes.add(v1))
    	    	{
    	    		v1.skills.addSkill("eat", (float)100.0);
    	    		yard.setObjectLocation(v1,
    	                    new Double2D(yard.getWidth() * 0.5 + 2*random.nextDouble() - 0.5,yard.getHeight() * 0.5 + 10*random.nextDouble() - 0.5));
    	            buddies.addNode(v1);
    	            
    	    		
    	    	}
    	    	else{
    	    		for(SimNode s:nodes){
    	    			if(s.equals(v1)){
    	    				v1 = s;
    	    				break;
    	    			}
    	    		}
    	    		
    	    	}
    	    	v1.addLabel(n[3]);
    	    	schedule.scheduleRepeating(v1);
    	    	if(nodes.add(v2))
    	    	{
    	    		System.out.println(v2.getNodeName());
    	    		
    	    		v2.skills.addSkill("eat", (float)100.0);
    	    		yard.setObjectLocation(v2,
    	                    new Double2D(yard.getWidth() * 0.5 + 2*random.nextDouble() - 0.5,yard.getHeight() * 0.5 + 20*random.nextDouble() - 0.5));
    	            buddies.addNode(v2);
    	            
    	    	}
    	    	else{
    	    		for(SimNode s:nodes){
    	    			if(s.equals(v2)){
    	    				v2 = s;
    	    				break;
    	    			}
    	    		}
    	    		
    	    	}
    	    	
    	    	v2.addLabel(n[3]);
    	    	schedule.scheduleRepeating(v2);
    	    	buddies.addEdge(v1, v2, n[2]);
    	    	
    	    	
    	    }
    	    for(SimNode s:nodes){
    	    	System.out.println(s.getNodeName());
    	    }
    	    System.out.println(nodes.size());
    	    
    	    //writeToCheckpoint(new File("/home/anant/Downloads/gitSOP/NetworkSIMS-master/mason/main/output.txt"));
    	}
    
    
    public void start() {
        super.start();
        // clear the yard
        yard.clear();
        // clear the buddies
        buddies.clear();
        try {
			takeInput(schedule);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
        
       

    
    public static void main(String[] args) {
        doLoop(SimNetwork.class, args);
        System.exit(0);
    }

    
}