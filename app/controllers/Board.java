package controllers;

import play.*;
import play.data.validation.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Board extends Controller {

    public static void show(String username, String urlKey) {
	// Look up the board via urlkey for username
	Boards board = Boards.findByUsernameAndUrlKey(username, urlKey);
	
	List<Pins> pins = Pins.findByUsernameAndBoardUrlKey(username, urlKey);

	render(username, board, pins);
    }

    /**
     * Renders the form to create a board
     */
    public static void create(String username) {
	render(username);
    }

    /**
     * Creates the board in the database
     */
    public static void createBoard(String username, @Required String title) {
	if (validation.hasErrors()) {
	    validation.keep();
	    create(username);
	}

	// TBD - we'll need to make sure the user is logged in

	// Look up the user
	Users user = Users.find("byUsername", username).first();
	
	// Create the board and save it
	Boards board = new Boards(user, title).save();
	
	show(user.username, board.urlKey);
    }
}