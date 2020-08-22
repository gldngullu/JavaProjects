package button;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class buttonMoving extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Text text = new Text(50, 50, "I should be label");
        Button button = new Button();
        pane.getChildren().add(text);
        button.setText("Button");
        button.setLayoutX(80);
        button.setLayoutY(80);
        pane.getChildren().add(button);

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Final question");
        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DOWN: button.setLayoutY((text.getY() + 10)); break;
                case UP:  button.setLayoutY((text.getY() - 10)); break;
                case LEFT: button.setLayoutX((text.getX() - 10)); break;
                case RIGHT: button.setLayoutX((text.getX() + 10)); break;
            }
            text.setText(e.getCode() + " key is pressed");
        });

        button.setOnMouseClicked(event -> text.setText("Mouse movement"));
        text.requestFocus();
    }
}
