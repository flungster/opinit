import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Before
    public void setup() {
	Fixtures.deleteDatabase();
    }

    @Test
    public void createUser() {
	// Create a new user named Joe Smith and save it
	new Users("jsmith", "jsmith@gmail.com", "foobar").save();
	
	// Retrieve the record and make sure it's Joe
	Users joe = Users.find("byUsername", "jsmith").first();
	
	// Test
	assertNotNull(joe);
	assertEquals("jsmith", joe.username);
       
    }
}
