import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

@SuppressWarnings("restriction")
public class PlotGUI extends Application {

    Stage window;
    Scene scene;
    Button button;
    CheckBox checkBox;
    static String totalTime = "";
    public PlotGUI() {
		// TODO Auto-generated constructor stub
	}
    
    

    public static void main(String[] args) {
        launch(args);
    }
    public void start()
    {
    	launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Degree distribution PLOT-Max(" + PlotGUI.totalTime+")");

        //Form
    	Label label = new Label("Enter the time");

        TextField ageInput = new TextField();
        
        checkBox = new CheckBox("Save PNG");
        checkBox.setSelected(false);

        button = new Button("Plot the graph");
        button.setOnAction( e -> isInt(ageInput, ageInput.getText(),checkBox.isSelected()));       

        //Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(label,ageInput, button,checkBox);

        scene = new Scene(layout, 570, 300);
        window.setScene(scene);
        window.show();
    }
    private void createDegreeDistribution(long age,boolean save)
    {
    	BufferedReader br = null;
    	String basePath = new File("").getAbsolutePath();
        ArrayList<String> degree = new ArrayList<String>();

    	try  {
    		
    		br = new BufferedReader(new FileReader(SimNetwork.combine(basePath,"src" + File.separator + "main" + File.separator + "resources" + File.separator + "logs" + File.separator + Long.toString( age) + ".txt")));
    	    String line;
    	    while ((line = br.readLine()) != null) {
    	       // process the line.
    	    	
    	    	degree.add(line);
    	    	
    	    }
    	    
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
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
    //Validate age
    private boolean isInt(TextField input, String message,boolean save){
        try{
            long age = Long.parseLong(input.getText());
            //Plot
            System.out.println("Node's age is: " + age);
            createDegreeDistribution(age,save);
            if(save){
            	//save PNG
            	System.out.println("saving....");
            }
            return true;
        }catch(Exception e){
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Please Enter a valid age");
        	alert.setContentText("Error: " + message + " is not an valid age");

        	alert.showAndWait();
            return false;
        }
    }


}
