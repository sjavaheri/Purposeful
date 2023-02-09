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

  public enum ReactionType {HighFive}

  // ------------------------
  // Reaction Attributes
  // ------------------------

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @Column(nullable = false)
  private ReactionType reactionType;

  private Date date;

  //private Time time;

  private String name;

  // ------------------------
  // Reaction Constructor
  // ------------------------

  public Reaction() {

  }

  // ------------------------
  // Getter/Setter Methods
  // ------------------------

  public String getId() {
    return id;
  }

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
}
