package tcss450.uw.edu.chorewizard.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alice on 5/3/2016.
 */
public class Member implements Serializable {
    private static final long serialVersionUID = 0L;

    private String name;
    private String phone;

    public static final String NAME = "name", PHONE = "phone";

    public Member() {
    }

    public Member(final String name, final String phone) {
        this.name = name;
        this.phone = phone;
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

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param courseJSON
     * @return reason or null if successful.
     */
    public static String parseCourseJSON(String courseJSON, List<Member> memberList) {
        String reason = null;
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);

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

    public String toString() {
        return name + " " + phone;
    }

}
