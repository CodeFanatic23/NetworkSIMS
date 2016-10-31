import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	public static void writeIntoFile(String content, String pathFromBase, String Filename, boolean appendOrNot) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		try {
			// path - /networkSIMS/src/main/resources/logs

			String basePath = new File("").getAbsolutePath();

			File file = new File(SimNetwork.combine(basePath, pathFromBase + Filename + ".txt"));

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile(), appendOrNot);
			bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);
			out.print(content);
			// out.write(content);
			// out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				// exception handling left as an exercise for the reader
			}
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				// exception handling left as an exercise for the reader
			}
		}
	}

	public void generateNodes() {
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
		ScaleFreeConstructor.writeIntoFile("", "src/main/resources/", "input", false);
		for (DefaultEdge e : s1) {
//			System.out.println(g.getEdgeSource(e) + " " + g.getEdgeTarget(e) + ' ' + 100 + ' ' + 'f');
			String content = g.getEdgeSource(e) + " " + g.getEdgeTarget(e) + ' ' + 100 + ' ' + 'f' + '\n';
			ScaleFreeConstructor.writeIntoFile(content, "src/main/resources/", "input", true);
		}

	}

	public static void main(String[] Args) {
		ScaleFreeConstructor c = new ScaleFreeConstructor();
		c.size = 5;
		c.generateNodes();
	}

}
