package courseSearchV2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Integer.getInteger;

public class Controller2 {

    @FXML
    public TextField crn_textfield, search2_textfield, minEmpty_textfield;
    @FXML
    public TextField hours1_textfield, hours2_textfield, min1_textfield, min2_textfield, min3_textfield;
    @FXML
    public CheckBox  isLike2_checkbox;
    @FXML
    public ComboBox level_combo;
    @FXML
    private ComboBox subject_combo;
    @FXML
    private ComboBox course_combo;
    @FXML
    private ComboBox section_combo,operations_choicebox, emptyOp_choicebox;

    @FXML
    private TableView results_table;
    @FXML
    public Button update_butt, restart_butt;
    @FXML
    public CheckBox monday_cbox, tuesday_cbox, wednesday_cbox, thursday_cbox, friday_cbox;
    @FXML
    public TextArea transLog_textarea;

    @FXML
    public void about(){
        new AboutBox().display("About us");
    }


    public void search2_buttAction() throws IOException, SQLException{
        String sqlString = new searchAlgorithm().mainAlgorithm(search2_textfield.getText());
        System.out.println(sqlString);
        String newQuery = "SELECT * FROM classes WHERE " + sqlString;
        makecombos();
        setTable(newQuery);
    }

    public void closeFunction() {
        Main.primaryStage.close();
    }
    public void usedata(List<List<String>> data){

        results_table = new makeTable().makeTable(data);
    }

    public void  update_ButtAction() throws Exception{
        String newQuery = "";
        if (!crn_textfield.getText().isEmpty()) { // give crn field listener and then reference the text
            newQuery = "SELECT * FROM classes WHERE crn = '" + crn_textfield.getText() + "'";
            SQLservices.setSQL_currentQuery(newQuery);
            makecombos();
            setTable(newQuery);
            System.out.println(newQuery);
            return;
        }
        checkLevel();
        checktime();
        checkdays();
        makecombos();
        setTable(SQLservices.SQL_currentQuery);
    }



    @FXML
    public void clear_combos(){
        System.out.println("here3 starting clear");
        if (subject_combo.getSelectionModel().getSelectedItem() == null){
            subject_combo.getItems().clear();
            System.out.println("subj cleared");
        }
        if (course_combo.getSelectionModel().getSelectedItem() == null){
            course_combo.getItems().clear();
            System.out.println("subj cleared");
        }
        if (section_combo.getSelectionModel().getSelectedItem() == null){
            course_combo.getItems().clear();
            System.out.println("subj cleared");
        }
        System.out.println("here4 cleared combos");
    }
    @FXML
    public void subject_comboAction() throws SQLException, IOException{
        String update = subject_combo.getSelectionModel().getSelectedItem().toString();
        String updateQuery;

        updateQuery = SQLservices.addToQuery("WHERE subj = '" + update+"'");
        SQLservices.setSQL_currentQuery(updateQuery);
        setTable(updateQuery);
        clear_combos();
        makecombos();
    }
    @FXML
    public void course_comboAction() throws SQLException{
        String update = course_combo.getSelectionModel().getSelectedItem().toString();
        String updateQuery;
        updateQuery = SQLservices.addToQuery("WHERE crse = '" + update+"'");

        SQLservices.setSQL_currentQuery(updateQuery);
        setTable(updateQuery);
        makecombos();
        System.out.println(updateQuery);
    }
    @FXML
    public void section_comboAction() throws SQLException{
        String update = section_combo.getSelectionModel().getSelectedItem().toString();
        String updateQuery;
        updateQuery = SQLservices.addToQuery("WHERE sec = '" + update+"'");

        SQLservices.setSQL_currentQuery(updateQuery);
        setTable(updateQuery);
        makecombos();
        System.out.println(updateQuery);
    }

