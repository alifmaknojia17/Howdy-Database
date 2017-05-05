package courseSearchV2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Glopez on 6/19/2016.
 */
public class mainAlgorithm {

    class SEARCH{
        String key;
        String condition;
        Integer count;
        public String toString(){
            return "key: "+key+", condition: "+condition+", count: "+count+"||";
        }
    }

    public String mainAlgorithm(String search) throws SQLException {

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


//    public void checkDeletes(String string){
//        if(toString().matches("delete")){
//
//        }
//    }

        ArrayList<SEARCH> searchResults = new ArrayList<SEARCH>();
        ArrayList<SEARCH> exactResults = new ArrayList<SEARCH>();

        String[] searchsplit = search.split(" ");
        System.out.println("starting");
        List<String> stringlist = new ArrayList<String>(Arrays.asList(searchsplit));
        System.out.println(stringlist);
        System.out.println("searchsplit: "+ searchsplit.length);// if length is larger than 1, else just do one search
        System.out.println("keys: "+ keys.length);
        String newQuery = null;
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
                    System.out.println(query+": count = "+val);
                }
                if(val2 > 0){
                    SEARCH results  = new SEARCH();
                    results.key = keys[p];
                    results.condition = searchsplit[i];
                    results.count = val;
                    exactResults.add(results);
                    System.out.println(query+": count = "+val);
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


        System.out.println("max is: "+max);
        System.out.println("choice is: "+choice+": "+searchResults.get(choice).condition);
        System.out.println("min is: "+max);
        System.out.println("minchoice is: "+minchoice+": "+searchResults.get(minchoice).condition);
        if (finalmincount != 0){
            newQuery = finalChoice+finalChoiceCond+" AND "+finalMinChoice+finalMinChoiceCond;
        }
        else{
            newQuery = finalChoice+finalChoiceCond;
        }
        System.out.println(newQuery);

        return newQuery;

    }

}
