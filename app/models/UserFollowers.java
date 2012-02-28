package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

/**
 * TBD
 */
@Entity(name = "user_followers")
public class UserFollowers extends GenericModel {

    /*
     * Make sure to have a separate sequence generator - due to the fact we're using postgres
     */
    @Id
    @SequenceGenerator(name = "user_followers_id_generator", sequenceName = "user_followers_id_sequence", initialValue = 1)
    @GeneratedValue(generator = "user_followers_id_generator")
    public Long id;

    @ManyToOne
    public Users user;
    
    @ManyToOne
    public Users follower;

    /**
     * TBD
     */
    public UserFollowers(Users user, Users follower) {
	this.user = user;
	this.follower = follower;
    }

    /**
     * TBD
     */
    public static List<UserFollowers> findForUser(Users user) {
	return find("byUser", user).fetch();
    }
}