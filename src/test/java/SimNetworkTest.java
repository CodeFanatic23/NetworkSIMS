import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import sim.engine.Schedule;
import sim.engine.SimState;
import sim.field.network.Edge;
import sim.util.Bag;

public class SimNetworkTest {
	
	private static SimNode node1;
	private static SimNode node2;
	private static SimNetwork a;
	private static SimNetworkTest obj;
	private static SimState state = new SimNetwork(System.currentTimeMillis());
	
	@BeforeClass
	public static void oneTimeSetup(){
		a = Mockito.spy(new SimNetwork(0));
		obj = new SimNetworkTest();
		node1 = obj.addNewNodes("node1");
		node2 = obj.addNewNodes("node2");
	}
	
	
	@Test
	public void addNodeToNetwork(){
		obj.fullResetState();
		a.buddies.addNode(node1);
		a.buddies.addNode(node2);
		a.nodes.add(node1);
		a.nodes.add(node2);
		Bag e = a.buddies.getAllNodes();
		assertEquals(e.size(),2);
		
	}
	@Test
	public void edgeGetsAddedProperly(){
		a.buddies.addEdge(node1, node2, "100");
		Bag b = a.buddies.getEdges(node1, node2, null);
		assertEquals(b.size(),1);
	}
	@Test
	public void labelGetsAddedProperly(){
		obj.addNodeToNetwork();
		obj.edgeGetsAddedProperly();
		a.addEdgeLabel(node1, node2, "LABEL");
		//verify(a).addEdgeLabel(node1, node2, "LABEL");
		Bag out = a.buddies.getEdges(a.returnNode(node1.getNodeName()), null);
		
    	String l = "";
    	for(int buddy = 0; buddy < out.size();buddy++)
    	{
    		System.out.println(((Edge)out.get(buddy)).getInfo());
    		l = ((((Edge) out.get(buddy)).getInfo())).toString();
    		if(buddy == 0){
    			break;
    		}
    		
    	}
    	System.out.println("LABEL:"+l);
		assertEquals(l,"LABEL");
	}

	@Test
	public void removeNodeTest()
	{
		int initSize = a.buddies.getAllNodes().size();
		a.buddies.removeNode(node1);
		assertEquals(a.buddies.getAllNodes().size(),initSize-1);
		
	}
	
	
	@Test
	public void createChildNodeTest(){
		Bag e = a.buddies.getAllNodes();
		int initSize = e.size();
		//SimNode n[] = new SimNode[2];
		try{
			
//			for(int i = 0;i < initSize;i++){
//				n[i] = (SimNode) e.get(i);
//				if(i == 1)
//				{
//					break;
//				}
//			}
//			a.createChildNode(n[0], n[1]);
		obj.addNodeToNetwork();
		a.createChildNode(node1,node2);
		}
		catch(Exception exp){
			assertEquals(initSize,initSize+1);
			verify(a).createChildNode(node1, node2);
		}
	}
	@Test
	public void startUpTest(){
		obj.fullResetState();
		a.start();
		assertEquals(a.nodes.size(),a.getGenderCounter());
		
		verify(a,times(1)).start();
		//Make
	}
	@Test
	public void returnNodeTest(){
		obj.fullResetState();
		obj.addNodeToNetwork();
		Bag e = a.buddies.getAllNodes();
		System.out.println("SIZE:" + e.size());
		assertEquals(node1,a.returnNode(node1.getNodeName()));
		verify(a).returnNode(node1.getNodeName());
	}
	@Test
	public void equallyLikelyGenderTest(){
		int male = 0,female=0;
		obj.addNodeToNetwork();
		//Add nodes to network
		SimNode node3 = obj.addNewNodes("node3");
		SimNode node4 = obj.addNewNodes("node4");
		SimNode node5 = obj.addNewNodes("node5");
	
		Bag e = a.buddies.getAllNodes();
		for(int i = 0;i<e.size();i++){
			if(((SimNode)e.get(i)).getGender().equals("male")){
				male++;
			}
			else{
				female++;
			}
			}
		if(e.size()%2 == 0){		//Even nodes
			assertEquals(male,female);
		}
		else{						//Odd nodes
			assertEquals(male,female+1);
		}
		//verify(spy(node1)).setGender(a.EquiLikelyGender());
		}
	@Test
	public void equallyLikelyProfession(){
		//Spawning 10 nodes
		obj.addNodeToNetwork();
		SimNode node3 = obj.addNewNodes("node3");
		SimNode node4 = obj.addNewNodes("node4");
		SimNode node5 = obj.addNewNodes("node5");
		SimNode node6 = obj.addNewNodes("node6");
		SimNode node7 = obj.addNewNodes("node7");
		SimNode node8 = obj.addNewNodes("node8");
		SimNode node9 = obj.addNewNodes("node9");
		SimNode node10 = obj.addNewNodes("node10");
		Bag e = a.buddies.getAllNodes();
		HashMap<String,Integer> s = new HashMap<String,Integer>();
		//Test for 5 professions
		int noOfProfessions = 5;
		for(int i = 0;i < e.size();i++){
			((SimNode)e.get(i)).setProfession(a.EquiLikelyProfession());
			s.put(((SimNode)e.get(i)).getProfession(),obj.increaseVal(e,s,i));
		}
		
		obj.printMap(s);
		double d = (double)e.size()/noOfProfessions;
		for(int i=0;i<noOfProfessions;i++){
			if(s.get(((SimNode)e.get(i)).getProfession()).doubleValue() != Math.ceil(d)){
		assertEquals(s.get(((SimNode)e.get(i)).getProfession()).doubleValue(),Math.floor(d),0);
		}
			else{
				assertEquals(s.get(((SimNode)e.get(i)).getProfession()).doubleValue(),Math.ceil(d),0);
			}
		}	
	}
	
