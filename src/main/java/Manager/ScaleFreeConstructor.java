package Manager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.SimpleGraph;

public class ScaleFreeConstructor {
	private long size;

	public ScaleFreeConstructor(long size) {
		// TODO Auto-generated constructor stub
		this.size = size;
	}

	public ScaleFreeConstructor() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> generateNodes() {
		ArrayList<String> ar = new ArrayList<String>();
		UndirectedGraph<String, DefaultEdge> g = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

		VertexFactory<String> vertexFactory = new VertexFactory<String>() {
			private int i = 1;

			@Override
			public String createVertex() {
				return Integer.toString(++i);
			}
		};
		Map<String, DefaultEdge> resultMap = new HashMap<String, DefaultEdge>();
		ScaleFreeGraphGenerator s = new ScaleFreeGraphGenerator((int) size);
		s.generateGraph(g, vertexFactory, resultMap);

		Set<DefaultEdge> s1 = g.edgeSet();
		SimManager.writeIntoFile("", "src/main/resources/", "input", false);
		for (DefaultEdge e : s1) {
//			System.out.println(g.getEdgeSource(e) + " " + g.getEdgeTarget(e) + ' ' + 100 + ' ' + 'f');
			String content = g.getEdgeSource(e) + " " + g.getEdgeTarget(e) + ' ' + 100 + ' ' + 'f' + '\n';
			ar.add(content);
//			ScaleFreeConstructor.writeIntoFile(content, "src/main/resources/", "input", true);
		}
		return ar;

	}

	public static void main(String[] Args) {
		ScaleFreeConstructor c = new ScaleFreeConstructor();
		c.size = 5;
		c.generateNodes();
	}

}
