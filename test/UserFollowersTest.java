import java.util.*;
import models.*;
import org.junit.*;
import play.test.*;

public class UserFollowersTest extends UnitTest {
    @Before
    public void setup() {
	Fixtures.deleteDatabase();
    }

    @Test
    public void createUserFollowers() {
	// Create a new user named Joe Smith and save it
	Users joe = new Users("jsmith", "jsmith@gmail.com", "foobar").save();

	// Test joe
	assertNotNull(joe);

	// Create a new user named Roy Rogers and save it
	Users roy = new Users("rrogers", "rrogers@yahoo.com", "password").save();

	// Test roy
	assertNotNull(roy);

	// Create a new user follower
	new UserFollowers(joe, roy).save();

	// Retrieve it from the database 
	UserFollowers uf = UserFollowers.find("byUser", joe).first();

	// Test
	assertNotNull(uf);
	assertEquals(uf.follower.username, "rrogers");
    }
}
