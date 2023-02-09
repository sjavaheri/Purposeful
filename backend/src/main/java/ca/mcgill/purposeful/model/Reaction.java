package ca.mcgill.purposeful.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Date;
//import java.sql.Date;
//import java.sql.Time;

/**
 * The Technology class, the model for all Technologies in the database
 */
@Entity
public class Reaction {

  // ------------------------
  // Reaction Associations
  // ------------------------

  @ManyToOne(optional = true)
  private Idea idea;

  // ------------------------
  // Enumerations
  // ------------------------
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  // ------------------------
  // Reaction Attributes
  // ------------------------
  @Column(nullable = false)
  private ReactionType reactionType;
  private Date date;
  private String name;

  // private Time time;

  public Reaction() {

  }

  // ------------------------
  // Reaction Constructor
  // ------------------------

  public String getId() {
    return id;
  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  public void setId(String id) {
    this.id = id;
  }

  public ReactionType getReactionType() {
    return reactionType;
  }

  public void setReactionType(ReactionType reactionType) {
    this.reactionType = reactionType;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public enum ReactionType {
    HighFive
  }
}
