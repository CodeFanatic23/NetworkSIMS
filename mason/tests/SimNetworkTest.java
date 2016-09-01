package tests;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import main.SimNode;
import main.SimSkill;
import sim.field.network.Network;

public class SimNetworkTest {
	
	private SimNode node1;
	private SimNode node2;
	private Network network;
	public SimNetworkTest(){
		network = Mockito.spy(new Network());
	}
	@Before
	public void setUp(){
		node1 = new SimNode("node", new SimSkill("skill1",(float)100.0));
		node2 = new SimNode("node", new SimSkill("skill2",(float)100.0));
	}

	@Test
	public void addNodeToNetwork(){
		network.addNode(node1);
		network.addNode(node2);
		verify(network).addNode(node1);
		verify(network).addNode(node2);
	}
	@Test
	public void edgeGetsAddedProperly(){
		network.addEdge(node1,node2,50);
		doNothing().when(network).addEdge(node1,node2,50);
	}

}
