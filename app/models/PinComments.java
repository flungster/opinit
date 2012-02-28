package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

/**
 * This is the model class representing the "pincomments" table stored in the database
 */
@Entity(name = "pin_comments")
public class PinComments extends GenericModel {

    /*
     * Make sure to have a separate sequence generator - due to the fact we're using postgres
     */
    @Id
    @SequenceGenerator(name = "pin_comment_id_generator", sequenceName = "pin_comment_id_sequence", initialValue = 1)
    @GeneratedValue(generator = "pin_comment_id_generator")
    public Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Pins pin;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Users user;

    @Required
    @Column(name = "created_at", nullable = false)
    public Date createdAt;
    
    @Required
    @Column(name = "updated_at", nullable = false)
    public Date updatedAt;

}