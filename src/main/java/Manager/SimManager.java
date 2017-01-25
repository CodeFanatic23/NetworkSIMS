package Manager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

public class SimManager {
	
	private SimManager(){
		
	}
	static SimManager s = new SimManager();
	public static SimManager createObj(){
		return s;
	}
	
	public void createFile(String name) {
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name + ".txt"), "utf-8"));

		} catch (IOException ex) {
			// report
			System.out.println(name + " file could not be create");
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				/* ignore */}
		}
	}
	
	
	public void deleteLogs() {
		// path - /networkSIMS/src/main/resources/logs
		String logPath = new File("").getAbsolutePath() + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + "logs";
		File folder = new File(logPath);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				File file = new File(combine(logPath, listOfFiles[i].getName()));
				deleteFile(file);
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("not a file");
			}
		}
	}
	
	
	 public void createDegreeDistribution(long age,boolean save)
	    {
	    	BufferedReader br = null;
	    	String basePath = new File("").getAbsolutePath();
	        ArrayList<String> degree = new ArrayList<String>();

	    	try  {
	    		
	    		br = new BufferedReader(new FileReader(combine(basePath,"src" + File.separator + "main" + File.separator + "resources" + File.separator + "logs" + File.separator + Long.toString( age) + ".sps")));
	    	    String line;
	    	    while ((line = br.readLine()) != null) {
	    	       // process the line.
	    	    	
	    	    	degree.add(line);
	    	    	
	    	    }
	    	    
	    	} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
	    		System.out.println("File not found");
	    		return;
	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	finally
	    	{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	ArrayList<Double> x = new ArrayList<Double>();
	    	for (int i = 0; i < degree.size(); i++) 
	    	{
	    		for (String j : degree.get(i).split(" ")) 
	    		{
	    			x.add(Double.parseDouble(j));
				}
			}
	    	 double[] x1 = new double[x.size()];
	         for (int i=0; i < x1.length; i++)
	         {
	             x1[i] = x.get(i).intValue();
	         }
	        
		     int number = 100;
		     HistogramDataset dataset = new HistogramDataset();
		     dataset.setType(HistogramType.FREQUENCY);
		     dataset.addSeries("Histogram",x1,number);
		     String plotTitle = "Histogram"; 
		     String xaxis = "degree";
		     String yaxis = "frequency"; 
		     PlotOrientation orientation = PlotOrientation.VERTICAL; 
		     boolean show = false; 
		     boolean toolTips = false;
		     boolean urls = false; 
		     JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
		                dataset, orientation, show, toolTips, urls);
		    
		     int width = 500;
		     int height = 300; 
		     ChartFrame frame = new ChartFrame("Degree distribution", chart);
		     frame.pack();
		     frame.setVisible(true);
		     if(save){
			     try {
			    	 String path = "src"+File.separator+"main"+File.separator+"resources"+File.separator+"degreeDistrubutionGraph"+File.separator;
			         ChartUtilities.saveChartAsPNG(new File(path+age + ".PNG"), chart, width, height);
			         } catch (IOException e) {
			        	 System.out.println("ERROR: Could not save the Histogram");
			         }
		     }
	    }
	 
		public static String combine(String path1, String path2) {
			File file1 = new File(path1);
			File file2 = new File(file1, path2);
			return file2.getPath();
		}
		
		public static void deleteFile(File file) {
			// File file = new File("c:\\logfile20100131.log");
			try {
				if (file.delete()) {
					System.out.println(file.getName() + " is deleted!");
				} else {
					System.out.println("Delete operation is failed.");
				}

			}

			catch (Exception e) {

				e.printStackTrace();
			}

		}
		
	public static void writeIntoFile(String content, String pathFromBase, String Filename, boolean appendOrNot) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		try {
			// path - /networkSIMS/src/main/resources/logs

			String basePath = new File("").getAbsolutePath();
//			System.out.println(pathFromBase);
//			System.out.println(basePath);
//			System.out.println(Filename);
			System.out.println(combine(basePath, pathFromBase + Filename + ".sps"));
			File file = new File(combine(basePath, pathFromBase + Filename + ".sps"));

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


}
