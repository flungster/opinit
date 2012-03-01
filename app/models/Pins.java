package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

/**
 * This is the model class representing the "pins" table stored in the database.
 */
@Entity
public class Pins extends GenericModel {
    
    /*
     * Make sure to have a separate sequence generator - due to the fact we're using postgres
     */
    @Id
    @SequenceGenerator(name = "pins_id_generator", sequenceName = "pins_id_sequence", initialValue = 1)
    @GeneratedValue(generator = "pins_id_generator")
    public Long id;
    public String url;
    public String description;
    public String image_url;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    public Users user;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Boards board;

    @ManyToOne
    public Pins parent_pin;

    @Required
    @Column(name = "created_at", nullable = false)
    public Date createdAt;

    @Required
    @Column(name = "updated_at", nullable = false)
    public Date updatedAt;

    public Pins(Users user, Boards board, String url, String description, String image_url, Pins parentPin) {
	this.user = user;
	this.board = board;
	this.url = url;
	this.description = description;
	this.image_url = image_url;
	this.parent_pin = parentPin;
	this.createdAt = new Date();
	this.updatedAt = new Date();
    }

    public Pins(Users user, Boards board, String url, String description, String image_url) {
	this(user, board, url, description, image_url, null);
    }

   
    public Pins(Users user, Boards board, Pins parentPin) {
	// We are repinning another pin
	this(user, board, parentPin.url, parentPin.description, parentPin.image_url, parentPin);
    }

    public static List<Pins> findByUsernameAndBoardUrlKey(@Required String username, @Required String boardUrlKey) {
	return find("select p from Pins p, Boards b, Users u where p.board = b and b.urlKey = ? and b.user = u and u.username = ?", boardUrlKey, username).fetch();
    }

    public static Pins findByUsernameAndId(String username, Long pinId) {
	return find("select p from Pins p, Users u where p.id = ? and p.user = u and u.username = ?", pinId, username).first();
    }
}