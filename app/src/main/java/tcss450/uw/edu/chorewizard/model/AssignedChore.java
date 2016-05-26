package tcss450.uw.edu.chorewizard.model;

/**
 * Created by chepovska_nina on 5/26/16.
 */
public class AssignedChore {

    private String mName;
    private String mChore;

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
