package courseSearchV2;

import javafx.scene.control.TextArea;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gus on 6/17/2016.
 */
public class transactionLog {

    public void printLog(String query, List<List<String>> data, TextArea log){
        log.appendText("\n");
        log.appendText("Query: "+query + "\n");
        log.appendText("Response: \n");
        for(int i =0; i <data.size(); i++){
//            System.out.println(data.get(i).toString());
            log.appendText(data.get(i).toString() + "\n");
        }
        log.appendText("--------------------------------------------------------------------------");
        log.appendText("--------------------------------------------------------------------------\n");
    }

    class transaction
    {
        public Integer Count;
        public String Query;
        public List<List<String>> ReturnVal;
    }

    private static ArrayList<String> qLog;
    private static List<List<String>> dataReturnLog;
    private static ArrayList<transaction> transLog;
    private Integer count =0;

    public void addTransaction(String Query, List<List<String>> returnval){
        transaction newtrans = new transaction();
        newtrans.Count = count;
        newtrans.Query = Query;
        newtrans.ReturnVal = returnval;
        transLog.add(newtrans);
        dataReturnLog.addAll(returnval);
        System.out.println(dataReturnLog);
        count ++;
    }



}
