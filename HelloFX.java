import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI
        Button btn = new Button("Say Hello");
        btn.setOnAction(e -> System.out.println("Hello, Yuvaraj!"));

        StackPane root = new StackPane(btn);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("JavaFX App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // launches the FX app
    }
}
