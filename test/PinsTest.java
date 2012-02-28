import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class PinsTest extends UnitTest {

    @Before
    public void setup() {
	Fixtures.deleteDatabase();
    }

    @Test
    public void createPins()  {
	// Create a new user named Joe Smith and save it
	new Users("jsmith", "jsmith@gmail.com", "foobar").save();
	
	// Retrieve the record and make sure it's Joe
	Users joe = Users.find("byUsername", "jsmith").first();
	
	// Test
	assertNotNull(joe);
	assertEquals("jsmith", joe.username);
       
	// Create a new board
	new Boards(joe, "Test Board").save();

	// Retrieve the new board
	Boards joesBoard = Boards.find("byTitle", "Test Board").first();
	
	// Test
	assertNotNull(joesBoard);

	// Create a new pin
	new Pins(joe, joesBoard, "http://www.cnn.com", "CNN website", "some image url").save();

	// Retrieve the new pin
	Pins joesFirstPin = Pins.find("byDescription", "CNN website").first();
	
	// Test
	assertNotNull(joesFirstPin);

	// Repin the first pin
	new Pins(joe, joesBoard, joesFirstPin).save();

	// Retrieve the pins
	List<Pins> joesPins = Pins.find("byDescription", "CNN website").fetch();

	// Test
	assertEquals(joesPins.size(), 2);
    }
}