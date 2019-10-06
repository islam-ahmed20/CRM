package net.systemsstars.crm.helper;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBController  extends SQLiteOpenHelper {

    public DBController(Context applicationcontext) {
        super(applicationcontext, "androidsqlite.db", null, 6);
    }
    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE users ( userId INTEGER PRIMARY KEY, userName TEXT, password TEXT, type TEXT, phone Text, payments Text, paymentsDate Text, udpateStatus Text)";
        database.execSQL(query);

        query = "CREATE TABLE projects ( projectId INTEGER PRIMARY KEY, projectName TEXT, description TEXT, cost TEXT, deliveryDate Text, udpateStatus Text)";
        database.execSQL(query);

        query = "CREATE TABLE project_assignment ( assignmentProjectId INTEGER PRIMARY KEY, userId INTEGER, projectName TEXT, udpateStatus Text)";
        database.execSQL(query);

        ContentValues values = new ContentValues();
        values.put("userName", "admin");
        values.put("password", "admin");
        values.put("type", "admin");
        values.put("udpateStatus", "no");
        database.insert("users", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS users";
        database.execSQL(query);
        query = "DROP TABLE IF EXISTS projects";
        database.execSQL(query);
        query = "DROP TABLE IF EXISTS project_assignment";
        database.execSQL(query);
        onCreate(database);
    }
    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    public long insertUser(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", queryValues.get("userName"));
        values.put("password", queryValues.get("password"));
        values.put("type", queryValues.get("type"));
        values.put("phone", queryValues.get("phone"));
        values.put("payments", queryValues.get("payments"));
        values.put("paymentsDate", queryValues.get("paymentsDate"));
        values.put("udpateStatus", "no");
        long userId = database.insert("users", null, values);
        database.close();
        return userId;
    }

    public void insertProject(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("projectName", queryValues.get("projectName"));
        values.put("description", queryValues.get("description"));
        values.put("cost", queryValues.get("cost"));
        values.put("deliveryDate", queryValues.get("deliveryDate"));
        values.put("udpateStatus", "no");
        database.insert("projects", null, values);
        database.close();
    }

    public void insertProjectAssignment(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", queryValues.get("userId"));
        values.put("projectName", queryValues.get("projectName"));
        values.put("udpateStatus", "no");
        database.insert("project_assignment", null, values);
        database.close();
    }

    public HashMap<String, String> getUser(HashMap<String, String> queryValues){
        String selectQuery = "SELECT  * FROM users where userName = '" + queryValues.get("userName") + "' and password = '"+ queryValues.get("password") + "'" ;
        Log.e("selectQuery",selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        HashMap<String, String> map = new HashMap<String, String>();
        if (cursor.moveToFirst()) {
            map.put("userId", cursor.getString(0));
            map.put("userName", cursor.getString(1));
            map.put("type", cursor.getString(3));
            map.put("phone", cursor.getString(4));
            map.put("payments", cursor.getString(5));
        }
        database.close();
        return map;
    }

    public HashMap<String, String> getUserInfo(String userId){
        String selectQuery = "SELECT  * FROM users where userId = '" + userId + "'";
        Log.e("selectQuery",selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        HashMap<String, String> map = new HashMap<String, String>();
        if (cursor.moveToFirst()) {
            map.put("userId", cursor.getString(0));
            map.put("userName", cursor.getString(1));
            map.put("type", cursor.getString(3));
            map.put("phone", cursor.getString(4));
            map.put("payments", cursor.getString(5));
            map.put("paymentsDate", cursor.getString(6));
        }
        database.close();
        return map;
    }

    /**
     * Get list of Users from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM users";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                map.put("password", cursor.getString(2));
                map.put("type", cursor.getString(3));
                map.put("phone", cursor.getString(4));
                map.put("payments", cursor.getString(5));
                map.put("paymentsDate", cursor.getString(6));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    public ArrayList<HashMap<String, String>> getAllProjects() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM projects";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("projectId", cursor.getString(0));
                map.put("projectName", cursor.getString(1));
                map.put("description", cursor.getString(2));
                map.put("cost", cursor.getString(3));
                map.put("deliveryDate", cursor.getString(4));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    public ArrayList<HashMap<String, String>> getAllProjectsAssignment() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM project_assignment";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("assignmentProjectId", cursor.getString(0));
                map.put("userId", cursor.getString(1));
                map.put("projectName", cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    public ArrayList<HashMap<String, String>> getAllAssignmentProjects(String userId) {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM project_assignment where userId = '"+userId+"'";
        Log.e("selectQuery", selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("assignmentProjectId", cursor.getString(0));
                map.put("projectName", cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Log.e("wordList", wordList.toString());
        return wordList;
    }
    /**
     * Compose JSON out of SQLite records
     * @return
     */
    public ArrayList<HashMap<String, String>> composeAllusers(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM users where udpateStatus = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                map.put("password", cursor.getString(2));
                map.put("type", cursor.getString(3));
                map.put("phone", cursor.getString(4));
                map.put("payments", cursor.getString(5));
                map.put("paymentsDate", cursor.getString(6));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    public ArrayList<HashMap<String, String>> composeAllProjects() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM projects where udpateStatus = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("projectId", cursor.getString(0));
                map.put("projectName", cursor.getString(1));
                map.put("description", cursor.getString(2));
                map.put("cost", cursor.getString(3));
                map.put("deliveryDate", cursor.getString(4));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    public ArrayList<HashMap<String, String>> composeAllProjectsAssignment() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM project_assignment where udpateStatus = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("assignmentProjectId", cursor.getString(0));
                map.put("userId", cursor.getString(1));
                map.put("projectName", cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }


    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync needed\n";
        }
        return msg;
    }

    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM users where udpateStatus = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }

    /**
     * Update Sync status against each User ID
     * @param users
     */

    public void updateUsersSyncStatus(ArrayList<HashMap<String, String>> users){
        SQLiteDatabase database = this.getWritableDatabase();
        for(int i=0 ; i<users.size(); i++) {
            String updateQuery = "Update users set udpateStatus = 'Yes' where userId="+"'"+ users.get(i).get("userId") +"'";
            Log.d("query",updateQuery);
            database.execSQL(updateQuery);
        }
        database.close();
    }

    public void updateProjectsSyncStatus(ArrayList<HashMap<String, String>> projects){
        SQLiteDatabase database = this.getWritableDatabase();
        for(int i=0 ; i<projects.size(); i++) {
            String updateQuery = "Update projects set udpateStatus = 'Yes' where projectId="+"'"+ projects.get(i).get("projectId") +"'";
            Log.d("query",updateQuery);
            database.execSQL(updateQuery);
        }
        database.close();
    }

    public void updateProjectsAssignmentSyncStatus(ArrayList<HashMap<String, String>> projectsAssignment){
        SQLiteDatabase database = this.getWritableDatabase();
        for(int i=0 ; i<projectsAssignment.size(); i++) {
            String updateQuery = "Update project_assignment set udpateStatus = 'Yes' where assignmentProjectId="+"'"+ projectsAssignment.get(i).get("assignmentProjectId") +"'";
            Log.d("query",updateQuery);
            database.execSQL(updateQuery);
        }
        database.close();
    }
}