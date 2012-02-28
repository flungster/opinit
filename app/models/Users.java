package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

/**
 * This is the odel class representing the "users" table stored in the database
 */
@Entity
public class Users extends GenericModel {

    @Id
    @SequenceGenerator(name = "users_id_generator", sequenceName = "users_id_sequence", initialValue = 1)
    @GeneratedValue(generator = "users_id_generator")
    public Long id;

    /*
     * TBD - need to make this unique
     */
    @Required
    @Column(nullable = false)
    public String username;
    
    /*
     * TBD - need to make this unique
     */
    @Required
    @Column(nullable = false)
    public String email;

    @Required
    @Column(nullable = false)
    public String password;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "middle_name")
    public String middleName;

    @Column(name = "last_name")
    public String lastName;
   
    @Required
    @Column(name = "created_at", nullable = false)
    public Date createdAt;

    @Required
    @Column(name = "updated_at", nullable = false)
    public Date updatedAt;

    @Column(name = "last_login")
    public Date lastLogin;

    public Users(String username, String email, String password) {
	this.username = username;
	this.email = email;
	this.password = password;       
	this.createdAt = new Date();
	this.updatedAt = new Date();
    }
    
    public static Users connect(String username, String password) {
	return connect(username, null, password);
    }

    public static Users connect(String username, String email, String password) {
	if (null == username) {
	    return find("byEmailAndPassword", email, password).first();
	} else {
	    return find("byUsernameAndPassword", username, password).first();
	}
    }

    public static Users findByUsername(String username) {
	return find("byUsername", username).first();
    }

}