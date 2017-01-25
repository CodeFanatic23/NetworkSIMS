import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import Manager.SimManager;
import Network.SimNetwork;
import Network.SimNode;
import Network.SimSkill;
import sim.engine.SimState;
import sim.field.network.Edge;
import sim.util.Bag;
@SuppressWarnings("static-access")
public class Tests {

	private static SimNode node1;
	private static SimNode node2;
	private static SimNetwork a;
	private static Tests obj;
	private static SimState state = new SimNetwork(System.currentTimeMillis());

	@BeforeClass
	public static void oneTimeSetup() {
		a = Mockito.spy(new SimNetwork(0));
		obj = new Tests();
		node1 = obj.addNewNodes("node1");
		node2 = obj.addNewNodes("node2");
	}

	@Test
	public void addNodeToNetwork() {
		obj.fullResetState();
		node1 = obj.addNewNodes("node1");
		node2 = obj.addNewNodes("node2");
		a.buddies.addNode(node1);
		a.buddies.addNode(node2);
		a.nodes.add(node1);
		a.nodes.add(node2);
		Bag e = a.buddies.getAllNodes();
		assertEquals(e.size(), 2);

	}

	@Test
	public void edgeGetsAddedProperly() {
		obj.fullResetState();
		node1 = obj.addNewNodes("node1");
		node2 = obj.addNewNodes("node2");
		a.buddies.addEdge(node1, node2, "100");
		try {
			a.createChildNode(node1, node2);
		} catch (Exception e) {

		}
		SimNode child = null;
		Bag nodes = a.buddies.getAllNodes();
		for (int i = 0; i < nodes.size(); i++) {
			SimNode n1 = ((SimNode) nodes.get(i));
			if (!(n1.equals(node1)) && !(n1.equals(node2))) {
				child = n1;
				break;
			}
		}

		Bag b = a.buddies.getEdges(node1, node2, null);
		Bag b2 = a.buddies.getEdges(node1, child, null);
		Bag b3 = a.buddies.getEdges(child, node2, null);

		assertEquals(b.size(), 1);
		assertEquals(b2.size(), 1);
		assertEquals(b3.size(), 1);

	}

	@Test
	public void labelGetsAddedProperly() {
		obj.addNodeToNetwork();
		a.buddies.addEdge(node1, node2, "100");
		a.addEdgeLabel(node1, node2, "LABEL");
		// verify(a).addEdgeLabel(node1, node2, "LABEL");
		Bag out = a.buddies.getEdges(a.returnNode(node1.getNodeName()), null);

		String l = "";
		for (int buddy = 0; buddy < out.size(); buddy++) {
			System.out.println(((Edge) out.get(buddy)).getInfo());
			l = ((((Edge) out.get(buddy)).getInfo())).toString();
			if (buddy == 0) {
				break;
			}

		}
		System.out.println("LABEL:" + l);
		assertEquals(l, "LABEL");
	}

	@Test
	public void removeNodeTest() {
		int initSize = a.buddies.getAllNodes().size();
		a.buddies.removeNode(node1);
		assertEquals(a.buddies.getAllNodes().size(), initSize - 1);

	}

	@Test
	public void createChildNodeTest() {
		Bag e = a.buddies.getAllNodes();
		int initSize = e.size();
		try {

			obj.addNodeToNetwork();
			a.createChildNode(node1, node2);
		} catch (Exception exp) {
			assertEquals(initSize, initSize + 1);
			verify(a).createChildNode(node1, node2);
		}
	}

	@Test
	public void startUpTest() {
		obj.fullResetState();
		a.start();
		assertEquals(a.nodes.size(), a.getGenderCounter());

		verify(a, times(1)).start();
		// Make
	}

	@Test
	public void returnNodeTest() {
		obj.fullResetState();
		obj.addNodeToNetwork();
		Bag e = a.buddies.getAllNodes();
		System.out.println("SIZE:" + e.size());
		assertEquals(node1, a.returnNode(node1.getNodeName()));
		verify(a).returnNode(node1.getNodeName());
	}

	@Test
	public void equallyLikelyGenderTest() {
		int male = 0, female = 0;
		obj.addNodeToNetwork();
		// Add nodes to network
		obj.addNewNodes("node3");
		obj.addNewNodes("node4");
		obj.addNewNodes("node5");

		Bag e = a.buddies.getAllNodes();
		for (int i = 0; i < e.size(); i++) {
			if (((SimNode) e.get(i)).getGender().equals("male")) {
				male++;
			} else {
				female++;
			}
		}
		if (e.size() % 2 == 0) { // Even nodes
			assertEquals(male, female);
		} else { // Odd nodes
			assertEquals(male, female + 1);
		}
		// verify(spy(node1)).setGender(a.EquiLikelyGender());
	}

	@Test
	public void equallyLikelyProfession() {
		// Spawning 10 nodes
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
		HashMap<String, Integer> s = new HashMap<String, Integer>();
		// Test for 5 professions
		int noOfProfessions = 5;
		for (int i = 0; i < e.size(); i++) {
			((SimNode) e.get(i)).setProfession(a.EquiLikelyProfession());
			s.put(((SimNode) e.get(i)).getProfession(), obj.increaseVal(e, s, i));
		}

		obj.printMap(s);
		double d = (double) e.size() / noOfProfessions;
		for (int i = 0; i < noOfProfessions; i++) {
			if (s.get(((SimNode) e.get(i)).getProfession()).doubleValue() != Math.ceil(d)) {
				assertEquals(s.get(((SimNode) e.get(i)).getProfession()).doubleValue(), Math.floor(d), 0);
			} else {
				assertEquals(s.get(((SimNode) e.get(i)).getProfession()).doubleValue(), Math.ceil(d), 0);
			}
		}
	}

