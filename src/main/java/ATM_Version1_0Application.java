import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.StageUtils;

import java.util.Objects;
public class ATM_Version1_0Application extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StageUtils.setStage(stage);
        StageUtils.setCurrentStage(stage);
        Parent root = FXMLLoader.load(Objects.requireNonNull(StageUtils.class.getResource("" +
                "/fxml/RegisterPane.fxml")));
        stage.setTitle("ATM");
        stage.setScene(new Scene(root, 650, 650));
        stage.show();

    }
}
