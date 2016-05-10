package tcss450.uw.edu.chorewizard.model;

/**
 * Created by alice on 5/10/2016.
 */
public class Chore {

    private String mChoreName;
    private String mChoreFrequency;

    public Chore(String mChoreName, String mChoreFrequency) {
        this.mChoreName = mChoreName;
        this.mChoreFrequency = mChoreFrequency;
    }

    public String getmChoreName() {
        return mChoreName;
    }

    public void setmChoreName(String mChoreName) {
        this.mChoreName = mChoreName;
    }

    public String getmChoreFrequency() {
        return mChoreFrequency;
    }

    public void setmChoreFrequency(String mChoreFrequency) {
        this.mChoreFrequency = mChoreFrequency;
    }

    public String toString() {
        return mChoreName + " " + mChoreFrequency;
    }
}
