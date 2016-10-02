import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import sim.util.Bag;

public class SimNetworkTest {
	
	private static SimNode node1;
	private static SimNode node2;
	private static SimNetwork a;
	
	@BeforeClass
	public static void oneTimeSetup(){
		a = Mockito.spy(new SimNetwork(0));
		node1 = new SimNode("node", new SimSkill("skill1",(float)100.0));
		node2 = new SimNode("node", new SimSkill("skill2",(float)100.0));
		node1.setNodeName("node1");
		node2.setNodeName("node2");
		node1.addLabel("l1");
		node2.addLabel("l2");
	}

	@Test
	public void addNodeToNetwork(){
		a.buddies.addNode(node1);
		a.buddies.addNode(node2);
		Bag e = a.buddies.getAllNodes();
		assertEquals(e.size(),2);
		
	}
	@Test
	public void edgeGetsAddedProperly(){
		a.buddies.addEdge(node1, node2, 100);
		Bag b = a.buddies.getEdges(node1, node2, null);
		assertEquals(b.size(),1);
	}
	@Test
	public void removeNodeTest()
	{
		int initSize = a.buddies.getAllNodes().size();
		a.buddies.removeNode(node1);
		assertEquals(a.buddies.getAllNodes().size(),initSize-1);
		
	}
	@Test
	public void inputTest(){
		assertFalse(a.relation.isEmpty());
		assertFalse(a.getData().isEmpty());
	}
	
	@Test
	public void createChildNodeTest(){
		int initSize = a.buddies.getAllNodes().size();
		try{		
		System.out.println(initSize);
		a.createChildNode(node1, node2);
		}
		catch(Exception e){
			assertEquals(a.buddies.getAllNodes().size(),initSize+1);
		}
	}
}
