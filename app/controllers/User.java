package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class User extends Controller {

    public static void show(String username) {
	// find the user and then render their page
	Users user = Users.findByUsername(username);
	List<Boards> boards = null;
	if (user != null) {
	    // find the boards belonging to this user
	    boards = Boards.findByUser(user);
	}
	notFoundIfNull(user);
	render(user, boards);
    }

}