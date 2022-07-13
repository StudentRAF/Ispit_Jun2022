package rs.raf.student.jun_2022.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import rs.raf.student.jun_2022.view.form.Form;

public class Application extends javafx.application.Application {

    private static Stage window;

    public static void run(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        window.setScene(Form.main());

        window.show();
    }

    public static void setForm(Scene form) {
        window.setScene(form);
    }

}
