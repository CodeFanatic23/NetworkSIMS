package main;
import java.util.ArrayList;
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

    private static final long serialVersionUID = 1L;
    public static Continuous2D yard = new Continuous2D(1.0, 100, 100);
    
    private static HashSet<SimNode> nodes = new HashSet<SimNode>();
    private ArrayList<String> data = new ArrayList<String>();
    protected static ArrayList<String> relation = new ArrayList<String>();
    public static Network buddies = new Network(false);
    private static Schedule schedule1 = null;
    public SimNetwork(long seed) {
    	super(seed);
    	try {
			takeInputFromFile();
			takeRelation();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
    	System.out.println("creating child");
    	String childName = parent1.getNodeName() + parent2.getNodeName();
    	MersenneTwisterFast random = new MersenneTwisterFast();
    	SimNode child = new SimNode(childName);
    	child.skills.addSkill("eat", (float)100.0);
		SimNetwork.yard.setObjectLocation(child,
                new Double2D(SimNetwork.yard.getWidth() * 0.5 + 2*random.nextDouble() - 0.5,SimNetwork.yard.getHeight() * 0.5 + 50*random.nextDouble() - 0.5));
        buddies.addNode(child);
        schedule1.scheduleRepeating(child);
        nodes.add(child);
        //WARNING: weight between parent and child is by default 100
        buddies.addEdge(parent1,child,"100");
        SimNetwork.addEdgeLabel(parent1, child, "Parent-child");
        
        buddies.addEdge(parent2,child,"100");
        SimNetwork.addEdgeLabel(parent2, child, "Parent-child");
        
        Bag out = SimNetwork.buddies.getEdges(child, null);
        System.out.println("childsize" + out.size());
        for(int buddy = 0; buddy < out.size();buddy++)
        {
        		Edge e = (Edge)out.get(buddy);
        		System.out.println("LABELCHILD " + e.getInfo());
        }
    }
    private void takeRelation()
    {
    	try (BufferedReader br = new BufferedReader(new FileReader("C://Programming/Java/Mason/mason/main/relation.txt"))) {
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

    	try (BufferedReader br = new BufferedReader(new FileReader("C://Programming/Java/Mason/mason/main/input.txt"))) {
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
    	
    	for(int i = 0; i < b.size();i++)
    	{
    		
    		try
    		{
    			Integer.parseInt((String) ((Edge) b.get(i)).getInfo());
    			
    			((Edge) b.get(i)).setInfo(s);
    		}
    		catch(Exception e) 
    		{
    			
    		}
    		
    	}
    }
    
    private  void takeInput(Schedule schedule) throws IOException
    {
    		int flag = 0;

    	    for(String line : data) {
    	       // process the line.
    	    	System.out.println(line);
    	    	String[] n = line.split(" ");
    	    	SimNode v1 = new SimNode(n[0]);
    	    	SimNode v2 = null;
    	    	if(n.length == 1)
    	    	{
    	    		flag = 1;
    	    	}
    	    	else
    	    	{
    	    		v2 = new SimNode(n[1]);
    	    		flag = 0;
    	    	}
    	    	if(nodes.add(v1))
    	    	{
    	    		v1.skills.addSkill("eat", (float)100.0);
    	    		yard.setObjectLocation(v1,
    	                    new Double2D(yard.getWidth() * 0.5 + 2*random.nextDouble() - 0.5,yard.getHeight() * 0.5 + 50*random.nextDouble() - 0.5));
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
    	    	if(flag == 0)
    	    	{
    	    		v1.addLabel(n[3]);
    	    	}
    	    	schedule.scheduleRepeating(v1);
    	    	
    	    	if(flag == 0)
    	    	{
	    	    	if(nodes.add(v2))
	    	    	{
	    	    		System.out.println(v2.getNodeName());
	    	    		
	    	    		v2.skills.addSkill("eat", (float)100.0);
	    	    		yard.setObjectLocation(v2,
	    	                    new Double2D(yard.getWidth() * 0.5 + 13*random.nextDouble() - 0.5,yard.getHeight() * 0.5 + 20*random.nextDouble() - 0.5));
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
	    	    	System.out.println(n[3]);
//	    	    	Bag b = buddies.getEdges(v1, v2,null);
//	    	    	for(int i = 0; i < b.size();i++)
//	    	    	{
//	    	    		try
//	    	    		{
//	    	    			Integer.parseInt((String) ((Edge) b.get(i)).getInfo());
//	    	    			((Edge) b.get(i)).setInfo(n[3]);
//	    	    		}
//	    	    		catch(Exception e) 
//	    	    		{
//	    	    			
//	    	    		}
//	    	    		
//	    	    	}
	    	    	SimNetwork.addEdgeLabel(v1, v2, n[3]);
	    	    	
	    	    
	    	    }
    	    }
    	    
    	  
    	    
    	
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