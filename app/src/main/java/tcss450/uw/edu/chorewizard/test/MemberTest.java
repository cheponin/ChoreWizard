package tcss450.uw.edu.chorewizard.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import tcss450.uw.edu.chorewizard.model.Member;

/**
 * The Member test class.
 * Created by chepovska_nina on 5/19/16.
 */
public class MemberTest extends TestCase {

    @Test
    public void TestConstructor() {
        Member member = new Member("John Doe", "1234567890");
        assertNotNull(member);
    }

    @Test
    public void testParseMemberJSON() {
        String memberJSON = "[{\"name\":\"John Doe\",\"phone\":\"1234567890\"},{\"name\":\"Jane Doe\",\"phone\":\"2345678901\"}]";
        String message =  Member.parseMemberJSON(memberJSON
                , new ArrayList<Member>());
        assertTrue("JSON With Valid String", message == null);
    }

    /**
     * The member variable.
     */
    private Member member;

    @Before
    public void setUp() {
        member = new Member("Jake Gyllenhaal", "3456789012");
    }

    @Test
    public void testSetNullMemberName() {
        try {
            member.setName(null);
            fail("Member name can be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testSetLengthMemberName() {
        try {
            member.setName("qwertyuiopasdfghjklzxcvbnm");
            fail("Member name can not be set to more than 25 characters long");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetName() {
        assertEquals("Jake Gyllenhaal", member.getName());
    }

    @Test
    public void testSetNullMemberPhone() {
        try {
            member.setPhone(null);
            fail("Member phone can be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testSetLengthMemberPhone() {
        try {
            member.setPhone("12345678900");
            fail("Member phone can not be set to more than 10 characters long");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetPhone() {
        assertEquals("3456789012", member.getPhone());
    }
}
