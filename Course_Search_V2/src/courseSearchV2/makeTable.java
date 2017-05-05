package courseSearchV2;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Glopez on 6/8/2016.
 * Modified code from source below. Transforms 2- dimensional array into tableview to be returned to GUI.
 *
 * source:http://stackoverflow.com/questions/20769723/populate-tableview-with-two-dimensional-array
 */
public class makeTable {

    TextField filtertext;
    TableView newTable = new TableView();
    ObservableList<String> master = FXCollections.observableArrayList();


    TableView makeTable(String[][] tableData){//[1]

        ObservableList<String[]> dataList = FXCollections.observableArrayList();
        dataList.addAll(Arrays.asList(tableData));
        dataList.remove(0); //removes titles
        for(int i = 0; i < tableData[0].length; i++){
            TableColumn column = new TableColumn(tableData[0][i]);
            final int col_num = i;
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> param) {
                    return new SimpleStringProperty((param.getValue()[col_num]));
                }
            });
            column.setPrefWidth(90);
            newTable.getColumns().add(column);
        }
        newTable.setItems(dataList);
        return newTable;
    }

    TableView makeTable(List<List<String>> data){//[1]
        String[][] tableData = to_2dStringArray(data);
        ObservableList<String[]> dataList = FXCollections.observableArrayList();
        dataList.addAll(Arrays.asList(tableData));
        dataList.remove(0); //removes titles
        for(int i = 0; i < tableData[0].length; i++){
            TableColumn column = new TableColumn(tableData[0][i]);
            final int col_num = i;
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> param) {
                    return new SimpleStringProperty((param.getValue()[col_num]));
                }
            });
            newTable.getColumns().add(column);
        }
        newTable.setItems(dataList);
        return newTable;
    }

    public void updateTable(List<List<String>> data, TableView table){//[1]
        table.getItems().clear();
        table.getColumns().clear();
        String[][] tableData = to_2dStringArray(data);
        ObservableList<String[]> dataList = FXCollections.observableArrayList();
        dataList.addAll(Arrays.asList(tableData));
        dataList.remove(0); //removes titles
        for(int i = 0; i < tableData[0].length; i++){
            TableColumn column = new TableColumn(tableData[0][i]);
            final int col_num = i;
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> param) {
                    return new SimpleStringProperty((param.getValue()[col_num]));
                }
            });
            table.getColumns().add(column);
        }
        table.setItems(dataList);
    }

    ObservableList<String> makeList(List<List<String>> data){//[1]
        String[][] tableData = to_2dStringArray(data);
        ObservableList<String> datalist = FXCollections.observableArrayList();
        for (int i = 1; i< tableData.length; i++){
            datalist.add(tableData[i][0]); //add(tableData[0][i]);
        }
        return datalist;
    }

    public ComboBox makecombo(List<List<String>> data){//[1]
        String[][] tableData = to_2dStringArray(data);
        ObservableList<String> datalist = FXCollections.observableArrayList();
        for (int i = 0; i< tableData.length; i++){
            datalist.add(tableData[i][0]); //add(tableData[0][i]);
        }
        ComboBox newbox = new ComboBox();
        newbox.promptTextProperty().setValue(datalist.get(0));
        datalist.remove(0);
        newbox.setItems(datalist);
        return newbox;
    }

    private String[][] to_2dStringArray(List<List<String>> data){ //modified from source: [1]
        final String[][] stringArray = new String[data.size()][];
        int i = 0;
        for (List<String> l : data)
            stringArray[i++] = l.toArray(new String[l.size()]);
        return stringArray;
    }
}

//[1]: http://stackoverflow.com/questions/10769357/how-can-i-convert-a-2d-string-arraylist-to-simple-2d-string-array
