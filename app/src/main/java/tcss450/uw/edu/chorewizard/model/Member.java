package tcss450.uw.edu.chorewizard.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * A class which represents a household member that will participate
 * in getting chores done.
 *
 * @version May 3, 2016
 */
public class Member implements Serializable {

    /** The household members name. */
    private String name;

    /** The household members phone number. */
    private String phone;

    /** Constants to hold the names of the class fields. */
    public static final String NAME = "name", PHONE = "phone";

    /** Serialization id. */
    private static final long serialVersionUID = 0L;

    /**
     * An empty class constructor.
     */
    public Member() {

    }

    /**
     * Constructor used to initialize the class fields.
     *
     * @param name the members name.
     * @param phone the members phone number.
     */
    public Member(final String name, final String phone) {
        this.name = name;
        this.phone = phone;
    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     *
     * @param memberJSON is the data file containing the members and their information.
     * @return reason or null if successful.
     */
    public static String parseCourseJSON(String memberJSON, List<Member> memberList) {
        String reason = null;
        if (memberJSON != null) {
            try {
                JSONArray arr = new JSONArray(memberJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Member member = new Member(obj.getString(Member.NAME), obj.getString(Member.PHONE));
                    memberList.add(member);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name + " " + phone;
    }

}