    public void restart() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        Main.primaryStage.setTitle("TAMU COURSE SEARCH");
        Main.primaryStage.setScene(new Scene(root, 1000, 600));
        Main.primaryStage.show();
    }

    @FXML
    public void makecombos() throws SQLException{
        List<List<String>> datalist, subjdata, coursedata, sectiondata;
        String subjQuery = SQLservices.SQL_currentQuery.replace("*", "DISTINCT subj");
        System.out.println(subjQuery);
        String courseQuery = SQLservices.SQL_currentQuery.replace("*", "DISTINCT crse");
        System.out.println(courseQuery);
        String sectionQuery = SQLservices.SQL_currentQuery.replace("*", "DISTINCT sec");
        System.out.println(sectionQuery);
        subjdata = new SQLservices().getData(subjQuery);
        coursedata = new SQLservices().getData(courseQuery);
        sectiondata = new SQLservices().getData(sectionQuery);
        ObservableList subj_list = new makeTable().makeList(subjdata);
        ObservableList course_list = new makeTable().makeList(coursedata);
        ObservableList section_list = new makeTable().makeList(sectiondata);
        subject_combo.getItems().setAll(subj_list);
        course_combo.getItems().setAll(course_list);//possible issue here----------------------------------------------
        section_combo.getItems().setAll(section_list);
    }

    @FXML
    public void setTable(String newQuery) throws SQLException{
        List<List<String>> data = new SQLservices().getData(newQuery);
        new makeTable().updateTable(data, results_table);
        new transactionLog().printLog(newQuery, data, transLog_textarea);
    }

    @FXML
    public void checkdays(){
        String days ="";
        String addToQuery;
        if(monday_cbox.isSelected()){
            days = days + "M";
        }
        if(tuesday_cbox.isSelected()){
            days = days+"T";
        }
        if(wednesday_cbox.isSelected()){
            days = days+"W";
        }
        if(thursday_cbox.isSelected()){
            days = days+"R";
        }
        if(friday_cbox.isSelected()){
            days = days+"F";
        }
        if(days != ""){
            String newQuery;
            newQuery = SQLservices.addToQuery("WHERE days LIKE '%"+ days +"%'");
            SQLservices.setSQL_currentQuery(newQuery);
            return;
        }
        else return;
    }

    @FXML
    public void checkLevel(){
        String level;
        if(level_combo.getSelectionModel().getSelectedItem() != null){
            String newQuery;
            level = level_combo.getSelectionModel().getSelectedItem().toString().replace("00","");
            newQuery = SQLservices.addToQuery("WHERE crse LIKE '"+ level +"%'");
            SQLservices.setSQL_currentQuery(newQuery);
            return;
        }
        else return;

    }



    @FXML
    public void checktime(){
//        public TextField hours1_textfield, hours2_textfield, min1_textfield, min2_textfield, min3_textfield;
        //start time
        if(!minEmpty_textfield.getText().isEmpty()){
            String operation = emptyOp_choicebox.getSelectionModel().getSelectedItem().toString();
            String val = minEmpty_textfield.getText();
            String newQuery = SQLservices.SQL_currentQuery.replace("classes", "course");
            SQLservices.setSQL_currentQuery(newQuery);
            String addtoQuery = "WHERE ABS(TimetoNext) "+operation+" '00:"+val+":00'";
            newQuery = SQLservices.addToQuery(addtoQuery);
            SQLservices.setSQL_currentQuery(newQuery);
        }
        if(!hours1_textfield.getText().isEmpty() && !min1_textfield.getText().isEmpty()){
            System.out.println("time vals are not null");
            if(isTimeReal(hours1_textfield) && isTimeReal(min1_textfield)){
                System.out.println("vals checked");
                String strtTime = hours1_textfield.getText()+":"+min1_textfield.getText();
                System.out.println(strtTime);
                String strtQuery;
                strtQuery = SQLservices.addToQuery("WHERE startTime = '"+strtTime+"'");
                //String strtQuery = SQLservices.SQL_currentQuery.replace("WHERE","WHERE startTime = '"+strtTime+"' AND ");
                System.out.println(strtQuery);
                SQLservices.setSQL_currentQuery(strtQuery);
            }
        }
        //endtime
        if(!hours2_textfield.getText().isEmpty() && !min2_textfield.getText().isEmpty()){
            System.out.println("time vals are not null2");
            if(isTimeReal(hours2_textfield) && isTimeReal(min2_textfield)){
                String endTime = hours2_textfield.getText()+":"+min2_textfield.getText();
                String strtQuery;
                strtQuery = SQLservices.addToQuery("WHERE endTime = '"+endTime+"'");
                SQLservices.setSQL_currentQuery(strtQuery);
            }
        }
        if(!min3_textfield.getText().isEmpty()){
            //math operation for room table and join with classes for results
            if(isTimeReal(min3_textfield)){
                Integer val = Integer.valueOf(min3_textfield.getText());
                String operation = operations_choicebox.getSelectionModel().getSelectedItem().toString();
                String Query;
                Query = SQLservices.addToQuery("WHERE TIMEDIFF(endTime, startTime) "+operation+" "+(val*100));
                SQLservices.setSQL_currentQuery(Query);
            }
        }
    }

    public String makeAmPm(String arg , boolean isPm){
        if (isPm){
            int newval = Integer.valueOf(arg);
            if(newval < 13 && newval > 0){
                newval = newval +12;
                System.out.println("PM: "+String.valueOf(newval));
                return String.valueOf(newval);
            }
        }
        System.out.println("AM: "+arg);
        return arg;
    }

    @FXML
    public boolean isTimeReal(TextField textfield){
        String testval = textfield.getText();
        System.out.println("start check");
        if (!testval.matches("\\d*")){
            System.out.println("no numbas!");
            return false;
        }
        Integer val = Integer.valueOf(testval);
        if( val < 0 || val > 60){
            System.out.println("Error: Time value is out of bounds");
            System.out.println("false");
            return false;
        }
        else {
            System.out.println("true");
            return true;
        }
    }


//----End of Controller
}

/*
* [1]-http://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
*
*
* */
