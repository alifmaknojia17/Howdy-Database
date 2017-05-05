package courseSearchV2;
/**
 *  Copyright(C) 2005
 *  All Rights Reserved. Salih Yurttas, Computer Science, TAMU.
 *
 *  Permission to use, copy, modify, and distribute this
 *  software and its documentation for EDUCATIONAL purposes
 *  and without fee is hereby granted provided that this
 *  copyright notice appears in all copies.
 *
 *  @date   : February 1, 2005.
 *  @author : Salih Yurttas.
 */


import java.sql.*;
import java.net.*;

import java.util.*;

public class db  {

    public static List<String> relations;

    protected static Connection con;

    private static String connectionString;
    //private static String driverClass ="com.mysql.jdbc.Driver";

    public String get_connectionString(){
        return connectionString;
    }


    public db() {
        connectionString = "jdbc:mysql://database2.cs.tamu.edu/alif-howdy";
        relations = Arrays.asList("CRN",
                "Subj",
                "Crse",
                "Sec",
                "Cmp",
                "Cred",
                "Title",
                "Days",
                "startTime",
                "endTime",
                "Cap",
                "Act",
                "Rem",
                "Instructor",
                "building",
                "roomnumber");
    }



    public boolean updateDatabase(String query) throws SQLException  //create table /INSERT / DELETE.
    {
        Statement st=con.createStatement();
        st.executeUpdate(query);
        return true;

    }

    public int countStatement(String query) throws SQLException
    {
        query = query.trim();
        query = query.replaceAll("//s+", " ");
        List<String> tokens = new ArrayList<>();
        Statement st = con.createStatement(); //sending request
        List<String> colm = new ArrayList<>();
        ResultSet rs = st.executeQuery(query);//get the result when execute the query.
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 1; i <= columnCount; i++ ) {
            tokens.add(rsmd.getColumnName(i));
            // Do stuff with name
        }
        for(String col: tokens)
        {
            colm.add(col);
        }
        int x=0;
        while(rs.next())
            x = rs.getInt(colm.get(0));

        return x;

    }

    public List<List<String>> selectStatement(String query) throws SQLException
    {
        List<List<String>> ret = new ArrayList<>();
        int count =0;
        query = query.trim();
        query = query.replaceAll("//s+", " ");
        List<String> tokens = new ArrayList<>();




        Statement st = con.createStatement(); //sending request
        List<String> colm = new ArrayList<>();
        ResultSet rs = st.executeQuery(query);//get the result when execute the query.

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++ ) {
            tokens.add(rsmd.getColumnName(i));
            // Do stuff with name
        }


        for(String col: tokens)
        {
            colm.add(col);
        }

        ret.add(colm);
        // ResultSet rs = st.executeQuery(query);//get the result when execute the query.
        while(rs.next())
        {
            List<String> atr =new ArrayList<>();
            for(String tok: tokens)
            {

                switch (tok)
                {
                    case "crn": int CRN = rs.getInt(tok);
                        atr.add(Integer.toString(CRN));
                        break;
                    case "subj" :
                        atr.add(rs.getString(tok));
                        break;
                    case "crse":
                        atr.add(rs.getString(tok));
                        break;
                    case "sec":
                        int SEC = rs.getInt(tok);
                        atr.add(Integer.toString(SEC));
                        break;
                    case "cmp":atr.add(rs.getString(tok));
                        break;
                    case "cred":
                        atr.add(rs.getString(tok));
                        break;
                    case "cap":
                        int Cap = rs.getInt(tok);
                        atr.add(Integer.toString(Cap));
                        break;
                    case "title":
                        atr.add(rs.getString(tok));
                        break;
                    case "days" :
                        atr.add(rs.getString(tok));
                        break;
                    case "endTime":
                        atr.add(rs.getString(tok));
                        break;
                    case "startTime":
                        atr.add(rs.getString(tok));
                        break;
                    case "act":
                        int ACT = rs.getInt(tok);
                        atr.add(Integer.toString(ACT));
                        break;
                    case "rem":
                        int REM = rs.getInt(tok);
                        atr.add(Integer.toString(REM));
                        break;
                    case "instructor":
                        atr.add(rs.getString(tok));
                        break;
                    case "building":
                        atr.add(rs.getString(tok));
                        break;
                    case "roomnumber" :
                        atr.add(rs.getString(tok));
                        break;
                    case "startDate":
                        atr.add(rs.getString(tok));
                        break;
                    case "endDate":
                        atr.add(rs.getString(tok));
                        break;
                    case "TimetoNext":
                        atr.add(rs.getString(tok));
                        break;
                    //still need to add TIME and DATE.


                }

            }
            ret.add(atr);
        }
        return ret;
    }

    public boolean connect()
            throws SQLException
    {

        //Class.forName(driverClass);
        con = DriverManager.getConnection(connectionString,"alif","team9pass");
        if(con.isClosed()) return false;



        return true;
    }

    public static boolean disconnect(Connection con)
            throws SQLException
    {
        con.close();
        return true;
    }

    public static boolean disconnect()
            throws SQLException
    {
        con.close();
        return true;
    }



}