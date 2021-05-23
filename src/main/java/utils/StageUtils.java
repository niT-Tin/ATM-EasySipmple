package utils;

import Myenum.PageStatus;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static Myenum.PageStatus.*;

public class StageUtils {
    private static Stage stage;
    static Scene[] scenes = new Scene[4];
    static Stage currentStage;

    public static Stage getCurrentStage() {
        return currentStage;
    }

    public static void setCurrentStage(Stage currentStage) {
        StageUtils.currentStage = currentStage;
    }

    private static Map<PageStatus, Stage> map = new HashMap<>();
    static{
        try {

            scenes[0] = new Scene(FXMLLoader.load(Objects.requireNonNull(StageUtils.class.getResource("/fxml" +
                    "/ExecutePane.fxml"))), 650, 650);
            scenes[1] = new Scene(FXMLLoader.load(Objects.requireNonNull(StageUtils.class.getResource(
                    "/fxml/LoginPane.fxml"
            ))), 650, 650);
            scenes[2] = new Scene(FXMLLoader.load(Objects.requireNonNull(StageUtils.class.getResource("" +
                    "/fxml/RegisterPane.fxml"))), 650, 650);
            scenes[3] = new Scene(FXMLLoader.load(Objects.requireNonNull(StageUtils.class.getResource("" +
                    "/fxml/LoginPane.fxml"))), 650, 650);
            scenes[3].getStylesheets().add("css/main.css");
            scenes[0].getStylesheets().add("css/main.css");
            Stage[] stages = new Stage[4];
            for (int i = 0; i < 4; i++) {
                stages[i] = new Stage();
                stages[i].setScene(scenes[i]);
            }
            map.put(LOGINED, stages[0]);
            map.put(NOTLOGINED, stages[1]);
            map.put(REGISTER, stages[2]);
            map.put(REGISTERED, stages[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        currentStage = stage;
        StageUtils.stage = stage;
        stage.show();
    }
    public static Map<PageStatus, Stage> getMap() {
        return map;
    }
}
