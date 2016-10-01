package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ec.util.MersenneTwisterFast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;

public class SimNetwork extends SimState {
	
    public static Continuous2D yard = new Continuous2D(1.0, 100, 100);
    public static HashSet<SimNode> nodes = new HashSet<SimNode>();
    public static HashMap<SimNode,sim.engine.Stoppable> stopper;
    public static Network buddies = new Network(false);
    
    protected static ArrayList<String> relation = new ArrayList<String>();
   
    private static Schedule schedule1 = null;
    private static final long serialVersionUID = 1L;
    private static double spreadWidth = 0.28;
    private static double spreadHeight = 0.4;
    private ArrayList<String> data = new ArrayList<String>();;
    private static int professionCounter = 0;
   
    public SimNetwork(long seed) {
    	
    	super(seed);
    	try {
			takeInputFromFile();
			takeRelation();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	stopper = new HashMap<SimNode, Stoppable>();
    }
    
    protected static SimNode returnNode(String nodeName)
    {
    	for(SimNode s : nodes)
    	{
    		if(s.getNodeName().equals(nodeName))
    		{
    			return s;
    		}
    	}
    	return null;
    }
    protected static void createChildNode(SimNode parent1,SimNode parent2)
    {
    	//creating a child between two nodes
    	System.out.println("creating child");
    	String childName = parent1.getNodeName() + parent2.getNodeName();
    	System.out.println(childName +  " born");
    	MersenneTwisterFast random = new MersenneTwisterFast();
    	SimNode child = new SimNode(childName);
    	child.setGenerationFlag(1);
    	sim.engine.Stoppable childstop = null;
    	child.skills.addSkill("eat", (float)100.0);
		SimNetwork.yard.setObjectLocation(child,
                new Double2D(SimNetwork.yard.getWidth() * spreadWidth + 2*random.nextDouble() - spreadWidth,SimNetwork.yard.getHeight() * spreadHeight + 50*random.nextDouble() - spreadHeight));
        buddies.addNode(child);
        childstop = schedule1.scheduleRepeating(child);
        stopper.put(child,childstop);
        nodes.add(child);
        //WARNING: weight between parent and child is by default 100
        buddies.addEdge(parent1,child,"100");
        SimNetwork.addEdgeLabel(parent1, child, "Parent-child");
        
        buddies.addEdge(parent2,child,"100");
        SimNetwork.addEdgeLabel(parent2, child, "Parent-child");
        
//        Bag out = SimNetwork.buddies.getEdges(child, null);
//        for(int buddy = 0; buddy < out.size();buddy++)
//        {
//        		Edge e = (Edge)out.get(buddy);
//        }
    }
    private void takeRelation()
    {
    	//taking relation
    	try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Anants pc/Downloads/Compressed/NetworkSIMS-master/mason/main/relation.txt"))) {
    	    String line;
    	    while ((line = br.readLine()) != null) {
    	       // process the line.
    	    	System.out.println(line);
    	    	relation.add(line);
    	    	
    	    }
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private  void takeInputFromFile() throws IOException
    {
    	//taking initial states
    	try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/Anants pc/Downloads/Compressed/NetworkSIMS-master/mason/main/input.txt"))) {
    	    String line;
    	    while ((line = br.readLine()) != null) {
    	       // process the line.
    	    	System.out.println(line);
    	    	data.add(line);
    	    	
    	    }
    	}
    }
   
    
    protected static void addEdgeLabel(SimNode v1,SimNode v2,String s)
    {
    	Bag b = buddies.getEdges(v1, v2,null);
    	//adding label on the edge between two nodes
    	for(int i = 0; i < b.size();i++)
    	{
    		
    		try
    		{
    			Integer.parseInt((String) ((Edge) b.get(i)).getInfo());
    			
    			((Edge) b.get(i)).setInfo(s);
    		}
    		catch(Exception e) 
    		{
    			continue;
    		}
    		
    	}
    }
    
    public static String EquiLikelyProfession()
    {
    	//distributing profession equally
    	if(professionCounter%5 == 0)
    	{
    		professionCounter++;
    		return "farmer";
    	}
    	else if(professionCounter%5 == 1)
    	{
    		professionCounter++;
    		return "doctor";
    	}
    	else if(professionCounter%5 == 2)
    	{
    		professionCounter++;
    		return "craftsman";
    	}
    	else if(professionCounter%5 == 3)
    	{
    		professionCounter++;
    		return "teacher";
    	}
    	else if(professionCounter%5 == 4)
    	{
    		professionCounter++;
    		return "banker";
    	}
    	return "";
    }
    private  void takeInput(Schedule schedule) throws IOException
    {
    		int flag = 0;

    	    for(String line : data) {
    	       // process the line.
    	    	System.out.println(line);
    	    	String[] n = line.split(" ");
    	    	SimNode v1 = new SimNode(n[0]);
    	    	sim.engine.Stoppable v1stop = null;
    	    	sim.engine.Stoppable v2stop = null;
    	    	SimNode v2 = null;
    	    	if(n.length == 1)
    	    	{
    	    		//single node
    	    		flag = 1;
    	    	}
    	    	else
    	    	{
    	    		v2 = new SimNode(n[1]);
    	    		flag = 0;
    	    	}
    	    	if(nodes.add(v1))
    	    	{
    	    		//adding node to graph
    	    		v1.skills.addSkill("eat", (float)100.0);
    	    		yard.setObjectLocation(v1,
    	                    new Double2D(yard.getWidth() * spreadWidth + 2*random.nextDouble() - spreadWidth,yard.getHeight() * spreadHeight + 50*random.nextDouble() - spreadHeight));
    	            buddies.addNode(v1);
    	            System.out.println(v1.getNodeName() + " born");
    	            v1stop = schedule.scheduleRepeating(v1);
    	    		stopper.put(v1, v1stop);
    	    	}
    	    	else{
    	    		for(SimNode s:nodes){
    	    			if(s.equals(v1)){
    	    				v1 = s;
    	    				break;
    	    			}
    	    		}
    	    		
    	    	}
    	    	if(flag == 0)
    	    	{
    	    		//adding label to v1
    	    		v1.addLabel(n[3]);
    	    	}
    	    	
    	    	
    	    	if(flag == 0)
    	    	{
	    	    	if(nodes.add(v2))
	    	    	{
	    	    		//adding v2 to graph
	    	    		v2.skills.addSkill("eat", (float)100.0);
	    	    		yard.setObjectLocation(v2,
	    	                    new Double2D(yard.getWidth() * spreadWidth + 13*random.nextDouble() - spreadWidth,yard.getHeight() * spreadHeight + 20*random.nextDouble() - spreadHeight));
	    	            buddies.addNode(v2);
	    	            v2stop = schedule.scheduleRepeating(v2);
	    	            stopper.put(v2,v2stop);
	    	            System.out.println(v2.getNodeName() + " born");
	    	            
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
	    	    	
	    	    	buddies.addEdge(v1, v2, n[2]);
	    	    	System.out.println(v1.getNodeName() + " " + v2.getNodeName() + "  " +  n[3]);
	    	    	SimNetwork.addEdgeLabel(v1, v2, n[3]);
	    	    	
	    	    
	    	    }
    	    }
    	    
       	}
    
    
    public void start() {
        super.start();
        // clear the yard
        yard.clear();
        // clear the buddies
        buddies.clear();
        SimNode.livingNode = nodes.size();
        try {
			takeInput(schedule);
			this.schedule1 = schedule;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
        
       

    
    public static void main(String[] args) {
        doLoop(SimNetwork.class, args);
        System.exit(0);
    }

    
}