	@Test
	public void takeInputTest() {
		obj.fullResetState();
		SimNetwork.setNodes(10);
		try {
			SimNetwork.takeInputFromFile();
			SimNetwork.takeInput(a.schedule);
			assertEquals(a.nodes.size(), 10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(a.data);

	}

	
	@Test
	public void stepTest() {
		obj.fullResetState();
		node1 = obj.addNewNodes("node1");
		node2 = obj.addNewNodes("node2");
		SimNode node3 = obj.addNewNodes("node3");
		SimNode node4 = obj.addNewNodes("node4");
		SimNetwork.buddies.addNode(node1);
		SimNetwork.nodes.add(node2);
		SimNetwork.buddies.addNode(node2);
		SimNetwork.nodes.add(node1);
		SimNetwork.stopper.put(node1, a.schedule.scheduleRepeating(node1));
		SimNetwork.stopper.put(node2, a.schedule.scheduleRepeating(node2));
		SimNetwork.buddies.addNode(node3);
		SimNetwork.nodes.add(node3);
		SimNetwork.buddies.addNode(node4);
		SimNetwork.nodes.add(node4);
		SimNetwork.stopper.put(node3, a.schedule.scheduleRepeating(node3));
		SimNetwork.stopper.put(node4, a.schedule.scheduleRepeating(node4));
		int a1,b,c,d;
		a1 = node1.getDeathAge();
		b = node2.getDeathAge();
		c = node3.getDeathAge();
		d = node4.getDeathAge();
		int min= 0;
		if(a1 < b && a1 < c && a1 < d){
			min = a1;
		}
		else if(b < c && b < a1 && b < d){
			min = b;
		}
		else if(c < a1 && c < b && c < d){
			min = c;
		}
		else{
			min = d;
		}
//		for(int i = 0;i < min;i++){
//		node1.step(state);
//		node2.step(state);
//		node3.step(state);
//		node4.step(state);
//		}
		System.out.println("dwew"+SimNetwork.nodes.size());
		assertTrue(SimNetwork.nodes.size() >= 4);
		
		
	}
	
	@Test
	public void marryTest(){
		obj.fullResetState();		
		node1 = obj.addNewNodes("N1");
		node2 = obj.addNewNodes("N2");
		node1.sharmaVariable = 10;
		node2.sharmaVariable = 10;
		Random r = new Random();
		
		node1.skills.changeSkillVal("knowledge", r.nextFloat()*10000);
		node2.skills.changeSkillVal("knowledge", r.nextFloat()*10000);
		node1.findSimilarAndMarry();
		float val1 = node1.skills.getSkillVal("knowledge");
		float val2 = node2.skills.getSkillVal("knowledge");
		System.out.println("VAL1:"+val1 + "---VAL2:" + val2);
		System.out.println((a.buddies.getEdges(node1, node2, null).size()));
		if(Math.abs(val2 - val1) < 1000.0) {
			assertEquals(a.buddies.getEdges(node1, node2, null).size(),1);
		}
		else{
			assertEquals(a.buddies.getEdges(node1, node2, null).size(),0);
		}
		}
	

	@Test
	public void setspouseTest() {
		obj.fullResetState();
		obj.addNodeToNetwork();
		ArrayList<SimNode> l = new ArrayList<SimNode>(a.nodes);
		for (SimNode node : l) {
			try {
				node.setSpouseObject(l.get(l.indexOf(node) + 1));
			} catch (Exception e) {
				e.getMessage();
			}
		}
		assertEquals(node2, node1.getSpouseObject());

	}

	@Test
	public void deleteFileTest() {
		// Create files
		SimManager.writeIntoFile("Test", "src/test/resources/", "test", false);
		SimManager.writeIntoFile("Test", "src/test/resources/", "test2", false);
		try {
			long count = Files.list(Paths.get("src/test/resources/")).count();
			List<File> filesInFolder = Files.walk(Paths.get("src/test/resources/")).filter(Files::isRegularFile)
					.map(Path::toFile).collect(Collectors.toList());
			for (File f : filesInFolder) {
				SimManager.deleteFile(f);
			}
			assertEquals(0, count - 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void setHealthTest(){
//		obj.fullResetState();
//		node1 = obj.addNewNodes("N1");
//		node1.sharmaVariable = 30;
//		//node1.setHealth();
//		float val1 = node1.skills.getSkillVal("health");
//		node1.sharmaVariable = 50;
//		//node1.setHealth();
//	
//		float val2 = node1.skills.getSkillVal("health");
//		System.out.println("VAL1:"+val1 + "---VAL2:" + val2);
//		assertTrue(val1 > val2);
//	}

	public int increaseVal(Bag e, HashMap<String, Integer> s, int i) {
		if (s.get(((SimNode) e.get(i)).getProfession()) != null) {
			return s.get(((SimNode) e.get(i)).getProfession()).intValue() + 1;
		} else {
			return 1;
		}
	}

	public void printMap(HashMap<String, Integer> m) {
		for (Map.Entry<String, Integer> d : m.entrySet()) {
			System.out.println(d);
		}
	}

	public SimNode addNewNodes(String nodeName) {
		SimNode node = new SimNode(nodeName, new SimSkill("skill3", (float) 100.0));
		a.buddies.addNode(node);
		a.nodes.add(node);
		node.addLabel("l" + nodeName.charAt(nodeName.length() - 1));
		node.setGender(a.EquiLikelyGender());
		node.sharmaVariable = 0;
		a.schedule.scheduleRepeating(node);
		return node;

	}
	

	public void fullResetState() {
		node1 = null;
		node2 = null;
		a.nodes.clear();
		a.buddies.clear();
		a.setGenderCounter(0);
	}
}