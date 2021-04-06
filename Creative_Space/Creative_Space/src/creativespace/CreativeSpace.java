package creativespace;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CreativeSpace extends Application {
    
    @Override
    public void start(Stage primaryStage) {

        FlowPane root = new FlowPane();
       
        Sidebar sbar = new Sidebar();

        root.getChildren().add(sbar);
        
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
	double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        
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
