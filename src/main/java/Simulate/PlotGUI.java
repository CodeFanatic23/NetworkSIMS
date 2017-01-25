package Simulate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import Manager.SimManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class PlotGUI extends Application {

	Stage window;
	Scene scene;
	Button button;
	CheckBox checkBox;
	static String totalTime = "";
	private SimManager simMan = SimManager.createObj();

	public PlotGUI() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void start() {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		String basePath = new File("").getAbsolutePath();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(SimManager.combine(basePath,
					"src" + File.separator + "main" + File.separator + "resources" + File.separator + "data.sps")));
			String line;
			while ((line = br.readLine()) != null) {
				// process the line.
				System.out.println(line);
				PlotGUI.totalTime = line;

			}
		} finally {
			br.close();
		}
		window.setTitle("Degree distribution PLOT-Max(" + PlotGUI.totalTime + ")");

		// Form
		Label label = new Label("Enter the time");

		TextField ageInput = new TextField();

		checkBox = new CheckBox("Save PNG");
		checkBox.setSelected(false);

		button = new Button("Plot the graph");
		button.setOnAction(e -> isInt(ageInput, ageInput.getText(), checkBox.isSelected()));

		// Layout
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.getChildren().addAll(label, ageInput, button, checkBox);

		scene = new Scene(layout, 570, 300);
		window.setScene(scene);
		window.show();
	}

	// Validate age
	private boolean isInt(TextField input, String message, boolean save) {
		try {
			long age = Long.parseLong(input.getText());
			// Plot
			System.out.println("Node's age is: " + age);
			simMan.createDegreeDistribution(age, save);
			if (save) {
				// save PNG
				System.out.println("saving....");
			}
			return true;
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Please Enter a valid age");
			alert.setContentText("Error: " + message + " is not an valid age");

			alert.showAndWait();
			return false;
		}
	}

}
