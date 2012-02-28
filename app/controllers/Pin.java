package controllers;

import play.*;
import play.data.validation.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Pin extends Controller {
    
    /**
     * A request to create a new pin
     */
    public static void create(String username) {
	Users user = Users.findByUsername(username);
	notFoundIfNull(user);
	
	List<Boards> boards = Boards.findByUsername(username);	
	
	render(username, boards);
    }

    /**
     * Method actually creates the pin
     */
    public static void createPin(@Required String username, @Required String boardUrlKey, @Required String url, String description, @Required String image_url) {
	if (validation.hasErrors()) {
	    validation.keep();
	    create(username);
	}
	
	Users user = Users.findByUsername(username);
	notFoundIfNull(user);

	Boards board = Boards.findByUsernameAndUrlKey(username, boardUrlKey);
	notFoundIfNull(board);


	new Pins(user, board, url, description, image_url, null).save();
	Board.show(username, boardUrlKey);
	
    }

    /**
     * A request to delete an existing pin
     */
    public static void delete() {

    }

    /**
     * A request to update an existing pin
     */
    public static void update() {

    }

    /**
     * A request to show a pin
     */
    public static void show() {

    }
 }