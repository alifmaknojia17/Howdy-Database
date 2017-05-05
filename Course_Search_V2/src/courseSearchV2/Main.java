package courseSearchV2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;
    //public static Scene introScene;
    public static Scene primaryScene;
    public static Scene secondaryScene;

    @Override
    public void start(Stage Stage) throws Exception{
        //new Controller().openWriter();
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        Stage.setTitle("TAMU COURSE SEARCH");
        primaryScene = new Scene(root, 1000, 600);
        //Stage.setScene(new Scene(root, 1000, 600));
        Stage.setScene(primaryScene);
        primaryStage = Stage;
        primaryStage.show();

        Parent root2 = FXMLLoader.load(getClass().getResource("SecondaryScene.fxml"));
        Stage.setTitle("TAMU COURSE SEARCH");
        secondaryScene = new Scene(root2, 1000, 600);
        //introScene = new Scene(root,1000,600);
        //Stage.setScene(new Scene(root, 1000, 600));

    }

//    public void Close(){
//        primaryStage.close();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
