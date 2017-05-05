package courseSearchV2;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

public class Controller {


    public TextField search1_textfield;

    @FXML
    public ComboBox intro1_combo, intro2_combo;
    public CheckBox isLike1_checkbox;
    @FXML
    public Button search1_butt, qsearch_butt;
    @FXML
    public TableView intro1_table, intro2_table;
    public Character operation;
    @FXML
    public GridPane gridpane;
    @FXML
    public VBox intro1_vbox, intro2_vbox;
    @FXML
    public String intro1_tableval, intro2_tableval;
    @FXML
    public BorderPane main_borderpane;
    @FXML
    public TextArea bottom_textfield;
    private String currentQuery;
    private boolean isLevelUsed = false;

    @FXML
    public void about(){
        new AboutBox().display("About us");
    }

    @FXML
    public void setlabel(String labelString){
        Label newlabel = new Label(labelString);
        main_borderpane.setBottom(newlabel);
    }
    @FXML
    public void time_search() throws SQLException, IOException{
        String newQuery = "SELECT * FROM classes";
        SQLservices.setSQL_currentQuery(newQuery);
        Parent root = FXMLLoader.load(getClass().getResource("SecondaryScene.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane p = fxmlLoader.load(getClass().getResource("SecondaryScene.fxml").openStream());
        Controller2 controller2 = fxmlLoader.getController();
        controller2.makecombos();
        SQLservices.setSQL_currentQuery(newQuery);
        Scene newscene = new Scene(p, 1000, 600);
        newscene.getStylesheets().addAll(Controller2.class.getResource("MainStyle.css").toExternalForm());
        Main.primaryStage.setScene(newscene);
        Main.primaryStage.show();
    }

    void setCurrentQuery(String arg){
        currentQuery = arg;
        System.out.println("Current Query: "+ currentQuery);
    }

    void gotoScene2()throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("SecondaryScene.fxml"));
        Main.primaryStage.setTitle("TAMU COURSE SEARCH");
        Main.primaryStage.setScene(new Scene(root, 1000, 600));
        Main.primaryStage.show();
    }

    private String checkCrse(String term){
        System.out.println(term);
        String SQLquery = new String();
        if(term.contains("00 crse")){
            String level = term.replace("00 crse", "");
//            level = level.replace("00","");
            System.out.println(level);
            term = level+"%";
            SQLquery = ("SELECT DISTINCT crse FROM classes WHERE crse LIKE '"+level+"%' ORDER BY crse");
            isLevelUsed = true;
        }
        else{
            SQLquery = ("SELECT DISTINCT "+ term + " FROM classes ORDER BY " + term);
        }
        return SQLquery;
    }

    public void intro1_setTable()throws IOException, SQLException{
        String selected = intro1_combo.getValue().toString();
        String term = new SQLservices().getQueryTerm(selected);
        String SQLquery = new String();
        term = checkop(term);
        SQLquery = checkCrse(term);
        System.out.println(SQLquery);
        List<List<String>> data = new SQLservices().getData(SQLquery);
        ObservableList datalist = new makeTable().makeList(data);
        intro1_table = new makeTable().makeTable(data);
        intro1_table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = intro1_table.getSelectionModel().getSelectedCells();
        selectedCells.addListener(new ListChangeListener() {//[3]
            @Override
            public void onChanged(Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                intro1_tableval = val.toString();
            }
        });
        intro1_vbox.getChildren().clear();
        intro1_vbox.getChildren().addAll(intro1_table);
    }

