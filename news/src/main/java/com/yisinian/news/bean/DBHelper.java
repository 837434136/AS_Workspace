package com.yisinian.news.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by deng on 2015/8/28.
 * Description: dbHelper manager
 */
public class DBHelper extends SQLiteOpenHelper{
	
    private final String TAG = getClass().getSimpleName();
    // 数据库名
    private final static String DATABASE_NAME = "News.db";
    // 数据库的版本
    private static int DB_VERSION = 1;
    private static DBHelper mInstance;
    
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
        Log.d(TAG, "super(context, DATABASE_NAME, null, DB_VERSION)");
    }
    public synchronized static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
        return mInstance;
    } 

    public synchronized static void destoryInstance() {
        if (mInstance != null) {
            mInstance.close();
            System.out.println("mInstance.close");
        }
    } 
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }
    
    /**
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return
     * 查找表
     */
    public synchronized Cursor query(String table, String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having, String orderBy){
        Cursor cursor = getWritableDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
    }
    
    /**
     * @param table
     * @param nullColumnHack
     * @param values
     * @return
     * 插入数据
     */
    public synchronized long insert(String table, String nullColumnHack, ContentValues values){
        return getWritableDatabase().insert(table, nullColumnHack, values);
    }
    
    /**
     * @param table
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     * 更新数据
     */
    public synchronized int update(String table, ContentValues values, String whereClause, String[] whereArgs){
        return getWritableDatabase().update(table, values, whereClause, whereArgs);
    }
    
    /**
     * @param table
     * @param whereClause
     * @param whereArgs
     * @return
     * 删除数据
     */
    public synchronized int delete(String table, String whereClause, String[] whereArgs){
        return getWritableDatabase().delete(table, whereClause, whereArgs);
    }
    
    /**
     * @param distinct
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return
     * 筛选重复数据
     */
    
	public synchronized Cursor distinct(Boolean distinct, String table, String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
    	Cursor cursor = getWritableDatabase().query(distinct, table, columns, selection, selectionArgs, 
    			groupBy, having, orderBy, limit);
    	return cursor;
    }
    
}
 