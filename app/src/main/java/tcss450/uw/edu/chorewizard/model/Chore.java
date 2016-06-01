package tcss450.uw.edu.chorewizard.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * The class that models the attributes of a chore.
 * Created by alice on 5/10/2016.
 */
public class Chore {

    /**
     * The chore's name.
     */
    private String mChoreName;

    /**
     * The chore's frequency.
     */
    private String mChoreFrequency;

    /** Constants to hold the names of the class fields. */
    public static final String NAME = "name", FREQUENCY = "frequency";

    /**
     * The Chore constructor.
     * @param mChoreName is assigned to the global variable
     * @param mChoreFrequency is assigned to the global variable
     */
    public Chore(String mChoreName, String mChoreFrequency) {
        this.mChoreName = mChoreName;
        this.mChoreFrequency = mChoreFrequency;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     *
     * @param choreJSON is the data file containing the members and their information.
     * @return reason or null if successful.
     */
    public static String parseChoreJSON(String choreJSON, List<Chore> choreList) {
        String reason = null;
        if (choreJSON != null) {
            try {
                JSONArray arr = new JSONArray(choreJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Chore chore = new Chore(obj.getString(Chore.NAME), obj.getString(Chore.FREQUENCY));
                    choreList.add(chore);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    public String getmChoreName() {
        return mChoreName;
    }

    public void setmChoreName(String mChoreName) {
        if (mChoreName == null) {
            throw new IllegalArgumentException();
        } else if (mChoreName.length() > 25) {
            throw new IllegalArgumentException();
        }

        this.mChoreName = mChoreName;
    }

    public String getmChoreFrequency() {
        return mChoreFrequency;
    }

    public void setmChoreFrequency(String mChoreFrequency) {
        if (mChoreFrequency == null) {
            throw new IllegalArgumentException();
        } else if (mChoreFrequency.length() > 25) {
            throw new IllegalArgumentException();
        }

        this.mChoreFrequency = mChoreFrequency;
    }

    @Override
    public String toString() {
        return mChoreName + " " + mChoreFrequency;
    }
}
