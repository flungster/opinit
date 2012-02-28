package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;
import play.db.jpa.Model;

/**
 * This is the model class representing the "boards" table stored in the database.
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "url_key"}))
public class Boards extends GenericModel {
    
    /*
     * Make sure to have a separate sequence generator - due to the fact we're using postgres
     */
    @Id
    @SequenceGenerator(name = "boards_id_generator", sequenceName = "boards_id_sequence", initialValue = 1)
    @GeneratedValue(generator = "boards_id_generator")
    public Long id;

    @Required
    @Column(nullable = false)
    public String title;
    
    /*
     * Validate the urlKey is unique before saving to the database
     */
    @Required
    @Column(name = "url_key", nullable = false)
    @CheckWith(UrlKeyCheck.class)
    public String urlKey;

    @Required
    @Column(name = "created_at", nullable = false)
    public Date createdAt;

    @Required
    @Column(name = "updated_at", nullable = false)
    public Date updatedAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Users user;

    /**
     * Default constructor for the Boards model class
     * @param user User object that will own the board
     * @param title Title of the board
     */
    public Boards(Users user, String title) {
	this.user = user;
	this.title = title;
	this.urlKey = constructUrlKey(title);
	this.createdAt = new Date();
	this.updatedAt = new Date();
    }

    /**
     * @param user user object to find
     * @return list of Boards the user has
     */
    public static List<Boards> findByUser(Users user) {
	return find("byUser", user).fetch();
    }

    public static Boards findByUsernameAndBoardId(@Required String username, @Required Long boardId) {
	return find("select b from Boards b, Users u where b.id = ? and b.user = u and u.username = ?", boardId, username).first();
    }

    public static Boards findByUsernameAndUrlKey(@Required String username, @Required String urlKey) {
	return find("select b from Boards b, Users u where b.urlKey = ? and b.user = u and u.username = ?", urlKey, username).first();
    }

    /**
     * @param title The board title is used to construct the url key. Punctuation marks and spaces are turned into '-'
     * @return the url key for the board
     */
    private String constructUrlKey(String title) {
	return title.toLowerCase().replaceAll("\\p{Punct}", "-").replaceAll(" ", "-");
    }

    static class UrlKeyCheck extends Check {
	@Override
	public boolean isSatisfied(Object boards, Object urlKey) {
	    Boards board = find("byUserAndUrlKey", ((Boards)boards).user, (String)urlKey).first();
	    return board == null;
	    //return true;
	}
    }
}