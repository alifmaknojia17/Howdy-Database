package courseSearchV2;


import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Glopez on 6/12/2016.
 */
public class searchAlgorithm {
    public String findKeys(String search, boolean box){
        //What classes does Walter Daugherity teach
        String[] wordArray = search.split(" ");
        System.out.println(Arrays.toString(wordArray));
        int index =0;
        String returnString = "";
        String searchString ="";
        //teaches
        for (int i=0;i<wordArray.length;i++) {
            if (wordArray[i].contains("Prof") || wordArray[i].contains("prof")) {
                index = i;
                System.out.println(wordArray[index + 1]+"1");
                searchString = "instructor LIKE ";
                break;

            } else if (wordArray[i].contains("Instr") || wordArray[i].contains("instr")) {
                index = i;
                System.out.println(wordArray[index + 1]+"2");
                searchString = "instructor LIKE ";
                break;

            } else if (wordArray[i].contains("Class") || wordArray[i].contains("class")) {
                index = i;
                System.out.println(wordArray[index + 1]);
                searchString = "title LIKE";
                break;

            } else if (wordArray[i].contains("Course") || wordArray[i].contains("course")) {
                index = i;
                System.out.println(wordArray[index + 1]);
                searchString = "crse LIKE ";
                wordArray[index + 1] = wordArray[index + 1].charAt(0) + "_";
                System.out.println(searchString + "'" + wordArray[index + 1] + "%'");
                return returnString = searchString + "'%" + wordArray[index + 1] + "%'";
            }
            else return "";
        }
        if (box == true){
            System.out.println(searchString +"'%" + wordArray[index+1] + "%'");
            return returnString = searchString +"'%" + wordArray[index+1] + "%'";
        }
        else{
            System.out.println(searchString +"'" + wordArray[index+1] + "'");
            return returnString = searchString +"'" + wordArray[index+1] + "'";
        }
    }

    public String mainAlgorithm(String search) throws SQLException{

        String[] keys = {
                "crn",
                "subj",
                "crse",
                "sec",
                "cmp",
                "cred",
                "title",
                "days",
                "cap",
                "act",
                "rem",
                "instructor",
                "building",
                "roomnumber"
        };

        class SEARCH{
            String key;
            String condition;
            Integer count;
            public String toString(){
                return "key: "+key+", condition: "+condition+", count: "+count+"||";
            }
        }
        ArrayList<SEARCH> searchResults = new ArrayList<SEARCH>();
        ArrayList<SEARCH> exactResults = new ArrayList<SEARCH>();

        String[] searchsplit = search.split(" ");
        List<String> stringlist = new ArrayList<String>(Arrays.asList(searchsplit));
        String newQuery;
        for (Integer i = 0 ; i<searchsplit.length; i++){
            for(Integer p = 0 ; p<keys.length; p++){
                String query = "SELECT count(1) from classes WHERE "+keys[p]+" LIKE '%"+ searchsplit[i] +"%'";
                String query2 = "SELECT count(1) from classes WHERE "+keys[p]+" = '"+ searchsplit[i] +"'";
                int val = new SQLservices().getCount(query);
                int val2 = new SQLservices().getCount(query2);
                if(val > 0){
                    SEARCH results  = new SEARCH();
                    results.key = keys[p];
                    results.condition = searchsplit[i];
                    results.count = val;
                    searchResults.add(results);
                    //System.out.println(query+": count = "+val);
                }
                if(val2 > 0){
                    SEARCH results  = new SEARCH();
                    results.key = keys[p];
                    results.condition = searchsplit[i];
                    results.count = val;
                    exactResults.add(results);
                    //System.out.println(query+": count = "+val);
                }
            }
        }
        int choice = 0;
        int minchoice = 0;
        int min = searchResults.get(0).count;
        int max = searchResults.get(0).count;
        for(int i =0; i<searchResults.size(); i++){
            if(searchResults.get(i).count > max){
                max = searchResults.get(i).count;
                choice = i;
            }
            if(searchResults.get(i).count < min){
                min = searchResults.get(i).count;
                minchoice = i;
            }
        }

        int choice2 = 0;
        int minchoice2 = 0;
        int min2 = exactResults.get(0).count;
        int max2 = exactResults.get(0).count;
        for(int i =0; i<exactResults.size(); i++){
            if(exactResults.get(i).count > max){
                max2 = exactResults.get(i).count;
                choice2 = i;
            }
            if(exactResults.get(i).count < min){
                min2 = exactResults.get(i).count;
                minchoice2 = i;
            }
        }

        String finalChoice;
        String finalChoiceCond;
        String finalMinChoice;
        String finalMinChoiceCond;
        int finalcount, finalmincount;



        if(searchResults.get(choice).count > exactResults.get(choice2).count){
            finalcount = searchResults.get(choice).count;
            finalChoice = searchResults.get(choice).key;
            finalChoiceCond = " LIKE '%"+searchResults.get(choice).condition+"%'";
        }
        else{
            finalcount = exactResults.get(choice2).count;
            finalChoice = exactResults.get(choice2).key;
            finalChoiceCond = " = '"+exactResults.get(choice2).condition+"'";
        }
        if(searchResults.get(minchoice).count > exactResults.get(minchoice2).count){
            finalmincount = searchResults.get(minchoice).count;
            finalMinChoice = searchResults.get(minchoice).key;
            finalMinChoiceCond = " LIKE '%"+searchResults.get(minchoice).condition+"%'";
        }
        else{
            finalmincount = exactResults.get(minchoice2).count;
            finalMinChoice = exactResults.get(minchoice2).key;
            finalMinChoiceCond = " = '"+exactResults.get(minchoice2).condition+"'";
        }

        if(searchsplit.length == 1){
            newQuery = finalChoice+finalChoiceCond;
        }
        else if (finalmincount != 0){
            newQuery = finalChoice+finalChoiceCond+" AND "+finalMinChoice+finalMinChoiceCond;
        }
        else{
            newQuery = finalChoice+finalChoiceCond;
        }
       // System.out.println(newQuery);

        return newQuery;

    }

}


