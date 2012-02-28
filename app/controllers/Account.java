package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Account extends Controller {

    public static void registerUser(@Required String username, @Required String email, @Required String password) {
	if (validation.hasErrors()) {
	    register();
	}

	Users user = new Users(username, email, password).save();
	User.show(username);
    }

    public static void register() {
	render();
    }

    public static void login() {

    }

    public static void logout() {

    }
}