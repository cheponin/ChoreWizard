package tcss450.uw.edu.chorewizard.model;

/**
 * Assigns values pulled from the database to member name and chore name.
 * Created by chepovska_nina on 5/26/16.
 */
public class AssignedChore {

    /**
     * The member's name.
     */
    private String mName;

    /**
     * The chore's name.
     */
    private String mChore;

    /**
     * AssignedChore constructor.
     * @param name is set to the global variable.
     * @param chore is set to the global variable.
     */
    public AssignedChore(String name, String chore) {
        mName = name;
        mChore = chore;
    }

    public String getmName() {
        return mName;
    }

    public String getmChore() {
        return mChore;
    }
}
