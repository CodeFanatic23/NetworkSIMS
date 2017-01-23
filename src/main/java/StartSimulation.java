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
public class StartSimulation extends Application {

	Stage window;
	Scene scene;
	Button button;
	CheckBox checkBox;
	static VBox layout;
	public static StartSimulation startUpTest = null;
	private static boolean finalblock = true;

	public static void main(String[] args) {
		
		launch(args);
	}
	
	public static void startSimulation()
	{
		launch();
	}

	public void start() {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("NetWorkSIMS Simulator ");

		// Form
		Label label = new Label("Enter No. of Nodes to simulate");

		TextField nodeInput = new TextField();
		nodeInput.setMaxWidth(100);

		button = new Button("Start Simulation");
		button.setOnAction(e -> {
			try {
				isInt(nodeInput, nodeInput.getText());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// Layout
		layout = new VBox(10);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.getChildren().addAll(label, nodeInput, button);

		scene = new Scene(layout, 400, 200);
		window.setScene(scene);
		window.show();

	}

	// Validate age
	@SuppressWarnings("finally")
	private boolean isInt(TextField input, String message) throws InterruptedException {
		try {
			long node = Long.parseLong(input.getText());
			// Plot
			System.out.println("Starting Simulation with: " + node + " nodes");
			// CALL SIMNETWORK
			// ScaleFreeConstructor sim = new ScaleFreeConstructor(node);
			// sim.generateNodes();

			// threadName = Thread.currentThread().getName();
			// Thread t = new Thread()
			// {
			// public void run(){
			// System.out.println("aafff" + Thread.currentThread());
			String[] args = new String[0];
			// SimNetwork simN= new SimNetwork(System.currentTimeMillis());
			SimNetwork.setNodes(node);
			SimNetwork.main(args);
			// //simN.start();
			// }
			// };
			//
			// t.start();
			finalblock = true;
			return true;
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Please Enter a valid number");
			alert.setContentText("Error: " + message + " is not an valid age");

			alert.showAndWait();
			// e.printStackTrace();
			finalblock = false;
			return false;
		} finally {
			// if(finalblock){
			// Alert alert = new Alert(AlertType.INFORMATION);
			// alert.setTitle("Message");
			// alert.setHeaderText("Program setup complete");
			// alert.setContentText("You can now start simulation from
			// SimNetwork");
			// alert.showAndWait();
			// if(alert.getResult().getButtonData().toString().equals("OK_DONE")){
			// SimNetwork.main(new String[0]);
			// }
			// else{
			// System.out.println("Aborting");
			// }

			// window.close();
			// }

			return false;
		}
	}

}
