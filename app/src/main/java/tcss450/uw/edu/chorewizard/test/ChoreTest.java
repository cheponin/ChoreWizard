package tcss450.uw.edu.chorewizard.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import tcss450.uw.edu.chorewizard.model.Chore;
import tcss450.uw.edu.chorewizard.model.Member;

/**
 * Created by chepovska_nina on 5/19/16.
 */
public class ChoreTest extends TestCase {

    @Test
    public void TestConstructor() {
        Chore chore = new Chore("Vacuum", "weekly");
        assertNotNull(chore);
    }

    @Test
    public void testParseChoreJSON() {
        String choreJSON = "[{\"name\":\"Vacuum\",\"frequency\":\"weekly\"},{\"name\":\"Mow\",\"frequency\":\"weekly\"}]";
        String message =  Chore.parseChoreJSON(choreJSON
                , new ArrayList<Chore>());
        assertTrue("JSON With Valid String", message == null);
    }

    private Chore chore;

    @Before
    public void setUp() {
        chore = new Chore("Dishes", "daily");
    }

    @Test
    public void testSetNullChoreName() {
        try {
            chore.setmChoreName(null);
            fail("Chore name can be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testSetLengthChoreName() {
        try {
            chore.setmChoreName("qwertyuiopasdfghjklzxcvbnm");
            fail("Chore name can not be set to more than 25 characters long");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetName() {
        assertEquals("Dishes", chore.getmChoreName());
    }

    @Test
    public void testSetNullChoreFrquency() {
        try {
            chore.setmChoreFrequency(null);
            fail("Chore name can not be set to null");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testSetLengthChoreFrequency() {
        try {
            chore.setmChoreFrequency("qwertyuiopasdfghjklzxcvbnm");
            fail("Chore frequency can not be set to more than 25 characters long");
        }
        catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testGetFrequency() {
        assertEquals("daily", chore.getmChoreFrequency());
    }
}
