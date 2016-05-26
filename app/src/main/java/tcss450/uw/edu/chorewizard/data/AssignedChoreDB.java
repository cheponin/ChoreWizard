package tcss450.uw.edu.chorewizard.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import tcss450.uw.edu.chorewizard.HomeActivity;

/**
 * Created by alice on 5/26/2016.
 */
public class AssignedChoreDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "AssignedChore.db";

    private HomeActivity.AssignedChoreDBHelper mAssignedChoreDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public AssignedChoreDB(Context context) {
        mAssignedChoreDBHelper = new HomeActivity.AssignedChoreDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mAssignedChoreDBHelper.getWritableDatabase();
    }

    /**
     * Inserts the course into the local sqlite table. Returns true if successful, false otherwise.
     * @param name
     * @param chore
     * @return true or false
     */
    public boolean insertAssignedChore(String name, String chore) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("chore", chore);

        long rowId = mSQLiteDatabase.insert("AssignedChore", null, contentValues);
        return rowId != -1;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }



}