    public void intro2_setTable()throws IOException, SQLException{
        if(intro1_tableval == null){
            System.out.println("Please choose from the first ComboBox to start.");
            return;
        }
        String selected = intro2_combo.getValue().toString();
        selected = new SQLservices().getQueryTerm(selected);
        selected = checkop(selected);
        String selected2 = intro1_combo.getValue().toString();
        selected2 = new SQLservices().getQueryTerm(selected2);
        selected2 = checkop(selected2);
        String SQLquery;
        String term = intro1_tableval;//checkop(intro1_tableval);

        //term = checkCrseterm(term);
        if(isLevelUsed){
           selected2 = "crse";
        }
        SQLquery = ("SELECT DISTINCT "+ selected + " FROM classes WHERE " +selected2+ operation+" '"+term+"'");
        List<List<String>> data = new SQLservices().getData(SQLquery);
        ObservableList datalist = new makeTable().makeList(data);
        intro2_table = new makeTable().makeTable(data);
        intro2_table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = intro2_table.getSelectionModel().getSelectedCells();
        selectedCells.addListener(new ListChangeListener() {//[3]
            @Override
            public void onChanged(Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                intro2_tableval = val.toString();
            }
        });
        intro2_vbox.getChildren().clear();
        intro2_vbox.getChildren().addAll(intro2_table);
    }

    public void closeFunction() {
        Main.primaryStage.close();
    }

    public void search1_buttAction() throws IOException, SQLException, InterruptedException{
        setlabel("Loading...");
        String sqlString = new searchAlgorithm().mainAlgorithm(search1_textfield.getText());
        System.out.println(sqlString);
        String newQuery = "SELECT * FROM classes WHERE " + sqlString ;
        setlabel("Done. Displaying...");
        updateScene(newQuery);
    }
    @FXML
    public void qsearch_buttAction() throws IOException, SQLException{ //clean this up
        String selected, selected2, term, term2;
        if (intro1_tableval != null && intro2_tableval != null){
            selected = intro1_combo.getValue().toString();
            selected = new SQLservices().getQueryTerm(selected);
            selected = checkop(selected);
            term = intro1_tableval;
            Character op1 = operation;
            //----------
            selected2 = intro2_combo.getValue().toString();
            selected2 = new SQLservices().getQueryTerm(selected2);
            selected2 = checkop(selected2);
            term2 = intro2_tableval;
            String SQLquery;
            SQLquery = ("SELECT * FROM classes WHERE " +selected+ " "+op1+" '"+term+"' AND "+selected2+" "+operation+" '"+term2+"'");
            updateScene(SQLquery);
        }
        else if (intro1_tableval != null && intro2_tableval == null){
            selected = intro1_combo.getValue().toString();
            selected = new SQLservices().getQueryTerm(selected);
            selected = checkop(selected);
            term = intro1_tableval;
            //term = checkop(intro1_tableval);

            String SQLquery = ("SELECT * FROM classes WHERE " +selected+ " "+operation+" '"+term+"'");
            updateScene(SQLquery);
        }
        else{
            System.out.println("Please select an item from the combobox and table to search.");
        }
    }

    public String checkop(String term){
        String termx;
        if(term.contains(">")){
            termx = term.replace(" >", "");
            operation = '>';
        }
        else if(term.contains("<")){
            termx = term.replace(" <", "");
            operation = '<';
        }
        else if(term.contains("=") ){
            termx = term.replace(" =", "");
            operation = '=';
        }
        else{
            termx = term;
            operation = '=';
        }
        return termx;
    }

    @FXML
    public void updateScene(String newQuery) throws IOException,SQLException {
        SQLservices.setSQL_currentQuery(newQuery);
        List<List<String>> data = new SQLservices().getData(newQuery);
        Parent root = FXMLLoader.load(getClass().getResource("SecondaryScene.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane p = fxmlLoader.load(getClass().getResource("SecondaryScene.fxml").openStream());
        Controller2 controller2 = fxmlLoader.getController();
        controller2.makecombos();
        controller2.setTable(newQuery);
        SQLservices.setSQL_currentQuery(newQuery);
        Scene newscene = new Scene(p, 1000, 600);
        newscene.getStylesheets().addAll(Controller2.class.getResource("MainStyle.css").toExternalForm());
        Main.primaryStage.setScene(newscene);
        Main.primaryStage.show();
    }
//----End of Controller
}
