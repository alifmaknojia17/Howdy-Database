package courseSearchV2;
/**
 * Created by Gus on 6/6/2016.
 * code modified from thenewboston
 */

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AboutBox {

    public static void display(String title){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);                //block other window inpts until this is closed
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(300);

        Label label = new Label();
        label.setText("team9");
        Text line3 = new Text("team9");
        Text about = new Text("team9 is a group of students enrolled in  ");
        Text line2 = new Text("CSCE 315-100 at Texas A&M University.");
        Text line4 = new Text("Members include");
        Text line5 = new Text("Gustavo Lopez, Di Lu,");
        Text line6 = new Text("and Alif Karim Maknojia.");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(line3, about, line2,line4, line5, line6);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();                                              //block user interaction till close
    }

    private void closeStage(){

    }

}