	@Test
	public void stepTest(){
		obj.fullResetState();
		obj.addNodeToNetwork();
		int initSize = a.nodes.size();
		ArrayList<SimNode> l = new ArrayList<SimNode>(a.nodes);
		boolean flag = true;
		while(flag){
		for(SimNode node:l){			
			a.schedule.scheduleRepeating(node);
			System.out.println(node.getNodeName()+"--Age:"+node.sharmaVariable);
			if(node.sharmaVariable == 27){
//				System.out.println(a.nodes.size()+1+"--------"+a.nodes.size());
				try{
					a.createChildNode(a.returnNode(node.getNodeName()),l.get(l.indexOf(a.returnNode(node.getNodeName()))+1));//Workaround for now
					
				}
				catch(Exception e){
					assertEquals(initSize+1,a.nodes.size());
					
				}
				
				
			}
			node.sharmaVariable++;
			if(node.sharmaVariable > 75){
				flag = false;
				break;
				
			}
		}
		}
		
//		Bag e = a.buddies.getAllNodes();
//		for(int i = 0;i < e.size();i++){
//			((SimNode)e.get(i)).step(state);
//		}
//		boolean flag = true;
//		while(flag){
//			for(SimNode node:l){
//				a.schedule.step(state);
//				if(node.sharmaVariable > 75){
//					flag = false;
//					break;
//				}
//				node.sharmaVariable++;
//			}
//		}	
	}
	@Test
	public void setspouseTest(){
		obj.fullResetState();
		obj.addNodeToNetwork();
		ArrayList<SimNode> l = new ArrayList<SimNode>(a.nodes);
		for(SimNode node:l){
			try{
			node.setSpouseObject(l.get(l.indexOf(node)+1));
			}
			catch(Exception e){
				e.getMessage();
			}
		}
		assertEquals(node2,node1.getSpouseObject());
		
	}
//	@Test
//	public void set(){
//		obj.fullResetState();
//		obj.addNodeToNetwork();
//		ArrayList<SimNode> l = new ArrayList<SimNode>(a.nodes);
//		for(SimNode node:l){
//			try{
//			node.se
//			}
//			catch(Exception e){
//				e.getMessage();
//			}
//		}
//		assertEquals(node2,node1.getSpouseObject());
//		
//	}
	public int increaseVal(Bag e,HashMap<String,Integer> s,int i){
		if(s.get(((SimNode)e.get(i)).getProfession()) != null){
			return s.get(((SimNode)e.get(i)).getProfession()).intValue()+1;
		}
		else{
			return 1;
		}
	}
	public void printMap(HashMap<String,Integer> m){
		for(Map.Entry<String, Integer> d:m.entrySet()){
			System.out.println(d);
		}
	}
	public SimNode addNewNodes(String nodeName){
		SimNode node = new SimNode(nodeName, new SimSkill("skill3",(float)100.0));
		a.buddies.addNode(node);
		a.nodes.add(node);
		node.addLabel("l"+nodeName.charAt(nodeName.length()-1));
		node.setGender(a.EquiLikelyGender());
		a.schedule.scheduleRepeating(node);
		return node;
		
	}

	public void fullResetState(){
		a.nodes.clear();
		a.buddies.clear();
		a.setGenderCounter(0);
	}
}
