package courseSearchV2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLservices {

    private String connectionString = new db().get_connectionString();
    public static String SQL_currentQuery;


    static public void setSQL_currentQuery(String Query){
        SQL_currentQuery = Query;
        System.out.println("SQL query: "+SQL_currentQuery);
    }

    static String addToQuery(String addition){
        String newQuery;
        if(SQL_currentQuery.contains("WHERE")){
            newQuery = SQL_currentQuery.replace("WHERE", addition +" AND");
        }
        else{
            newQuery = new SQLservices().SQL_currentQuery +" "+addition;
        }
        return newQuery;
    }

    public void testData(String query) throws SQLException{
        db dbc = new db();
        if(dbc.connect()) { //connect to the database.
            List<List<String>> testing = new ArrayList<>(); // 2D arraylist for getting the result from query.
            String selcttext = query;//"select Crse, Title FROM classes";//doing select query.
            testing = dbc.selectStatement(selcttext);
            for(int i =0; i< testing.size(); i++) {
                for(int j =0; j< testing.get(i).size();j++) {
                    System.out.print(testing.get(i).get(j) + " ");
                }
                System.out.print("\n");
            }
            if(dbc.disconnect());
                //System.out.println("Disconnected from "+ connectionString);
        }
        else System.out.println("Could not connect to "+ connectionString);
    }

    public List<List<String>> getData(String query) throws SQLException{
        db dbc = new db();
        List<List<String>> testing = new ArrayList<>();// 2D arraylist for getting the result from query.
        if(dbc.connect()) { //connect to the database.
            //System.out.println("Connected to" + connectionString);
            String selcttext = query;//"select Crse, Title FROM classes";//doing select query.
            testing = dbc.selectStatement(selcttext);
            if(dbc.disconnect());
                //System.out.println("Disconnected from "+ connectionString);
        }
        else System.out.println("Could not connect to "+ connectionString);
        return testing;
    }

    public int getCount(String query) throws SQLException{
        db dbc = new db();
        int val = 0;
        if(dbc.connect()) { //connect to the database.
            //System.out.println("Connected to" + connectionString);
            val = dbc.countStatement(query);
            if(dbc.disconnect());
                //System.out.println("Disconnected from "+ connectionString);
        }
        else System.out.println("Could not connect to "+ connectionString);
        return val;
    }

    public ComboBox<String> updateCombobox(List<List<String>> array){
        ObservableList<String> stringArray = FXCollections.observableArrayList();
        ComboBox<String> box = new ComboBox<String>();
        boolean elementsLeft = true;
        int column = 0;
        while (elementsLeft) {
            for (List<String> subList : array) {
                if (subList.size() > column) {
                    System.out.print(subList.get(column) + " ");
                    stringArray.add(subList.get(column));
                    box.getItems().add(subList.get(column));
                }else {
                    System.out.print("x ");
                }
            }
            System.out.println();

            elementsLeft = isElementsLeft((ArrayList) array, column);
            column++;
        }
        System.out.println(stringArray.toString());
        return  box;
    }

    private static boolean isElementsLeft(ArrayList<ArrayList<Double>> someArray, int column) {
        for (ArrayList<Double> subList : someArray) {
            if (subList.size() > column) {
                return true;
            }
        }
        return false;
    }

    public String getQueryTerm(String arg){
        String queryTerm;
        switch (arg) {
            case "CRN":
                queryTerm = "crn";
                break;
            case "Subject":
                queryTerm = "subj";//issue here
                break;
            case "Course Number":
                queryTerm = "crse";
                break;
            case "Section":
                queryTerm = "sec";
                break;
            case "Cmp":
                queryTerm = "cmp";
                break;
            case "Credits":
                queryTerm = "cred";
                break;
            case "Course Name":
                queryTerm = "title";
                break;
            case "Days":
                queryTerm = "days";
                break;
            case "Period":
                queryTerm = "period";
                break;
            case "Capacity":
                queryTerm = "cap";
                break;
            case "Capacity =":
                queryTerm = "cap =";
                break;
            case "Capacity >":
                queryTerm = "cap >";
                break;
            case "Capacity <":
                queryTerm = "cap <";
                break;
            case "Seats Occupied":
                queryTerm = "act";
                break;
            case "Seats Occupied =":
                queryTerm = "act =";
                break;
            case "Seats Occupied >":
                queryTerm = "act >";
                break;
            case "Seats Occupied <":
                queryTerm = "act <";
                break;
            case "Seats Remaining":
                queryTerm = "rem";
                break;
            case "Seats Remaining =":
                queryTerm = "rem =";
                break;
            case "Seats Remaining >":
                queryTerm = "rem >";
                break;
            case "Seats Remaining <":
                queryTerm = "rem <";
                break;
            case "Instructor":
                queryTerm = "instructor";
                break;
            case "Time Span":
                queryTerm = "span";
                break;
            case "Building":
                queryTerm = "building";
                break;
            case "Room Number":
                queryTerm = "roomnumber";
                break;
            default:
                queryTerm = arg;
        }
        return queryTerm;
    }
    //end of class
}
