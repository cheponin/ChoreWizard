package tcss450.uw.edu.chorewizard.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.chorewizard.HomeActivity;
import tcss450.uw.edu.chorewizard.model.AssignedChore;
import tcss450.uw.edu.chorewizard.model.Chore;

/**
 * Created by alice on 5/26/2016.
 */
public class AssignedChoreDB {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "AssignedChore.db";

    private HomeActivity.AssignedChoreDBHelper mAssignedChoreDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    private static final String ASSIGNEDCHORE_TABLE = "AssignedChore";

    public AssignedChoreDB(Context context) {
        mAssignedChoreDBHelper = new HomeActivity.AssignedChoreDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mAssignedChoreDBHelper.getWritableDatabase();

        mAssignedChoreDBHelper.onCreate(mSQLiteDatabase);
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

    /**
     * Returns the list of courses from the local Course table.
     * @return list
     */
    public List<AssignedChore> getAssignedChores() {

        String[] columns = {
                "name", "chore"
        };

        Cursor c = mSQLiteDatabase.query(
                ASSIGNEDCHORE_TABLE,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        c.moveToFirst();

        List<AssignedChore> list = new ArrayList<AssignedChore>();

        for (int i = 0; i < c.getCount(); i++) {
            String name = c.getString(0);
            String chore = c.getString(1);
            AssignedChore assignedChore = new AssignedChore(name, chore);
            list.add(assignedChore);
            c.moveToNext();
        }

        return list;
    }

    public void closeDB() {

        mSQLiteDatabase.close();
    }



}
