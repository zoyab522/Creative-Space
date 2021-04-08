package creativespace;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CreativeSpace extends Application {

@Override
	public void start(Stage primaryStage) {

		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

		FlowPane root = new FlowPane();
		Sidebar sbar = new Sidebar(screenWidth, screenHeight);
		root.getChildren().add(sbar);

		Scene scene = new Scene(root, screenWidth, screenHeight);

		primaryStage.setTitle("Creative Space");
		primaryStage.setScene(scene);
		primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
}
