import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class UserTest extends UnitTest {

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

    public void connectAsUser() {
	// Create a new user named Bob Barker and save it
	new Users("bBarker", "bbarker@gmail.com", "password").save();

	// Connect to the user
	Users bobBarker = Users.connect("bbarker", "password");
	assertNotNull(bobBarker);

	// Connect to the user but should fail
	Users notBobBarker = Users.connect("bbarker", "duh");
	assertNull(notBobBarker);
    }
}
