import org.junit.*;

import java.util.*;
import javax.persistence.*;

import models.*;

import play.test.*;


public class BoardTest extends UnitTest {

    @Before
    public void setup() {
	Fixtures.deleteDatabase();
    }
    
    @Test
    public void createBoard() {
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
	
	// Create a second board
	new Boards(joe, "Test Board Two").save();
	Boards joesSecondBoard = Boards.find("byTitle", "Test Board Two").first();

	// Test
	assertNotNull(joesSecondBoard);

	// Create a third board
	new Boards(joe, "Test Board Three").save();
	Boards joesThirdBoard = Boards.find("byTitle", "Test Board Three").first();
	
	// Test
	assertNotNull(joesThirdBoard);
    }

    @Test
    public void attemptToCreateDuplicateBoards() {
	Users bob = new Users("bobby", "bobby@gmail.com", "pass");
	bob.save();
	
	new Boards(bob, "My Dog").validateAndSave();
           
	new Boards(bob, "My Dog").validateAndSave();
       
    }
    
    @Test
    public void createMultipleUsersAndBoards() {
	Users sam = new Users("sam", "sam@gmail.com", "pass");
	sam.save();

	Users tod = new Users("tod", "tod@yahoo.com", "pass");
	tod.save();

	new Boards(sam, "My Dog").validateAndSave();
	new Boards(tod, "My Dog").validateAndSave();
	new Boards(sam, "My Dog 2").validateAndSave();
	new Boards(sam, "My Dog").validateAndSave();
	new Boards(sam, "Not My Dog").validateAndSave();
	new Boards(tod, "It's all good").validateAndSave();
    }
}
